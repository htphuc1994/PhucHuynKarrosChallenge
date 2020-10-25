package com.karros.gpx.controllers;

import com.karros.gpx.dtos.*;
import com.karros.gpx.services.KarrosGpxService;
import com.karros.gpx.utils.KarrosUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Transactional
public class GpxController {

    private static final Logger logger = LoggerFactory.getLogger(GpxController.class);

    @Autowired
    private KarrosGpxService karrosGpxService;

    @PostMapping("/api/v1/upload_gpx_file")
    public Response uploadFile(@RequestParam(name = "gpx_file") MultipartFile gpxFile) {
        Response response;
        try {
            response = karrosGpxService.handleUploadedGpxFile(gpxFile);
        } catch (Exception ex) {
            response = KarrosUtils.getBadRequest(ex.getMessage());
        }

        return response;
    }

    @PostMapping("/api/v1/get_latest_track")
    public Response getLatestTrack() {
        Response response;
        try {
            response = karrosGpxService.getLatestTrack();
        } catch (Exception ex) {
            response = KarrosUtils.getBadRequest(ex.getMessage());
        }

        return response;
    }

    @PostMapping("/api/v1/get_gpx_info")
    public Response getGpxInfo(@Validated @RequestBody GetGpxRequest request) {
        Response response;
        try {
            response = karrosGpxService.getGpxInfo(request.getGpxId());
        } catch (Exception ex) {
            response = KarrosUtils.getBadRequest(ex.getMessage());
        }

        return response;
    }

}

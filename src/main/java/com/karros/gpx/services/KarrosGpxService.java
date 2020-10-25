package com.karros.gpx.services;

import com.karros.gpx.dtos.GetGpxResponse;
import com.karros.gpx.dtos.GetLatestTrackResponse;
import com.karros.gpx.dtos.UploadFileResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface KarrosGpxService {
    UploadFileResponse handleUploadedGpxFile(MultipartFile gpxFile) throws IOException;

    GetLatestTrackResponse getLatestTrack() throws Exception;

    GetGpxResponse getGpxInfo(Long gpxId) throws Exception;
}

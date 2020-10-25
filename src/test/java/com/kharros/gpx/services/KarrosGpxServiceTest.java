package com.kharros.gpx.services;

import com.karros.gpx.dtos.GetGpxResponse;
import com.karros.gpx.dtos.GetLatestTrackResponse;
import com.karros.gpx.dtos.UploadFileResponse;
import com.karros.gpx.services.KarrosGpxService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.kharros.gpx.utils.TestConstants.*;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@SpringBootApplication
@AutoConfigureMockMvc
public class KarrosGpxServiceTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private KarrosGpxService karrosGpxService;

    @Test
    public void handleUploadedGpxFile_correctGpxFileGiven_shouldStoreDb() throws Exception {
        MockMultipartFile gpxFile = new MockMultipartFile("data",
                                                          CORRECT_GPX_UPLOAD_FILE_NAME,
                                                          CORRECT_GPX_UPLOAD_FILE_TYPE,
                                                          CORRECT_GPX_DATA.getBytes());
        UploadFileResponse response = karrosGpxService.handleUploadedGpxFile(gpxFile);

        assertEquals(HttpStatus.OK.value(), response.getCode());
        assertEquals(CORRECT_GPX_UPLOAD_FILE_NAME, response.getFileName());
    }

    @Test
    public void handleUploadedGpxFile_incorrectGpxFileGiven_shouldReturnBadRequest() throws Exception {
        MockMultipartFile gpxFile = new MockMultipartFile("json",
                                                          CORRECT_GPX_UPLOAD_FILE_NAME,
                                                          INCORRECT_GPX_UPLOAD_FILE_TYPE,
                                                          "{\\\"json\\\": \\\"someValue\\\"}".getBytes());
        UploadFileResponse response = karrosGpxService.handleUploadedGpxFile(gpxFile);

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getCode());
    }

    @Test
    public void getLatestTrack_Given_shouldReturnLatestTrack() throws Exception {
        GetLatestTrackResponse response = karrosGpxService.getLatestTrack();

        assertEquals(HttpStatus.OK.value(), response.getCode());

        // can include time in the latest tract then insert and check it
    }

    @Test
    public void getGpxInfo_GpxIdGiven_shouldReturnItsGpxInfo() throws Exception {
        Long givenGpxId = 1L;
        GetGpxResponse response = karrosGpxService.getGpxInfo(givenGpxId);

        assertEquals(HttpStatus.OK.value(), response.getCode());
        assertEquals(givenGpxId.toString(), response.getGpx().getId().toString());
    }

    @Test
    public void getGpxInfo_Given_shouldReturnBadRequestMissingId() throws Exception {
        Long givenGpxId = 1L;
        GetGpxResponse response = karrosGpxService.getGpxInfo(givenGpxId);

        assertEquals(HttpStatus.OK.value(), response.getCode());
        assertEquals(givenGpxId.toString(), response.getGpx().getId().toString());
    }

}

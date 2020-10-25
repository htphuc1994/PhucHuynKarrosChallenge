package com.kharros.gpx.controllers;

import com.karros.gpx.dtos.GetGpxRequest;
import com.karros.gpx.dtos.GetGpxResponse;
import com.karros.gpx.dtos.GetLatestTrackResponse;
import com.karros.gpx.dtos.UploadFileResponse;
import com.karros.gpx.services.KarrosGpxService;
import com.karros.gpx.utils.AMObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.karros.gpx.utils.JsonHelper.toJson;
import static com.kharros.gpx.utils.TestConstants.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@SpringBootApplication
@AutoConfigureMockMvc
public class GpxControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private KarrosGpxService karrosGpxService;

    @Test
    public void getGpxInfo_MissingIdGiven_shouldReturnBadRequest() throws Exception {
        GetGpxRequest missingIdReq = new GetGpxRequest();
        ResultActions resultActions = mvc.perform(post("/api/v1/get_gpx_info")
                                                          .contentType(MediaType.APPLICATION_JSON)
                                                          .content(toJson(
                                                                  missingIdReq)))
                                         .andDo(print())
                                         .andExpect(status().is4xxClientError());

        String contentAsString = resultActions.andReturn().getResponse().getContentAsString();

        System.out.println("contentAsString:" + contentAsString);

        GetGpxResponse result = AMObjectMapper.toObject(contentAsString, GetGpxResponse.class);
        System.out.println(result);
    }

    @Test
    public void uploadFile_multipartFileGiven_shouldReturnSuccess() throws Exception {
        MockMultipartFile gpxFile = new MockMultipartFile("data",
                                                          CORRECT_GPX_UPLOAD_FILE_NAME,
                                                          CORRECT_GPX_UPLOAD_FILE_TYPE,
                                                          CORRECT_GPX_DATA.getBytes());
        ResultActions resultActions = mvc.perform(post("/api/v1/upload_gpx_file")
                                                          .contentType(MediaType.MULTIPART_FORM_DATA)
                                                          .content(gpxFile.getBytes()))
                                         .andDo(print())
                                         .andExpect(status().isOk());

        String contentAsString = resultActions.andReturn().getResponse().getContentAsString();

        System.out.println("contentAsString:" + contentAsString);

        UploadFileResponse result = AMObjectMapper.toObject(contentAsString, UploadFileResponse.class);
        System.out.println(result);
    }

    @Test
    public void getLatestTrack_Given_shouldReturnSuccess() throws Exception {
        ResultActions resultActions = mvc.perform(post("/api/v1/get_latest_track")
                                                          .contentType(MediaType.APPLICATION_JSON)
                                                          .content(toJson("")))
                                         .andDo(print())
                                         .andExpect(status().isOk());

        String contentAsString = resultActions.andReturn().getResponse().getContentAsString();

        System.out.println("contentAsString:" + contentAsString);

        GetLatestTrackResponse result = AMObjectMapper.toObject(contentAsString, GetLatestTrackResponse.class);
        System.out.println(result);
    }

}

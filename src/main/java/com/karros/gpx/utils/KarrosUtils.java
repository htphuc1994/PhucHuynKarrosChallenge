package com.karros.gpx.utils;

import com.karros.gpx.dtos.GetGpxResponse;
import com.karros.gpx.dtos.Response;
import org.springframework.http.HttpStatus;

import static com.karros.gpx.utils.GpxConstants.ApiMessage.FAILED;

public class KarrosUtils {

    public static Response getBadRequest(String message) {
        Response response = new GetGpxResponse();
        response.setCode(HttpStatus.BAD_REQUEST.value());
        response.setMessage(FAILED.getValue() + ": " + message);

        return response;
    }
}

package com.karros.gpx.dtos;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.karros.gpx.models.Gpx;
import com.karros.gpx.models.Track;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GetGpxResponse extends Response {
    private GpxDto gpx;

    public GetGpxResponse() {

    }

    public GpxDto getGpx() {
        return gpx;
    }

    public void setGpx(GpxDto gpx) {
        this.gpx = gpx;
    }
}

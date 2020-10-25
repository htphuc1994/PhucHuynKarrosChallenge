package com.karros.gpx.dtos;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import javax.validation.constraints.NotNull;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GetGpxRequest {
    @NotNull
    private Long gpxId;
    public  GetGpxRequest() {}

    public Long getGpxId() {
        return gpxId;
    }

    public void setGpxId(Long gpxId) {
        this.gpxId = gpxId;
    }
}

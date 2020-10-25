package com.karros.gpx.dtos;

import java.util.ArrayList;
import java.util.List;

public class TrackDto {

	public TrackDto() {
	}

	private Long id;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

	private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

	private String description;
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    private GpxDto gpx;
    public GpxDto getGpx() {
        return gpx;
    }
    public void setGpx(GpxDto gpx) {
        this.gpx = gpx;
    }

    private List<SegmentDto> segments = new ArrayList<>();
    public List<SegmentDto> getSegments() {
        return segments;
    }
    public void setSegments(List<SegmentDto> segments) {
        this.segments = segments;
    }

	private java.sql.Timestamp createdAt;
    public java.sql.Timestamp getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(java.sql.Timestamp createdAt) {
        this.createdAt = createdAt;
    }

	private java.sql.Timestamp updatedAt;
    public java.sql.Timestamp getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(java.sql.Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}

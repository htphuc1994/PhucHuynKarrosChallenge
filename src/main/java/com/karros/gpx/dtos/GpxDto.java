package com.karros.gpx.dtos;

import java.util.ArrayList;
import java.util.List;

public class GpxDto {

	public GpxDto() {
	}

	private Long id;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

	private String creator;
    public String getCreator() {
        return creator;
    }
    public void setCreator(String creator) {
        this.creator = creator;
    }

	private String version;
    public String getVersion() {
        return version;
    }
    public void setVersion(String version) {
        this.version = version;
    }

    private String symbol;
    public String getSymbol() {
        return symbol;
    }
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    private MetadataDto metadata;
    public MetadataDto getMetadata() {
        return metadata;
    }
    public void setMetadata(MetadataDto metadata) {
        this.metadata = metadata;
    }

    private List<TrackDto> tracks = new ArrayList<>();
    public List<TrackDto> getTracks() {
        return tracks;
    }
    public void setTracks(List<TrackDto> tracks) {
        this.tracks = tracks;
    }

    private List<WayPointDto> wayPoints = new ArrayList<>();
    public List<WayPointDto> getWayPoints() {
        return wayPoints;
    }
    public void setWayPoints(List<WayPointDto> wayPoints) {
        this.wayPoints = wayPoints;
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

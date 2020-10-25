package com.karros.gpx.dtos;

import java.util.ArrayList;
import java.util.List;

public class SegmentDto {

	public SegmentDto() {
	}

	private Long id;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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

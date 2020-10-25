package com.karros.gpx.models;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * A class which represents the way_point table in the gpx_access database.
 */
@javax.persistence.Entity
@Table(name = "way_point")
public class WayPoint {

	public WayPoint() {
	}

    @Id
	@GeneratedValue(strategy = IDENTITY)
    @Column(name="id")
	private Long id;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @Column(name="latitude", length = 2048, columnDefinition = "varchar")
	private String latitude;
    public String getLatitude() {
        return latitude;
    }
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @Column(name="longitude", length = 2048, columnDefinition = "varchar")
	private String longitude;
    public String getLongitude() {
        return longitude;
    }
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Column(name="name", length = 2048, columnDefinition = "varchar")
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Column(name="symbol", length = 2048, columnDefinition = "varchar")
    private String symbol;
    public String getSymbol() {
        return symbol;
    }
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @ManyToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinTable(name = "gpx_way_point_aff",
            joinColumns = @JoinColumn(name = "way_point_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "gpx_id", referencedColumnName = "id"))
    private List<Gpx> gpxs = new ArrayList<>();
    public List<Gpx> getGpxs() {
        return gpxs;
    }
    public void setGpxs(List<Gpx> gpxs) {
        this.gpxs = gpxs;
    }

    @ManyToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinTable(name = "segment_way_point_aff",
            joinColumns = @JoinColumn(name = "way_point_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "segment_id", referencedColumnName = "id"))
    private List<Segment> segments = new ArrayList<>();
    public List<Segment> getSegments() {
        return segments;
    }
    public void setSegments(List<Segment> segments) {
        this.segments = segments;
    }

    @Column(name="created_at")
    @CreationTimestamp
	private java.sql.Timestamp createdAt;
    public java.sql.Timestamp getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(java.sql.Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Column(name="updated_at")
    @UpdateTimestamp
	private java.sql.Timestamp updatedAt;
    public java.sql.Timestamp getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(java.sql.Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}

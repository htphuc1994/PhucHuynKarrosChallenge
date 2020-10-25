package com.karros.gpx.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * A class which represents the way_point table in the gpx_access database.
 */
@Entity
@Table(name = "gpx")
public class Gpx {

	public Gpx() {
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

    @Column(name="creator", length = 2048, columnDefinition = "varchar")
	private String creator;
    public String getCreator() {
        return creator;
    }
    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Column(name="version", length = 2048, columnDefinition = "varchar")
	private String version;
    public String getVersion() {
        return version;
    }
    public void setVersion(String version) {
        this.version = version;
    }

    @Column(name="symbol", length = 2048, columnDefinition = "varchar")
    private String symbol;
    public String getSymbol() {
        return symbol;
    }
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "gpx", cascade=CascadeType.ALL)
    private Metadata metadata;
    public Metadata getMetadata() {
        return metadata;
    }
    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "gpx", cascade=CascadeType.ALL)
    private List<Track> tracks = new ArrayList<>();
    public List<Track> getTracks() {
        return tracks;
    }
    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "gpxs", cascade=CascadeType.ALL)
    private List<WayPoint> wayPoints = new ArrayList<>();
    public List<WayPoint> getWayPoints() {
        return wayPoints;
    }
    public void setWayPoints(List<WayPoint> wayPoints) {
        this.wayPoints = wayPoints;
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

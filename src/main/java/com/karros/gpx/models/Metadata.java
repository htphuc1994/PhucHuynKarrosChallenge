package com.karros.gpx.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * A class which represents the metadata table in the gpx_access database.
 */
@Entity
@Table(name = "metadata")
public class Metadata {

	public Metadata() {
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

    @Column(name="name", length = 2048, columnDefinition = "varchar")
	private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Column(name="longitude", length = 2048, columnDefinition = "varchar")
	private String longitude;
    public String getLongitude() {
        return longitude;
    }
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }


    @Column(name="description", length = 4000, columnDefinition = "varchar")
    private String description;
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name="author", length = 2048, columnDefinition = "varchar")
    private String author;
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }

    @Column(name="symbol", length = 2048, columnDefinition = "varchar")
    private String symbol;
    public String getSymbol() {
        return symbol;
    }
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Column(name="time", length = 2048, columnDefinition = "varchar")
    private String time;
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }

    @OneToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "gpx_id", insertable = false, updatable = false)
    private Gpx gpx;
    public Gpx getGpx() {
        return gpx;
    }
    public void setGpx(Gpx gpx) {
        this.gpx = gpx;
    }

    @JsonBackReference
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "metadataLink", fetch = FetchType.LAZY)
    private List<Link> metadataLinks = new ArrayList<>();
    public List<Link> getMetadataLinks() {
        return metadataLinks;
    }
    public void setMetadataLinks(List<Link> metadataLinks) {
        this.metadataLinks = metadataLinks;
    }

    @Column(name="created_at")
    @CreationTimestamp
	private java.sql.Timestamp createdAt;
    public static final String CreatedAtColumn = "created_at";
    public java.sql.Timestamp getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(java.sql.Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Column(name="updated_at")
    @UpdateTimestamp
	private java.sql.Timestamp updatedAt;
    public static final String UpdatedAtColumn = "updated_at";
    public java.sql.Timestamp getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(java.sql.Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }


}

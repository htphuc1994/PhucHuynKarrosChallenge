package com.karros.gpx.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * A class which represents the link table in the gpx_access database.
 */
@Entity
@Table(name = "link")
public class Link {

	public Link() {
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

    @Column(name="href", length = 4000, columnDefinition = "varchar")
	private String href;
    public String getHref() {
        return href;
    }
    public void setHref(String href) {
        this.href = href;
    }

    @Column(name="text", length = 4000, columnDefinition = "varchar")
    private String text;
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
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

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "metadata_id", insertable = false, updatable = false)
    private Link metadataLink;
    public Link getMetadataLink() {
        return metadataLink;
    }
    public void setMetadataLink(Link metadataLink) {
        this.metadataLink = metadataLink;
    }

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "track_id", insertable = false, updatable = false)
    private Link trackLink;
    public Link getTrackLink() {
        return trackLink;
    }
    public void setTrackLink(Link trackLink) {
        this.trackLink = trackLink;
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

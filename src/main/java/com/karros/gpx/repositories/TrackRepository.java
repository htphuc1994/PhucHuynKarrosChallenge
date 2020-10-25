package com.karros.gpx.repositories;

import com.karros.gpx.models.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackRepository extends JpaRepository<Track, Long> {
    Track findFirstByOrderByIdDesc();
}

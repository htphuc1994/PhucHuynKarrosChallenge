package com.karros.gpx.repositories;

import com.karros.gpx.models.Gpx;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GpxRepository extends JpaRepository<Gpx, Long> {

}

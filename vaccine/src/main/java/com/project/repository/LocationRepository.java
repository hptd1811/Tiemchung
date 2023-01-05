package com.project.repository;

import com.project.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location,Integer> {

    @Query(
            value = "SELECT * FROM location;",
            nativeQuery = true)
    List<Location> findAllLocation();

}

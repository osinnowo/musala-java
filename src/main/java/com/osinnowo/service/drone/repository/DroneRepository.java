package com.osinnowo.service.drone.repository;

import com.osinnowo.service.drone.models.entity.DroneEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DroneRepository extends JpaRepository<DroneEntity, Long> {
    @Query("SELECT d FROM DroneEntity d WHERE d.isSoftDeleted = false AND (d.state = 'IDLE' OR d.state = 'LOADING')")
    List<DroneEntity> findAllAvailableDrones();
}

package com.osinnowo.service.drone.repository;

import com.osinnowo.service.drone.models.entity.MedicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicationRepository extends JpaRepository<MedicationEntity, Long> {
    Optional<MedicationEntity> findByCode(String code);
}

package com.osinnowo.service.drone.service.drone;

import com.osinnowo.service.drone.models.dto.DroneDto;
import com.osinnowo.service.drone.models.entity.DroneEntity;
import com.osinnowo.service.drone.models.entity.MedicationEntity;
import com.osinnowo.service.drone.models.request.DroneRequest;
import com.osinnowo.service.drone.models.request.MedicationRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface DroneService {
    Flux<DroneDto> getAllAvailableDrones();
    void loadNewDroneWithMedications(DroneEntity droneEntity, List<MedicationEntity> medicationEntity);
    void loadDroneWithMedications(Long id, List<MedicationEntity> medicationEntities);
    void loadDroneWithMedication(Long id, List<MedicationRequest> medicationEntities);
    void saveDrones(List<DroneEntity> drones);
    Mono<DroneDto> getDroneById(Long id);
    Mono<DroneDto> saveDrone(DroneEntity droneEntity);
    Mono<DroneDto> createDrone(DroneRequest droneRequest);
    Mono<DroneDto> loadDrone(Long id, MedicationRequest request);
    Mono<DroneDto> loadDrone(Long droneId, Long medicationId);
    Mono<DroneDto> setDroneStatus(Long droneId, String status);
}

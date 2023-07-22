package com.osinnowo.service.drone.service.drone;

import com.osinnowo.service.drone.configuration.AppConfig;
import com.osinnowo.service.drone.exception.*;
import com.osinnowo.service.drone.models.common.WeightCalculator;
import com.osinnowo.service.drone.models.dto.DroneDto;
import com.osinnowo.service.drone.models.entity.DroneEntity;
import com.osinnowo.service.drone.models.entity.DroneState;
import com.osinnowo.service.drone.models.entity.MedicationEntity;
import com.osinnowo.service.drone.models.mapper.DroneMapper;
import com.osinnowo.service.drone.models.mapper.MedicationMapper;
import com.osinnowo.service.drone.models.request.DroneRequest;
import com.osinnowo.service.drone.models.request.MedicationRequest;
import com.osinnowo.service.drone.repository.DroneRepository;
import com.osinnowo.service.drone.repository.MedicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DroneServiceImpl implements DroneService {

    private final DroneRepository droneRepository;
    private final MedicationRepository medicationRepository;

    private final AppConfig appConfig;

    public Flux<DroneDto> getAllAvailableDrones() {
        return Flux.fromIterable(
            droneRepository.
                    findAllAvailableDrones()
                    .stream()
                    .map(DroneMapper::fromEntity)
                    .toList()
        );
    }

    @Override
    public void loadNewDroneWithMedications(DroneEntity drone, List<MedicationEntity> medications) {
        double totalMedicationWeight = WeightCalculator.calculate(medications);

        if (drone.getState() != DroneState.IDLE && drone.getState() != DroneState.LOADING) {
            throw new DroneNotAvailableException(String.format("Drone with %s is not available", drone.getSerialNumber()));
        }

        if(totalMedicationWeight > appConfig.getWeightLimit()) {
            throw new WeightLimitExceededException(
                    String.format("Total weight sum %f grams exceeded drone's weight limit quota", totalMedicationWeight)
            );
        }

        if(drone.getBatteryCapacity() <= 25) {
            throw new DroneBatteryLowException("Drone battery power is low");
        }

        drone.setWeightLimit(drone.getWeightLimit() - Math.ceil(totalMedicationWeight)); drone.setMedications(medications);
        medications.forEach(medication -> medication.setDrone(drone));
        drone.setState(DroneState.LOADING);

        if(drone.getWeightLimit() == 0) { drone.setState(DroneState.READY); }

        DroneEntity savedEntity = droneRepository.saveAndFlush(drone);
        Mono.just(DroneMapper.fromEntity(savedEntity));
    }

    @Override
    public void loadDroneWithMedications(Long id, List<MedicationEntity> medications) {
        DroneEntity drone = droneRepository.findById(id).orElseThrow(() -> new NotFoundException("Cannot find drone with " + id));
        double totalMedicationWeight = WeightCalculator.calculate(medications);

        if (drone.getState() != DroneState.IDLE && drone.getState() != DroneState.LOADING) {
            throw new DroneNotAvailableException(String.format("Drone with %s is not available", drone.getSerialNumber()));
        }

        if(totalMedicationWeight > drone.getWeightLimit()) {
            throw new WeightLimitExceededException(
                    String.format("Total weight sum %f grams exceeded drone's weight limit quota", totalMedicationWeight)
            );
        }

        if(drone.getBatteryCapacity() <= 25) {
            throw new DroneBatteryLowException("Drone battery power is low");
        }

        drone.setWeightLimit(drone.getWeightLimit() - Math.ceil(totalMedicationWeight));
        drone.setMedications(medications);
        medications.forEach(medication -> medication.setDrone(drone));

        drone.setState(DroneState.LOADING);
        if(drone.getWeightLimit() == 0) { drone.setState(DroneState.READY); }

        droneRepository.saveAndFlush(drone);
    }

    @Override
    public void loadDroneWithMedication(Long id, List<MedicationRequest> medicationEntities) {
        List<MedicationEntity> medications = medicationEntities.stream().map(MedicationMapper::fromRequest).toList();
        loadDroneWithMedications(id, medications);
    }

    @Override
    public Mono<DroneDto> saveDrone(DroneEntity droneEntity) {
        DroneEntity savedEntity = droneRepository.saveAndFlush(droneEntity);
        return Mono.just(DroneMapper.fromEntity(savedEntity));
    }

    @Override
    public Mono<DroneDto> createDrone(DroneRequest droneRequest) {

        if(droneRequest.getWeightLimit() > appConfig.getWeightLimit()) {
            throw new WeightLimitExceededException(String.format("Weight limit %f grams exceeded quota for drone", droneRequest.getWeightLimit()));
        }

        DroneEntity entity = DroneMapper.fromRequest(droneRequest);
        entity.setState(DroneState.IDLE);
        DroneEntity savedEntity = droneRepository.saveAndFlush(entity);
        return Mono.just(DroneMapper.fromEntity(savedEntity));
    }


    @Override
    public Mono<DroneDto> getDroneById(Long droneId){
        DroneEntity drone = droneRepository.findById(droneId).orElseThrow(() -> new NotFoundException("Cannot find drone with " + droneId));
        return Mono.just(DroneMapper.fromEntity(drone));
    }

    @Override
    public Mono<DroneDto> loadDrone(Long id, MedicationRequest request) {
        DroneEntity drone = droneRepository.findById(id).orElseThrow(() -> new NotFoundException("Cannot find drone with " + id));

        if(medicationRepository.findByCode(request.getCode()).isPresent()) {
            throw new DuplicateItemException(String.format("Medication with code %s already exists", request.getCode()));
        }

        if (drone.getState() != DroneState.IDLE && drone.getState() != DroneState.LOADING) {
            throw new DroneNotAvailableException(String.format("Drone with %s is not available", drone.getSerialNumber()));
        }

        if(request.getWeight() > drone.getWeightLimit()) {
            throw new WeightLimitExceededException(String.format("Weight limit %f grams exceeded quota for drone", drone.getWeightLimit()));
        }

        if(drone.getBatteryCapacity() <= 25) {
            throw new DroneBatteryLowException("Drone battery power is low");
        }

        MedicationEntity medication = MedicationMapper.fromRequest(request);
        medication.setDrone(drone); drone.getMedications().add(medication);
        drone.setState(DroneState.LOADING);

        DroneEntity savedEntity = droneRepository.saveAndFlush(drone);
        return Mono.just(DroneMapper.fromEntity(savedEntity));
    }

    @Override
    public Mono<DroneDto> loadDrone(Long droneId, Long medicationId) {
        DroneEntity drone = droneRepository.findById(droneId).orElseThrow(() -> new NotFoundException("Cannot find drone with " + droneId));
        MedicationEntity medication = medicationRepository.findById(medicationId).orElseThrow(() -> new NotFoundException("Cannot find medication with " + medicationId));

        if(drone.getMedications().contains(medication)) {
            throw new DuplicateItemException(String.format("Medication with %d already assigned to drone", medicationId));
        }

        if (drone.getState() != DroneState.IDLE && drone.getState() != DroneState.LOADING) {
            throw new DroneNotAvailableException(String.format("Drone with %s is not available", drone.getSerialNumber()));
        }

        if(medication.getWeight() > drone.getWeightLimit()) {
            throw new WeightLimitExceededException(String.format("Weight limit %f grams exceeded quota for drone", drone.getWeightLimit()));
        }

        if(drone.getBatteryCapacity() <= 25) {
            throw new DroneBatteryLowException("Drone battery power is low");
        }

        DroneEntity formerDrone = medication.getDrone();
        formerDrone.setWeightLimit(formerDrone.getWeightLimit() + medication.getWeight());
        formerDrone.getMedications().remove(medication);

        if(formerDrone.getMedications().isEmpty()) {
            formerDrone.setState(DroneState.IDLE);
            formerDrone.setWeightLimit(appConfig.getWeightLimit());
        }

        droneRepository.saveAndFlush(formerDrone);

        drone.setWeightLimit(drone.getWeightLimit() - Math.ceil(medication.getWeight()));
        medication.setDrone(drone); drone.getMedications().add(medication);
        drone.setState(DroneState.LOADING);

        droneRepository.saveAndFlush(drone);
        return Mono.just(DroneMapper.fromEntity(drone));
    }

    @Override
    public Mono<DroneDto> setDroneStatus(Long droneId, String status) {
        DroneEntity drone = droneRepository.findById(droneId).orElseThrow(() -> new NotFoundException("Cannot find drone with " + droneId));
        drone.setState(DroneState.valueOf(status.toUpperCase()));
        droneRepository.saveAndFlush(drone);
        return Mono.just(DroneMapper.fromEntity(drone));
    }

    @Override
    public void saveDrones(List<DroneEntity> drones) {
        if(!drones.isEmpty()) {
            for(DroneEntity entity: drones) {
                saveDrone(entity);
            }
        }
    }
}

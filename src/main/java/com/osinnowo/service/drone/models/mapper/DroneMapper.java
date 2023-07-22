package com.osinnowo.service.drone.models.mapper;

import com.osinnowo.service.drone.models.dto.DroneDto;
import com.osinnowo.service.drone.models.entity.DroneEntity;
import com.osinnowo.service.drone.models.entity.DroneModel;
import com.osinnowo.service.drone.models.entity.DroneState;
import com.osinnowo.service.drone.models.request.DroneRequest;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

public class DroneMapper {
    public static DroneDto fromEntity(DroneEntity entity) {
        return DroneDto.builder()
                .id(entity.getId())
                .serialNumber(entity.getSerialNumber())
                .model(entity.getModel().toString())
                .weightLimit(entity.getWeightLimit())
                .state(entity.getState().toString())
                .batteryCapacity(entity.getBatteryCapacity())
                .medications(Optional.ofNullable(entity.getMedications())
                        .orElse(Collections.emptyList())
                        .stream()
                        .map(MedicationMapper::fromEntity)
                        .collect(Collectors.toList())
                )
                .build();
    }

    public static DroneEntity fromRequest(DroneRequest request) {
        return DroneEntity
                .builder()
                .serialNumber(request.getSerialNumber())
                .weightLimit(request.getWeightLimit())
                .batteryCapacity(request.getBatteryCapacity())
                .state(DroneState.valueOf(request.getState().toUpperCase()))
                .model(DroneModel.valueOf(request.getModel().toUpperCase()))
                .build();
    }
}

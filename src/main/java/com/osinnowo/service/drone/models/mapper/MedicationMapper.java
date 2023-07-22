package com.osinnowo.service.drone.models.mapper;

import com.osinnowo.service.drone.models.dto.MedicationDto;
import com.osinnowo.service.drone.models.entity.MedicationEntity;
import com.osinnowo.service.drone.models.request.MedicationRequest;

public class MedicationMapper {
    public static MedicationDto fromEntity(MedicationEntity entity) {
        return MedicationDto
                .builder()
                .id(entity.getId())
                .name(entity.getName())
                .code(entity.getCode())
                .weight(entity.getWeight())
                .image(entity.getImage())
                .droneId(entity.getDrone().getId())
                .build();
    }

    public static MedicationEntity fromRequest(MedicationRequest request) {
        return MedicationEntity
                .builder()
                .name(request.getName())
                .code(request.getCode())
                .image(request.getImage())
                .weight(request.getWeight())
                .build();
    }
}

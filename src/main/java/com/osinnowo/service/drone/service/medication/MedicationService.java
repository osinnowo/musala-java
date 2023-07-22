package com.osinnowo.service.drone.service.medication;

import com.osinnowo.service.drone.models.dto.MedicationDto;
import com.osinnowo.service.drone.models.entity.MedicationEntity;
import reactor.core.publisher.Mono;

public interface MedicationService {
    Mono<MedicationDto> saveMedication(MedicationEntity entity);
}

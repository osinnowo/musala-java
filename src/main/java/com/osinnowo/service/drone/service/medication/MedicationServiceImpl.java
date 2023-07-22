package com.osinnowo.service.drone.service.medication;

import com.osinnowo.service.drone.models.dto.MedicationDto;
import com.osinnowo.service.drone.models.entity.MedicationEntity;
import com.osinnowo.service.drone.models.mapper.MedicationMapper;
import com.osinnowo.service.drone.repository.MedicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MedicationServiceImpl implements MedicationService {

    private final MedicationRepository repository;

    @Override
    public Mono<MedicationDto> saveMedication(MedicationEntity entity) {
        MedicationEntity saveEntity = repository.saveAndFlush(entity);
        return Mono.just(MedicationMapper.fromEntity(saveEntity));
    }
}

package com.osinnowo.service.drone.models.common;

import com.osinnowo.service.drone.models.entity.MedicationEntity;

import java.util.List;

public class WeightCalculator {
    public static double calculate(List<MedicationEntity> medications) {
        return medications.stream().mapToDouble(MedicationEntity::getWeight).sum();
    }
}

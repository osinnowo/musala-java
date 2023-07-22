package com.osinnowo.service.drone.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DroneDto {
    private Long id;
    private String serialNumber;
    private String model;
    private Double weightLimit;
    private Integer batteryCapacity;
    private String state;
    private List<MedicationDto> medications;
}

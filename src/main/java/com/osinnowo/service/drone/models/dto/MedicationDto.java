package com.osinnowo.service.drone.models.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MedicationDto {
    private Long id;
    private String name;
    private double weight;
    private String code;
    private String image;
    private Long droneId;
}

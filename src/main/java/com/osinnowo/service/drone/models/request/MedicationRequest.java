package com.osinnowo.service.drone.models.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MedicationRequest {
    private String name;
    private double weight;
    private String code;
    private String image;
    private Long droneId;
}

package com.osinnowo.service.drone.models.request;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class DroneRequest {
    @NotBlank(message = "Serial number is required")
    @Size(max = 100, message = "Serial number cannot exceed {max} characters")
    private String serialNumber;

    @NotBlank(message = "Model is required")
    @Pattern(regexp = "(?i)^(Lightweight|Middleweight|Cruiserweight|Heavyweight)$", message = "Invalid drone model")
    private String model;

    //@DecimalMax(value = "500", message = "Weight limit cannot exceed {value} grams")
    private Double weightLimit;

    @Min(value = 0, message = "Battery capacity must be a positive value")
    @Max(value = 100, message = "Battery capacity cannot exceed {value}%")
    private Integer batteryCapacity;

    @NotBlank(message = "State is required")
    @Pattern(regexp = "(?i)^(Idle|Loaded|Delivering|Delivered|Returning)$", message = "Invalid state")
    private String state;
}
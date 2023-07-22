package com.osinnowo.service.drone.models.entity;

import com.osinnowo.service.drone.models.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "drones")
@EqualsAndHashCode(callSuper = true)
public class DroneEntity extends BaseEntity<DroneEntity> {
    @Column(length = 100, unique = true)
    private String serialNumber;

    @Enumerated(EnumType.STRING)
    private DroneModel model;

    private double weightLimit;

    private int batteryCapacity;

    @Enumerated(EnumType.STRING)
    private DroneState state;

    @OneToMany(mappedBy = "drone", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<MedicationEntity> medications;
}

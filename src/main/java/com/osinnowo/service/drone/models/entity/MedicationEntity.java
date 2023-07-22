package com.osinnowo.service.drone.models.entity;

import com.osinnowo.service.drone.models.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "medications")
@EqualsAndHashCode(callSuper = true)
public class MedicationEntity extends BaseEntity<MedicationEntity> {

    @Column(unique = true)
    private String name;

    private double weight;

    @Column(unique = true)
    private String code;

    private String image;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "drone_id")
    private DroneEntity drone;
}
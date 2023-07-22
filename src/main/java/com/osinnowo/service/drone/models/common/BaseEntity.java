package com.osinnowo.service.drone.models.common;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class BaseEntity<T> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDate dateCreated;
    private LocalDate dateModified;
    private Boolean isSoftDeleted;

    @PreUpdate
    public void preUpdate() {
        dateModified = LocalDate.now();
    }

    @PrePersist
    public void prePersist() {
        dateModified = LocalDate.now();
        dateCreated = LocalDate.now();
        isSoftDeleted = false;
    }
}


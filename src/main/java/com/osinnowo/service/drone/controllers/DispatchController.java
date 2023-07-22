package com.osinnowo.service.drone.controllers;

import com.osinnowo.service.drone.models.common.BaseResponse;
import com.osinnowo.service.drone.models.dto.DroneDto;
import com.osinnowo.service.drone.models.request.DroneRequest;
import com.osinnowo.service.drone.models.request.MedicationRequest;
import com.osinnowo.service.drone.service.drone.DroneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/dispatch")
@RequiredArgsConstructor
public class DispatchController {

    private final DroneService droneService;

    @GetMapping("/drone/available")
    public Mono<ResponseEntity<BaseResponse<List<DroneDto>>>> getAllDrones() {
        return droneService.getAllAvailableDrones()
                .collectList()
                .map(BaseResponse::okResponse);
    }

    @GetMapping("/drone/{id}")
    public Mono<ResponseEntity<BaseResponse<DroneDto>>> getDroneBy(@Validated @PathVariable Long id) {
        return droneService
                .getDroneById(id)
                .map(BaseResponse::okResponse);
    }

    @PostMapping("/drone/load")
    public Mono<ResponseEntity<BaseResponse<DroneDto>>> registerDrone(@Validated @RequestBody DroneRequest droneRequest) {
        return droneService
                .createDrone(droneRequest)
                .map(BaseResponse::okResponse);
    }

    @PutMapping("/drone/load/{id}/single")
    public Mono<ResponseEntity<BaseResponse<DroneDto>>> loadDroneWith(@Validated @PathVariable Long id, @Validated @RequestBody MedicationRequest request) {
        return droneService
                .loadDrone(id, request)
                .map(BaseResponse::okResponse);
    }

    @PutMapping("/drone/load/{id}/multiple")
    public Mono<ResponseEntity<BaseResponse<DroneDto>>> loadDroneWith(@Validated @PathVariable Long id, @Validated @RequestBody List<MedicationRequest> medications) {
        droneService.loadDroneWithMedication(id, medications);
        return Mono.just(BaseResponse.okResponse());
    }

    @PutMapping("/drone/load/{droneId}/{medicationId}")
    public Mono<ResponseEntity<BaseResponse<DroneDto>>> loadDroneWith(@PathVariable @Validated Long droneId, @Validated @PathVariable Long medicationId) {
        return droneService
                .loadDrone(droneId, medicationId)
                .map(BaseResponse::okResponse);
    }

    @PutMapping("/drone/{droneId}/status/{status}")
    public Mono<ResponseEntity<BaseResponse<DroneDto>>> setDroneStatus(@PathVariable @Validated Long droneId, @Validated @PathVariable String status) {
        return droneService
                .setDroneStatus(droneId, status)
                .map(BaseResponse::okResponse);
    }
}

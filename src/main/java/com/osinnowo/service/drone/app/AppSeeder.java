package com.osinnowo.service.drone.app;

import com.osinnowo.service.drone.configuration.AppConfig;
import com.osinnowo.service.drone.models.common.WeightCalculator;
import com.osinnowo.service.drone.models.entity.DroneEntity;
import com.osinnowo.service.drone.models.entity.DroneModel;
import com.osinnowo.service.drone.models.entity.DroneState;
import com.osinnowo.service.drone.models.entity.MedicationEntity;
import com.osinnowo.service.drone.service.drone.DroneService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AppSeeder {

    private final DroneService droneService;
    private final AppConfig appConfig;

    public void seed() {
        List<DroneEntity> drones = List.of(
                new DroneEntity("SRN10000000000", DroneModel.HEAVYWEIGHT, appConfig.getWeightLimit(), 100, DroneState.LOADING, null),
                new DroneEntity("SRN10000000001", DroneModel.HEAVYWEIGHT, appConfig.getWeightLimit(), 10, DroneState.IDLE, null),
                new DroneEntity("SRN10000000002", DroneModel.LIGHTWEIGHT, appConfig.getWeightLimit(), 80, DroneState.IDLE, null),
                new DroneEntity("SRN10000000003", DroneModel.MIDDLEWEIGHT, appConfig.getWeightLimit(), 80, DroneState.IDLE, null),
                new DroneEntity("SRN10000000004", DroneModel.MIDDLEWEIGHT, appConfig.getWeightLimit(), 80, DroneState.IDLE, null),
                new DroneEntity("SRN10000000005", DroneModel.HEAVYWEIGHT, appConfig.getWeightLimit(), 90, DroneState.IDLE, null)
        );

        List<MedicationEntity> medications = List.of(
                new MedicationEntity("Aspirin", 10.5, "CD1000000", "aspirin.jpg", null),
                new MedicationEntity("Ibuprofen", 10.5, "CD1000001", "ibuprofen.jpg", null),
                new MedicationEntity("Paracetamol", 11.2, "CD1000002", "paracetamol.jpg", null),
                new MedicationEntity("Acetaminophen", 20.15, "CD1000003", "acetaminophen.jpg", null),
                new MedicationEntity("Amoxicillin", 22.15, "CD1000004", "amoxicillin.jpg", null),
                new MedicationEntity("Azithromycin", 12.5, "CD1000005", "azithromycin.jpg", null)
        );

       drones.get(0).setMedications(medications);
       medications.forEach(item -> item.setDrone(drones.get(0)));

       double totalMedicationWeight = WeightCalculator.calculate(medications);
       drones.get(0).setWeightLimit(drones.get(0).getWeightLimit() - totalMedicationWeight);

       droneService.saveDrones(drones);
    }
}

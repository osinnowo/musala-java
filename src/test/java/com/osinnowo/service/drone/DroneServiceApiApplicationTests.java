package com.osinnowo.service.drone;

import com.osinnowo.service.drone.models.dto.DroneDto;
import com.osinnowo.service.drone.models.request.DroneRequest;
import com.osinnowo.service.drone.models.request.MedicationRequest;
import com.osinnowo.service.drone.service.drone.DroneService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import java.util.Collections;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static reactor.core.publisher.Mono.when;

@ExtendWith({SpringExtension.class})
@SpringBootTest
class DroneServiceApiApplicationTests {

	@MockBean
	private DroneService droneService;
	private WebTestClient webTestClient;
	PodamFactory podamFactory = new PodamFactoryImpl();

	@BeforeEach
	void setUp(@Autowired ApplicationContext applicationContext) {
		this.webTestClient = WebTestClient.bindToApplicationContext(applicationContext).configureClient()
				.build();
	}

	@Test
	public void testRegisterDrone() {
		DroneRequest droneRequest = podamFactory.manufacturePojo(DroneRequest.class);
		droneRequest.setState("IDLE");
		droneRequest.setModel("LIGHTWEIGHT");
		droneRequest.setBatteryCapacity(10);
		DroneDto droneDto = createDroneDto();

		when(droneService.createDrone(any())).thenReturn(Mono.just(droneDto));

		webTestClient.post()
				.uri("/api/dispatch/drone/load")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(droneRequest)
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$.status").isEqualTo("SUCCESS")
				.jsonPath("$.data.serialNumber").isEqualTo(droneDto.getSerialNumber());
	}

	@Test
	public void testLoadDroneWithSingleMedication() {
		PodamFactory podamFactory = new PodamFactoryImpl();
		MedicationRequest medicationRequest = podamFactory.manufacturePojo(MedicationRequest.class);
		Long droneId = 1L;
		DroneDto droneDto = createDroneDto();
		when(droneService.loadDrone(eq(droneId), any(MedicationRequest.class))).thenReturn(Mono.just(droneDto));

		webTestClient.put()
				.uri("/api/dispatch/drone/load/{id}/single", droneId)
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(medicationRequest)
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$.status").isEqualTo("SUCCESS")
				.jsonPath("$.data.serialNumber").isEqualTo(droneDto.getSerialNumber());
	}

	private DroneDto createDroneDto() {
		DroneDto droneDto = new DroneDto();
		droneDto.setId(1L);
		droneDto.setSerialNumber("DRN123");
		droneDto.setModel("Middleweight");
		droneDto.setWeightLimit(500.0);
		droneDto.setBatteryCapacity(80);
		droneDto.setState("IDLE");
		droneDto.setMedications(Collections.emptyList());
		return droneDto;
	}
}

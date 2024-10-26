package com.dani.dcl.parcauto.journy;

import com.dani.dcl.parcauto.dto.VehiculeRequest;
import com.dani.dcl.parcauto.model.Vehicule;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class VehiculeIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    private static final Random RANDOM = new Random();
    private static final String VEHICULE_URI = "/api/v1/vehicules";

    @Test
    void canAddVehicule() {
        //create add request
        String matricule = UUID.randomUUID().toString();
        String marque = "Foo";
        String carburant = "Diesel";
        String type = "Foo";
        int anneeCirculation = RANDOM.nextInt(2000, 2024);

        VehiculeRequest request = new VehiculeRequest(matricule, marque, carburant, type, anneeCirculation);
        //send a post request
        webTestClient.post()
                .uri(VEHICULE_URI)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), VehiculeRequest.class)
                .exchange()
                .expectStatus()
                .isOk();
        //get all vehicules
        List<Vehicule> allVehicules = webTestClient.get()
                .uri(VEHICULE_URI)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<Vehicule>() {
                })
                .returnResult()
                .getResponseBody();

        //make sure that vehicule is present
        Vehicule expectedVehicule = Vehicule.builder().matricule(matricule).marque(marque).carburant(carburant).type(type).anneeCirculation(anneeCirculation)
                .build();

        Assertions.assertThat(allVehicules)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .contains(expectedVehicule);
        //get vehicule by id
        Long id = allVehicules.stream()
                .filter((v) -> v.getMatricule().equals(matricule))
                .map(Vehicule::getId).findFirst().orElseThrow();

        expectedVehicule.setId(id);

        webTestClient.get()
                .uri(VEHICULE_URI + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<Vehicule>() {
                })
                .isEqualTo(expectedVehicule);
    }

    @Test
    void canDeleteVehicle() {
        //create add request
        String matricule = UUID.randomUUID().toString();
        String marque = "Foo";
        String carburant = "Diesel";
        String type = "Foo";
        int anneeCirculation = RANDOM.nextInt(2000, 2024);

        VehiculeRequest request = new VehiculeRequest(matricule, marque, carburant, type, anneeCirculation);
        //send a post request
        webTestClient.post()
                .uri(VEHICULE_URI)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), VehiculeRequest.class)
                .exchange()
                .expectStatus()
                .isOk();
        //get all vehicules
        List<Vehicule> allVehicules = webTestClient.get()
                .uri(VEHICULE_URI)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<Vehicule>() {
                })
                .returnResult()
                .getResponseBody();

        //delete vehicle by id
        Long id = allVehicules.stream()
                .filter((v) -> v.getMatricule().equals(matricule))
                .map(Vehicule::getId).findFirst().orElseThrow();

        webTestClient.delete()
                .uri(VEHICULE_URI + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk();

        //get vehicule by id
        webTestClient.get()
                .uri(VEHICULE_URI + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    void canUpdateVehicle() {
        //create add request
        String matricule = UUID.randomUUID().toString();
        String marque = "Foo";
        String carburant = "Diesel";
        String type = "Foo";
        int anneeCirculation = RANDOM.nextInt(2000, 2024);

        VehiculeRequest request = new VehiculeRequest(matricule, marque, carburant, type, anneeCirculation);
        //send a post request
        webTestClient.post()
                .uri(VEHICULE_URI)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), VehiculeRequest.class)
                .exchange()
                .expectStatus()
                .isOk();
        //get all vehicules
        List<Vehicule> allVehicules = webTestClient.get()
                .uri(VEHICULE_URI)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<Vehicule>() {
                })
                .returnResult()
                .getResponseBody();

        //update vehicle by id
        Long id = allVehicules.stream()
                .filter((v) -> v.getMatricule().equals(matricule))
                .map(Vehicule::getId).findFirst().orElseThrow();
        VehiculeRequest updateRequest = new VehiculeRequest(matricule, marque, "Hybrid", type, anneeCirculation);

        webTestClient.put()
                .uri(VEHICULE_URI + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(updateRequest), VehiculeRequest.class)
                .exchange()
                .expectStatus()
                .isOk();

        //get vehicule by id
        Vehicule uodatedVehicle = webTestClient.get()
                .uri(VEHICULE_URI + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Vehicule.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertThat(uodatedVehicle.getCarburant()).isEqualTo("Hybrid");
    }
}

package com.dani.dcl.parcauto.repository;

import com.dani.dcl.parcauto.AbstractTestcontainers;
import com.dani.dcl.parcauto.dao.VehiculeJDBCDaoImpl;
import com.dani.dcl.parcauto.model.Vehicule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContext;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class VehiculeRepositoryTest extends AbstractTestcontainers {

    @Autowired
    private VehiculeRepository underTest;

    @Autowired
    private ApplicationContext applicationContext;

    Random random = new Random();

    @BeforeEach
    void setUp() {
        System.out.println(applicationContext.getBeanDefinitionCount());
    }

    @Test
    void findVehiculeByMatricule() {
        // Given
        Vehicule vehicule = Vehicule.builder()
                .matricule("J178130").marque("Dacia Duster 4X4").type("Service").anneeCirculation(random.nextInt(2000, 2024))
                .build();
        underTest.save(vehicule);
        // When
        Vehicule actuel = underTest.findVehiculeByMatricule("J178130");
        // Then
        assertThat(actuel.getMatricule()).isEqualTo("J178130");
    }

    @Test
    void existsVehiculeByMatricule() {
        // Given
        Vehicule vehicule = Vehicule.builder()
                .matricule("J179056").marque("HYUNDAI").type("Ambulance").anneeCirculation(random.nextInt(2000, 2024))
                .build();
        underTest.save(vehicule);
        // When
        boolean actuel = underTest.existsVehiculeByMatricule("J179056");
        // Then
        assertThat(actuel).isTrue();

    }

    @Test
    void existsVehiculeById() {
        // Given
        Vehicule vehicule = Vehicule.builder()
                .matricule("J0185634").marque("FIAT DUCATO").type("Transport Scolaire").anneeCirculation(random.nextInt(2000, 2024))
                .build();
        underTest.save(vehicule);
        // When
        Vehicule savedVehicule = underTest.findVehiculeByMatricule("J0185634");
        boolean actuel = underTest.existsVehiculeById(savedVehicule.getId());
        // Then
        assertThat(actuel).isTrue();
    }
}
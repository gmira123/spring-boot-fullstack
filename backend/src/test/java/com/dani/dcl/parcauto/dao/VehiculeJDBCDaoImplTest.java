package com.dani.dcl.parcauto.dao;

import com.dani.dcl.parcauto.AbstractTestcontainers;
import com.dani.dcl.parcauto.model.Vehicule;
import com.dani.dcl.parcauto.rowmapper.VehiculeRowMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

class VehiculeJDBCDaoImplTest extends AbstractTestcontainers {

    private VehiculeJDBCDaoImpl underTest;
    private VehiculeRowMapper vehiculeRowMapper = new VehiculeRowMapper();
    Random random = new Random();

    @BeforeEach
    void setUp() {
        underTest = new VehiculeJDBCDaoImpl(getJdbcTemplate(), vehiculeRowMapper);
    }

    @Test
    @Order(1)
    void selectAllVehicules() {
        // Given
        Vehicule vehicule = Vehicule.builder()
                .matricule("J178130").marque("Dacia Duster 4X4").type("Service").anneeCirculation(random.nextInt(2000, 2024))
                .build();
        underTest.insertVehicule(vehicule);
        // When
        List<Vehicule> vehicules = underTest.selectAllVehicules();

        // Then
        assertThat(vehicules).isNotEmpty();
    }

    @Test
    @Order(2)
    void selectVehiculeById() {
        // Given
        Vehicule vehicule = Vehicule.builder()
                .matricule("J179056").marque("HYUNDAI").type("Ambulance").anneeCirculation(random.nextInt(2000, 2024))
                .build();
        underTest.insertVehicule(vehicule);
        Long id = underTest.selectAllVehicules().stream()
                .filter(v -> v.getMatricule().equals("J179056"))
                .map(Vehicule::getId)
                .findFirst()
                .orElseThrow();
        // When
        Optional<Vehicule> actuel = underTest.selectVehiculeById(id);
        // Then
        assertThat(actuel).isPresent().hasValueSatisfying(v -> {
            assertThat(v.getMatricule()).isEqualTo(vehicule.getMatricule());
            assertThat(v.getMarque()).isEqualTo(vehicule.getMarque());
            assertThat(v.getType()).isEqualTo(vehicule.getType());
            assertThat(v.getCarburant()).isEqualTo(vehicule.getCarburant());
        });
    }

    @Test
    void willReturnEmptyIfVehiculeDosentExixts() {
        // Given
        Long id = -1L;
        // When
        Optional<Vehicule> vehicule = underTest.selectVehiculeById(id);
        // Then
        assertThat(vehicule).isEmpty();
    }

    @Test
    void insertVehicule() {
        // Given
        Vehicule vehicule = Vehicule.builder()
                .matricule("J0185634").marque("FIAT DUCATO").type("Transport Scolaire").anneeCirculation(random.nextInt(2000, 2024))
                .build();

        // When
        int countBeforInsertion = underTest.selectAllVehicules().size();
        underTest.insertVehicule(vehicule);
        int countAfterInsertion = underTest.selectAllVehicules().size();
        // Then
        assertThat(countAfterInsertion).isGreaterThan(countBeforInsertion);
    }

    @Test
    void existsVehiculeByMatricule() {
        // Given
        Vehicule vehicule = Vehicule.builder()
                .matricule("J0185630").marque("FIAT PALIO").type("Transport Scolaire").anneeCirculation(random.nextInt(2000, 2024))
                .build();

        underTest.insertVehicule(vehicule);
        // When
        boolean actuel = underTest.existsVehiculeByMatricule("J0185630");

        // Then
        assertThat(actuel).isTrue();
    }

    @Test
    void existsVehiculeById() {
        // Given
        Vehicule vehicule = Vehicule.builder()
                .matricule("J17905690").marque("HYUNDAI").type("Ambulance").anneeCirculation(random.nextInt(2000, 2024))
                .build();
        underTest.insertVehicule(vehicule);
        Long id = underTest.selectAllVehicules().stream()
                .filter(v -> v.getMatricule().equals("J17905690"))
                .map(Vehicule::getId)
                .findFirst()
                .orElseThrow();
        // When
        boolean actuel = underTest.existsVehiculeById(id);
        // Then
        assertThat(actuel).isTrue();
    }

    @Test
    void notExistsVehiculeById() {
        // Given
        Long id = -1L;
        // When
        boolean actuel = underTest.existsVehiculeById(id);
        // Then
        assertThat(actuel).isFalse();
    }

    @Test
    void deleteVehiculeById() {
        // Given
        Vehicule vehicule = Vehicule.builder()
                .matricule("J1723333").marque("HYUNDAI").type("Ambulance").anneeCirculation(random.nextInt(2000, 2024))
                .build();
        underTest.insertVehicule(vehicule);
        Long id = underTest.selectAllVehicules().stream()
                .filter(v -> v.getMatricule().equals("J1723333"))
                .map(Vehicule::getId)
                .findFirst()
                .orElseThrow();
        // When
        underTest.deleteVehiculeById(id);
        // Then
        Optional<Vehicule> actuel = underTest.selectVehiculeById(id);
        assertThat(actuel).isNotPresent();
    }

    @Test
    void updateVehicule() {
        // Given
        Vehicule vehicule = Vehicule.builder()
                .matricule("J1000").marque("HYUNDAI").type("Ambulance").anneeCirculation(random.nextInt(2000, 2024))
                .build();
        underTest.insertVehicule(vehicule);
        Long id = underTest.selectAllVehicules().stream()
                .filter(v -> v.getMatricule().equals("J1000"))
                .findFirst()
                .map(Vehicule::getId)
                .orElseThrow();
        // When
        Vehicule vehiculeRequest = new Vehicule();
        vehiculeRequest.setId(id);
        vehiculeRequest.setMarque("Mercedes");
        underTest.updateVehicule(vehiculeRequest);
        Optional<Vehicule> actuel = underTest.selectVehiculeById(id);
        // Then
        assertThat(actuel).isPresent().hasValueSatisfying(v -> {
            assertThat(v.getId()).isEqualTo(id);
            assertThat(v.getMatricule()).isEqualTo(vehicule.getMatricule());
            assertThat(v.getMarque()).isNotEqualTo(vehicule.getMarque());
            assertThat(v.getType()).isEqualTo(vehicule.getType());
            assertThat(v.getCarburant()).isEqualTo(vehicule.getCarburant());
        });
    }
}
package com.dani.dcl.parcauto.dao;

import com.dani.dcl.parcauto.model.Vehicule;
import com.dani.dcl.parcauto.repository.VehiculeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

class VehiculeJPADaoImplTest {

    private VehiculeDaoImpl underTest;
    @Mock
    private VehiculeRepository vehiculeRepository;

    private AutoCloseable autoCloseable;

    private

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new VehiculeDaoImpl(vehiculeRepository);

    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void selectAllVehicules() {
        // When
        underTest.selectAllVehicules();

        // Then
        verify(vehiculeRepository).findAll();
    }

    @Test
    void selectVehiculeById() {
        // Given
        Long id = 1L;

        // When
        underTest.selectVehiculeById(id);

        // Then
        verify(vehiculeRepository).findById(id);
    }

    @Test
    void insertVehicule() {
        // Given
        Vehicule vehicule = new Vehicule();

        // When
        underTest.insertVehicule(vehicule);

        // Then
        verify(vehiculeRepository).save(vehicule);
    }

    @Test
    void existsVehiculeByMatricule() {
        // Given
        String matricule = "J123";

        // When
        underTest.existsVehiculeByMatricule(matricule);

        // Then
        verify(vehiculeRepository).existsVehiculeByMatricule(matricule);
    }

    @Test
    void existsVehiculeById() {
        // Given
        Long id = 1L;

        // When
        underTest.existsVehiculeById(id);

        // Then
        verify(vehiculeRepository).existsVehiculeById(id);
    }

    @Test
    void deleteVehiculeById() {
        // Given
        Long id = 1L;

        // When
        underTest.deleteVehiculeById(id);

        // Then
        verify(vehiculeRepository).deleteById(id);
    }

    @Test
    void updateVehicule() {
        // Given
        Vehicule vehicule = new Vehicule();

        // When
        underTest.updateVehicule(vehicule);

        // Then
        verify(vehiculeRepository).save(vehicule);
    }
}
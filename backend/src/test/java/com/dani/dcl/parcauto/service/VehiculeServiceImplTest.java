package com.dani.dcl.parcauto.service;

import com.dani.dcl.parcauto.dao.VehiculeDao;
import com.dani.dcl.parcauto.dto.VehiculeRequest;
import com.dani.dcl.parcauto.exception.DuplacateResourceException;
import com.dani.dcl.parcauto.exception.RequestValidationException;
import com.dani.dcl.parcauto.exception.ResourceNotFoundException;
import com.dani.dcl.parcauto.model.Vehicule;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehiculeServiceImplTest {

    @Mock
    private VehiculeDao vehiculeDao;
    private VehiculeServiceImpl underTest;

    @BeforeEach
    void setUp() {
        underTest = new VehiculeServiceImpl(vehiculeDao);
    }

    @Test
    void getAllVehicules() {
        // When
        underTest.getAllVehicules();

        // Then
        verify(vehiculeDao).selectAllVehicules();
    }

    @Test
    void canGetVehiculeById() {
        // Given
        Long id = 10L;
        Vehicule vehicule = Vehicule.builder().id(id).matricule("AA7899").marque("Foo").build();
        Mockito.when(vehiculeDao.selectVehiculeById(id)).thenReturn(Optional.of(vehicule));

        // When
        Vehicule actuel = underTest.getVehiculeById(id);

        // Then
        Assertions.assertThat(actuel).isEqualTo(vehicule);
    }

    @Test
    void willThrowWhenGetVehiculeByIdReturnEmpty() {
        // Given
        Long id = 10L;
        Mockito.when(vehiculeDao.selectVehiculeById(id)).thenReturn(Optional.empty());

        // When
        // Then
        Assertions.assertThatThrownBy(() -> underTest.getVehiculeById(id))
                .isInstanceOfAny(ResourceNotFoundException.class)
                .hasMessage(String.format("Vehicule with id [%s] not found ", id));
    }

    @Test
    void addVehicule() {
        // Given
        String matricule = "AA7899";
        when(vehiculeDao.existsVehiculeByMatricule(matricule)).thenReturn(false);

        // When
        VehiculeRequest vehiculeRequest = new VehiculeRequest("AA7899", "Foo", "Diesel", "Foo", 2016);
        underTest.addVehicule(vehiculeRequest);

        // Then
        ArgumentCaptor<Vehicule> vehiculeArgumentCaptor = ArgumentCaptor.forClass(Vehicule.class);
        verify(vehiculeDao).insertVehicule(vehiculeArgumentCaptor.capture());

        Vehicule vehicule = vehiculeArgumentCaptor.getValue();

        Assertions.assertThat(vehicule.getId()).isNull();
        Assertions.assertThat(vehicule.getMatricule()).isEqualTo(vehiculeRequest.matricule());
        Assertions.assertThat(vehicule.getMarque()).isEqualTo(vehiculeRequest.marque());
        Assertions.assertThat(vehicule.getCarburant()).isEqualTo(vehiculeRequest.carburant());
        Assertions.assertThat(vehicule.getType()).isEqualTo(vehiculeRequest.type());
        Assertions.assertThat(vehicule.getAnneeCirculation()).isEqualTo(vehiculeRequest.anneeCirculation());
    }

    @Test
    void willThrowWhenMatriculeExistsWhiladdVehicule() {
        // Given
        String matricule = "AA7899";
        when(vehiculeDao.existsVehiculeByMatricule(matricule)).thenReturn(true);

        // When
        VehiculeRequest request = new VehiculeRequest("AA7899", "Foo", "Diesel", "Foo", 2016);
        Assertions.assertThatThrownBy(() -> underTest.addVehicule(request))
                .isInstanceOf(DuplacateResourceException.class)
                .hasMessage("Vehicule with matricule [%s] exists", request.matricule());

        // Then
        verify(vehiculeDao, never()).insertVehicule(any());


    }

    @Test
    void deleteVehiculeById() {
        // Given
        Long id = 10L;
        when(vehiculeDao.existsVehiculeById(id)).thenReturn(true);

        // When
        underTest.deleteVehiculeById(id);

        // Then
        verify(vehiculeDao).deleteVehiculeById(id);
    }

    @Test
    void ThrowWhenIDNotExistsWhiledeleteVehiculeById() {
        // Given
        Long id = 10L;
        when(vehiculeDao.existsVehiculeById(id)).thenReturn(false);

        // When
        Assertions.assertThatThrownBy(() -> underTest.deleteVehiculeById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage(String.format("Vehicule with id [%s] not found ", id));

        // Then
        verify(vehiculeDao, never()).deleteVehiculeById(id);
    }

    @Test
    void updateVehiculeById() {
        // Given
        Long id = 10L;
        Vehicule vehicule = Vehicule.builder().id(id).matricule("AA7898").marque("Foo").carburant("Essence").build();
        when(vehiculeDao.selectVehiculeById(id)).thenReturn(Optional.of(vehicule));
        String matricule = "AA7899";
        VehiculeRequest vehiculeRequest = new VehiculeRequest("AA7899", "Foo", "Diesel", "Foo", 2016);

        // When
        when(vehiculeDao.existsVehiculeByMatricule(matricule)).thenReturn(false);
        underTest.updateVehiculeById(id, vehiculeRequest);


        // Then
        ArgumentCaptor<Vehicule> vehiculeArgumentCaptor = ArgumentCaptor.forClass(Vehicule.class);
        verify(vehiculeDao).updateVehicule(vehiculeArgumentCaptor.capture());
        Vehicule capturedVehicule = vehiculeArgumentCaptor.getValue();

        Assertions.assertThat(capturedVehicule.getCarburant().equals(vehiculeRequest.carburant()));
        Assertions.assertThat(capturedVehicule.getMatricule().equals(vehiculeRequest.matricule()));
    }

    @Test
    void canUpdateOnlyCarburant() {
        // Given
        Long id = 10L;
        Vehicule vehicule = Vehicule.builder().id(id).matricule("AA7898").marque("Foo").carburant("Essence").build();
        when(vehiculeDao.selectVehiculeById(id)).thenReturn(Optional.of(vehicule));
        VehiculeRequest vehiculeRequest = new VehiculeRequest(null, null, "Diesel", null, null);

        // When
        underTest.updateVehiculeById(id, vehiculeRequest);


        // Then
        ArgumentCaptor<Vehicule> vehiculeArgumentCaptor = ArgumentCaptor.forClass(Vehicule.class);
        verify(vehiculeDao).updateVehicule(vehiculeArgumentCaptor.capture());
        Vehicule capturedVehicule = vehiculeArgumentCaptor.getValue();

        Assertions.assertThat(capturedVehicule.getCarburant().equals(vehiculeRequest.carburant()));
        Assertions.assertThat(capturedVehicule.getMatricule().equals(vehicule.getMatricule()));
    }

    @Test
    void willThrowWhenMatriculeExists() {
        // Given
        Long id = 10L;
        Vehicule vehicule = Vehicule.builder()
                .id(id).matricule("AA7898").marque("Foo").carburant("Essence").type("Utilitaire").anneeCirculation(2016)
                .build();
        when(vehiculeDao.selectVehiculeById(id)).thenReturn(Optional.of(vehicule));

        String newMatricule = "AA7899";
        VehiculeRequest vehiculeRequest = new VehiculeRequest(newMatricule, null, null, null, null);

        when(vehiculeDao.existsVehiculeByMatricule(newMatricule)).thenReturn(true);

        // When
        Assertions.assertThatThrownBy(() -> underTest.updateVehiculeById(id, vehiculeRequest))
                .isInstanceOf(DuplacateResourceException.class)
                .hasMessage(String.format("Vehicule with matricule [%s] exists", newMatricule));

        // Then
        verify(vehiculeDao, never()).updateVehicule(any());

    }

    @Test
    void willThrowWhenNoDataChangeWhileUpdateVehicule() {
        // Given
        Long id = 10L;
        Vehicule vehicule = Vehicule.builder()
                .id(id).matricule("AA7898").marque("Foo").carburant("Essence").type("Utilitaire").anneeCirculation(2016)
                .build();
        when(vehiculeDao.selectVehiculeById(id)).thenReturn(Optional.of(vehicule));

        VehiculeRequest vehiculeRequest = new VehiculeRequest(vehicule.getMatricule(), vehicule.getMarque(),
                vehicule.getCarburant(), vehicule.getType(), vehicule.getAnneeCirculation());

        // When
        Assertions.assertThatThrownBy(()->underTest.updateVehiculeById(id, vehiculeRequest))
                .isInstanceOf(RequestValidationException.class)
                .hasMessage("No data change");

        // Then
        verify(vehiculeDao, never()).updateVehicule(any());

    }
}
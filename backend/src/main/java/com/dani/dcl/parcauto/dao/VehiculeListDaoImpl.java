package com.dani.dcl.parcauto.dao;

import com.dani.dcl.parcauto.model.Vehicule;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository("list")
public class VehiculeListDaoImpl implements VehiculeDao {

    private static List<Vehicule> vehicules;

    static {
        vehicules = new ArrayList<>();

        vehicules.add( Vehicule.builder()
                .id(1L).matricule("J178130").marque("Dacia Duster 4X4").type("Service")
                .build());
        vehicules.add(Vehicule.builder()
                .id(2L).matricule("J179056").marque("HYUNDAI").type("Ambulance")
                .build());
        vehicules.add(Vehicule.builder()
                .id(3L).matricule("J0185634").marque("FIAT DUCATO").type("Transport Scolaire")
                .build());
    }
    @Override
    public List<Vehicule> selectAllVehicules() {
        return vehicules;
    }

    @Override
    public Optional<Vehicule> selectVehiculeById(Long id) {
        return vehicules.stream()
                .filter(v -> v.getId().equals(id))
                .findFirst();

    }

    @Override
    public void insertVehicule(Vehicule vehicule) {
        vehicules.add(vehicule);
    }

    @Override
    public boolean existsVehiculeByMatricule(String matricule) {
        return vehicules.stream().anyMatch( v -> v.getMatricule().equals(matricule));
    }

    @Override
    public boolean existsVehiculeById(Long id) {
        return vehicules.stream().anyMatch(
                vehicule -> vehicule.getId().equals(id)
        );
    }

    @Override
    public void deleteVehiculeById(Long id) {
        vehicules.stream()
                .filter(v -> v.getId().equals(id))
                .findFirst()
                .ifPresent(vehicules::remove);
    }

    @Override
    public void updateVehicule(Vehicule vehicule) {
        vehicules.add(vehicule);
    }


}

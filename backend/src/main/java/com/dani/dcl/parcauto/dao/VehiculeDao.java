package com.dani.dcl.parcauto.dao;

import com.dani.dcl.parcauto.model.Vehicule;

import java.util.List;
import java.util.Optional;

public interface VehiculeDao {
    List<Vehicule> selectAllVehicules();
    Optional<Vehicule> selectVehiculeById(Long id);
    void insertVehicule(Vehicule vehicule);
    boolean existsVehiculeByMatricule(String matricule);
    boolean existsVehiculeById(Long id);
    void deleteVehiculeById(Long id);

    void updateVehicule(Vehicule vehicule);
}

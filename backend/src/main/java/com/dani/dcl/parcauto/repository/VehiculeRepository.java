package com.dani.dcl.parcauto.repository;

import com.dani.dcl.parcauto.model.Vehicule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehiculeRepository extends JpaRepository<Vehicule, Long> {
    Vehicule findVehiculeByMatricule(String matricule);
    boolean existsVehiculeByMatricule(String matricule);
    boolean existsVehiculeById(Long id);
}

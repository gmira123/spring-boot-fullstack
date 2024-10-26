package com.dani.dcl.parcauto.service;

import com.dani.dcl.parcauto.dto.VehiculeRequest;
import com.dani.dcl.parcauto.model.Vehicule;

import java.util.List;

public interface VehiculeService {
    List<Vehicule> getAllVehicules();
    Vehicule getVehiculeById(Long id);
    void addVehicule(VehiculeRequest request);
    void deleteVehiculeById(Long id);
    void updateVehiculeById(Long id, VehiculeRequest vehicule);
}

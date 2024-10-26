package com.dani.dcl.parcauto.dao;

import com.dani.dcl.parcauto.model.Vehicule;
import com.dani.dcl.parcauto.repository.VehiculeRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jpa")
public class VehiculeDaoImpl implements VehiculeDao {

    private final VehiculeRepository vehiculeRepository;

    public VehiculeDaoImpl(VehiculeRepository vehiculeRepository) {
        this.vehiculeRepository = vehiculeRepository;
    }

    @Override
    public List<Vehicule> selectAllVehicules() {
        return vehiculeRepository.findAll();
    }

    @Override
    public Optional<Vehicule> selectVehiculeById(Long id) {
        return vehiculeRepository.findById(id);
    }

    @Override
    public void insertVehicule(Vehicule vehicule) {
        vehiculeRepository.save(vehicule);
    }

    @Override
    public boolean existsVehiculeByMatricule(String matricule) {
        return vehiculeRepository.existsVehiculeByMatricule(matricule);
    }

    @Override
    public boolean existsVehiculeById(Long id) {
        return vehiculeRepository.existsVehiculeById(id);
    }

    @Override
    public void deleteVehiculeById(Long id) {
        vehiculeRepository.deleteById(id);
    }

    @Override
    public void updateVehicule(Vehicule vehicule) {
        vehiculeRepository.save(vehicule);
    }

}

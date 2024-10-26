package com.dani.dcl.parcauto.service;

import com.dani.dcl.parcauto.dao.VehiculeDao;
import com.dani.dcl.parcauto.dto.VehiculeRequest;
import com.dani.dcl.parcauto.exception.DuplacateResourceException;
import com.dani.dcl.parcauto.exception.RequestValidationException;
import com.dani.dcl.parcauto.exception.ResourceNotFoundException;
import com.dani.dcl.parcauto.model.Vehicule;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class VehiculeServiceImpl implements VehiculeService {

    private VehiculeDao vehiculeDao;

    public VehiculeServiceImpl(@Qualifier("jdbc") VehiculeDao vehiculeDao) {
        this.vehiculeDao = vehiculeDao;
    }

    @Override
    public List<Vehicule> getAllVehicules() {
        return vehiculeDao.selectAllVehicules();
    }

    @Override
    public Vehicule getVehiculeById(Long id) {
        return vehiculeDao.selectVehiculeById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Vehicule with id [%s] not found ", id))
                );

    }

    @Override
    public void addVehicule(VehiculeRequest request) {
        if (vehiculeDao.existsVehiculeByMatricule(request.matricule())) {
            throw new DuplacateResourceException(
                    String.format("Vehicule with matricule [%s] exists", request.matricule())
            );
        }
        vehiculeDao.insertVehicule(
                Vehicule.builder()
                        .matricule(request.matricule())
                        .marque(request.marque())
                        .carburant(request.carburant())
                        .type(request.type())
                        .anneeCirculation(request.anneeCirculation())
                        .build()
        );
    }

    @Override
    public void deleteVehiculeById(Long id) {
        if (!vehiculeDao.existsVehiculeById(id)) {
            throw new ResourceNotFoundException(
                    String.format("Vehicule with id [%s] not found ", id));
        }
        vehiculeDao.deleteVehiculeById(id);
    }

    @Override
    public void updateVehiculeById(Long id, VehiculeRequest vehicule) {
        Vehicule vehiculeToUpdate = this.getVehiculeById(id);
        boolean change = false;
        if (vehicule.anneeCirculation() != null
                && !vehicule.anneeCirculation().equals(vehiculeToUpdate.getAnneeCirculation())) {
            vehiculeToUpdate.setAnneeCirculation(vehicule.anneeCirculation());
            change = true;
        }
        if (vehicule.carburant() != null
                && !vehicule.carburant().equals(vehiculeToUpdate.getCarburant())) {
            vehiculeToUpdate.setCarburant(vehicule.carburant());
            change = true;
        }
        if (vehicule.marque() != null
                && !vehicule.marque().equals(vehiculeToUpdate.getMarque())) {
            vehiculeToUpdate.setMarque(vehicule.marque());
            change = true;
        }
        if (vehicule.type() != null
                && !vehicule.type().equals(vehiculeToUpdate.getType())) {
            vehiculeToUpdate.setType(vehicule.type());
            change = true;
        }
        if (vehicule.matricule() != null
                && !vehicule.matricule().equals(vehiculeToUpdate.getMatricule())) {
            if (vehiculeDao.existsVehiculeByMatricule(vehicule.matricule())) {
                throw new DuplacateResourceException(
                        String.format("Vehicule with matricule [%s] exists", vehicule.matricule())
                );
            }
            vehiculeToUpdate.setMatricule(vehicule.matricule());
            change = true;
        }
        if (change) {
            vehiculeDao.updateVehicule(vehiculeToUpdate);
        }else {
            throw new RequestValidationException("No data change");
        }

    }
}

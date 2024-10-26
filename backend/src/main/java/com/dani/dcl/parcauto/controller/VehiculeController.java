package com.dani.dcl.parcauto.controller;

import com.dani.dcl.parcauto.dto.VehiculeRequest;
import com.dani.dcl.parcauto.model.Commune;
import com.dani.dcl.parcauto.model.Vehicule;
import com.dani.dcl.parcauto.service.VehiculeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/vehicules")
public class VehiculeController {

    private VehiculeService vehiculeService;

    @GetMapping
    public List<Vehicule> getAllVehicules() {
        return vehiculeService.getAllVehicules();
    }

    @GetMapping("/{id}")
    public Vehicule getVehiculeById(@PathVariable("id") Long id) {
        return vehiculeService.getVehiculeById(id);
    }

    @PostMapping
    public void addVehicule(@RequestBody VehiculeRequest request){
        vehiculeService.addVehicule(request);
    }

    @DeleteMapping("/{id}")
    public void deleteVehiculeById(@PathVariable("id") Long id) {
         vehiculeService.deleteVehiculeById(id);
    }

    @PutMapping("/{id}")
    public void updateVehiculeById(@PathVariable("id") Long id, @RequestBody VehiculeRequest vehicule) {
        vehiculeService.updateVehiculeById(id, vehicule);
    }


}

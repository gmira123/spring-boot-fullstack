package com.dani.dcl.parcauto.dto;

public record VehiculeRequest(
        String matricule,
        String marque,
        String carburant,
        String type,
        Integer anneeCirculation
) {
}

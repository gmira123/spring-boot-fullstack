package com.dani.dcl.parcauto.rowmapper;

import com.dani.dcl.parcauto.model.Vehicule;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
@Component
public class VehiculeRowMapper implements RowMapper<Vehicule> {
    @Override
    public Vehicule mapRow(ResultSet rs, int rowNum) throws SQLException {
        Vehicule vehicule = new Vehicule(
                rs.getLong("id"),
                rs.getString("matricule"),
                rs.getString("marque"),
                rs.getString("carburant"),
                rs.getString("type"),
                rs.getInt("annee_circulation")
        );
        return vehicule;
    }
}

package com.dani.dcl.parcauto.rowmapper;

import com.dani.dcl.parcauto.model.Vehicule;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class VehiculeRowMapperTest {

    VehiculeRowMapper vehiculeRowMapper = new VehiculeRowMapper();

    @Test
    void mapRow() throws SQLException {
        // Given
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getLong("id")).thenReturn(10L);
        when(resultSet.getString("matricule")).thenReturn("A12345");
        when(resultSet.getString("marque")).thenReturn("Fiat");
        when(resultSet.getString("carburant")).thenReturn("Diesel");
        when(resultSet.getString("type")).thenReturn("Utilitaire");
        when(resultSet.getInt("annee_circulation")).thenReturn(2019);

        // When
        Vehicule actuel = vehiculeRowMapper.mapRow(resultSet, 1);

        Vehicule expected = new Vehicule(10L,"A12345","Fiat","Diesel","Utilitaire",2019);

        // Then
        Assertions.assertThat(actuel).isEqualTo(expected);
    }
}
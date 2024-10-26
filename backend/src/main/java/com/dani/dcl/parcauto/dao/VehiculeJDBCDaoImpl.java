package com.dani.dcl.parcauto.dao;

import com.dani.dcl.parcauto.model.Vehicule;
import com.dani.dcl.parcauto.rowmapper.VehiculeRowMapper;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
@Repository("jdbc")
@AllArgsConstructor
public class VehiculeJDBCDaoImpl implements VehiculeDao {

    private final JdbcTemplate jdbcTemplate;
    private final VehiculeRowMapper vehiculeRowMapper;

    @Override
    public List<Vehicule> selectAllVehicules() {
        var sql = """
                SELECT * FROM vehicule
                """;
        return jdbcTemplate.query(sql, vehiculeRowMapper);
    }

    @Override
    public Optional<Vehicule> selectVehiculeById(Long id) {
        var sql = """
                SELECT * FROM vehicule
                WHERE id = ?
                """;
        return jdbcTemplate
                .query(sql, vehiculeRowMapper, id)
                .stream()
                .findFirst();
        //return Optional.empty();
    }

    @Override
    public void insertVehicule(Vehicule vehicule) {
        String sql = """
                insert into vehicule(matricule, marque, carburant, type, annee_circulation) 
                values(?, ?, ?, ?, ?)
                """;
        int update = jdbcTemplate.update(sql, vehicule.getMatricule(), vehicule.getMarque(),
                vehicule.getCarburant(), vehicule.getType(), vehicule.getAnneeCirculation());
        System.out.println("Row inserted : " + update);
    }

    @Override
    public boolean existsVehiculeByMatricule(String matricule) {
        var sql = """
                SELECT * FROM vehicule
                WHERE matricule = ?
                """;
        boolean result = jdbcTemplate.query(sql, vehiculeRowMapper, matricule).isEmpty();
        return !result;
    }

    @Override
    public boolean existsVehiculeById(Long id) {
        var sql = """
                SELECT COUNT(id) 
                FROM vehicule
                WHERE id = ?
                """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

    @Override
    public void deleteVehiculeById(Long id) {
        var sql = """
                DELETE FROM vehicule
                WHERE id = ?
                """;
        jdbcTemplate.update(sql,id);
    }

    @Override
    public void updateVehicule(Vehicule vehicule) {
        var sql = "";
        if(vehicule.getMatricule() != null){
            sql = """
                UPDATE  vehicule
                SET matricule = ?
                WHERE id = ?
                """;
            jdbcTemplate.update(sql,vehicule.getMatricule(), vehicule.getId());
        }

        if(vehicule.getMarque() != null){
            sql = """
                UPDATE  vehicule
                SET marque = ?
                WHERE id = ?
                """;
            jdbcTemplate.update(sql,vehicule.getMarque(), vehicule.getId());
        }

        if(vehicule.getType() != null){
            sql = """
                UPDATE  vehicule
                SET type = ?
                WHERE id = ?
                """;
            jdbcTemplate.update(sql,vehicule.getType(), vehicule.getId());
        }
        if(vehicule.getCarburant() != null){
            sql = """
                UPDATE  vehicule
                SET carburant = ?
                WHERE id = ?
                """;
            jdbcTemplate.update(sql,vehicule.getCarburant(), vehicule.getId());
        }

        if(vehicule.getAnneeCirculation() != null){
            sql = """
                UPDATE  vehicule
                SET annee_circulation = ?
                WHERE id = ?
                """;
            jdbcTemplate.update(sql,vehicule.getAnneeCirculation(), vehicule.getId());
        }

    }
}

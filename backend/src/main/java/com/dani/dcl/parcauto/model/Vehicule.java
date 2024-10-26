package com.dani.dcl.parcauto.model;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Builder
@Entity
@Table(
        name = "vehicule",
        uniqueConstraints = {
                @UniqueConstraint(name = "vehicule_matricule_key",
                        columnNames = "matricule")
        }
)
public class Vehicule {
    @Id
    @SequenceGenerator(
            name = "vehicule_id_seq",
            sequenceName = "vehicule_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "vehicule_id_seq"
    )
    private Long id;
    @Column(nullable = false)
    private String matricule;
    private String marque;
    private String carburant;
    private String type;
    private Integer anneeCirculation;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vehicule vehicule = (Vehicule) o;

        if (!id.equals(vehicule.id)) return false;
        if (!matricule.equals(vehicule.matricule)) return false;
        if (marque != null ? !marque.equals(vehicule.marque) : vehicule.marque != null) return false;
        if (carburant != null ? !carburant.equals(vehicule.carburant) : vehicule.carburant != null) return false;
        if (type != null ? !type.equals(vehicule.type) : vehicule.type != null) return false;
        if (anneeCirculation != null ? !anneeCirculation.equals(vehicule.anneeCirculation) : vehicule.anneeCirculation != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + matricule.hashCode();
        result = 31 * result + (marque != null ? marque.hashCode() : 0);
        result = 31 * result + (carburant != null ? carburant.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (anneeCirculation != null ? anneeCirculation.hashCode() : 0);
        return result;
    }
}

package org.example.covoiturage.entités;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class Vehicule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // Utilisation du camelCase

    @NotNull
    private String type;
    private String image;

    @NotNull
    private String num_matricule;

    @Enumerated(EnumType.STRING)
    private EtatVehicule etat;
    @Min(1)  // Valeur minimale pour le confort
    @Max(5)  // Valeur maximale pour le confort
    private int degre_confort;

    public enum EtatVehicule {
        NEUF,
        BON,
        MOYEN,
        MAUVAIS
    }


}

package org.example.covoiturage.entités;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String nom_complet;
    @Column(nullable = false)
    private String mdp_utilisateur;

    private String role; // ADMIN, CONDUCTEUR, PASSAGER
    private Boolean isEnabled = false;  // Validation par admin
    private String tel;
}

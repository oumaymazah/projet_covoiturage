package org.example.covoiturage.entités;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Administrateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String nom_adm;
    @Column(nullable = false, unique = true)
    private String email_adm;
    @Column(nullable = false)
    private String mdp_adm;

}

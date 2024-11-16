package org.example.covoiturage.entités;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Conducteur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne
    @MapsId
    @JoinColumn(name = "id_utilisateur", referencedColumnName = "id", nullable = false)
    private Utilisateur utilisateur;
    private String photo;
    private String num_permis;
    private String adresse;
    private String cin;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_vehicule", referencedColumnName = "id", nullable = false)
    private Vehicule vehicule;
}

package org.example.covoiturage.entités;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Passager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id_utilisateur_pass", referencedColumnName = "id", nullable = false)
    private Utilisateur utilisateur;
}

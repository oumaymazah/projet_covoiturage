package org.example.covoiturage.dto;

import lombok.Data;
import org.example.covoiturage.entités.Vehicule;

//cette classe permet de transferer le donner de l'inscription de backend vers le frontend
@Data
public class SignupRequest {
    private String email;
    private String nom_complet;
    private String mdp_utilisateur;
    private String role;
    private String tel;

    // Spécifique Conducteur
    private String num_permis;
    private String adresse;
    private String cin;
    private String photo;
    // les données de vehicule
    private String type;
    private String image;
    private String num_matricule;
    private Vehicule.EtatVehicule etat;
    private int degre_confort;

}

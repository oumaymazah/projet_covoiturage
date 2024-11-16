package org.example.covoiturage.repositories;

import org.example.covoiturage.entités.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {
   //declarer une methode pour recupérer les utilisateur a partire de son email
    Optional<Utilisateur> findByEmail(String email);
    Optional<Utilisateur> findById(Integer id);
}

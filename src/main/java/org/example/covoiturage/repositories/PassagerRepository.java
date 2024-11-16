package org.example.covoiturage.repositories;

import org.example.covoiturage.entités.Conducteur;
import org.example.covoiturage.entités.Passager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassagerRepository extends JpaRepository<Passager,Integer> {
}

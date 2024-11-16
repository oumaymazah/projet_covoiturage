package org.example.covoiturage.repositories;

import org.example.covoiturage.entités.Conducteur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConducteurRepository extends JpaRepository<Conducteur,Integer> {

}

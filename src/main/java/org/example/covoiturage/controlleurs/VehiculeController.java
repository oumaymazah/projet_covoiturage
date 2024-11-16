package org.example.covoiturage.controlleurs;

import lombok.AllArgsConstructor;
import org.example.covoiturage.entités.Vehicule;
import org.example.covoiturage.services.VehiculeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// Le contrôleur est une classe responsable du traitement des requêtes HTTP et de la gestion des réponses.

@RestController
@RequestMapping("/api/vehicules")  //toutes les méthodes dans cette classe auront un préfixe d'URL , ce qui signifie que toutes les routes du contrôleur commenceront par cette URL.
@AllArgsConstructor
public class VehiculeController {
    private final VehiculeService vehiculeService;  // Déclaration du service qui gère la logique métier


    @PostMapping  // L'annotation @PostMapping mappe cette méthode aux requêtes HTTP POST
    public ResponseEntity<Vehicule> createVehicule(@RequestBody Vehicule vehicule) {
        // La requête contient un corps JSON qui sera converti en un objet Vehicule grâce à @RequestBody
        Vehicule nouveauVehicule = vehiculeService.createVehicule(vehicule);
        // Retourne une réponse HTTP 201 (Created) avec le nouveau véhicule dans le corps de la réponse
        return ResponseEntity.status(HttpStatus.CREATED).body(nouveauVehicule);
    }

    @GetMapping  // L'annotation @GetMapping mappe cette méthode aux requêtes HTTP GET sur l'URL /vehicules
    public ResponseEntity<List<Vehicule>> getAllVehicules() {
        List<Vehicule> vehicules = vehiculeService.getAllVehicules();  // Appel du service pour obtenir la liste des véhicules
        // Si la liste est vide, retourne un statut 204 (No Content), sinon retourne la liste des véhicules avec un statut 200 (OK)
        return vehicules.isEmpty() ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(vehicules);
    }

    @GetMapping("/{id}")  // @GetMapping avec une variable de chemin {id} pour indiquer l'ID du véhicule
    public ResponseEntity<Vehicule> getVehiculeById(@PathVariable int id) {
        // @PathVariable permet d'extraire l'ID de la requête HTTP pour rechercher le véhicule correspondant
        Optional<Vehicule> vehicule = vehiculeService.getVehiculeById(id);  // Appel du service pour obtenir le véhicule par ID
        // Si le véhicule est trouvé, retourne une réponse HTTP 200 (OK), sinon retourne un statut 404 (Not Found)
        return vehicule.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }


    @DeleteMapping("/{id}")  // @DeleteMapping mappe cette méthode aux requêtes HTTP DELETE
    public ResponseEntity<Void> DeleteVehicule(@PathVariable int id) {
        boolean supprimé = vehiculeService.DeleteVehicule(id);  // Appel du service pour supprimer le véhicule
        // Si le véhicule a été supprimé, retourne un statut HTTP 204 (No Content), sinon retourne un statut 404 (Not Found)
        return supprimé ? ResponseEntity.noContent().build()
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/{id}")  // @PutMapping mappe cette méthode aux requêtes HTTP PUT pour mettre à jour un véhicule
    public ResponseEntity<Vehicule> UpdateVehicule(@PathVariable int id, @RequestBody Vehicule vehicule) {
        // @RequestBody permet de désérialiser l'objet JSON du corps de la requête en un objet Vehicule
        Vehicule vehiculeMisAJour = vehiculeService.UpdateVehicule(id, vehicule);  // Appel du service pour mettre à jour le véhicule
        // Si le véhicule a été mis à jour, retourne une réponse HTTP 200 (OK) avec le véhicule mis à jour,
        // sinon retourne un statut HTTP 404 (Not Found) si le véhicule n'a pas été trouvé
        return vehiculeMisAJour != null ? ResponseEntity.ok(vehiculeMisAJour)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


    //ResponseEntity est une classe de Spring qui permet de personnaliser la réponse HTTP dans une API REST.
    // Elle permet de contrôler à la fois le corps de la réponse, les en-têtes HTTP, et le statut HTTP.
    // Son rôle principal est de fournir une réponse complète à une requête HTTP,
    // en permettant un contrôle total sur la réponse envoyée au client.
}

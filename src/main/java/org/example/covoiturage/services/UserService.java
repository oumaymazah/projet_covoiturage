package org.example.covoiturage.services;

import lombok.AllArgsConstructor;
import org.example.covoiturage.dto.SignupRequest;
import org.example.covoiturage.entités.Conducteur;
import org.example.covoiturage.entités.Passager;
import org.example.covoiturage.entités.Utilisateur;
import org.example.covoiturage.entités.Vehicule;
import org.example.covoiturage.repositories.ConducteurRepository;
import org.example.covoiturage.repositories.PassagerRepository;
import org.example.covoiturage.repositories.UtilisateurRepository;
import org.example.covoiturage.repositories.VehiculeRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.springframework.security.authorization.AuthorityAuthorizationManager.hasRole;

@Service
@AllArgsConstructor
public class UserService {
    private final UtilisateurRepository utilisateurRepository;
    private final ConducteurRepository conducteurRepository;
    private final PassagerRepository passagerRepository;
    private PasswordEncoder passwordEncoder;
    private final VehiculeRepository vehiculeRepository;
    @Transactional//assure que le user enregistrer dans la table utilisateur avec le role conducteur et enregistrer aussi dansla table conducteur
    //si l'enregistrement est echoué dans l'un de deux tables l'enregistrement soit annulé pour le 2 table
    public void registerUser(SignupRequest signupRequest){
        if (utilisateurRepository.findByEmail(signupRequest.getEmail()).isPresent()) {
            throw new RuntimeException("Email déjà utilisé");
        }
        else{

            Utilisateur user=new Utilisateur();
            user.setNom_complet(signupRequest.getNom_complet());
            user.setEmail(signupRequest.getEmail());
            user.setMdp_utilisateur(passwordEncoder.encode(signupRequest.getMdp_utilisateur()));
            user.setRole(signupRequest.getRole());
            user.setTel(signupRequest.getTel());
            utilisateurRepository.save(user);
            if("conducteur".equalsIgnoreCase(signupRequest.getRole())){
                Conducteur conducteur=new Conducteur();
                conducteur.setUtilisateur(user);
                conducteur.setNum_permis(signupRequest.getNum_permis());
                conducteur.setAdresse(signupRequest.getAdresse());
                conducteur.setCin(signupRequest.getCin());
                conducteur.setPhoto(signupRequest.getPhoto());
                Vehicule vehicule=new Vehicule();
                vehicule.setType(signupRequest.getType());
                vehicule.setImage(signupRequest.getImage());
                vehicule.setNum_matricule(signupRequest.getNum_matricule());
                vehicule.setDegre_confort(signupRequest.getDegre_confort());
                vehicule.setEtat(signupRequest.getEtat());
                vehiculeRepository.save(vehicule);
                conducteur.setVehicule(vehicule);
                conducteurRepository.save(conducteur);
            }
            else if("passager".equalsIgnoreCase(signupRequest.getRole())){
                Passager passager=new Passager();
                passager.setUtilisateur(user);
                passagerRepository.save(passager);

            }
    }}
    //creer une methode pour valider l'inscription de user

    public void validateUser(Integer userId){
        //chercher l'utilisateur a partir de son id ,si l'id n'est pas trouvé il genere une exception
        Utilisateur user=utilisateurRepository.findById(userId).orElseThrow(() ->new RuntimeException(("utilisateur non trouvé")));
        //si l'utilisateur trouvé on medifier le booléan en true càd l'utilsateur est valider
        user.setIsEnabled(true);
        //Cette ligne sauvegarde la mise à jour dans la base de données, ce qui applique la validation de l'utilisateur.
        utilisateurRepository.save(user);
    }

}

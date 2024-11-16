package org.example.covoiturage.controlleurs;

import lombok.AllArgsConstructor;
import org.example.covoiturage.dto.LoginRequest;
import org.example.covoiturage.dto.SignupRequest;
import org.example.covoiturage.entités.Utilisateur;
import org.example.covoiturage.repositories.UtilisateurRepository;
import org.example.covoiturage.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final UtilisateurRepository utilisateurRepository;
    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody SignupRequest signupRequest){
        userService.registerUser(signupRequest);
        return ResponseEntity.ok("Inscription réussie!");
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        Utilisateur user = utilisateurRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        if (!user.getIsEnabled()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Votre compte n'a pas encore été validé par un administrateur.");
        } else {
            try {
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequest.getEmail(),
                                loginRequest.getPassword()
                        )
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
                return ResponseEntity.ok("Connexion réussie !");
            } catch (UsernameNotFoundException ex) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Erreur : " + ex.getMessage());
            } catch (BadCredentialsException ex) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email ou mot de passe incorrect.");
            }
        }
    }

}

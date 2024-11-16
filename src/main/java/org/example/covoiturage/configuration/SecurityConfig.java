package org.example.covoiturage.configuration;

import lombok.AllArgsConstructor;
import org.example.covoiturage.repositories.UtilisateurRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.example.covoiturage.entités.Utilisateur;

@Configuration
@EnableWebSecurity// son role: l'application doit etre proteger par une securité
@AllArgsConstructor
public class SecurityConfig {
    private final UtilisateurRepository utilisateurRepository;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)//csrf c'ent un attaque connue dans cyber security est il par defaut soit activé dans spring et nou travaillant sur un projet local donc s'est n'est pas nécessaire
               //definition des requetes qui sont autorisé et que n'est sont pas
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/signup", "/api/auth/login").permitAll()
                        .requestMatchers("/api/admin/validate/**").permitAll()
                        .requestMatchers("/api/vehicules/**").permitAll()
                        .anyRequest().authenticated()//le reste de requetes soit identifiées
                ).build();
    }

    /*@Bean
    public UserDetailsService adminDetailsService() {
        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder().encode("admin123*"))
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(admin);
    }*/
    @Bean
    public UserDetailsService userDetailsService() {
        return email -> utilisateurRepository.findByEmail(email)
                .filter(Utilisateur::getIsEnabled)
                .map(user -> org.springframework.security.core.userdetails.User
                        .withUsername(user.getEmail())
                        .password(user.getMdp_utilisateur())
                        .roles(user.getRole())
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non validé ou introuvable : " + email));
    }


    // cette methode permet de cripter le mdp lors de l'enregistrement dans la bd
    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}

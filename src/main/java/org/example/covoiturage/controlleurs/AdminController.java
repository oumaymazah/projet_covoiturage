package org.example.covoiturage.controlleurs;

import ch.qos.logback.classic.Logger;
import lombok.AllArgsConstructor;
import org.example.covoiturage.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@AllArgsConstructor
public class AdminController {
    private final UserService userService;
    @PutMapping("/validate/{userId}")
    public ResponseEntity<String> validateUser(@PathVariable Integer userId){
        userService.validateUser(userId);
        return ResponseEntity.ok("Utilisateur validé !");
    }
}

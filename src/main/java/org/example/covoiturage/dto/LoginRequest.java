package org.example.covoiturage.dto;

import lombok.Data;

//cette classe permet de transferer le données de login de beckend vers le frontend
@Data
public class LoginRequest {
    private String email;
    private String password;
}

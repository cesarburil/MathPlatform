package br.com.cesarburil.mathBackend.profile.model;

import jakarta.persistence.*;
import lombok.*;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Profile {
    private String full_name;
    private String cpf;
    private String phone;
    private String bio;
    private String instagram;
}

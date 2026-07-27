package br.com.cesarburil.mathBackend.auth.model;

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
}

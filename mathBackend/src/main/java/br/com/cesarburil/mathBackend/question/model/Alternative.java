package br.com.cesarburil.mathBackend.question.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "alternatives")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Alternative {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private boolean correct;
    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;
}

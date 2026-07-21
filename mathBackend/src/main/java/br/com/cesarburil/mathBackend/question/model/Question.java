package br.com.cesarburil.mathBackend.question.model;

import br.com.cesarburil.mathBackend.category.model.Category;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "questions")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title", length = 1024)
    private String title;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    private Difficulty difficulty;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "question", fetch = FetchType.LAZY)
    private List<Alternative> alternatives = new ArrayList<>();
    @Column(name = "video", length = 512)
    private String video;
}

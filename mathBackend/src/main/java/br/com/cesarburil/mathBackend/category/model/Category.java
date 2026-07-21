package br.com.cesarburil.mathBackend.category.model;

import br.com.cesarburil.mathBackend.lesson.model.Lesson;
import br.com.cesarburil.mathBackend.question.model.Question;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "categories")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<Lesson> lessons = new ArrayList<>();
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private Set<Question> questions;
}

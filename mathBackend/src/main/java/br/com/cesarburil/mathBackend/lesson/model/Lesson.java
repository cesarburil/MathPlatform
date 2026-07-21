package br.com.cesarburil.mathBackend.lesson.model;

import br.com.cesarburil.mathBackend.category.model.Category;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "lessons")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    private String description;
    @Column(name = "video", length = 512)
    private String video;


}

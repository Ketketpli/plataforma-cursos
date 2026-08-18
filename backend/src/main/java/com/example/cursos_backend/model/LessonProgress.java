package com.example.cursos_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "progress", uniqueConstraints = @UniqueConstraint(columnNames = {"enrollment_id", "lesson_id"}))
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LessonProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "progress_seq")
    @SequenceGenerator(name = "progress_seq", sequenceName = "progress_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enrollment_id", nullable = false)
    private Enrollment enrollment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;

    @Column(nullable = false)
    private boolean completed;
}

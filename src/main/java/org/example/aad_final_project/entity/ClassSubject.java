package org.example.aad_final_project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ClassSubject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "class_id",nullable = false)
    private ClassEntity classEntity;

    @ManyToOne
    @JoinColumn(name = "subject_id",nullable = false)
    private Subject subject;

    private String subject_name;

    private String class_name;

    private String time_duration;

}

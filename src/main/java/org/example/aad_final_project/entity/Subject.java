package org.example.aad_final_project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int subject_id;
    private String subject_name;
    private String subject_code;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
    private List<ClassSubject> classSubjects;

    @OneToMany(mappedBy = "subject",cascade = CascadeType.ALL)
    private List<Instructor> instructors;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
    private List<StudentSubject> studentSubjects;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
    private List<SubjectSchedule> subjectSchedules;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
    private List<PaperMark> paperMarks;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;

    private String grade_range;
}

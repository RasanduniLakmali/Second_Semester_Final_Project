package org.example.aad_final_project.entity;

import jakarta.persistence.*;
import lombok.*;

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
    private double fees;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private User admin;

    @OneToMany(mappedBy ="subject", cascade = CascadeType.ALL )
    private List<Attendance> attendances;

    private String grade_range;

    private String time_duration;

}

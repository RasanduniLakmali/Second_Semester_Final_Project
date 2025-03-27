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

@Table(name = "class")
public class ClassEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int class_id;
    private String class_name;
    private int capacity;
    private String hall_number;
    private String admin_name;

    @OneToMany(mappedBy = "classEntity", cascade = CascadeType.ALL)
    private List<Schedule> schedules;

    @OneToMany(mappedBy = "classEntity", cascade = CascadeType.ALL)
    private List<ClassStudent> classStudents;

    @OneToMany(mappedBy = "classEntity", cascade = CascadeType.ALL)
    private List<ClassInstructor> classInstructors;

    @OneToMany(mappedBy = "classEntity", cascade = CascadeType.ALL)
    private List<ClassSubject> classSubjects;

    @OneToMany(mappedBy ="classEntity", cascade = CascadeType.ALL )
    private List<Payment> payments;

    @OneToMany(mappedBy ="classEntity", cascade = CascadeType.ALL )
    private List<Attendance> attendances;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;
}

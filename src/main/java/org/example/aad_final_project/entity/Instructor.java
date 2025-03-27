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
public class Instructor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int instructor_id;
    private String instructor_name;
    private String image;
    private String address;
    private String phone;
    private String email;
    private String qualification;
    private String subject_code;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL)
    private List<ClassInstructor> classInstructors;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;

    @OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL)
    private List<InstructorSchedule> instructorSchedules;
}

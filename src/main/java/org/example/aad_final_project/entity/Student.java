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
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int student_id;
    private String student_name;
    private int age;
    private String email;
    private String phone;
    private String address;
    private String school;
    private String image;

    @OneToMany(mappedBy = "student",cascade = CascadeType.ALL)
    private List<Payment> payments;

    @OneToMany(mappedBy = "student",cascade = CascadeType.ALL)
    private List<Attendance> attendances;

    @OneToMany(mappedBy = "student",cascade = CascadeType.ALL)
    private List<ClassStudent> classStudents;

    @OneToMany(mappedBy = "student",cascade = CascadeType.ALL)
    private List<PaperMark> paperMarks;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private int admin_id;
}

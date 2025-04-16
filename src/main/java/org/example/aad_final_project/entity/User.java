package org.example.aad_final_project.entity;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String password;
    @Column(unique = true)
    private String email;
    private String mobile;
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "admin",cascade = CascadeType.ALL)
    private List<ClassEntity> classes;

    @OneToMany(mappedBy = "admin",cascade = CascadeType.ALL)
    private List<Subject> subjects;

    private User(String name, String email, String mobile) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
    }
//    @OneToMany(mappedBy = "admin",cascade = CascadeType.ALL)
//    private List<Payment> payments;
}

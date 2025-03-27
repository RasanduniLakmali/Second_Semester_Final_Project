package org.example.aad_final_project.dto;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class StudentDTO {

    private int student_id;
    private String student_name;
    private int age;
    private String email;
    private String phone;
    private String address;
    private String school;
    private String image;
    private String adminId;

    public StudentDTO(String student_name, int age, String email, String phone, String address, String school, String image, String adminId) {
        this.student_name = student_name;
        this.age = age;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.school = school;
        this.image = image;
        this.adminId = adminId;
    }

    public StudentDTO(String student_name, int age, String email, String phone, String address, String school) {
        this.student_name = student_name;
        this.age = age;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.school = school;
    }
}

package org.example.aad_final_project.dto;


import jakarta.validation.constraints.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class StudentDTO {

    private int student_id;

    @NotBlank(message = "Student name is required")
    private String student_name;

    @Min(value = 5, message = "Age should be at least 5")
    @Max(value = 100, message = "Age should be less than 100")
    private int age;

    @Email(message = "Invalid email format")
    private String email;

    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
    private String phone;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "School is required")
    private String school;

    private String image;

    private int userId;

//    @NotNull(message = "Admin ID is required")
    private int admin_id;

    public StudentDTO(String student_name, int age, String email, String phone, String address, String school, String image,Integer admin_id) {
        this.student_name = student_name;
        this.age = age;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.school = school;
        this.image = image;
        this.admin_id = admin_id;
    }

    public StudentDTO(String student_name, int age, String email, String phone, String address, String school) {
        this.student_name = student_name;
        this.age = age;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.school = school;
    }
    public StudentDTO(String student_name,int age,String email, String phone, String address, String school,String image) {
        this.student_name = student_name;
        this.age = age;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.school = school;
        this.image = image;
    }
}

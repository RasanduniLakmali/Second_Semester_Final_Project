package org.example.aad_final_project.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class InstructorDTO {

    private int instructor_id;
    @NotBlank(message = "Instructor name cannot be blank")
    @Size(min = 3, max = 100, message = "Instructor name must be between 3 and 100 characters")
    private String instructor_name;

    private String image; // Optional — add validation if needed

    @NotBlank(message = "Address cannot be blank")
    @Size(min = 5, max = 255, message = "Address must be between 5 and 255 characters")
    private String address;

    @NotBlank(message = "Phone number cannot be blank")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be exactly 10 digits")
    private String phone;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Qualification cannot be blank")
    private String qualification;

    @NotBlank(message = "Subject code cannot be blank")
    private String subject_code;

    @NotNull(message = "Subject ID cannot be null")
    private Integer subject_id;

    @NotNull(message = "User ID cannot be null")
    private int user_id;

    @NotNull(message = "Admin ID cannot be null")
    private int admin_id;


    public InstructorDTO(String instructor_name,String image, String address, String phone, String email, String qualification, String subject_code, Integer subject_id, Integer admin_id) {
        this.instructor_name = instructor_name;
        this.image = image;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.qualification = qualification;
        this.subject_code = subject_code;
        this.subject_id = subject_id;
        this.admin_id = admin_id;
    }


}

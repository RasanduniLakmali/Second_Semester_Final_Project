package org.example.aad_final_project.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ClassInstructorDTO {

    @Min(value = 1, message = "ID must be greater than 0")
    private int id;

    @Min(value = 1, message = "Class ID must be greater than 0")
    private int class_id;

    @NotBlank(message = "Class name cannot be blank")
    private String class_name;

    @Min(value = 1, message = "Instructor ID must be greater than 0")
    private int instructor_id;

    @NotBlank(message = "Instructor name cannot be blank")
    private String instructor_name;
}

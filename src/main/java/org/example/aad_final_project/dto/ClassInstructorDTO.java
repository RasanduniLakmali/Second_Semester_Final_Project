package org.example.aad_final_project.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ClassInstructorDTO {

    private int id;
    private int class_id;
    private String class_name;
    private int instructor_id;
    private String instructor_name;
}

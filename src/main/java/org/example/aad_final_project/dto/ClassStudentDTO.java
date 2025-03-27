package org.example.aad_final_project.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ClassStudentDTO {

    private int id;
    private int class_id;
    private int student_id;
    private String class_name;
    private String student_name;
}

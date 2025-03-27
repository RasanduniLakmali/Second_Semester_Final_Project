package org.example.aad_final_project.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class StudentSubjectDTO {

    private int id;
    private int student_id;
    private int subject_id;
    private String student_name;
    private String subject_name;
    private String instructor_name;
}

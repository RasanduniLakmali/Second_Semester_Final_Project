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

    public StudentSubjectDTO(String student_name, String subject_name, String instructor_name) {
        this.student_name = student_name;
        this.subject_name = subject_name;
        this.instructor_name = instructor_name;
    }
}

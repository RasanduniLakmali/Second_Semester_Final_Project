package org.example.aad_final_project.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SubjectDTO {

    private int subject_id;
    private String subject_name;
    private String subject_code;
    private String admin_id;
    private String grade_range;

    public SubjectDTO(String subject_name, String subject_code, String admin_id,String grade_range) {
        this.subject_name = subject_name;
        this.subject_code = subject_code;
        this.admin_id = admin_id;
        this.grade_range = grade_range;
    }
}

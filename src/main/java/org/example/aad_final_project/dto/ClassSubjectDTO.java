package org.example.aad_final_project.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ClassSubjectDTO {

    private int id;
    private int class_id;
    private int subject_id;
    private String subject_name;
    private String class_name;
    private String time_duration;
}

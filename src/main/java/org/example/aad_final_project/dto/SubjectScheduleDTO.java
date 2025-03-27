package org.example.aad_final_project.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SubjectScheduleDTO {

    private int id;
    private int subject_id;
    private int schedule_id;
    private String subject_name;
}

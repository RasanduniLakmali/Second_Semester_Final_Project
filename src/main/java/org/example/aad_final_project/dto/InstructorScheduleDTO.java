package org.example.aad_final_project.dto;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class InstructorScheduleDTO {

    private int id;
    private int instructor_id;
    private int schedule_id;
    private String instructor_name;
}

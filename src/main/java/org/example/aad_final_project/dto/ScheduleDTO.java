package org.example.aad_final_project.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ScheduleDTO {

    private int schedule_id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate schedule_date;
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime start_time;

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime end_time;
    private int class_id;
    private String class_name;
    private String instructor_name;
    private String subject_name;
    private String schedule_day;

}

package org.example.aad_final_project.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AttendanceDTO {

    private int attendance_id;

    @NotNull(message = "Attendance date is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate attendance_date;

    @NotBlank(message = "Status is required")
    private String status;

    @NotBlank(message = "Student name is required")
    private String student_name;

    @NotBlank(message = "Class name is required")
    private String class_name;

    @NotBlank(message = "Subject name is required")
    private String subject_name;

    @Min(value = 1, message = "Student ID must be valid")
    private int student_id;

    @Min(value = 1, message = "Subject ID must be valid")
    private int subject_id;

//    private AttendanceDTO(int student_id,String student_name,)
}

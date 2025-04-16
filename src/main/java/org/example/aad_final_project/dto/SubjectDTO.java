package org.example.aad_final_project.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SubjectDTO {

    private int subject_id;
    @NotBlank(message = "Subject name is required")
    private String subject_name;

    @NotBlank(message = "Subject code is required")
    private String subject_code;

    @NotNull(message = "Admin ID is required")
    @Min(value = 1, message = "Admin ID must be a positive number")
    private Integer admin_id;

    @NotBlank(message = "Grade range is required")
    private String grade_range;

    @NotBlank(message = "Time duration is required")
    private String time_duration;

    @Positive(message = "Fees must be a positive value")
    private double fees;


    public SubjectDTO(String subject_name, String subject_code, Integer admin_id,String grade_range,String time_duration,double fees) {
        this.subject_name = subject_name;
        this.subject_code = subject_code;
        this.admin_id = admin_id;
        this.grade_range = grade_range;
        this.time_duration = time_duration;
        this.fees = fees;
    }
}

package org.example.aad_final_project.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ClassDTO {


    private int class_id;

    @NotBlank(message = "Class name cannot be blank")
    @Size(min = 3, max = 100, message = "Class name should be between 3 and 100 characters")
    private String class_name;

    @Min(value = 1, message = "Capacity must be greater than 0")
    private int capacity;

    @NotBlank(message = "Hall number cannot be blank")
    private String hall_number;

    @NotNull(message = "Admin ID cannot be null")
    private int admin_id;

    @NotBlank(message = "Admin name cannot be blank")
    @Size(min = 3, max = 100, message = "Admin name should be between 3 and 100 characters")
    private String admin_name;

    @DecimalMin(value = "0.0", inclusive = false, message = "Class fee must be greater than 0")
    private double class_fee;

}

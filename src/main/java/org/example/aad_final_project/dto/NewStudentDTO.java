package org.example.aad_final_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class NewStudentDTO {

    private int id;
    private String name;
    private String class_name;
    private String subject_name;
    private String email;
    private String phone;
    private String message;
}

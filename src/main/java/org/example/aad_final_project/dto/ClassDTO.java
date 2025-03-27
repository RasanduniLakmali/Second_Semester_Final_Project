package org.example.aad_final_project.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ClassDTO {

    private int class_id;
    private String class_name;
    private int capacity;
    private String hall_number;
    private int admin_id;
    private String admin_name;
}

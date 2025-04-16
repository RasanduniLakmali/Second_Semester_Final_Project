package org.example.aad_final_project.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PlannerRequestDTO {
    private String email;
    private int freeHours;
}

package org.example.aad_final_project.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PaymentDTO {

    private int payment_id;
    private int student_id;
    private int class_id;
    private Double total_amount;
    private String month;
    private String card_number;
    private String card_name;
    private LocalDateTime createdAt;
    private String student_name;
    private String class_name;
    private String status;
}

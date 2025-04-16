package org.example.aad_final_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PasswordChangeRequestDTO {

    private String email;
    private String currentPassword;
    private String newPassword;
}

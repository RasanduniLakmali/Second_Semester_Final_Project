package org.example.aad_final_project.service;

import org.example.aad_final_project.dto.PasswordChangeRequestDTO;
import org.example.aad_final_project.dto.UserDTO;
import org.example.aad_final_project.entity.Role;

import java.util.List;
import java.util.Optional;

public interface AdminService {

    public List<Integer> getAllIds();

    List<String> getAllNames();

    public Integer getAdmin(Role role, String adminName);

    String getAdminName(Integer adminId);

    boolean adminLogin(String email, String password);

    UserDTO getAdminByEmail(String email);

    UserDTO updateAdmin(UserDTO userDTO);

    boolean changePassword(PasswordChangeRequestDTO request);
}

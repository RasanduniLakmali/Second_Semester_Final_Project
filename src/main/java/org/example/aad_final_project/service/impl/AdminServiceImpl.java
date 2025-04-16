package org.example.aad_final_project.service.impl;

import org.example.aad_final_project.dto.AttendanceSummaryDTO;
import org.example.aad_final_project.dto.PasswordChangeRequestDTO;
import org.example.aad_final_project.dto.UserDTO;
import org.example.aad_final_project.entity.Role;
import org.example.aad_final_project.entity.User;
import org.example.aad_final_project.repo.AdminRepo;
import org.example.aad_final_project.repo.AttendanceRepo;
import org.example.aad_final_project.repo.UserRepository;
import org.example.aad_final_project.service.AdminService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepo adminRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AttendanceRepo attendanceRepo;

    @Override
    public List<Integer> getAllIds() {
        return adminRepo.findByRole(Role.ADMIN)
                .stream()
                .map(User::getId)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllNames() {
        return adminRepo.findByRole(Role.ADMIN)
                .stream()
                .map(User::getName)
                .collect(Collectors.toList());
    }

    @Override
    public Integer getAdmin(Role role,String adminName) {
        System.out.println(role + " " + adminName);

//        return adminRepo.findByRoleAndName(Role.ADMIN, adminName)
//                .map(User::getId)
//                .orElse(null);
        Integer adminId = adminRepo.findByRoleAndName(role, adminName);
        System.out.println("exist Id " + adminId);
        return adminId;
    }

    @Override
    public String getAdminName(Integer adminId) {
        return adminRepo.findById(adminId)
                .filter(user -> user.getRole() == Role.ADMIN)
                .map(User::getName)
                .orElse(null);
    }

    @Override
    public boolean adminLogin(String email, String password) {
        Optional<User> admin = adminRepo.findByAdminEmail(email);

        if (admin.isPresent()) {
            return admin.get().getPassword().equals(password);
        }
        return false;
    }

    @Override
    public UserDTO getAdminByEmail(String email) {
        Optional<User> admin =adminRepo.findByEmail(email);
        return modelMapper.map(admin.get(), UserDTO.class);
    }

    @Override
    public UserDTO updateAdmin(UserDTO userDTO) {
        Optional<User> optionalUser = adminRepo.findByAdminEmail(userDTO.getEmail());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setName(userDTO.getName());
            user.setEmail(userDTO.getEmail());
            user.setMobile(userDTO.getMobile());
            adminRepo.save(user);
            return modelMapper.map(user, UserDTO.class);
        }
        return null;
    }

    // AdminServiceImpl.java
    @Override
    public boolean changePassword(PasswordChangeRequestDTO request) {
        Optional<User> optionalAdmin = adminRepo.findByEmail(request.getEmail());

        if (optionalAdmin.isEmpty()) {
            throw new RuntimeException("Admin not found.");
        }

        if (optionalAdmin.isPresent()){
            User admin = optionalAdmin.get();

            // Use BCryptPasswordEncoder directly
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            // Check current password
            if (!passwordEncoder.matches(request.getCurrentPassword(), admin.getPassword())) {
                throw new RuntimeException("Current password is incorrect.");
            }

            // Encode and set new password
            admin.setPassword(passwordEncoder.encode(request.getNewPassword()));
            adminRepo.save(admin);
            return true;
        }
       return false;
    }


}

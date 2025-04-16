package org.example.aad_final_project.controller;

import jakarta.validation.Valid;
import org.example.aad_final_project.config.EmailUsernameAuthenticationToken;
import org.example.aad_final_project.dto.UserDTO;
import org.example.aad_final_project.dto.AuthDTO;
import org.example.aad_final_project.dto.ResponseDTO;
import org.example.aad_final_project.service.impl.UserServiceImpl;
import org.example.aad_final_project.util.JwtUtil;
import org.example.aad_final_project.util.VarList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "Authorization")
@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserServiceImpl userService;
    private final ResponseDTO responseDTO;

    //constructor injection
    public AuthController(JwtUtil jwtUtil, AuthenticationManager authenticationManager, UserServiceImpl userService, ResponseDTO responseDTO) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.responseDTO = responseDTO;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<ResponseDTO> authenticate(@RequestBody UserDTO userDTO) {

        System.out.println(userDTO.toString());

        try {
            Authentication authentication;

            if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
                // 🔹 Admin authentication (Email + Password)
                authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(userDTO.getEmail(), userDTO.getPassword()));
            } else {
                // 🔹 Student/Instructor authentication (Email + Username)
                authentication = authenticationManager.authenticate(
                        new EmailUsernameAuthenticationToken(userDTO.getEmail(), userDTO.getName()));
            }

            // 🔹 Retrieve authenticated user
            UserDTO loadedUser = userService.loadUserDetailsByUsername(userDTO.getEmail());

            System.out.println("loaduser :" + loadedUser);

            // 🔹 Generate JWT token
            String token = jwtUtil.generateToken(loadedUser);
            if (token == null || token.isEmpty()) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ResponseDTO(VarList.Internal_Server_Error, "Token Generation Failed", null));
            }

            // 🔹 Return the token
            AuthDTO authDTO = new AuthDTO();
            authDTO.setEmail(loadedUser.getEmail());
            authDTO.setToken(token);
            authDTO.setUsername(loadedUser.getName());

            return ResponseEntity.ok(new ResponseDTO(VarList.OK, "Success", authDTO));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseDTO(VarList.Unauthorized, "Invalid Credentials", e.getMessage()));
        }
    }



}


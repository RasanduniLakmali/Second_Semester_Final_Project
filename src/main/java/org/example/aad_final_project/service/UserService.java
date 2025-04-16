package org.example.aad_final_project.service;


import org.example.aad_final_project.dto.UserDTO;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public interface UserService {
    int saveUser(UserDTO adminDTO);
    UserDTO searchUser(String username);

    public UserDTO loadUserDetailsByUsername(String email) throws UsernameNotFoundException;
}
package org.example.aad_final_project.service.impl;

import org.example.aad_final_project.dto.UserDTO;
import org.example.aad_final_project.entity.Role;
import org.example.aad_final_project.entity.User;
import org.example.aad_final_project.repo.UserRepository;
import org.example.aad_final_project.service.UserService;
import org.example.aad_final_project.util.VarList;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        System.out.println(email);
        User admin = userRepository.findByEmail(email);

        System.out.println(admin.toString());

        if (admin == null) {
            System.out.println("admin null");
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        if(admin.getRole() == Role.STUDENT){
            String password = admin.getPassword();
            if (password == null || password.isEmpty()) {
                // If the password is missing, provide a default or empty password
                password = ""; // You can also use a default password here if needed

                return new org.springframework.security.core.userdetails.User(admin.getEmail(), password, getAuthority(admin));
            }
        }

        return new org.springframework.security.core.userdetails.User(admin.getEmail(), admin.getPassword(), getAuthority(admin));
    }

    // 🔍 Fetch user details using email
    public UserDTO loadUserDetailsByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            System.out.println("User not found with email: " + email);
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        return modelMapper.map(user, UserDTO.class);
    }

    // 🔍 Authenticate using email & username
    public UserDTO loadUserByEmailAndUsername(String email, String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        if (!user.getName().equals(username)) {
            throw new UsernameNotFoundException("Invalid username for the given email.");
        }

        return modelMapper.map(user, UserDTO.class);
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));

        System.out.println(user.getRole());

        return authorities;
    }

    @Override
    public UserDTO searchUser(String email) {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            return null;
        }

        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public int saveUser(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            return VarList.Not_Acceptable; // Email already exists
        }

        // Encode password before saving
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        // Ensure role is properly set
        if (userDTO.getRole() == null) {
            userDTO.setRole(Role.STUDENT); // Default role (change if needed)
        }

        // Convert DTO to Entity and save
        User user = modelMapper.map(userDTO, User.class);
        userRepository.save(user);

        return VarList.Created;
    }
}

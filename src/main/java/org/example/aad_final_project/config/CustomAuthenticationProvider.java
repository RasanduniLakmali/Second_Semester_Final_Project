package org.example.aad_final_project.config;


import org.example.aad_final_project.dto.UserDTO;
import org.example.aad_final_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import java.util.ArrayList;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) {
        EmailUsernameAuthenticationToken authToken = (EmailUsernameAuthenticationToken) authentication;
        String email = authToken.getPrincipal().toString();
        String username = authToken.getUsername();

        System.out.println("Authenticating user: " + email);

        // 🔹 Fetch user by email
        UserDTO user = userService.loadUserDetailsByUsername(email);
        if (user == null) {
            throw new BadCredentialsException("Invalid email or username");
        }

        // 🔹 Validate username
        if (!user.getName().equals(username)) {
            throw new BadCredentialsException("Invalid username for this email");
        }


        EmailUsernameAuthenticationToken authenticatedToken =
                new EmailUsernameAuthenticationToken(email, username);
        authenticatedToken.setAuthenticated(true);
        System.out.println("Authentication successful for: " + email);

        return authenticatedToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return EmailUsernameAuthenticationToken.class.isAssignableFrom(authentication);
    }
}



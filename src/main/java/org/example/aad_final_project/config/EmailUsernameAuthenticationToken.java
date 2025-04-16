package org.example.aad_final_project.config;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class EmailUsernameAuthenticationToken extends AbstractAuthenticationToken {
    private final String email;
    private final String username;

    public EmailUsernameAuthenticationToken(String email, String username) {
        super(null);
        this.email = email;
        this.username = username;
        setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return null; // No password required
    }

    @Override
    public Object getPrincipal() {
        return email;
    }

    public String getUsername() {
        return username;
    }
}

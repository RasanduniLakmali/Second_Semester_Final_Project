package org.example.aad_final_project.config;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.example.aad_final_project.service.impl.UserServiceImpl;
import org.example.aad_final_project.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserServiceImpl userService;
    @Value("${jwt.secret}")
    private String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String authorization = httpServletRequest.getHeader("Authorization");
        String token = null;
        String email = null;

        if (authorization != null && authorization.startsWith("Bearer ")) {
            token = authorization.substring(7);  // Extract the token from the header

            if (token == null || token.isEmpty()) {
                // Handle the case when the token is null or empty
                httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token is missing or invalid.");
                return; // Stop further processing
            }

            email = jwtUtil.getUsernameFromToken(token);
            Claims claims = jwtUtil.getUserRoleCodeFromToken(token);

            if (claims == null) {
                httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token.");
                return;
            }

            httpServletRequest.setAttribute("email", email);
            httpServletRequest.setAttribute("role", claims.get("role"));
        }

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userService.loadUserByUsername(email);

            if (jwtUtil.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails,
                                null, userDetails.getAuthorities());

                usernamePasswordAuthenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(httpServletRequest)
                );

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }


    private Claims getClaimsFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(token).getBody();
    }

}

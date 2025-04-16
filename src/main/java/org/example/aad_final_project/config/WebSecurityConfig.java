package org.example.aad_final_project.config;

import org.example.aad_final_project.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static ch.qos.logback.classic.spi.ThrowableProxyVO.build;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)

public class WebSecurityConfig {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(customAuthenticationProvider)
                .build();
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/v1/auth/authenticate",
                                "/api/v1/instructor/**",
                                "/api/v1/admin/**",
                                "/api/v1/subject/**",
                                "/api/v1/student/**",
                                "/api/v1/sbClass/getAll",
                                "/api/v1/sbClass/all",
                                "/api/v1/class/getName",
                                "/api/v1/class/**",
                                "/api/v1/stClass/**",
                                "/api/v1/stSubject/**",
                                "/api/v1/sbClass/**",
                                "/api/v1/schedule/**",
                                "/api/v1/sbSchedule/**",
                                "/api/v1/instSchedule/**",
                                "/api/v1/email/**",
                                "/api/v1/marks/**",
                                "/api/v1/payment/**",
                                "/api/v1/attendance/**",
                                "/api/v1/planner/**",
                                "/api/v1/user/register",
                                "/api/v1/auth/refreshToken",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html").permitAll()
                        .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
//                        .requestMatchers("/student/**").hasRole("STUDENT")
                        .requestMatchers("/payment/save").hasRole("STUDENT")
                        .requestMatchers("/instructor/**").hasRole("INSTRUCTOR")

                        .requestMatchers("/uploads/**").permitAll()
                        .anyRequest().authenticated()
                )
                .authenticationProvider(customAuthenticationProvider)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public DaoAuthenticationProvider usernamePasswordAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }
}

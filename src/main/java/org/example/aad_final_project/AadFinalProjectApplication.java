package org.example.aad_final_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "org.example.aad_final_project.repo")
@EntityScan(basePackages = "org.example.aad_final_project.entity")
public class AadFinalProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(AadFinalProjectApplication.class, args);
    }

}

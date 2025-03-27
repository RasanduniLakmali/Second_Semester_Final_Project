package org.example.aad_final_project.controller;

import org.example.aad_final_project.dto.ClassInstructorDTO;
import org.example.aad_final_project.service.ClassInstructorService;
import org.example.aad_final_project.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/v1/clInstructor")
public class ClassInstructorController {

    @Autowired
    private ClassInstructorService classInstructorService;


    @PostMapping("/save")
    public ResponseEntity<String> saveClassInstructor(@RequestBody ClassInstructorDTO classInstructorDTO) {
        try {

            classInstructorService.saveAll(classInstructorDTO);
            return ResponseEntity.ok("Classes assigned successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error while saving class instructor.");
        }
    }

}



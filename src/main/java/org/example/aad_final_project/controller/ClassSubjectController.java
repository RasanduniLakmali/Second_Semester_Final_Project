package org.example.aad_final_project.controller;


import org.example.aad_final_project.dto.ClassSubjectDTO;
import org.example.aad_final_project.dto.SubjectInstructorDTO;
import org.example.aad_final_project.service.ClassSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.example.aad_final_project.util.ResponseUtil;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/v1/sbClass")
public class ClassSubjectController {

    @Autowired
    private ClassSubjectService classSubjectService;


    @PostMapping("save")
    public ResponseUtil save(@RequestBody ClassSubjectDTO classSubjectDTO) {
        System.out.println(classSubjectDTO.toString());
        boolean isSaved =classSubjectService.save(classSubjectDTO);

        if (isSaved) {
            return new ResponseUtil(201, "Class subject detail saved!", null);
        } else {
            return new ResponseUtil(200, "Class subject detail not saved!", null);
        }
    }

    @GetMapping("getAll")
    public List<ClassSubjectDTO> getAll() {
        List<ClassSubjectDTO> classSubjectDTOS = classSubjectService.getAll();

        return classSubjectDTOS;
    }

    @PutMapping("update")
    public ResponseUtil update(@RequestBody ClassSubjectDTO classSubjectDTO) {

        System.out.println(classSubjectDTO.toString());

        boolean isUpdated = classSubjectService.update(classSubjectDTO);

        if (isUpdated) {
            return new ResponseUtil(201, "Class subject detail updated!", null);
        } else {
            return new ResponseUtil(200, "Class subject detail not updated!", null);
        }
    }


    @DeleteMapping("delete/{className}/{subjectName}")
    public ResponseUtil delete(@PathVariable String className, @PathVariable String subjectName) {
        boolean isDeleted = classSubjectService.delete(subjectName,className);

        if (isDeleted) {
            return new ResponseUtil(201, "Class subject detail deleted!", null);
        } else {
            return new ResponseUtil(200, "Class subject detail not deleted!", null);
        }
    }


    @GetMapping("all")
    public ResponseEntity<List<SubjectInstructorDTO>> getAllSubjects() {
        List<SubjectInstructorDTO> subjects = classSubjectService.getAllSubjects();
        return ResponseEntity.ok(subjects);
    }

}

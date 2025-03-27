package org.example.aad_final_project.controller;


import org.example.aad_final_project.dto.StudentSubjectDTO;
import org.example.aad_final_project.service.StudentSubjectService;
import org.example.aad_final_project.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/v1/stSubject")
public class StudentSubjectController {

    @Autowired
    private StudentSubjectService studentSubjectService;

    @PostMapping("save")
    public ResponseUtil saveStudentSubject(@RequestBody StudentSubjectDTO studentSubjectDTO) {
        System.out.println(studentSubjectDTO.toString());
        boolean isSaved = studentSubjectService.saveStudentSubject(studentSubjectDTO);
        if (isSaved) {
            return new ResponseUtil(201, "Detail saved!", null);
        } else {
            return new ResponseUtil(200, "Detail not saved!", null);
        }
    }

    @GetMapping("getSubject/{studentName}")
    public List<String> getSubjectNames(@PathVariable String studentName) {
         return studentSubjectService.getNames(studentName);
    }

    @PutMapping("update")
    public ResponseUtil updateStudentSubject(@RequestBody StudentSubjectDTO studentSubjectDTO) {
        System.out.println(studentSubjectDTO.toString());
        boolean isUpdated = studentSubjectService.update(studentSubjectDTO);
        System.out.println(isUpdated);
        if (isUpdated) {
            return new ResponseUtil(201, "Student subject detail updated!", null);
        } else {
            return new ResponseUtil(200, "Student subject detail not updated!", null);
        }
    }
}

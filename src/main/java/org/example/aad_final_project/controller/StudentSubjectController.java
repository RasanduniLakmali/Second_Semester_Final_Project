package org.example.aad_final_project.controller;


import org.example.aad_final_project.dto.ResponseDTO;
import org.example.aad_final_project.dto.StudentSubjectDTO;
import org.example.aad_final_project.service.StudentSubjectService;
import org.example.aad_final_project.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.aad_final_project.util.ResponseUtil;

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
    public ResponseEntity<ResponseDTO> updateStudentSubject(@RequestBody StudentSubjectDTO studentSubjectDTO) {
        System.out.println("before updated : " + studentSubjectDTO.toString());

        StudentSubjectDTO studentSubjectDTO1 = studentSubjectService.update(studentSubjectDTO);

        System.out.println("after updated : " + studentSubjectDTO1);

        if (studentSubjectDTO1 != null) {
            return ResponseEntity.ok(new ResponseDTO(VarList.OK, "Student subject detail updated!", studentSubjectDTO1));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(VarList.Not_Found, "Student subject detail  not updated!", null));
        }
    }

    @GetMapping("getAll")
    public List<StudentSubjectDTO> getAllStudentSubjects() {
        System.out.println(studentSubjectService.getAll());
        return studentSubjectService.getAll();
    }

}

package org.example.aad_final_project.controller;

import jakarta.validation.Valid;
import org.example.aad_final_project.dto.SubjectDTO;
import org.example.aad_final_project.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.example.aad_final_project.util.ResponseUtil;

import java.util.List;

@Validated
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/v1/subject")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;


    @PostMapping("save")
    public ResponseUtil saveSubject(@Valid @RequestBody SubjectDTO subjectDTO) {
        System.out.println(subjectDTO);
       boolean isSaved =  subjectService.saveSubject(subjectDTO);

        if (isSaved) {
            return new ResponseUtil(201, "Subject saved!", null);
        } else {
            return new ResponseUtil(200, "Subject not saved!", null);
        }
    }


    @GetMapping("getAll")
    public List<SubjectDTO> getAllSubject() {
        List<SubjectDTO> subjectDTOS = subjectService.getAllSubjects();

        // Debugging: Log the response
        subjectDTOS.forEach(subject ->
                System.out.println("Subject: " + subject.getSubject_name() +
                        ", Admin ID: " + subject.getAdmin_id())
        );

        return subjectDTOS;
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("update")
    public ResponseUtil updateSubject(@Valid @RequestBody SubjectDTO subjectDTO) {
        boolean isUpdated = subjectService.updateSubject(subjectDTO);
        System.out.println(isUpdated);
        if (isUpdated) {
            return new ResponseUtil(201, "Subject updated!", null);
        } else {
            return new ResponseUtil(200, "Subject not updated!", null);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("delete/{subjectName}")
    public ResponseUtil deleteSubject(@Valid @PathVariable String subjectName) {
        boolean isDeleted = subjectService.deleteSubject(subjectName);
        if (isDeleted) {
            return new ResponseUtil(201, "Subject deleted!", null);
        } else {
            return new ResponseUtil(200, "Subject not deleted!", null);
        }
    }


    @GetMapping("getCodes")
    public List<String> getSubjectCodes() {
        return subjectService.getAllCodes();
    }

    @GetMapping("getId/{subjectCode}")
    public Integer getSubjectId(@PathVariable String subjectCode) {
        return subjectService.getId(subjectCode);
    }

    @GetMapping("getSubName")
    public List<String> getSubjectNames(){
        return subjectService.getAllNames();
    }

    @GetMapping("getSubId/{subjectName}")
    public Integer getSubjectIds(@PathVariable String subjectName){
        System.out.println("Received Subject Name: " + subjectName);
        Integer id = subjectService.getSubId(subjectName);
        System.out.println("Returning Subject ID: " + id);
        return id;
    }

}

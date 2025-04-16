package org.example.aad_final_project.controller;

import org.example.aad_final_project.util.ResponseUtil;
import org.example.aad_final_project.dto.ClassStudentDTO;
import org.example.aad_final_project.service.ClassStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/v1/stClass")
public class ClassStudentController {

    @Autowired
    private ClassStudentService classStudentService;

    @PostMapping("save")
    public ResponseUtil saveStudentClass(@RequestBody ClassStudentDTO classStudentDTO) {
        System.out.println(classStudentDTO.toString());
        boolean isSaved = classStudentService.saveStudentClass(classStudentDTO);
        if (isSaved) {
            return new ResponseUtil(201, "Detail saved!", null);
        } else {
            return new ResponseUtil(200, "Detail not saved!", null);
        }
    }

    @GetMapping("getClass/{studentName}")
    public String getStudentClass(@PathVariable String studentName) {
        System.out.println(studentName);
        String className =  classStudentService.getClassName(studentName);

        System.out.println(className);
        return className;
    }


    @PutMapping("update")
    public ResponseUtil updateStudentClass(@RequestBody ClassStudentDTO classStudentDTO) {
        System.out.println(classStudentDTO.toString());
        boolean isUpdated = classStudentService.update(classStudentDTO);

        if (isUpdated) {
            return new ResponseUtil(201, "Class student detail updated!", null);
        } else {
            return new ResponseUtil(200, "Class student detail not updated!", null);
        }
    }
}

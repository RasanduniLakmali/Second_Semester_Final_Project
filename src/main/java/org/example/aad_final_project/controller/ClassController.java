package org.example.aad_final_project.controller;

import org.example.aad_final_project.dto.ClassDTO;
import org.example.aad_final_project.service.ClassService;
import org.example.aad_final_project.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/v1/class")
public class ClassController {

    @Autowired
    private ClassService classService;


    @PostMapping("save")
    public ResponseUtil saveClass(@RequestBody ClassDTO classDTO){
         boolean isSaved = classService.saveClass(classDTO);

        if (isSaved) {
            return new ResponseUtil(201, "Class saved!", null);
        } else {
            return new ResponseUtil(200, "Class not saved!", null);
        }
    }

    @GetMapping("getAll")
    public List<ClassDTO> getAllClasses(){
            List<ClassDTO> classDTOS = classService.getAll();
            return classDTOS;
    }

    @PutMapping("update")
    public ResponseUtil updateClass(@RequestBody ClassDTO classDTO){

        boolean isUpdated = classService.updateClass(classDTO);
        System.out.println(isUpdated);

        if (isUpdated) {
            return new ResponseUtil(201, "Class updated!", null);
        } else {
            return new ResponseUtil(200, "Class not updated!", null);
        }
    }

    @DeleteMapping("delete/{className}")
    public ResponseUtil deleteClass(@PathVariable String className){

        boolean isDeleted = classService.deleteClass(className);
        System.out.println(isDeleted);

        if (isDeleted) {
            return new ResponseUtil(201, "Class deleted!", null);
        } else {
            return new ResponseUtil(200, "Class not deleted!", null);
        }
    }

    @GetMapping("getName")
    public List<String> getAllNames(){
        return classService.getClassNames();
    }


    @GetMapping("getClId/{className}")
    public String getClassId(@PathVariable String className){
        return classService.getClassId(className);
    }
}

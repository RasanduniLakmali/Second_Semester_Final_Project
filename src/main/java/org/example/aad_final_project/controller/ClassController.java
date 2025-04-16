package org.example.aad_final_project.controller;

import jakarta.validation.Valid;
import org.example.aad_final_project.dto.ResponseDTO;
import org.example.aad_final_project.dto.StudentMyClassDTO;
import org.example.aad_final_project.util.ResponseUtil;
import org.example.aad_final_project.dto.ClassDTO;
import org.example.aad_final_project.service.ClassService;
import org.example.aad_final_project.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Validated
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/v1/class")
public class ClassController {

    @Autowired
    private ClassService classService;


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("save")
    public ResponseUtil saveClass(@Valid @RequestBody ClassDTO classDTO){
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


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("update")
    public ResponseUtil updateClass(@Valid @RequestBody ClassDTO classDTO){

        boolean isUpdated = classService.updateClass(classDTO);
        System.out.println(isUpdated);

        if (isUpdated) {
            return new ResponseUtil(201, "Class updated!", null);
        } else {
            return new ResponseUtil(200, "Class not updated!", null);
        }
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("delete/{className}")
    public ResponseUtil deleteClass(@Valid @PathVariable String className){

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


    @GetMapping("getClass/{classId}/{scheduleDate}")
    public List<StudentMyClassDTO> getClass(@PathVariable String classId, @PathVariable LocalDate scheduleDate){

        System.out.println("Received " + " " + classId + " " + scheduleDate);

        List<StudentMyClassDTO> details = classService.getClassDetails(classId,scheduleDate);

        System.out.println("Received detail " + details);

//        if (details != null) {
//            return ResponseEntity.ok(new ResponseDTO(VarList.OK, "Student Details Retrieved", details));
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body(new ResponseDTO(VarList.Not_Found, "Student Not Found", null));
//        }

        return details;
    }


    @GetMapping("getFee/{className}")
    public String getFee(@PathVariable String className){
        System.out.println("Received " + " " + className);

        return classService.getClassFee(className);
    }

    @GetMapping("count")
    public ResponseEntity<Long> getClassesCount() {
        long count = classService.getClassesCount();
        return ResponseEntity.ok(count);
    }


    @GetMapping("getClassName/{studentName}")
    public ResponseEntity<ResponseDTO> getClassName(@PathVariable String studentName){
        System.out.println("Received " + " " + studentName);
        String className = classService.getUniqueClass(studentName);

        if (className != null) {
            return ResponseEntity.ok(new ResponseDTO(VarList.OK, "Class Name Retrieved", className));
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(VarList.Not_Found, "Class Name Not Found", null));
        }

    }
}

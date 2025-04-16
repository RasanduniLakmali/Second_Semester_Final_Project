package org.example.aad_final_project.controller;


import org.example.aad_final_project.dto.PaperMarkDTO;
import org.example.aad_final_project.service.PaperMarksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.aad_final_project.util.ResponseUtil;

import java.util.Collections;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/v1/marks")
public class PaperMarksController {

     @Autowired
     private PaperMarksService paperMarksService;

     @PostMapping("save")
     public ResponseUtil saveMarks(@RequestBody PaperMarkDTO paperMarkDTO){
         boolean isSaved = paperMarksService.saveAllMarks(paperMarkDTO);

          if (isSaved) {
               return new ResponseUtil(201, "Marks saved!", null);
          } else {
               return new ResponseUtil(200, "Marks not saved!", null);
          }
     }

     @GetMapping("getAll")
     public List<PaperMarkDTO> getAllMarks(){
         List<PaperMarkDTO> paperMarkDTOS = paperMarksService.getAllMarks();

         System.out.println(paperMarkDTOS.toString());

         return paperMarkDTOS;
     }

     @PutMapping("update")
     public ResponseUtil updateMarks(@RequestBody PaperMarkDTO paperMarkDTO){
         boolean isUpdated = paperMarksService.updateMarks(paperMarkDTO);

         System.out.println("updated " + isUpdated);

         if (isUpdated) {
             return new ResponseUtil(201, "Marks updated!", null);
         } else {
             return new ResponseUtil(200, "Marks not updated!", null);
         }
     }


     @DeleteMapping("delete/{studentName}/{subjectName}")
     public ResponseUtil deleteMarks(@PathVariable String studentName, @PathVariable String subjectName){
         boolean isDeleted = paperMarksService.deleteMarks(studentName,subjectName);

         System.out.println("deleted " + isDeleted);

         if (isDeleted) {
             return new ResponseUtil(201, "Marks deleted!", null);
         } else {
             return new ResponseUtil(200, "Marks not deleted!", null);
         }
     }


    @GetMapping("/getDetail/{studentId}/{subjectName}")
    public ResponseEntity<List<PaperMarkDTO>> getStudentMarks(@PathVariable String studentId, @PathVariable String subjectName) {
        List<PaperMarkDTO> marks = paperMarksService.getStudentMarks(studentId, subjectName);

        if (marks.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }

        System.out.println("Fetched Marks: " + marks);
        return ResponseEntity.ok(marks);
    }

}

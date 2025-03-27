package org.example.aad_final_project.controller;


import org.example.aad_final_project.dto.ScheduleDTO;
import org.example.aad_final_project.dto.SubjectScheduleDTO;
import org.example.aad_final_project.service.SubjectScheduleService;
import org.example.aad_final_project.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/v1/sbSchedule")
public class SubjectScheduleController {

    @Autowired
    private SubjectScheduleService subjectScheduleService;

    @PostMapping("save")
    public ResponseUtil saveSubSchedule(@RequestBody SubjectScheduleDTO subjectScheduleDTO) {
        boolean isSaved = subjectScheduleService.save(subjectScheduleDTO);
        if (isSaved) {
            return new ResponseUtil(201, "Subject schedule detail saved!", null);
        } else {
            return new ResponseUtil(200, "Subject schedule detail not saved!", null);
        }
    }

    @PutMapping("update")
    public ResponseUtil updateSubSchedule(@RequestBody SubjectScheduleDTO subjectScheduleDTO) {
        System.out.println(subjectScheduleDTO.toString());

        boolean isUpdated =subjectScheduleService.update(subjectScheduleDTO);

        if (isUpdated) {
            return new ResponseUtil(201, "Subject schedule detail updated!", null);
        } else {
            return new ResponseUtil(200, "Subject schedule detail not updated!", null);
        }
    }

    @DeleteMapping("delete/{scheduleId}")
    public ResponseUtil deleteSubSchedule(@PathVariable Integer scheduleId){

        boolean isDeleted = subjectScheduleService.delete(scheduleId);
        System.out.println(isDeleted);

        if (isDeleted) {
            return new ResponseUtil(201, "Subject schedule detail deleted!", null);
        } else {
            return new ResponseUtil(200, "Subject schedule detail not deleted!", null);
        }
    }
}

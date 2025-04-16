package org.example.aad_final_project.controller;

import org.example.aad_final_project.dto.InstructorScheduleDTO;
import org.example.aad_final_project.service.InstructorScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.example.aad_final_project.util.ResponseUtil;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/v1/instSchedule")
public class InstructorScheduleController {

    @Autowired
    private InstructorScheduleService instructorScheduleService;

    @PostMapping("save")
    public ResponseUtil saveInstSchedule(@RequestBody InstructorScheduleDTO instructorScheduleDTO) {
        boolean isSaved = instructorScheduleService.save(instructorScheduleDTO);
        if (isSaved) {
            return new ResponseUtil(201, "InstructorSchedule saved!", null);
        } else {
            return new ResponseUtil(200, "InstructorSchedule not saved!", null);
        }
    }

    @PutMapping("update")
    public ResponseUtil updateInstSchedule(@RequestBody InstructorScheduleDTO instructorScheduleDTO) {
        System.out.println(instructorScheduleDTO.toString());

        boolean isUpdated =instructorScheduleService.update(instructorScheduleDTO);

        if (isUpdated) {
            return new ResponseUtil(201, "InstructorSchedule updated!", null);
        } else {
            return new ResponseUtil(200, "InstructorSchedule not updated!", null);
        }
    }

    @DeleteMapping("delete/{scheduleId}")
    public ResponseUtil deleteInstSchedule(@PathVariable Integer scheduleId){

        boolean isDeleted = instructorScheduleService.delete(scheduleId);
        System.out.println(isDeleted);

        if (isDeleted) {
            return new ResponseUtil(201, "InstructorSchedule deleted!", null);
        } else {
            return new ResponseUtil(200, "InstructorSchedule not deleted!", null);
        }
    }
}

package org.example.aad_final_project.controller;

import org.example.aad_final_project.dto.ScheduleDTO;
import org.example.aad_final_project.entity.Schedule;
import org.example.aad_final_project.service.ClassService;
import org.example.aad_final_project.service.ScheduleService;
import org.example.aad_final_project.service.impl.ClassServiceImpl;
import org.example.aad_final_project.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/v1/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private ClassService classService;

    @GetMapping("getNames")
    public List<String> getNames() {
        return classService.getClassNames();
    }

    @PostMapping("save")
    public ResponseUtil saveSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        System.out.println(scheduleDTO.toString());
        boolean isSaved = scheduleService.saveSchedule(scheduleDTO);
        System.out.println(isSaved);
        if (isSaved) {
            return new ResponseUtil(201, "Schedule saved!", null);
        } else {
            return new ResponseUtil(200, "Schedule not saved!", null);
        }
    }

    @GetMapping("getID/{scheduleDate}/{className}/{instructorName}")
    public Integer getScheduleID(@PathVariable LocalDate scheduleDate, @PathVariable String className, @PathVariable String instructorName) {
        return scheduleService.getId(scheduleDate,className,instructorName);
    }

    @GetMapping("getAll")
    public List<ScheduleDTO> getAll() {
        return scheduleService.getAll();
    }

    @PutMapping("update")
    public ResponseUtil updateSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        System.out.println("update" + scheduleDTO.toString());
        boolean isUpdated =scheduleService.updateSchedule(scheduleDTO);
        System.out.println(isUpdated);
        if (isUpdated) {
            return new ResponseUtil(201, "Schedule updated!", null);
        } else {
            return new ResponseUtil(200, "Schedule not updated!", null);
        }
    }

    @DeleteMapping("delete/{date}/{className}/{instructorName}")
    public ResponseUtil deleteSchedule(@PathVariable LocalDate date, @PathVariable String className, @PathVariable String instructorName){

        boolean isDeleted = scheduleService.deleteSchedule(date,className,instructorName);
        System.out.println(isDeleted);

        if (isDeleted) {
            return new ResponseUtil(201, "Schedule deleted!", null);
        } else {
            return new ResponseUtil(200, "Schedule not deleted!", null);
        }
    }
}

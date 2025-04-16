package org.example.aad_final_project.service;

import org.example.aad_final_project.dto.ScheduleDTO;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface ScheduleService {


    boolean saveSchedule(ScheduleDTO scheduleDTO);

    Integer getId(LocalDate scheduleDate, String className, String instructorName);

    List<ScheduleDTO> getAll();

    boolean updateSchedule(ScheduleDTO scheduleDTO);


    boolean deleteSchedule(LocalDate date, String className, String instructorName);

    public ScheduleDTO getNearestScheduleForGrade(String grade);


}

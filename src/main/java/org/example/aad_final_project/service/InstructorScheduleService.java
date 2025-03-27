package org.example.aad_final_project.service;

import org.example.aad_final_project.dto.InstructorScheduleDTO;

public interface InstructorScheduleService {

    boolean save(InstructorScheduleDTO instructorScheduleDTO);

    boolean update(InstructorScheduleDTO instructorScheduleDTO);

    boolean delete(Integer scheduleId);
}

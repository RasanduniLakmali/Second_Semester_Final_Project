package org.example.aad_final_project.service;

import org.example.aad_final_project.dto.SubjectScheduleDTO;

public interface SubjectScheduleService {

    boolean save(SubjectScheduleDTO subjectScheduleDTO);

    boolean update(SubjectScheduleDTO subjectScheduleDTO);

    boolean delete(Integer scheduleId);
}

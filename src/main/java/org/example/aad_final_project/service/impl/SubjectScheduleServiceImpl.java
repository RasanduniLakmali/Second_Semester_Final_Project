package org.example.aad_final_project.service.impl;

import org.example.aad_final_project.dto.SubjectScheduleDTO;
import org.example.aad_final_project.entity.Schedule;
import org.example.aad_final_project.entity.Subject;
import org.example.aad_final_project.entity.SubjectSchedule;
import org.example.aad_final_project.repo.ScheduleRepo;
import org.example.aad_final_project.repo.SubjectRepo;
import org.example.aad_final_project.repo.SubjectScheduleRepo;
import org.example.aad_final_project.service.SubjectScheduleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubjectScheduleServiceImpl implements SubjectScheduleService {

    @Autowired
    private SubjectScheduleRepo subjectScheduleRepo;

    @Autowired
    private SubjectRepo subjectRepo;

    @Autowired
    private ScheduleRepo scheduleRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public boolean save(SubjectScheduleDTO subjectScheduleDTO) {

        SubjectSchedule subjectSchedule = modelMapper.map(subjectScheduleDTO, SubjectSchedule.class);

        Optional<Subject> optionalSubject =subjectRepo.findById(subjectScheduleDTO.getSubject_id());
        Optional<Schedule> optionalSchedule = scheduleRepo.findById(subjectScheduleDTO.getSchedule_id());

        if (optionalSubject.isPresent() && optionalSchedule.isPresent()) {

            subjectSchedule.setSubject(optionalSubject.get());
            subjectSchedule.setSchedule(optionalSchedule.get());
            subjectSchedule.setSubject_name(subjectScheduleDTO.getSubject_name());
            subjectScheduleRepo.save(subjectSchedule);
            return true;
        }
        return false;
    }

    @Override
    public boolean update(SubjectScheduleDTO subjectScheduleDTO) {
        Optional<SubjectSchedule> optionalSubjectSchedule = subjectScheduleRepo.findById(subjectScheduleDTO.getSchedule_id());

        if (optionalSubjectSchedule.isPresent()) {
            SubjectSchedule subjectSchedule = optionalSubjectSchedule.get();
            subjectSchedule.setSubject(subjectRepo.findById(subjectScheduleDTO.getSubject_id()).get());
            subjectSchedule.setSchedule(scheduleRepo.findById(subjectScheduleDTO.getSchedule_id()).get());
            subjectSchedule.setSubject_name(subjectScheduleDTO.getSubject_name());
            subjectScheduleRepo.save(subjectSchedule);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Integer scheduleId) {
        Optional<SubjectSchedule> subjectSchedule = subjectScheduleRepo.findById(scheduleId);

        if (subjectSchedule.isPresent()) {
            subjectScheduleRepo.deleteById(scheduleId);
            return true;
        }
        return false;
    }
}

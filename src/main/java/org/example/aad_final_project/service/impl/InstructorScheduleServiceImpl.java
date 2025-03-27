package org.example.aad_final_project.service.impl;

import org.example.aad_final_project.dto.InstructorScheduleDTO;
import org.example.aad_final_project.entity.Instructor;
import org.example.aad_final_project.entity.InstructorSchedule;
import org.example.aad_final_project.entity.Schedule;
import org.example.aad_final_project.repo.InstructorRepo;
import org.example.aad_final_project.repo.InstructorScheduleRepo;
import org.example.aad_final_project.repo.ScheduleRepo;
import org.example.aad_final_project.service.InstructorScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InstructorScheduleServiceImpl implements InstructorScheduleService {

    @Autowired
    private InstructorScheduleRepo instructorScheduleRepo;

    @Autowired
    private InstructorRepo instructorRepo;

    @Autowired
    private ScheduleRepo scheduleRepo;

    @Override
    public boolean save(InstructorScheduleDTO instructorScheduleDTO) {
        Optional<Instructor> optionalInstructor =instructorRepo.findById(instructorScheduleDTO.getInstructor_id());
        Optional<Schedule> optionalSchedule = scheduleRepo.findById(instructorScheduleDTO.getSchedule_id());

        if(optionalInstructor.isPresent() && optionalSchedule.isPresent()){
            InstructorSchedule instructorSchedule = new InstructorSchedule();
            instructorSchedule.setInstructor(optionalInstructor.get());
            instructorSchedule.setSchedule(optionalSchedule.get());
            instructorSchedule.setInstructor_name(instructorScheduleDTO.getInstructor_name());
            instructorScheduleRepo.save(instructorSchedule);
            return true;
        }
        return false;
    }

    @Override
    public boolean update(InstructorScheduleDTO instructorScheduleDTO) {
        Optional<InstructorSchedule> instructorSchedule = instructorScheduleRepo.findById(instructorScheduleDTO.getSchedule_id());

        if(instructorSchedule.isPresent()){
            InstructorSchedule instructorSchedule1 = instructorSchedule.get();
            instructorSchedule1.setInstructor(instructorRepo.findById(instructorScheduleDTO.getInstructor_id()).get());
            instructorSchedule1.setSchedule(scheduleRepo.findById(instructorScheduleDTO.getSchedule_id()).get());
            instructorSchedule1.setInstructor_name(instructorScheduleDTO.getInstructor_name());
            instructorScheduleRepo.save(instructorSchedule1);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Integer scheduleId) {
        Optional<InstructorSchedule> instructorSchedule = instructorScheduleRepo.findById(scheduleId);

        if(instructorSchedule.isPresent()){
            instructorScheduleRepo.deleteById(scheduleId);
            return true;
        }
        return false;
    }
}

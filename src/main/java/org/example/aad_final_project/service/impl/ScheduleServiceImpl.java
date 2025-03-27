package org.example.aad_final_project.service.impl;

import org.example.aad_final_project.dto.ClassDTO;
import org.example.aad_final_project.dto.ScheduleDTO;
import org.example.aad_final_project.entity.Admin;
import org.example.aad_final_project.entity.ClassEntity;
import org.example.aad_final_project.entity.Schedule;
import org.example.aad_final_project.repo.ClassRepo;
import org.example.aad_final_project.repo.ScheduleRepo;
import org.example.aad_final_project.service.ScheduleService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleRepo scheduleRepo;

    @Autowired
    private ClassRepo classRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public boolean saveSchedule(ScheduleDTO scheduleDTO) {
        System.out.println(scheduleDTO.getSchedule_date());

        Schedule schedule = modelMapper.map(scheduleDTO, Schedule.class);

        Optional<ClassEntity> classEntity = classRepo.findById(scheduleDTO.getClass_id());

        if (classEntity.isPresent()) {

            schedule.setClassEntity(classEntity.get());
            schedule.setSchedule_date(scheduleDTO.getSchedule_date());
            schedule.setClass_name(scheduleDTO.getClass_name());
            schedule.setStart_time(scheduleDTO.getStart_time());
            schedule.setEnd_time(scheduleDTO.getEnd_time());
            schedule.setInstructor_name(scheduleDTO.getInstructor_name());
            schedule.setSubject_name(scheduleDTO.getSubject_name());
            scheduleRepo.save(schedule);
            return true;
        }
        return false;
    }

    @Override
    public Integer getId(LocalDate scheduleDate, String className, String instructorName) {
        return scheduleRepo.findByDateAndClassAndInstructor(scheduleDate, className, instructorName);
    }

    @Override
    public List<ScheduleDTO> getAll() {
        return modelMapper.map(scheduleRepo.findAll(),
                new TypeToken<List<ScheduleDTO>>() {}.getType());
    }

    @Override
    public boolean updateSchedule(ScheduleDTO scheduleDTO) {

        Optional<Schedule> optionalSchedule =scheduleRepo.findSchedule(scheduleDTO.getSchedule_date(), scheduleDTO.getClass_name(), scheduleDTO.getInstructor_name());

        if (optionalSchedule.isPresent()) {

            Schedule schedule = optionalSchedule.get();
            schedule.setSchedule_date(scheduleDTO.getSchedule_date());
            schedule.setStart_time(scheduleDTO.getStart_time());
            schedule.setEnd_time(scheduleDTO.getEnd_time());
            schedule.setClassEntity(classRepo.findById(scheduleDTO.getClass_id()).get());
            schedule.setClass_name(scheduleDTO.getClass_name());
            schedule.setInstructor_name(scheduleDTO.getInstructor_name());
            schedule.setSubject_name(scheduleDTO.getSubject_name());
            scheduleRepo.save(schedule);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteSchedule(LocalDate date, String className, String instructorName) {
        Optional<Schedule> optionalSchedule = scheduleRepo.findSchedule(date, className, instructorName);
        if (optionalSchedule.isPresent()) {
            scheduleRepo.deleteSchedule(date, className, instructorName);
            return true;
        }
        return false;
    }


}

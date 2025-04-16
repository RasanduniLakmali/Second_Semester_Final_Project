package org.example.aad_final_project.service.impl;

import jakarta.annotation.PostConstruct;
import org.example.aad_final_project.dto.AttendanceDTO;
import org.example.aad_final_project.dto.AttendanceSummaryDTO;
import org.example.aad_final_project.dto.InstructorDTO;
import org.example.aad_final_project.dto.ScheduleDTO;
import org.example.aad_final_project.entity.*;
import org.example.aad_final_project.repo.AttendanceRepo;
import org.example.aad_final_project.repo.ScheduleRepo;
import org.example.aad_final_project.repo.StudentRepo;
import org.example.aad_final_project.repo.SubjectRepo;
import org.example.aad_final_project.service.AttendanceService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private AttendanceRepo attendanceRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private SubjectRepo subjectRepo;

    @Autowired
    private ScheduleRepo scheduleRepo;

    @Override
    public boolean saveAttendance(AttendanceDTO attendanceDTO) {
        Attendance attendance = modelMapper.map(attendanceDTO, Attendance.class);


        Optional<Student> optionalStudent = studentRepo.findById(attendanceDTO.getStudent_id());
        Optional<Subject> optionalSubject = subjectRepo.findById(attendanceDTO.getSubject_id());

        if (optionalStudent.isPresent() && optionalSubject.isPresent()) {
            attendance.setStudent(optionalStudent.get());
            attendance.setSubject(optionalSubject.get());
            attendanceRepo.save(attendance);
            return true;
        }
        return false;
    }

    //ek student kenekge multiple lines fetch krnn beri hnda mek dmme
    @PostConstruct
    public void init() {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        modelMapper.typeMap(Attendance.class, AttendanceDTO.class).addMappings(mapper -> {
            mapper.map(src -> src.getStudent().getStudent_id(), AttendanceDTO::setStudent_id);
            mapper.map(src -> src.getSubject().getSubject_id(), AttendanceDTO::setSubject_id);
            mapper.map(src -> src.getStudent().getStudent_name(), AttendanceDTO::setStudent_name);
            mapper.map(src -> src.getSubject().getSubject_name(), AttendanceDTO::setSubject_name);
            mapper.map(src -> src.getClass_name(), AttendanceDTO::setClass_name);
        });
    }

    @Override
    public List<AttendanceDTO> getAll() {
        List<Attendance> attendances = attendanceRepo.findAll();
        return attendances.stream()
                .map(attendance -> modelMapper.map(attendance, AttendanceDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean updateAttendance(AttendanceDTO attendanceDTO) {
         Optional<Attendance> optionalAttendance =  attendanceRepo.findAttendance(attendanceDTO.getStudent_name(),attendanceDTO.getAttendance_date(),attendanceDTO.getSubject_name());

         if (optionalAttendance.isPresent()) {
             Attendance attendance = optionalAttendance.get();
             attendance.setAttendance_date(attendanceDTO.getAttendance_date());
             attendance.setStatus(attendanceDTO.getStatus());
             attendance.setStudent_name(attendanceDTO.getStudent_name());
             attendance.setClass_name(attendanceDTO.getClass_name());
             attendance.setSubject_name(attendanceDTO.getSubject_name());
             attendance.setStudent(studentRepo.findById(attendanceDTO.getStudent_id()).get());
             attendance.setSubject(subjectRepo.findById(attendanceDTO.getSubject_id()).get());
             attendanceRepo.save(attendance);
             return true;
         }
         return false;
    }

    public List<AttendanceSummaryDTO> getAttendanceSummary(String subject, int month) {
        return attendanceRepo.getAttendanceSummary(subject, month);
    }

    @Override
    public List<AttendanceDTO> getAttendanceBySubjectAndMonth(String subject, int month) {
        return List.of();
    }
}

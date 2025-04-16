package org.example.aad_final_project.service.impl;

import org.example.aad_final_project.dto.InstructorDTO;
import org.example.aad_final_project.dto.ScheduleDTO;
import org.example.aad_final_project.dto.StudentSubjectDTO;
import org.example.aad_final_project.entity.*;
import org.example.aad_final_project.repo.StudentRepo;
import org.example.aad_final_project.repo.StudentSubjectRepo;
import org.example.aad_final_project.repo.SubjectRepo;
import org.example.aad_final_project.service.StudentSubjectService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentSubjectServiceImpl implements StudentSubjectService {


    @Autowired
    private StudentSubjectRepo studentSubjectRepo;

    @Autowired
    private SubjectRepo subjectRepo;

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public boolean saveStudentSubject(StudentSubjectDTO studentSubjectDTO) {

        StudentSubject studentSubject = modelMapper.map(studentSubjectDTO, StudentSubject.class);

        Optional<Subject> optionalSubjectEntity = subjectRepo.findById(studentSubjectDTO.getSubject_id());
        Optional<Student> optionalStudent = studentRepo.findById(studentSubjectDTO.getStudent_id());

        if (optionalSubjectEntity.isPresent() && optionalStudent.isPresent()) {

            studentSubject.setSubject(optionalSubjectEntity.get());  // Set the Class entity
            studentSubject.setStudent(optionalStudent.get());
            studentSubject.setStudent_name(optionalStudent.get().getStudent_name());
            studentSubject.setSubject_name(optionalSubjectEntity.get().getSubject_name());
            studentSubjectRepo.save(studentSubject);
            return true;
        }
        return false;
    }

    public List<String> getNames(String studentName) {
        return studentSubjectRepo.findByStudentName(studentName);
    }

    @Override
    public StudentSubjectDTO update(StudentSubjectDTO studentSubjectDTO) {
        System.out.println(studentSubjectDTO.getStudent_id());
        System.out.println(studentSubjectDTO.getSubject_id());

        Optional<StudentSubject> studentSubject = studentSubjectRepo.findByStudent_idAndSubject_id(
                studentSubjectDTO.getStudent_id(),
                studentSubjectDTO.getSubject_id()
        );

        System.out.println("Record found: " + studentSubject.isPresent());

        if (studentSubject.isPresent()) {
            StudentSubject studentSubjectEntity = studentSubject.get();
            studentSubjectEntity.setStudent_name(studentSubjectDTO.getStudent_name());
            studentSubjectEntity.setSubject_name(studentSubjectDTO.getSubject_name());
            studentSubjectEntity.setStudent(studentRepo.findById(studentSubjectDTO.getStudent_id()).get());
            studentSubjectEntity.setSubject(subjectRepo.findById(studentSubjectDTO.getSubject_id()).get());
            studentSubjectEntity.setInstructor_name(studentSubjectDTO.getInstructor_name());
            studentSubjectRepo.save(studentSubjectEntity);
            return modelMapper.map(studentSubjectEntity, StudentSubjectDTO.class);
        }

        return null;
    }

    @Override
    public List<String> getSubjectsByEmail(String email) {
        Student student = studentRepo.findByEmail(email);
        List<Subject> subjects = studentSubjectRepo.findByStudentsContaining(student);

        return subjects.stream().map(Subject::getSubject_name).collect(Collectors.toList());
    }

    @Override
    public List<StudentSubjectDTO> getAll() {
        List<StudentSubject> list = studentSubjectRepo.findAll();
        return list.stream().map(s -> {
            StudentSubjectDTO dto = new StudentSubjectDTO();
            dto.setStudent_name(s.getStudent().getStudent_name());
            dto.setSubject_name(s.getSubject().getSubject_name());
            dto.setInstructor_name(s.getInstructor_name());
            return dto;
        }).collect(Collectors.toList());
    }



}

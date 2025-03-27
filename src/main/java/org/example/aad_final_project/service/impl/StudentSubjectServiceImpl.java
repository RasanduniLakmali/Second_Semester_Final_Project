package org.example.aad_final_project.service.impl;

import org.example.aad_final_project.dto.StudentSubjectDTO;
import org.example.aad_final_project.entity.*;
import org.example.aad_final_project.repo.StudentRepo;
import org.example.aad_final_project.repo.StudentSubjectRepo;
import org.example.aad_final_project.repo.SubjectRepo;
import org.example.aad_final_project.service.StudentSubjectService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public boolean update(StudentSubjectDTO studentSubjectDTO) {
        System.out.println(studentSubjectDTO.toString());

        Optional<StudentSubject> studentSubjectOptional = studentSubjectRepo.findByStudentAndName(
                studentSubjectDTO.getStudent_id(),
                studentSubjectDTO.getStudent_name()
        );

        System.out.println("Record found: " + studentSubjectOptional.isPresent());

        if (studentSubjectOptional.isPresent()) {
            StudentSubject studentSubjectEntity = studentSubjectOptional.get();

            studentSubjectEntity.setStudent_name(studentSubjectDTO.getStudent_name());
            studentSubjectEntity.setSubject_name(studentSubjectDTO.getSubject_name());


            studentRepo.findById(studentSubjectDTO.getStudent_id()).ifPresent(studentSubjectEntity::setStudent);
            subjectRepo.findById(studentSubjectDTO.getSubject_id()).ifPresent(studentSubjectEntity::setSubject);

            studentSubjectRepo.save(studentSubjectEntity);
            return true;
        }
        return false;
    }

}

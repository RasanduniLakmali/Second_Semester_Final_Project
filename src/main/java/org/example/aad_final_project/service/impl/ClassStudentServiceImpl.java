package org.example.aad_final_project.service.impl;

import org.example.aad_final_project.dto.ClassStudentDTO;
import org.example.aad_final_project.entity.ClassEntity;
import org.example.aad_final_project.entity.ClassStudent;
import org.example.aad_final_project.entity.Student;
import org.example.aad_final_project.repo.ClassRepo;
import org.example.aad_final_project.repo.ClassStudentRepo;
import org.example.aad_final_project.repo.StudentRepo;
import org.example.aad_final_project.service.ClassStudentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClassStudentServiceImpl implements ClassStudentService {

    @Autowired
    private ClassStudentRepo classStudentRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private ClassRepo classRepo;

    @Override
    public boolean saveStudentClass(ClassStudentDTO classStudentDTO) {

        Optional<ClassEntity> optionalClassEntity = classRepo.findById(classStudentDTO.getClass_id());
        Optional<Student> optionalStudent = studentRepo.findById(classStudentDTO.getStudent_id());

        if (optionalClassEntity.isPresent() && optionalStudent.isPresent()) {
            ClassStudent classStudent = new ClassStudent();
            classStudent.setClassEntity(optionalClassEntity.get());  // Set the Class entity
            classStudent.setStudent(optionalStudent.get());
            classStudent.setClass_name(optionalClassEntity.get().getClass_name());
            classStudent.setStudent_name(optionalStudent.get().getStudent_name());
            classStudentRepo.save(classStudent);
            return true;
        }
        return false; // Return false if class or student is not found
    }

    @Override
    public String getClassName(String studentName) {
        return classStudentRepo.findByStudent_name(studentName);
    }

    @Override
    public boolean update(ClassStudentDTO classStudentDTO) {

        Optional<ClassStudent> classStudent = classStudentRepo.findById(classStudentDTO.getStudent_id());

        if (classStudent.isPresent()) {
            ClassStudent classStudent1 = classStudent.get();
            classStudent1.setStudent_name(classStudentDTO.getStudent_name());
            classStudent1.setStudent(studentRepo.findById(classStudentDTO.getStudent_id()).get());
            classStudent1.setClassEntity(classRepo.findById(classStudentDTO.getClass_id()).get());
            classStudent1.setClass_name(classStudentDTO.getClass_name());
            classStudentRepo.save(classStudent1);
            return true;
        }
        return false;
    }

}

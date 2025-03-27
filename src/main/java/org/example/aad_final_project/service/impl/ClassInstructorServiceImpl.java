package org.example.aad_final_project.service.impl;

import org.example.aad_final_project.dto.ClassInstructorDTO;
import org.example.aad_final_project.entity.ClassEntity;
import org.example.aad_final_project.entity.ClassInstructor;
import org.example.aad_final_project.entity.Instructor;
import org.example.aad_final_project.repo.ClassInstructorRepo;
import org.example.aad_final_project.repo.ClassRepo;
import org.example.aad_final_project.repo.InstructorRepo;
import org.example.aad_final_project.service.ClassInstructorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ClassInstructorServiceImpl implements ClassInstructorService {

    @Autowired
    private ClassInstructorRepo classInstructorRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ClassRepo classRepo;

    @Autowired
    InstructorRepo instructorRepo;

    @Override
    public boolean saveAll(ClassInstructorDTO classInstructorDTO) {
        // Fetch Class entity using classId
        ClassEntity classEntity = classRepo.findById(classInstructorDTO.getClass_id())
                .orElseThrow(() -> new RuntimeException("Class not found with id: " + classInstructorDTO.getClass_id()));

        // Fetch Instructor entity using instructorId
        Instructor instructor = instructorRepo.findById(classInstructorDTO.getInstructor_id())
                .orElseThrow(() -> new RuntimeException("Instructor not found with id: " + classInstructorDTO.getInstructor_id()));

        // Create ClassInstructor entity
        ClassInstructor classInstructor = new ClassInstructor();
        classInstructor.setClassEntity(classEntity);
        classInstructor.setClass_name(classInstructorDTO.getClass_name());
        classInstructor.setInstructor(instructor);
        classInstructor.setInstructor_name(classInstructorDTO.getInstructor_name());

        // Save the ClassInstructor entity to the database
        classInstructorRepo.save(classInstructor);
        return true;
    }

}

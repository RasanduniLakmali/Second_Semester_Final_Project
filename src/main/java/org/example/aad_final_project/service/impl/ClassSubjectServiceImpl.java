package org.example.aad_final_project.service.impl;

import org.example.aad_final_project.dto.ClassSubjectDTO;
import org.example.aad_final_project.dto.InstructorDTO;
import org.example.aad_final_project.dto.SubjectInstructorDTO;
import org.example.aad_final_project.entity.ClassEntity;
import org.example.aad_final_project.entity.ClassSubject;
import org.example.aad_final_project.entity.Instructor;
import org.example.aad_final_project.entity.Subject;
import org.example.aad_final_project.repo.ClassRepo;
import org.example.aad_final_project.repo.ClassSubjectRepo;
import org.example.aad_final_project.repo.SubjectRepo;
import org.example.aad_final_project.service.ClassSubjectService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClassSubjectServiceImpl implements ClassSubjectService {
    
    @Autowired
    private ClassSubjectRepo classSubjectRepo;
    
    @Autowired
    private ClassRepo classRepo;
    
    @Autowired
    private SubjectRepo subjectRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public boolean save(ClassSubjectDTO classSubjectDTO) {

        ClassSubject classSubject = modelMapper.map(classSubjectDTO, ClassSubject.class);

        Optional<ClassEntity> optionalClassEntity = classRepo.findById(classSubjectDTO.getClass_id());
        Optional<Subject> optionalSubject = subjectRepo.findById(classSubjectDTO.getSubject_id());

        if (optionalClassEntity.isPresent() && optionalSubject.isPresent()) {
            classSubject.setClassEntity(optionalClassEntity.get());
            classSubject.setSubject(optionalSubject.get());
//            classSubject.setSubject_name(classSubjectDTO.getSubject_name());
//            classSubject.setTime_duration(classSubjectDTO.getTime_duration());
            classSubjectRepo.save(classSubject);
            return true;
        }
        return false;
    }

    @Override
    public List<ClassSubjectDTO> getAll() {
        List<ClassSubject> classSubjects = classSubjectRepo.findAll();
        return classSubjects.stream()
                .map(classSubject -> modelMapper.map(classSubject, ClassSubjectDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean update(ClassSubjectDTO classSubjectDTO) {
        Optional<ClassSubject> optionalClassSubject = classSubjectRepo.findBySubjectAndClass(classSubjectDTO.getSubject_name(),classSubjectDTO.getClass_name());

        if (optionalClassSubject.isPresent()) {
            ClassSubject classSubject = optionalClassSubject.get();
            classSubject.setSubject(subjectRepo.findById(classSubjectDTO.getSubject_id()).get());
            classSubject.setClassEntity(classRepo.findById(classSubjectDTO.getClass_id()).get());
            classSubject.setSubject_name(classSubjectDTO.getSubject_name());
            classSubject.setClass_name(classSubjectDTO.getClass_name());
            classSubject.setTime_duration(classSubjectDTO.getTime_duration());
            classSubjectRepo.save(classSubject);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(String subjectName, String className) {

        Optional<ClassSubject> optionalClassSubject = classSubjectRepo.findBySubjectAndClass(subjectName, className);

        if (optionalClassSubject.isPresent()) {
            classSubjectRepo.deleteBySubjectAndClass(subjectName, className);
            return true;
        }
        return false;
    }

    @Override
    public List<SubjectInstructorDTO> getAllSubjects() {
        return classSubjectRepo.getAllSubjects();
    }
}

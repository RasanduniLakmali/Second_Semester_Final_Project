package org.example.aad_final_project.service.impl;

import org.example.aad_final_project.dto.SubjectDTO;
import org.example.aad_final_project.entity.Admin;
import org.example.aad_final_project.entity.Subject;
import org.example.aad_final_project.repo.AdminRepo;
import org.example.aad_final_project.repo.SubjectRepo;
import org.example.aad_final_project.service.SubjectService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectRepo subjectRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AdminRepo adminRepo;

    @Override
    public boolean saveSubject(SubjectDTO subjectDTO) {
         Subject subject = modelMapper.map(subjectDTO, Subject.class);

        // Fetch Admin entity from database
        Optional<Admin> optionalAdmin = adminRepo.findById(Integer.valueOf(subjectDTO.getAdmin_id()));

        if (optionalAdmin.isPresent()) {
            subject.setAdmin(optionalAdmin.get()); // Set the Admin entity
            subjectRepo.save(subject);
            return true;
        } else {
            System.out.println("Admin not found with ID: " + subjectDTO.getAdmin_id());
            return false;
        }
    }

    @Override
    public List<SubjectDTO> getAllSubjects() {
//        List<Subject> subjects = subjectRepo.findAll();
//        System.out.println(subjects);
//        return subjects.stream()
//                .map(student -> modelMapper.map(subjects, SubjectDTO.class))
//                .collect(Collectors.toList());

        return modelMapper.map(subjectRepo.findAll(),
                new TypeToken<List<SubjectDTO>>() {}.getType());
    }

    @Override
    public boolean updateSubject(SubjectDTO subjectDTO) {

        Optional<Subject> optionalSubject = subjectRepo.findBySubjectName(subjectDTO.getSubject_name());

        if (optionalSubject.isPresent()) {
            Subject subject = optionalSubject.get();
            subject.setSubject_name(subjectDTO.getSubject_name());
            subject.setSubject_code(subjectDTO.getSubject_code());
            subject.setAdmin(adminRepo.findById(Integer.valueOf(subjectDTO.getAdmin_id())).orElse(null));
            subject.setGrade_range(subjectDTO.getGrade_range());

            subjectRepo.save(subject);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteSubject(String subject_name) {
        Optional<Subject> subject = subjectRepo.findBySubjectName(subject_name);

        if (subject.isPresent()) {
            subjectRepo.deleteBySubjectName(subject_name);
            return true;
        }
        return false;
    }

    @Override
    public List<String> getAllCodes() {
        return subjectRepo.findAll().stream()
                .map(Subject::getSubject_code) // Extract only customer IDs
                .collect(Collectors.toList());
    }

    @Override
    public Integer getId(String subjectCode) {
        Integer subjectId = subjectRepo.findBySubject_code(subjectCode);
        return subjectId;
    }

    @Override
    public List<String> getAllNames() {
        return subjectRepo.findAll().stream()
                .map(Subject::getSubject_name) // Extract only customer IDs
                .collect(Collectors.toList());
    }

    @Override
    public Integer getSubId(String subjectName) {
        return subjectRepo.findBySubject_name(subjectName);
    }


}

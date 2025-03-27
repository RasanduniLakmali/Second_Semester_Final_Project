package org.example.aad_final_project.service.impl;

import org.example.aad_final_project.dto.InstructorDTO;
import org.example.aad_final_project.dto.StudentDTO;
import org.example.aad_final_project.entity.*;
import org.example.aad_final_project.repo.AdminRepo;
import org.example.aad_final_project.repo.InstructorRepo;
import org.example.aad_final_project.repo.SubjectRepo;
import org.example.aad_final_project.service.InstructorService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InstructorServiceImpl implements InstructorService {

    @Autowired
    private InstructorRepo instructorRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AdminRepo adminRepo;

    @Autowired
    private SubjectRepo subjectRepo;

    @Override
    public boolean saveInstructor(InstructorDTO instructorDTO) {

        Instructor instructor = modelMapper.map(instructorDTO, Instructor.class);

        // Fetch Admin entity from database
        Optional<Admin> optionalAdmin = adminRepo.findById(instructorDTO.getAdmin_id());
        Optional<Subject> optionalSubject = subjectRepo.findById(instructorDTO.getSubject_id());

        if (optionalAdmin.isPresent() && optionalSubject.isPresent()) {
            instructor.setAdmin(optionalAdmin.get());
            instructor.setSubject(optionalSubject.get());
            instructorRepo.save(instructor);
            return true;

        } else {
            System.out.println("Admin not found with ID: " +instructorDTO.getAdmin_id());
            return false;
        }
    }

    @Override
    public List<InstructorDTO> getAll() {
        List<Instructor> instructors = instructorRepo.findAll();
        return instructors.stream()
                .map(instructor -> modelMapper.map(instructor,InstructorDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public InstructorDTO getInstructor(String instructorName) {
        Optional<Instructor> instructorDTO =  instructorRepo.findInstructorByName(instructorName);

        if (instructorDTO.isPresent()) {
            Instructor instructor = instructorDTO.get();
            InstructorDTO instructorDTO1 = modelMapper.map(instructor, InstructorDTO.class);

            if (instructor.getAdmin()!= null && instructor.getSubject()!=null) {
                instructorDTO1.setAdmin_id(Integer.valueOf(String.valueOf(instructor.getAdmin().getAdmin_id()))); // Manually set adminId
                instructorDTO1.setSubject_id(Integer.valueOf(String.valueOf(instructor.getSubject().getSubject_id())));
            }

            return instructorDTO1;
        }
        return null;
    }

    @Override
    public boolean updateInstructor(InstructorDTO instructorDTO) {
        Optional<Instructor> optionalInstructor = instructorRepo.findInstructorByName(instructorDTO.getInstructor_name());

        if (optionalInstructor.isPresent()) {

            Instructor instructor = optionalInstructor.get();
            instructor.setInstructor_name(instructorDTO.getInstructor_name());
            instructor.setAddress(instructorDTO.getAddress());
            instructor.setPhone(instructorDTO.getPhone());
            instructor.setEmail(instructorDTO.getEmail());
            instructor.setQualification(instructorDTO.getQualification());
            instructor.setSubject_code(instructorDTO.getSubject_code());
            instructor.setAdmin(adminRepo.findById(Integer.valueOf(instructorDTO.getAdmin_id())).orElse(null));
            instructor.setSubject(subjectRepo.findById(Integer.valueOf(instructorDTO.getSubject_id())).orElse(null));

            if (instructorDTO.getImage() != null) {
                instructor.setImage(instructorDTO.getImage());
            }

            instructorRepo.save(instructor);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteInstructor(String email) {
        Optional<Instructor> instructor = instructorRepo.findByEmail(email);

        if (instructor.isPresent()) {
            instructorRepo.deleteByEmail(email);
            return true;
        }
        return false;
    }

    @Override
    public List<String> getNames() {
        return instructorRepo.findAll().stream()
                .map(Instructor::getInstructor_name)
                .collect(Collectors.toList());
    }

    @Override
    public String getId(String instructorName) {
        return instructorRepo.findByInstructor_name(instructorName);
    }


}

package org.example.aad_final_project.service;

import org.example.aad_final_project.dto.InstructorDTO;

import java.util.List;

public interface InstructorService {

    boolean saveInstructor(InstructorDTO instructorDTO);

    List<InstructorDTO> getAll();

    InstructorDTO getInstructor(String instructorName);

    boolean updateInstructor(InstructorDTO instructorDTO);

    boolean deleteInstructor(String email);

    List<String> getNames();

    String getId(String instructorName);

    String getEmail(String instructorName);

    long getInstructorCount();
}

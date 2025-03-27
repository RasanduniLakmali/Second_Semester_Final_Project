package org.example.aad_final_project.service;

import org.example.aad_final_project.dto.ClassSubjectDTO;
import org.example.aad_final_project.dto.SubjectInstructorDTO;

import java.util.List;

public interface ClassSubjectService {

    boolean save(ClassSubjectDTO classSubjectDTO);

    List<ClassSubjectDTO> getAll();

    boolean update(ClassSubjectDTO classSubjectDTO);

    boolean delete(String subjectName, String className);

    List<SubjectInstructorDTO> getAllSubjects();
}

package org.example.aad_final_project.service;

import org.example.aad_final_project.dto.SubjectDTO;

import java.util.List;

public interface SubjectService {
    boolean saveSubject(SubjectDTO subjectDTO);

    List<SubjectDTO> getAllSubjects();

    boolean updateSubject(SubjectDTO subjectDTO);

    boolean deleteSubject(String subjectName);

    List<String> getAllCodes();

    Integer getId(String subjectCode);

    List<String> getAllNames();

    Integer getSubId(String subjectName);
}

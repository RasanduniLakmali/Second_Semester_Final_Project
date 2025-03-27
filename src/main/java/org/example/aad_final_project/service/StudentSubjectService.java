package org.example.aad_final_project.service;

import org.example.aad_final_project.dto.StudentSubjectDTO;

import java.util.List;

public interface StudentSubjectService {

    boolean saveStudentSubject(StudentSubjectDTO studentSubjectDTO);

    List<String> getNames(String studentName);


    boolean update(StudentSubjectDTO studentSubjectDTO);
}

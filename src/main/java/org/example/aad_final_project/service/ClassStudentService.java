package org.example.aad_final_project.service;

import org.example.aad_final_project.dto.ClassStudentDTO;

public interface ClassStudentService {

    boolean saveStudentClass(ClassStudentDTO classStudentDTO);

    String getClassName(String studentName);

    boolean update(ClassStudentDTO classStudentDTO);
}

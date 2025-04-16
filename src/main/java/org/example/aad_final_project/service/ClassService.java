package org.example.aad_final_project.service;

import org.example.aad_final_project.dto.ClassDTO;
import org.example.aad_final_project.dto.StudentMyClassDTO;

import java.time.LocalDate;
import java.util.List;

public interface ClassService {

    boolean saveClass(ClassDTO classDTO);

    List<ClassDTO> getAll();

    boolean updateClass(ClassDTO classDTO);

    boolean deleteClass(String className);

    List<String> getClassNames();

    String getClassId(String className);

    List<StudentMyClassDTO> getClassDetails(String classId, LocalDate scheduleDate);

    String getClassFee(String className);

    long getClassesCount();

    String getUniqueClass(String studentName);
}

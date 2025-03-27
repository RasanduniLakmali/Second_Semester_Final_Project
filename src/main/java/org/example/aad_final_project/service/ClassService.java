package org.example.aad_final_project.service;

import org.example.aad_final_project.dto.ClassDTO;

import java.util.List;

public interface ClassService {

    boolean saveClass(ClassDTO classDTO);

    List<ClassDTO> getAll();

    boolean updateClass(ClassDTO classDTO);

    boolean deleteClass(String className);

    List<String> getClassNames();

    String getClassId(String className);
}

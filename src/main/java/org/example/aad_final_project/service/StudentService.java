package org.example.aad_final_project.service;

import org.example.aad_final_project.dto.StudentDTO;
import org.example.aad_final_project.entity.Student;

import java.util.List;

public interface StudentService {

    public boolean save(StudentDTO studentDTO);

    public List<StudentDTO> getAllStudents();

    public StudentDTO getStudent(String mobile);

    boolean updateStudent(StudentDTO studentDTO);

    boolean deleteStudent(String mobile);

    List<String> getStudentNames();

    Integer getStudentID(String studentName);



    public List<StudentDTO> getStudents(String instructorName, int classId);
}

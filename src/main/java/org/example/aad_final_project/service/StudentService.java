package org.example.aad_final_project.service;

import org.example.aad_final_project.dto.NewStudentDTO;
import org.example.aad_final_project.dto.StudentDTO;
import org.example.aad_final_project.dto.StudentMyClassDTO;
import org.example.aad_final_project.entity.Student;

import java.util.List;

public interface StudentService {

    public StudentDTO save(StudentDTO studentDTO);

    public List<StudentDTO> getAllStudents();

    public StudentDTO getStudent(String mobile);

    boolean updateStudent(StudentDTO studentDTO);

    boolean deleteStudent(String mobile);

    List<String> getStudentNames();

    Integer getStudentID(String studentName);



    public List<StudentDTO> getStudents(String instructorName, int classId);

    Integer getStudentIdByEmail(String email);

    StudentMyClassDTO getStudentDetails(Integer studentId);

    String getName(Integer studentId);

    boolean saveNewStudent(NewStudentDTO newStudentDTO);

    StudentDTO getStudentProfile(String email);

    public long getStudentCount();
}

package org.example.aad_final_project.service.impl;

import org.example.aad_final_project.dto.StudentDTO;
import org.example.aad_final_project.entity.Admin;
import org.example.aad_final_project.entity.Student;
import org.example.aad_final_project.repo.AdminRepo;
import org.example.aad_final_project.repo.StudentRepo;
import org.example.aad_final_project.service.StudentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private AdminRepo adminRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public boolean save(StudentDTO studentDTO) {

        Student student = modelMapper.map(studentDTO, Student.class);

        Optional<Admin> optionalAdmin = adminRepo.findById(Integer.valueOf(studentDTO.getAdminId()));

        if (optionalAdmin.isPresent()) {
            student.setAdmin(optionalAdmin.get());
            studentRepo.save(student);
            return true;
        } else {
            System.out.println("Admin not found with ID: " + studentDTO.getAdminId());
            return false;
        }
    }

    @Override
    public List<StudentDTO> getAllStudents() {
        List<Student> students = studentRepo.findAll();
        return students.stream()
                .map(student -> modelMapper.map(student, StudentDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public StudentDTO getStudent(String phone) {
        Optional<Student> optionalStudent = studentRepo.findByPhone(phone);
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            StudentDTO studentDTO = modelMapper.map(student, StudentDTO.class);

            if (student.getAdmin() != null) {
                studentDTO.setAdminId(String.valueOf(student.getAdmin().getAdmin_id())); // Manually set adminId
            }

            return studentDTO;
        }
        throw new RuntimeException("Student not found with mobile: " + phone);
    }

    @Override
    public boolean updateStudent(StudentDTO studentDTO) {
        Optional<Student> optionalStudent = studentRepo.findByPhone(studentDTO.getPhone());

        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();

            student.setStudent_name(studentDTO.getStudent_name());
            student.setAge(studentDTO.getAge());
            student.setEmail(studentDTO.getEmail());
            student.setAddress(studentDTO.getAddress());
            student.setSchool(studentDTO.getSchool());
            student.setAdmin(adminRepo.findById(Integer.valueOf(studentDTO.getAdminId())).orElse(null));

            if (studentDTO.getImage() != null) {
                student.setImage(studentDTO.getImage()); // Only update image if new one is provided
            }

            studentRepo.save(student);
            return true;
        }

        return false;
    }

    @Override
    public boolean deleteStudent(String phone) {
        Optional<Student> student = studentRepo.findByPhone(phone);

        if (student.isPresent()) {
            studentRepo.deleteByPhone(phone);
            return true;
        }
        return false;
    }

    @Override
    public List<String> getStudentNames() {
        return studentRepo.findAll().stream()
                .map(Student::getStudent_name) // Extract only customer IDs
                .collect(Collectors.toList());
    }

    @Override
    public Integer getStudentID(String studentName) {
        return studentRepo.findByStudent_name(studentName);
    }


   @Override
    public List<StudentDTO> getStudents(String instructorName, int classId) {
        return studentRepo.findStudents(instructorName, classId);
    }
}


package org.example.aad_final_project.service.impl;

import org.example.aad_final_project.dto.NewStudentDTO;
import org.example.aad_final_project.dto.StudentDTO;
import org.example.aad_final_project.dto.StudentMyClassDTO;
import org.example.aad_final_project.entity.NewStudent;
import org.example.aad_final_project.entity.Role;
import org.example.aad_final_project.entity.User;
import org.example.aad_final_project.entity.Student;
import org.example.aad_final_project.repo.AdminRepo;
import org.example.aad_final_project.repo.NewStudentRepo;
import org.example.aad_final_project.repo.StudentRepo;
import org.example.aad_final_project.repo.UserRepository;
import org.example.aad_final_project.service.StudentService;
import org.example.aad_final_project.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NewStudentRepo newStudentRepo;

    @Override
    public StudentDTO save(StudentDTO studentDTO) {

        System.out.println(studentDTO.toString());

        User admin = adminRepo.findById(studentDTO.getAdmin_id())
                .orElseThrow(() -> new RuntimeException("Admin not found with ID: " + studentDTO.getAdmin_id()));


        Student student = modelMapper.map(studentDTO, Student.class);

        Optional<User> optionalAdmin = adminRepo.findById(Integer.valueOf(studentDTO.getUserId()));

        User user = new User();
        user.setName(studentDTO.getStudent_name());
        user.setEmail(studentDTO.getEmail());
        user.setMobile(studentDTO.getPhone());
        user.setRole(Role.STUDENT);

        User savedUser = userRepository.save(user);

        student.setAddress(studentDTO.getAddress());
        student.setAge(studentDTO.getAge());
        student.setEmail(studentDTO.getEmail());
        student.setImage(studentDTO.getImage());
        student.setPhone(studentDTO.getPhone());
        student.setSchool(studentDTO.getSchool());
        student.setStudent_name(studentDTO.getStudent_name());
        student.setUser(savedUser);
        student.setAdmin_id(studentDTO.getAdmin_id());




        Student savedStudent = studentRepo.save(student);

        StudentDTO responseDTO = new StudentDTO();
        responseDTO.setStudent_id(savedStudent.getStudent_id());
        responseDTO.setUserId(savedStudent.getStudent_id());
        return responseDTO;

//        if (optionalAdmin.isPresent() && user.isPresent()) {
//            student.setAdmin(optionalAdmin.get());
//            student.setUser(user.get());
//            studentRepo.save(student);
//            return true;
//        } else {
//            System.out.println("Admin not found with ID: " + studentDTO.getAdminId());
//            return false;
//        }
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

            if (student.getUser() != null) {
                studentDTO.setUserId(Integer.parseInt(String.valueOf(student.getUser().getId()))); // Manually set adminId
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
            student.setAdmin_id(studentDTO.getAdmin_id());

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

    @Override
    public Integer getStudentIdByEmail(String email) {
        return studentRepo.findStudentIdByEmail(email);
    }

    @Override
    public StudentMyClassDTO getStudentDetails(Integer studentId) {
        Pageable pageable = PageRequest.of(0, 1);
        List<StudentMyClassDTO> results = studentRepo.findStudentDetails(studentId, pageable);

        return results.isEmpty() ? null : results.get(0);
    }

    @Override
    public String getName(Integer studentId) {
        String studentName = studentRepo.findStudentNameById(studentId);

        if (studentName != null) {
            return studentName;
        }
        else {
            return null;
        }
    }

    @Override
    public boolean saveNewStudent(NewStudentDTO newStudentDTO) {
        NewStudent newStudent = modelMapper.map(newStudentDTO, NewStudent.class);
        newStudentRepo.save(newStudent);
        return true;
    }

    @Override
    public StudentDTO getStudentProfile(String email) {
        Student student = studentRepo.findByEmail(email);

        if (student != null) {
            StudentDTO studentDTO = modelMapper.map(student, StudentDTO.class);
            return studentDTO;
        }
        return null;
    }

    @Override
    public long getStudentCount() {
        return studentRepo.count();
    }
}


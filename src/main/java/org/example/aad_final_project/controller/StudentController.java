package org.example.aad_final_project.controller;


import org.example.aad_final_project.dto.StudentDTO;
import org.example.aad_final_project.service.impl.StudentServiceImpl;
import org.example.aad_final_project.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@CrossOrigin(origins = "http://localhost:63342")
@RestController
@RequestMapping("api/v1/student")
public class StudentController {

       @Autowired
       private StudentServiceImpl studentService;

    public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/uploads/";

    @PostMapping("save")
    public ResponseUtil saveStudent(
            @RequestParam("student_name") String studentName,
            @RequestParam("age") int age,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam("address") String address,
            @RequestParam("school") String school,
            @RequestParam("image") MultipartFile file,
            @RequestParam("adminId") String adminId
    ) throws IOException {
        String uniqueFileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDirectory, uniqueFileName);


        Files.createDirectories(filePath.getParent());


        Files.write(filePath, file.getBytes());


        StudentDTO studentDTO = new StudentDTO(studentName, age, email, phone, address, school, uniqueFileName,adminId);
        boolean isSaved = studentService.save(studentDTO);

        if (isSaved) {
            return new ResponseUtil(201, "Student saved!", null);
        } else {
            return new ResponseUtil(200, "Student not saved!", null);
        }
    }


    @GetMapping("getAll")
    public List<StudentDTO> getAllStudents() {
         List<StudentDTO> studentDTOS = studentService.getAllStudents();
         return studentDTOS;
    }

    @GetMapping("getStudent/{mobile}")
    public ResponseEntity<StudentDTO> getStudent(@PathVariable String mobile) {
        System.out.println("Fetching student with mobile: " + mobile);

        StudentDTO studentDTO = studentService.getStudent(mobile);

        if (studentDTO != null) {
            System.out.println("Student found: " + studentDTO);
            return ResponseEntity.ok(studentDTO);
        } else {
            System.out.println("Student not found for mobile: " + mobile);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @PutMapping("update")
    public ResponseUtil updateStudent(
            @RequestParam("student_name") String studentName,
            @RequestParam("age") int age,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam("address") String address,
            @RequestParam("school") String school,
            @RequestPart(value = "image", required = false) MultipartFile file,
            @RequestParam("adminId") String adminId
    ) throws IOException {

        String uniqueFileName = null;

        if (file != null && !file.isEmpty()) {
            uniqueFileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDirectory, uniqueFileName);

            // Ensure the directory exists
            Files.createDirectories(filePath.getParent());

            // Save the file
            Files.write(filePath, file.getBytes());
        }

        // Fetch the student using phone number
        StudentDTO studentDTO = studentService.getStudent(phone);

        if (studentDTO == null) {
            return new ResponseUtil(404, "Student not found!", null);
        }

        // Update details
        studentDTO.setStudent_name(studentName);
        studentDTO.setAge(age);
        studentDTO.setEmail(email);
        studentDTO.setAddress(address);
        studentDTO.setSchool(school);
        studentDTO.setAdminId(adminId);

        if (uniqueFileName != null) {
            studentDTO.setImage(uniqueFileName); // Update only if new image is uploaded
        }

        boolean isUpdated = studentService.updateStudent(studentDTO);

        if (isUpdated) {
            return new ResponseUtil(200, "Student updated!", null);
        }
        return new ResponseUtil(500, "Student update failed!", null);
    }


    @DeleteMapping("delete/{mobile}")
    public ResponseEntity<ResponseUtil> deleteStudent(@PathVariable String mobile) {
        boolean isDeleted = studentService.deleteStudent(mobile);

        if (isDeleted) {
            return ResponseEntity.ok(new ResponseUtil(200, "Student deleted!", null));  // ✅ 200 OK
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResponseUtil(404, "Student not found!", null));  // ✅ 404 NOT FOUND
    }


    @GetMapping("getStName")
    public List<String> getStNames() {
        List<String> studentNames = studentService.getStudentNames();
        return studentNames;
    }

    @GetMapping("getStId/{studentName}")
    public Integer getStId(@PathVariable String studentName) {

        return studentService.getStudentID(studentName);
    }

    @GetMapping("/getStudents")
    public ResponseEntity<List<StudentDTO>> getStudents(
            @RequestParam String instructorName,
            @RequestParam int classId) {
        List<StudentDTO> students = studentService.getStudents(instructorName, classId);
        return ResponseEntity.ok(students);
    }

}


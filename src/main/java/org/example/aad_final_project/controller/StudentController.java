package org.example.aad_final_project.controller;


import jakarta.validation.*;
import org.example.aad_final_project.dto.NewStudentDTO;
import org.example.aad_final_project.dto.ResponseDTO;
import org.example.aad_final_project.dto.StudentDTO;
import org.example.aad_final_project.dto.StudentMyClassDTO;
import org.example.aad_final_project.service.impl.StudentServiceImpl;
import org.example.aad_final_project.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.example.aad_final_project.util.ResponseUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Validated
@CrossOrigin(origins = "http://localhost:63342")
@RestController
@RequestMapping("api/v1/student")
public class StudentController {

       @Autowired
       private StudentServiceImpl studentService;

    public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/uploads/";

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("save")
    public StudentDTO saveStudent(
            @RequestParam("student_name") String studentName,
            @RequestParam("age") int age,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam("address") String address,
            @RequestParam("school") String school,
            @RequestParam("image") MultipartFile file,
            @RequestParam("adminId") Integer adminId
    ) throws IOException {

        StudentDTO dto = new StudentDTO(studentName, age, email, phone, address, school, "", adminId);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<StudentDTO>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }


        String uniqueFileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDirectory, uniqueFileName);


        Files.createDirectories(filePath.getParent());


        Files.write(filePath, file.getBytes());


        StudentDTO studentDTO = new StudentDTO(studentName, age, email, phone, address, school, uniqueFileName,adminId);
        StudentDTO studentDTO1 = studentService.save(studentDTO);

//        if (isSaved) {
//            return new ResponseUtil(201, "Student saved!", null);
//        } else {
//            return new ResponseUtil(200, "Student not saved!", null);
//        }
        return studentDTO1;
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


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("update")
    public ResponseUtil updateStudent(
            @RequestParam("student_name") String studentName,
            @RequestParam("age") int age,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam("address") String address,
            @RequestParam("school") String school,
            @RequestPart(value = "image", required = false) MultipartFile file,
            @RequestParam("adminId") Integer adminId
    ) throws IOException {



        StudentDTO dto = new StudentDTO(studentName, age, email, phone, address, school, "", adminId);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<StudentDTO>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }


        String uniqueFileName = null;

        if (file != null && !file.isEmpty()) {
            uniqueFileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDirectory, uniqueFileName);


            Files.createDirectories(filePath.getParent());


            Files.write(filePath, file.getBytes());
        }


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
        studentDTO.setAdmin_id(adminId);

        if (uniqueFileName != null) {
            studentDTO.setImage(uniqueFileName); // Update only if new image is uploaded
        }

        boolean isUpdated = studentService.updateStudent(studentDTO);

        if (isUpdated) {
            return new ResponseUtil(200, "Student updated!", null);
        }
        return new ResponseUtil(500, "Student update failed!", null);
    }


    @PutMapping("updateProfile")
    public ResponseUtil updateStudent(
            @RequestParam("student_name") String studentName,
            @RequestParam("age") int age,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam("address") String address,
            @RequestParam("school") String school,
            @RequestPart(value = "image", required = false) MultipartFile file

    ) throws IOException {

        StudentDTO dto = new StudentDTO(studentName, age, email, phone, address, school, "");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<StudentDTO>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }


        String uniqueFileName = null;

        if (file != null && !file.isEmpty()) {
            uniqueFileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDirectory, uniqueFileName);


            Files.createDirectories(filePath.getParent());


            Files.write(filePath, file.getBytes());
        }


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


        if (uniqueFileName != null) {
            studentDTO.setImage(uniqueFileName); // Update only if new image is uploaded
        }

        boolean isUpdated = studentService.updateStudent(studentDTO);

        if (isUpdated) {
            return new ResponseUtil(200, "Student updated!", null);
        }
        return new ResponseUtil(500, "Student update failed!", null);
    }



    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("delete/{mobile}")
    public ResponseEntity<ResponseUtil> deleteStudent(@PathVariable String mobile) {
        boolean isDeleted = studentService.deleteStudent(mobile);

        if (isDeleted) {
            return ResponseEntity.ok(new ResponseUtil(200, "Student deleted!", null));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResponseUtil(404, "Student not found!", null));
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

    @GetMapping("getStudents")
    public ResponseEntity<List<StudentDTO>> getStudents(
            @RequestParam String instructorName,
            @RequestParam int classId) {
        List<StudentDTO> students = studentService.getStudents(instructorName, classId);
        return ResponseEntity.ok(students);
    }

    @GetMapping("getStudentId/{email}")
    public ResponseEntity<ResponseDTO> getStudentId(@PathVariable String email) {
        System.out.println("loggedEmail " + email);
        Integer studentId = studentService.getStudentIdByEmail(email);

        System.out.println("studentId " + studentId);

        if (studentId != null) {
            return ResponseEntity.ok(new ResponseDTO(VarList.OK, "Student ID Retrieved", studentId));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(VarList.Not_Found, "Student Not Found", null));
        }
    }


    @GetMapping("details/{studentId}")
    public ResponseEntity<ResponseDTO> getStudentDetails(@PathVariable Integer studentId) {

        System.out.println(studentId);

        StudentMyClassDTO details = studentService.getStudentDetails(studentId);

        System.out.println(details.toString());

        if (details != null) {
            return ResponseEntity.ok(new ResponseDTO(VarList.OK, "Student Details Retrieved", details));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(VarList.Not_Found, "Student Not Found", null));
        }
    }


    @GetMapping("getName/{studentId}")
    public ResponseEntity<ResponseDTO> getStudentName(@PathVariable String studentId) {
        String studentName = studentService.getName(Integer.valueOf(studentId));
        System.out.println("Student Name: " + studentName);

        if (studentName != null) {
            return ResponseEntity.ok(new ResponseDTO(VarList.OK, "Student Name Retrieved", studentName));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(VarList.Not_Found, "Student Name Not Found", null));
        }
    }


    @PostMapping("saveNew")
    public ResponseUtil saveNewStudent(@RequestBody NewStudentDTO newStudentDTO) {
        boolean isSaved = studentService.saveNewStudent(newStudentDTO);
        if (isSaved) {
            return new ResponseUtil(200, "Student saved!", null);
        }
        return new ResponseUtil(500, "Student saving failed!", null);
    }


    @GetMapping("getStudentProfile/{email}")
    public ResponseEntity<StudentDTO> getStudentProfile(@PathVariable String email) {
        StudentDTO studentDTO = studentService.getStudentProfile(email);

        if (studentDTO != null) {
            System.out.println("Student found: " + studentDTO);
            return ResponseEntity.ok(studentDTO);
        } else {
            System.out.println("Student not found for mobile: " + email);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("count")
    public ResponseEntity<Long> getStudentCount() {
        long count = studentService.getStudentCount();
        return ResponseEntity.ok(count);
    }
}


package org.example.aad_final_project.controller;

import org.example.aad_final_project.dto.InstructorDTO;
import org.example.aad_final_project.dto.SubjectDTO;
import org.example.aad_final_project.entity.Instructor;
import org.example.aad_final_project.service.InstructorService;
import org.example.aad_final_project.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.example.aad_final_project.controller.StudentController.uploadDirectory;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/v1/instructor")
public class InstructorController {

    @Autowired
    private InstructorService instructorService;

    public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/uploads/";

    @PostMapping("save")
    public ResponseUtil saveInstructor(
            @RequestParam("instructor_name") String instructorName,
            @RequestParam("image") MultipartFile file,
            @RequestParam("address") String address,
            @RequestParam("phone") String phone,
            @RequestParam("email") String email,
            @RequestParam("qualification") String qualification,
            @RequestParam("subject_code") String subjectCode,
            @RequestParam("subject_id") Integer subjectID,
            @RequestParam("admin_id") Integer adminId
    ) throws Exception {

        System.out.println(instructorName +" "+file + " " + address + " " + phone + " " + email + " " + qualification + " " + subjectCode + " " + subjectID);

        String uniqueFileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDirectory, uniqueFileName);

        Files.createDirectories(filePath.getParent());

        Files.write(filePath, file.getBytes());

        InstructorDTO instructorDTO = new InstructorDTO(instructorName,uniqueFileName,address,phone,email,qualification,subjectCode,subjectID,adminId);

        boolean isSaved =  instructorService.saveInstructor(instructorDTO);

        System.out.println("isSaved "+ isSaved);

        if (isSaved) {

            return new ResponseUtil(201, "Instructor saved!", null);
        } else {
            return new ResponseUtil(200, "Instructor not saved!", null);
        }
    }

    @GetMapping("getAll")
    public List<InstructorDTO> getAllInstructors() {
        List<InstructorDTO> instructorDTOS = instructorService.getAll();

        return instructorDTOS;
    }


    @GetMapping("getInstructor/{instructorName}")
    public InstructorDTO getInstructor(@PathVariable String instructorName) {
        InstructorDTO instructorDTO = instructorService.getInstructor(instructorName);
        return instructorDTO;
    }

    @PutMapping("update")
    public ResponseUtil updateInstructor(
            @RequestParam("instructor_name") String instructorName,
            @RequestPart(value = "image", required = false) MultipartFile file,
            @RequestParam("address") String address,
            @RequestParam("phone") String phone,
            @RequestParam("email") String email,
            @RequestParam("qualification") String qualification,
            @RequestParam("subject_code") String subjectCode,
            @RequestParam("subject_id") Integer subjectID,
            @RequestParam("admin_id") Integer adminId
    ) throws Exception {
        System.out.println(file);

        String uniqueFileName = null;

        if (file != null && !file.isEmpty()) {
            uniqueFileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDirectory, uniqueFileName);

            // Ensure the directory exists
            Files.createDirectories(filePath.getParent());

            // Save the file
            Files.write(filePath, file.getBytes());
        }

        InstructorDTO instructorDTO = instructorService.getInstructor(instructorName);

        if (instructorDTO == null) {
            return new ResponseUtil(404, "Instructor not found!", null);
        }

        instructorDTO.setInstructor_name(instructorName);
        instructorDTO.setAddress(address);
        instructorDTO.setPhone(phone);
        instructorDTO.setEmail(email);
        instructorDTO.setQualification(qualification);
        instructorDTO.setSubject_code(subjectCode);
        instructorDTO.setSubject_id(subjectID);
        instructorDTO.setAdmin_id(adminId);

        if (uniqueFileName != null) {
            instructorDTO.setImage(uniqueFileName);
        }

        boolean isUpdated = instructorService.updateInstructor(instructorDTO);
        System.out.println(isUpdated);

        if (isUpdated) {
            return new ResponseUtil(201, "Instructor updated!", null);
        } else {
            return new ResponseUtil(200, "Instructor not updated!", null);
        }
    }

    @DeleteMapping("delete/{email}")
    public ResponseUtil deleteInstructor(@PathVariable String email) {

        boolean isDeleted = instructorService.deleteInstructor(email);

        if (isDeleted) {
            return new ResponseUtil(201, "Instructor deleted!", null);
        } else {
            return new ResponseUtil(200, "Instructor not deleted!", null);
        }
    }

    @GetMapping("getInstName")
    public List<String> getInstructorNames(){
        return instructorService.getNames();
    }

    @GetMapping("getId/{instructorName}")
    public String getInstructorId(@PathVariable String instructorName){
        return instructorService.getId(instructorName);
    }

}

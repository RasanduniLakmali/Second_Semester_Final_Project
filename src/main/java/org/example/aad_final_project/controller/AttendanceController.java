package org.example.aad_final_project.controller;

import org.example.aad_final_project.dto.AttendanceDTO;
import org.example.aad_final_project.dto.AttendanceSummaryDTO;
import org.example.aad_final_project.dto.ResponseDTO;
import org.example.aad_final_project.dto.ScheduleDTO;
import org.example.aad_final_project.service.AttendanceService;
import org.example.aad_final_project.util.ResponseUtil;
import org.example.aad_final_project.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/v1/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;


    @PostMapping("save")
    public ResponseEntity<ResponseDTO> saveAttendance(@RequestBody AttendanceDTO attendanceDTO) {
        System.out.println("Attendance " + attendanceDTO);

        boolean isSaved = attendanceService.saveAttendance(attendanceDTO);

        System.out.println(isSaved);

        if (isSaved) {
            return ResponseEntity.ok(new ResponseDTO(VarList.OK, "Attendance saved successfully!", null));
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(VarList.Not_Found, "Attendance not saved!", null));
        }
    }

    @GetMapping("getAll")
    public List<AttendanceDTO> getAllAttendance() {
        return attendanceService.getAll();
    }

    @PutMapping("update")
    public ResponseUtil updateAttendance(@RequestBody AttendanceDTO attendanceDTO) {

        System.out.println("update" + attendanceDTO.toString());

        boolean isUpdated =attendanceService.updateAttendance(attendanceDTO);
        System.out.println(isUpdated);
        if (isUpdated) {
            return new ResponseUtil(201, "Attendance updated!", null);
        } else {
            return new ResponseUtil(200, "Attendance not updated!", null);
        }
    }


    @GetMapping("/summary")
    public ResponseEntity<List<AttendanceSummaryDTO>> getSummary(
            @RequestParam String subject,
            @RequestParam int month) {

        List<AttendanceSummaryDTO> summaryList = attendanceService.getAttendanceSummary(subject, month);
        return ResponseEntity.ok(summaryList);
    }

}

package org.example.aad_final_project.service;

import org.example.aad_final_project.dto.AttendanceDTO;
import org.example.aad_final_project.dto.AttendanceSummaryDTO;

import java.util.List;
import java.util.Map;

public interface AttendanceService {

   boolean saveAttendance(AttendanceDTO attendanceDTO);

    List<AttendanceDTO> getAll();

    boolean updateAttendance(AttendanceDTO attendanceDTO);

    public List<AttendanceSummaryDTO> getAttendanceSummary(String subjectName, int month);
    List<AttendanceDTO> getAttendanceBySubjectAndMonth(String subject, int month);
}

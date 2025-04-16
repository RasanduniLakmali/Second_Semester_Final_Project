package org.example.aad_final_project.repo;

import org.example.aad_final_project.dto.AttendanceSummaryDTO;

import org.example.aad_final_project.entity.Attendance;
import org.example.aad_final_project.entity.ClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepo extends JpaRepository<Attendance,Integer> {

    @Query("SELECT a from Attendance a where a.student_name=:studentName AND a.attendance_date=:attendanceDate AND a.subject_name=:subjectName")
    Optional<Attendance> findAttendance(@Param("studentName") String studentName,@Param("attendanceDate") LocalDate attendanceDate,@Param("subjectName") String subjectName);


    @Query("SELECT a FROM Attendance a WHERE a.subject.subject_name = :subject AND MONTH(a.attendance_date) = :month")
    List<Attendance> findBySubjectNameAndMonth(@Param("subject") String subject, @Param("month") int month);

    @Query("SELECT a FROM Attendance a WHERE a.subject_name = :subjectName AND MONTH(a.attendance_date) = :month")
    List<Attendance> findBySubjectAndMonth(@Param("subjectName") String subjectName, @Param("month") int month);

    @Query("SELECT new org.example.aad_final_project.dto.AttendanceSummaryDTO(" +
            "a.student.student_id, a.student.student_name, COUNT(a), " +
            "SUM(CASE WHEN a.status = 'Present' THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN a.status = 'Absent' THEN 1 ELSE 0 END), " +
            "ROUND(SUM(CASE WHEN a.status = 'Present' THEN 1 ELSE 0 END) * 100.0 / COUNT(a), 2), " +
            "CASE " +
            "WHEN SUM(CASE WHEN a.status = 'Present' THEN 1 ELSE 0 END) * 100.0 / COUNT(a) < 50 THEN 'At Risk' " +
            "WHEN SUM(CASE WHEN a.status = 'Present' THEN 1 ELSE 0 END) * 100.0 / COUNT(a) < 75 THEN 'Warning' " +
            "ELSE 'Good' " +
            "END) " +
            "FROM Attendance a WHERE a.subject.subject_name = :subjectName AND MONTH(a.attendance_date) = :month " +
            "GROUP BY a.student.student_id, a.student.student_name")
    List<AttendanceSummaryDTO> getAttendanceSummary(String subjectName, int month);



}






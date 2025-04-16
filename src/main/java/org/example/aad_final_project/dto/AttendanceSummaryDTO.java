package org.example.aad_final_project.dto;

import lombok.*;

@NoArgsConstructor

@Getter
@Setter
@ToString
public class AttendanceSummaryDTO {
    private int studentId;
    private String studentName;
    private long totalClasses;
    private long presentCount;
    private long absentCount;
    private double attendancePercentage;
    private String status;

    public AttendanceSummaryDTO(int studentId, String studentName, long totalClasses,
                                long presentCount, long absentCount,
                                double attendancePercentage, String status) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.totalClasses = totalClasses;
        this.presentCount = presentCount;
        this.absentCount = absentCount;
        this.attendancePercentage = attendancePercentage;
        this.status = status;
    }

}





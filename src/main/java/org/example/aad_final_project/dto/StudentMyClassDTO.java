package org.example.aad_final_project.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;


@NoArgsConstructor
@Getter
@Setter
@ToString
public class StudentMyClassDTO {

    private String subjectName;
    private String className;
    private String hallNumber;
    private LocalDate scheduleDay;
    private LocalTime startTime;
    private LocalTime endTime;
    private String instructorName;

    public StudentMyClassDTO(String subjectName, String className, String hallNumber, LocalDate scheduleDay, LocalTime startTime,LocalTime endTime) {
        this.subjectName = subjectName;
        this.className = className;
        this.hallNumber = hallNumber;
        this.scheduleDay = scheduleDay;
        this.startTime = startTime;
        this.endTime = endTime;
    }


    public StudentMyClassDTO(String subjectName, String className, String hallNumber, LocalDate scheduleDay, LocalTime startTime, LocalTime endTime, String instructorName) {
        this.subjectName = subjectName;
        this.className = className;
        this.hallNumber = hallNumber;
        this.scheduleDay = scheduleDay;
        this.startTime = startTime;
        this.endTime = endTime;
        this.instructorName = instructorName;
    }

}

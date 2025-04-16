package org.example.aad_final_project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Scope;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Timer;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int schedule_id;
    private LocalDate schedule_date;
    private LocalTime start_time;
    private LocalTime end_time;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private ClassEntity classEntity;

    private String class_name;

    private String instructor_name;

    private String subject_name;

    private String schedule_day;

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL)
    private List<SubjectSchedule> subjectSchedules;

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL)
    private List<InstructorSchedule> instructorSchedules;
}

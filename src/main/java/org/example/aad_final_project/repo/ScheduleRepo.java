package org.example.aad_final_project.repo;

import jakarta.transaction.Transactional;
import org.example.aad_final_project.entity.Instructor;
import org.example.aad_final_project.entity.Schedule;
import org.example.aad_final_project.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

@Repository
public interface ScheduleRepo extends JpaRepository<Schedule,Integer> {

    @Query("SELECT s.schedule_id from Schedule  s where s.schedule_date=:date AND s.class_name=:className AND s.instructor_name=:instructorName")
    Integer findByDateAndClassAndInstructor(@Param("date") LocalDate date, @Param("className") String className, @Param("instructorName") String instructorName);


    @Query("SELECT s from Schedule  s where s.schedule_date=:date2 AND s.class_name=:className2 AND s.instructor_name=:instructorName2")
    Optional<Schedule> findSchedule(@Param("date2") LocalDate date2, @Param("className2") String className2, @Param("instructorName2") String instructorName2);

    @Modifying
    @Query("DELETE from Schedule s where s.schedule_date=:date AND s.class_name=:className AND s.instructor_name=:instructorName")
    @Transactional
    void deleteSchedule(@Param("date") LocalDate date,@Param("className") String className,@Param("instructorName") String instructorName);
}

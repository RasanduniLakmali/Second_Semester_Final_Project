package org.example.aad_final_project.repo;

import org.example.aad_final_project.dto.StudentMyClassDTO;
import org.example.aad_final_project.entity.ClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClassRepo extends JpaRepository<ClassEntity,Integer> {

    @Query("SELECT c from ClassEntity c where c.class_name=:className")
    Optional<ClassEntity> findByClassName(@Param("className") String className);


    @Modifying
    @Query("DELETE from ClassEntity c where c.class_name=:class_name")
    @Transactional
    void deleteByClassName(@Param("class_name") String class_name);


    @Query("SELECT c.class_id from ClassEntity c where c.class_name=:class_name")
    String findByClass_name(@Param("class_name") String class_name);


    @Query("SELECT new org.example.aad_final_project.dto.StudentMyClassDTO(s.subject_name, s.class_name, c.hall_number, s.schedule_date, s.start_time, s.end_time,s.instructor_name) " +
            "FROM StudentSubject ss " +
            "JOIN Schedule s ON s.subject_name = ss.subject_name " +
            "JOIN ClassEntity c ON c.class_id = s.classEntity.class_id " +
            "WHERE s.classEntity.class_id =:classId AND s.schedule_date =:scheduleDate")
    List<StudentMyClassDTO> findByClassAndSubject(@Param("classId") String classId, @Param("scheduleDate")LocalDate scheduleDate);

    @Query("SELECT c.class_fee from ClassEntity c where c.class_name=:className")
    String findClassFee(@Param("className") String className);


    @Query("SELECT cs.class_name from ClassStudent cs where cs.student_name=:studentName")
    String findByStudent_name(@Param("studentName") String studentName);
}

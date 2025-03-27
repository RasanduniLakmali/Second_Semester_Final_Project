package org.example.aad_final_project.repo;

import org.example.aad_final_project.entity.Student;
import org.example.aad_final_project.entity.StudentSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentSubjectRepo extends JpaRepository<StudentSubject,Integer> {

    @Query("SELECT s.subject_name FROM StudentSubject s WHERE s.student_name = :studentName")
    List<String> findByStudentName(@Param("studentName") String studentName);

    @Query("SELECT s FROM StudentSubject s WHERE s.student.student_id = :studentId AND s.student_name = :studentName")
    Optional<StudentSubject> findByStudentAndName(@Param("studentId") Integer studentId, @Param("studentName") String studentName);


}

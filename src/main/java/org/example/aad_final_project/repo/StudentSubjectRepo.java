package org.example.aad_final_project.repo;

import org.example.aad_final_project.dto.StudentSubjectDTO;
import org.example.aad_final_project.entity.Student;
import org.example.aad_final_project.entity.StudentSubject;
import org.example.aad_final_project.entity.Subject;
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

    @Query("SELECT s FROM StudentSubject s WHERE s.student.student_id = :studentId AND s.subject.subject_id = :subjectId")
    Optional<StudentSubject> findByStudent_idAndSubject_id(Integer studentId,Integer subjectId);


    @Query("SELECT s.subject from StudentSubject s where s.student=:student")
    List<Subject> findByStudentsContaining(@Param("student") Student student);
}

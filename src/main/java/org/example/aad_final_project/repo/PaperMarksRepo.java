package org.example.aad_final_project.repo;

import jakarta.transaction.Transactional;
import org.example.aad_final_project.dto.PaperMarkDTO;
import org.example.aad_final_project.entity.ClassEntity;
import org.example.aad_final_project.entity.PaperMark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaperMarksRepo extends JpaRepository<PaperMark,Integer> {

    @Query("SELECT p FROM PaperMark p where p.student_name=:studentName AND p.subject_name=:subjectName")
    Optional<PaperMark> findByStudentAndSubject(@Param("studentName") String studentName,@Param("subjectName") String subjectName);


    @Modifying
    @Query("DELETE FROM PaperMark p where p.student_name=:studentName AND p.subject_name=:subjectName")
    @Transactional
    void deleteByStudentAndSubject(@Param("studentName") String studentName,@Param("subjectName") String subjectName);

    @Query("SELECT m FROM PaperMark m WHERE m.student.student_id = :studentId AND m.subject_name = :subjectName")
    List<PaperMark> findByStudent_StudentId(String studentId, String subjectName);

}

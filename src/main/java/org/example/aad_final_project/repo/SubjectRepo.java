package org.example.aad_final_project.repo;

import jakarta.transaction.Transactional;
import org.example.aad_final_project.entity.Student;
import org.example.aad_final_project.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubjectRepo extends JpaRepository<Subject,Integer> {

    @Query("SELECT s FROM Subject s WHERE s.subject_name = :subjectName")
    Optional<Subject> findBySubjectName(@Param("subjectName") String subjectName);


    @Modifying
    @Query("DELETE from Subject s where s.subject_name = :subject_name")
    @Transactional
    void deleteBySubjectName(@Param("subject_name") String subject_name);


    @Query("SELECT s.subject_id from Subject s where s.subject_code=:subjectCode")
    Integer findBySubject_code(@Param("subjectCode") String subjectCode);

    @Query("SELECT s.subject_id FROM Subject s WHERE s.subject_name = :subject_name")
    Integer findBySubject_name(@Param("subject_name") String subject_name);
}

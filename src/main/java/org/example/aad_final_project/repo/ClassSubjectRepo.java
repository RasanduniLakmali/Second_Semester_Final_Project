package org.example.aad_final_project.repo;

import jakarta.transaction.Transactional;
import org.example.aad_final_project.dto.SubjectInstructorDTO;
import org.example.aad_final_project.entity.ClassStudent;
import org.example.aad_final_project.entity.ClassSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassSubjectRepo extends JpaRepository<ClassSubject,Integer> {


    @Query("SELECT cs FROM ClassSubject cs WHERE cs.subject_name = :subjectName AND cs.class_name = :className")
    Optional<ClassSubject> findBySubjectAndClass(
            @Param("subjectName") String subjectName,
            @Param("className") String className
    );

    @Modifying
    @Query("DELETE from ClassSubject cs where cs.subject_name = :subjectName AND cs.class_name = :className")
    @Transactional
    void deleteBySubjectAndClass(@Param("subjectName") String subjectName,
                                 @Param("className") String className);



    @Query("SELECT DISTINCT new org.example.aad_final_project.dto.SubjectInstructorDTO(" +
            "s.subject_name, s.grade_range, i.instructor_name, cs.time_duration, cs.class_name) " +
            "from Subject s JOIN ClassSubject cs " +
            "ON s.subject_id = cs.subject.subject_id JOIN Instructor i " +
            "ON i.subject.subject_id = cs.subject.subject_id")
    List<SubjectInstructorDTO> getAllSubjects();



}

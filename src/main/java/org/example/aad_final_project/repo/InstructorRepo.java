package org.example.aad_final_project.repo;

import jakarta.transaction.Transactional;
import org.example.aad_final_project.dto.InstructorDTO;
import org.example.aad_final_project.entity.Instructor;
import org.example.aad_final_project.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface InstructorRepo extends JpaRepository<Instructor,Integer> {

        @Query("SELECT i from Instructor i where i.instructor_name=:instructorName")
        Optional<Instructor> findInstructorByName(@Param("instructorName")String instructorName);

        @Query("SELECT i from Instructor i where i.email=:email")
        Optional<Instructor> findByEmail(@Param("email") String email);

        @Transactional
        void deleteByEmail(String email);

        @Query("SELECT i.instructor_id from Instructor i where i.instructor_name=:instructor_name")
        String findByInstructor_name(@Param("instructor_name") String instructor_name);

        @Query("SELECT i.email from Instructor i where i.instructor_name=:instructorName")
        String findEmailByName(@Param("instructorName") String instructorName);
}

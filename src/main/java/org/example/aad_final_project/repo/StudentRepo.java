package org.example.aad_final_project.repo;

import org.example.aad_final_project.dto.StudentDTO;
import org.example.aad_final_project.entity.Admin;
import org.example.aad_final_project.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepo extends JpaRepository<Student,Integer> {

    Optional<Student> findByPhone(String phone);

    boolean existsByPhone(String phone);

    @Transactional
    void deleteByPhone(String phone);

    @Query("SELECT s.student_id from Student s where s.student_name=:student_name")
    Integer findByStudent_name(@Param("student_name") String student_name);


    @Query("SELECT new org.example.aad_final_project.dto.StudentDTO(s.student_name, s.age, s.email, s.phone, s.address, s.school) " +
            "FROM Student s " +
            "JOIN StudentSubject ss ON s.student_id = ss.student.student_id " +
            "JOIN ClassStudent cs ON s.student_id = cs.student.student_id " +
            "WHERE ss.instructor_name = :instructorName AND cs.classEntity.class_id = :classId")
    List<StudentDTO> findStudents(@Param("instructorName") String instructorName, @Param("classId") int classId);
}

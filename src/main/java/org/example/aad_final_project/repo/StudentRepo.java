package org.example.aad_final_project.repo;

import org.example.aad_final_project.dto.StudentDTO;
import org.example.aad_final_project.dto.StudentMyClassDTO;
import org.example.aad_final_project.entity.Student;
import org.springframework.data.domain.Pageable;
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


    @Query("SELECT s.student_id FROM Student s WHERE s.email = :email")
    Integer findStudentIdByEmail(@Param("email") String email);

    @Query("SELECT new org.example.aad_final_project.dto.StudentMyClassDTO(s.subject_name, s.class_name, c.hall_number, s.schedule_date, s.start_time, s.end_time,s.instructor_name) " +
            "FROM StudentSubject ss " +
            "JOIN Schedule s ON s.subject_name = ss.subject_name " +
            "JOIN ClassEntity c ON c.class_id = s.classEntity.class_id " +
            "WHERE ss.student.student_id = :studentId " +
            "AND s.schedule_date >= CURRENT_DATE " +
            "ORDER BY s.schedule_date ASC")
    List<StudentMyClassDTO> findStudentDetails(@Param("studentId") Integer studentId, Pageable pageable);


    @Query("SELECT s.student_name from Student s where s.student_id=:studentId")
    String findStudentNameById(@Param("studentId") Integer studentId);


    Student findByEmail(String email);
}

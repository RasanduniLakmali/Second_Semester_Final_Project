package org.example.aad_final_project.repo;

import org.example.aad_final_project.entity.ClassStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassStudentRepo extends JpaRepository<ClassStudent,Integer> {

    @Query("SELECT c.class_name from ClassStudent c where c.student_name=:student_name")
    String findByStudent_name(@Param("student_name") String student_name);


}

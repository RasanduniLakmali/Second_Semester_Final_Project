package org.example.aad_final_project.repo;

import org.example.aad_final_project.entity.Instructor;
import org.example.aad_final_project.entity.NewStudent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewStudentRepo extends JpaRepository<NewStudent,Integer> {
}

package org.example.aad_final_project.repo;

import org.example.aad_final_project.entity.ClassInstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassInstructorRepo extends JpaRepository<ClassInstructor,Integer> {
}

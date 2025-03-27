package org.example.aad_final_project.repo;

import org.example.aad_final_project.entity.InstructorSchedule;
import org.example.aad_final_project.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructorScheduleRepo extends JpaRepository<InstructorSchedule,Integer> {
}

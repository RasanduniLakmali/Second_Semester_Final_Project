package org.example.aad_final_project.repo;

import jakarta.transaction.Transactional;
import org.example.aad_final_project.entity.Subject;
import org.example.aad_final_project.entity.SubjectSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectScheduleRepo extends JpaRepository<SubjectSchedule,Integer> {

//    @Query("DELETE from SubjectSchedule s where s.schedule.schedule_id=:schedule_id")
//    @Transactional
//    void deleteByScheduleId(@Param("schedule_id") Integer schedule_id);
}

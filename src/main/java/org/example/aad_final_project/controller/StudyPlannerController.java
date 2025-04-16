package org.example.aad_final_project.controller;

import org.example.aad_final_project.dto.PlannerRequestDTO;
import org.example.aad_final_project.dto.StudyPlanDTO;
import org.example.aad_final_project.service.StudentSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/v1/planner")
public class StudyPlannerController {

    @Autowired
    private StudentSubjectService studentSubjectService;

    @PostMapping("generate")
    public ResponseEntity<Map<String, Object>> generatePlanner(@RequestBody PlannerRequestDTO plannerRequestDTO) {
        System.out.println(plannerRequestDTO.getEmail());

        List<String> enrolledSubjects = studentSubjectService.getSubjectsByEmail(plannerRequestDTO.getEmail());

        // Check if there are enrolled subjects
        if (enrolledSubjects.isEmpty()) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "No subjects enrolled for this student.");
            return ResponseEntity.badRequest().body(errorResponse);
        }

        // Get total free hours
        int totalFreeHours = plannerRequestDTO.getFreeHours();

        // Calculate how much time to allocate per subject (can be fractional)
        double hoursPerSubject = (double) totalFreeHours / enrolledSubjects.size();

        // Optional: Round to 2 decimal places (or your desired precision)
        hoursPerSubject = Math.round(hoursPerSubject * 100.0) / 100.0;

        List<StudyPlanDTO> plan = new ArrayList<>();

        // For each subject, allocate the calculated time
        for (String subject : enrolledSubjects) {
            plan.add(new StudyPlanDTO(subject, hoursPerSubject));
        }

        Map<String, Object> response = new HashMap<>();
        response.put("plan", plan);
        return ResponseEntity.ok(response);
    }

}

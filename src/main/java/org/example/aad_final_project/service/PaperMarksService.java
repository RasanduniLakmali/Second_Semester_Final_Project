package org.example.aad_final_project.service;

import org.example.aad_final_project.dto.PaperMarkDTO;

import java.util.List;

public interface PaperMarksService {

    boolean saveAllMarks(PaperMarkDTO paperMarkDTO);

    List<PaperMarkDTO> getAllMarks();

    boolean updateMarks(PaperMarkDTO paperMarkDTO);

    boolean deleteMarks(String studentName, String subjectName);

    List<PaperMarkDTO> getStudentMarks(String studentId,String subjectName);


}

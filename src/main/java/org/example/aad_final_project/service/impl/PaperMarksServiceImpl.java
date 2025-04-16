package org.example.aad_final_project.service.impl;

import org.example.aad_final_project.dto.ClassSubjectDTO;
import org.example.aad_final_project.dto.PaperMarkDTO;
import org.example.aad_final_project.entity.ClassSubject;
import org.example.aad_final_project.entity.PaperMark;
import org.example.aad_final_project.entity.Student;
import org.example.aad_final_project.entity.Subject;
import org.example.aad_final_project.repo.PaperMarksRepo;
import org.example.aad_final_project.repo.StudentRepo;
import org.example.aad_final_project.repo.SubjectRepo;
import org.example.aad_final_project.service.PaperMarksService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaperMarksServiceImpl implements PaperMarksService {

    @Autowired
    private PaperMarksRepo paperMarksRepo;

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private SubjectRepo subjectRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public boolean saveAllMarks(PaperMarkDTO paperMarkDTO) {
        PaperMark paperMark = modelMapper.map(paperMarkDTO, PaperMark.class);

        Optional<Student> optionalStudent = studentRepo.findById(Integer.valueOf(paperMarkDTO.getStudent_id()));
        Optional<Subject> optionalSubject = subjectRepo.findById(Integer.valueOf(paperMarkDTO.getSubject_id()));

        if (optionalStudent.isPresent() && optionalSubject.isPresent()) {
            paperMark.setStudent(optionalStudent.get());
            paperMark.setSubject(optionalSubject.get());
            paperMarksRepo.save(paperMark);
            return true;
        }
        return false;
    }

    @Override
    public List<PaperMarkDTO> getAllMarks() {
        List<PaperMark> paperMarks = paperMarksRepo.findAll();

        return paperMarks.stream().map(paperMark -> {
            PaperMarkDTO dto = new PaperMarkDTO();


            dto.setId(paperMark.getId());
            dto.setStudent_id(String.valueOf(paperMark.getStudent().getStudent_id()));
            dto.setSubject_id(String.valueOf(paperMark.getSubject().getSubject_id()));
            dto.setStudent_name(paperMark.getStudent().getStudent_name());
            dto.setSubject_name(paperMark.getSubject().getSubject_name());
            dto.setPaper_number(paperMark.getPaper_number());
            dto.setTerm_name(paperMark.getTerm_name());
            dto.setMark(paperMark.getMark());

            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public boolean updateMarks(PaperMarkDTO paperMarkDTO) {
        Optional<PaperMark> optionalPaperMark = paperMarksRepo.findByStudentAndSubject(paperMarkDTO.getStudent_name(), paperMarkDTO.getSubject_name());

        if (optionalPaperMark.isPresent()) {
            PaperMark paperMark = optionalPaperMark.get();
            paperMark.setStudent(studentRepo.findById(Integer.valueOf(paperMarkDTO.getStudent_id())).orElse(null));
            paperMark.setSubject(subjectRepo.findById(Integer.valueOf(paperMarkDTO.getSubject_id())).orElse(null));
            paperMark.setStudent_name(paperMarkDTO.getStudent_name());
            paperMark.setSubject_name(paperMarkDTO.getSubject_name());
            paperMark.setPaper_number(paperMarkDTO.getPaper_number());
            paperMark.setTerm_name(paperMarkDTO.getTerm_name());
            paperMark.setMark(paperMarkDTO.getMark());
            paperMarksRepo.save(paperMark);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteMarks(String studentName, String subjectName) {
        System.out.println(studentName +" "+ subjectName);
        Optional<PaperMark> optionalPaperMark = paperMarksRepo.findByStudentAndSubject(studentName, subjectName);

        if (optionalPaperMark.isPresent()) {
            paperMarksRepo.deleteByStudentAndSubject(studentName,subjectName);
            return true;
        }
        return false;
    }

    @Override
    public List<PaperMarkDTO> getStudentMarks(String studentId, String subjectName) {
        List<PaperMark> paperMarks = paperMarksRepo.findByStudent_StudentId(studentId, subjectName);

        if (paperMarks == null || paperMarks.isEmpty()) {
            System.out.println("No marks found for studentId: " + studentId + " and subject: " + subjectName);
            return Collections.emptyList();
        }

        return paperMarks.stream()
                .map(mark -> new PaperMarkDTO(
                        mark.getPaper_number(),
                        mark.getMark(),
                        mark.getTerm_name()
                ))
                .collect(Collectors.toList());
    }


}

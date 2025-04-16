package org.example.aad_final_project.dto;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PaperMarkDTO {

    private int id;
    private String student_id;
    private String subject_id;
    private String student_name;
    private String subject_name;
    private String paper_number;
    private String mark;
    private String term_name;


    public PaperMarkDTO(String paper_number, String mark, String term_name) {
        this.paper_number = paper_number;
        this.mark = mark;
        this.term_name = term_name;
    }
}

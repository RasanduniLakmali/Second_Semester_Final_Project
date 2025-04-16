package org.example.aad_final_project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PaperMark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    private String student_name;
    private String subject_name;
    private String paper_number;
    private String mark;
    private String term_name;

    public PaperMark(String paper_number, String mark, String term_name) {
        this.paper_number = paper_number;
        this.mark = mark;
        this.term_name = term_name;
    }
}

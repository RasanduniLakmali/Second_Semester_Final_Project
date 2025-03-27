package org.example.aad_final_project.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class InstructorDTO {

    private int instructor_id;
    private String instructor_name;
    private String image;
    private String address;
    private String phone;
    private String email;
    private String qualification;
    private String subject_code;
    private Integer subject_id;
    private Integer admin_id;

    public InstructorDTO(String instructor_name,String image, String address, String phone, String email, String qualification, String subject_code, Integer subject_id, Integer admin_id) {
        this.instructor_name = instructor_name;
        this.image = image;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.qualification = qualification;
        this.subject_code = subject_code;
        this.subject_id = subject_id;
        this.admin_id = admin_id;
    }


}

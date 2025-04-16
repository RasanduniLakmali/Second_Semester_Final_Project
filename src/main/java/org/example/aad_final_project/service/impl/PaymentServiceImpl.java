package org.example.aad_final_project.service.impl;

import org.example.aad_final_project.dto.PaymentDTO;
import org.example.aad_final_project.entity.ClassEntity;
import org.example.aad_final_project.entity.Payment;
import org.example.aad_final_project.entity.Student;
import org.example.aad_final_project.repo.ClassRepo;
import org.example.aad_final_project.repo.PaymentRepo;
import org.example.aad_final_project.repo.StudentRepo;
import org.example.aad_final_project.service.PaymentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private ClassRepo classRepo;

    @Autowired
    private PaymentRepo paymentRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PaymentDTO savePayment(PaymentDTO paymentDTO) {
        Payment payment = modelMapper.map(paymentDTO, Payment.class);

        Optional<Student> optionalStudent = studentRepo.findById(paymentDTO.getStudent_id());
        Optional<ClassEntity> optionalClass = classRepo.findById(paymentDTO.getClass_id());

        if (optionalStudent.isPresent() && optionalClass.isPresent()) {
            payment.setStudent(optionalStudent.get());
            payment.setClassEntity(optionalClass.get());
            Payment payment1 = paymentRepo.save(payment);
            return modelMapper.map(payment1, PaymentDTO.class);
        }
       return null;
    }

    @Override
    public void updateStatus(int id, String newStatus) {

        Optional<Payment> payment = paymentRepo.findById(id);
        if (payment.isPresent()) {
            payment.get().setStatus(newStatus);
            paymentRepo.save(payment.get());

        }
    }
}

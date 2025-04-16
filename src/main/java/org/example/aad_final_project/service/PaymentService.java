package org.example.aad_final_project.service;

import org.example.aad_final_project.dto.PaymentDTO;

public interface PaymentService {


    PaymentDTO savePayment(PaymentDTO paymentDTO);

    void updateStatus(int id, String newStatus);
}

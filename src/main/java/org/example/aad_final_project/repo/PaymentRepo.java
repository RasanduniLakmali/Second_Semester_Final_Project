package org.example.aad_final_project.repo;

import org.example.aad_final_project.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PaymentRepo extends JpaRepository<Payment, Integer> {



}

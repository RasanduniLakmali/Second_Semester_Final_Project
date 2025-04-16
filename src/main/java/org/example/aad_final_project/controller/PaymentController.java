package org.example.aad_final_project.controller;

import org.example.aad_final_project.dto.PaymentDTO;
import org.example.aad_final_project.dto.ResponseDTO;
import org.example.aad_final_project.entity.Payment;
import org.example.aad_final_project.service.PaymentService;
import org.example.aad_final_project.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/v1/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;


    @PostMapping("save")
    public ResponseEntity<ResponseDTO> savePayment(@RequestBody PaymentDTO paymentDTO) {

        System.out.println(paymentDTO.toString());

        PaymentDTO paymentDTO1 = paymentService.savePayment(paymentDTO);

        System.out.println(paymentDTO1.toString());

        if (paymentDTO1 != null) {
            return ResponseEntity.ok(new ResponseDTO(VarList.OK, "Payment saved successfully", paymentDTO1));
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(VarList.Not_Found, "Payment Not Saved", null));
        }
    }


    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<?> updatePaymentStatus(@PathVariable int id, @RequestBody Map<String, String> request) {
        String newStatus = request.get("status");
        paymentService.updateStatus(id, newStatus); // create this method
        return ResponseEntity.ok("Status updated");
    }


    @PostMapping("/notify")
    public ResponseEntity<String> handleNotification(@RequestParam Map<String, String> params) {
        System.out.println("Received Payment Notification:");
        params.forEach((k, v) -> System.out.println(k + ": " + v));

        String orderId = params.get("order_id");
        String status = params.get("status");

        if ("2".equals(status)) {
            System.out.println("✅ Payment Successful for Order: " + orderId);
        } else {
            System.out.println("❌ Payment Failed or Pending for Order: " + orderId);
        }

        return ResponseEntity.ok("OK");
    }

    @GetMapping("/return")
    public ResponseEntity<String> paymentReturn() {
        return ResponseEntity.ok("<h2>Thank you! Payment completed successfully.</h2>");
    }

    @GetMapping("/cancel")
    public ResponseEntity<String> paymentCancel() {
        return ResponseEntity.ok("<h2>Payment was cancelled. Please try again.</h2>");
    }
}

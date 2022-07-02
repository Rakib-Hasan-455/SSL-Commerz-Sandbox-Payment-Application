package com.example.sslcommerzsandboxpaymentapplication;

import com.example.sslcommerzsandboxpaymentapplication.entity.Appointment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@SpringBootApplication
public class SslCommerzSandboxPaymentApplication {

    public static void main(String[] args) {
        SpringApplication.run(SslCommerzSandboxPaymentApplication.class, args);
    }


    @GetMapping("/")
    public String indexPay(@ModelAttribute Appointment appointment, Model model) {
        model.addAttribute("Appointment", appointment);
        return "Patient_appoint_doctor";
    }

    @PostMapping("/handle-payment")
    public String handlePay(@ModelAttribute Appointment appointment, Model model) {
        model.addAttribute("Appointment", appointment);
        return "Patient_appoint_doctor";
    }


    @GetMapping("/pay-success")
    public String paymentSuccessful() {
        return "Payment_Success";
    }


}

package com.example.sslcommerzsandboxpaymentapplication;

import com.example.sslcommerzsandboxpaymentapplication.commerz.SSLCommerz;
import com.example.sslcommerzsandboxpaymentapplication.commerz.Utility.ParameterBuilder;
import com.example.sslcommerzsandboxpaymentapplication.entity.Appointment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

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

    @RequestMapping(value = "/handle-payment", method = RequestMethod.POST)
    public RedirectView payTest(@ModelAttribute Appointment appointment) throws Exception {
        String baseurl = "https://sslpay.herokuapp.com/";
        String payment = appointment.getAppointTime();
        String transactionID = "SKJSDKFE123";
        String time = appointment.getAppointTime();
        String patientID = appointment.getPatient_ID();
        String doctorID = appointment.getDoctor_ID();
        Map<String, String> transactionMap = ParameterBuilder.constructRequestParam(baseurl, payment, transactionID, time, patientID, doctorID);
        SSLCommerz sslCommerz = new SSLCommerz("docto62c031c5a653e", "docto62c031c5a653e@ssl", true);
        String url = sslCommerz.initiateTransaction(transactionMap, false);
        System.out.println("The url: " + url);
        System.out.println("after previous url");
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(url);
        return redirectView;
    }


    @GetMapping("/pay-success")
    public String paymentSuccessful(HttpServletRequest httpServletRequest) {
        String id = httpServletRequest.getParameter("cus_name");
        System.out.println("This is successful page.. ");
        return "Payment_Success";
    }


}

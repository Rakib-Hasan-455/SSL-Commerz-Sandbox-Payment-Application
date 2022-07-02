package com.example.sslcommerzsandboxpaymentapplication.Controller;

import com.example.sslcommerzsandboxpaymentapplication.commerz.SSLCommerz;
import com.example.sslcommerzsandboxpaymentapplication.commerz.TransactionResponseValidator;
import com.example.sslcommerzsandboxpaymentapplication.commerz.Utility.ParameterBuilder;
import com.example.sslcommerzsandboxpaymentapplication.entity.Appointment;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@ComponentScan
public class HomeController {

    @GetMapping("/")
    public String indexPay(@ModelAttribute Appointment appointment, Model model) {
        model.addAttribute("Appointment", appointment);
        return "Patient_appoint_doctor";
    }

    @PostMapping(value = "/handle-payment")
    public RedirectView payTest(@ModelAttribute Appointment appointment) throws Exception {
        String baseurl = "https://sslpay.herokuapp.com/";
        String payment = appointment.getAppointTime();
        String transactionID = "SKJSDKFE123";
        String time = appointment.getAppointTime();
        String patientID = appointment.getPatient_ID();
        String doctorID = appointment.getDoctor_ID();
//        Map<String, String> transactionMap = ParameterBuilder.constructRequestParam(baseurl, payment, transactionID, time, patientID, doctorID);
        Map<String, String> transactionMap = ParameterBuilder.constructRequestParameters();

        SSLCommerz sslCommerz = new SSLCommerz("docto62c031c5a653e", "docto62c031c5a653e@ssl", true);
        String url = sslCommerz.initiateTransaction(transactionMap, false);
        System.out.println("The url: " + url);
        System.out.println("after previous url");
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(url);
        return redirectView;
    }


    @RequestMapping("/pay-success")
    @ResponseBody
    public String paymentSuccessful(HttpServletRequest request) {

        String customerID = "";
        customerID = request.getAttribute("tran_id").toString();
//        model.addAttribute("customerID", customerID);
        System.out.println("This is successful page.. ");
        return customerID+"abc";
    }
}

package com.example.sslcommerzsandboxpaymentapplication.Controller;

import com.example.sslcommerzsandboxpaymentapplication.commerz.SSLCommerz;
import com.example.sslcommerzsandboxpaymentapplication.commerz.TransactionResponseValidator;
import com.example.sslcommerzsandboxpaymentapplication.commerz.Utility.ParameterBuilder;
import com.example.sslcommerzsandboxpaymentapplication.entity.Appointment;
import jdk.jfr.ContentType;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Random;

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
        String payment = appointment.getPaid();
        String transactionID = "TXID"+Math.random()*10000;
        String time = appointment.getAppointTime();
        String patientID = appointment.getPatient_ID();
        String doctorID = appointment.getDoctor_ID();
        System.out.println(payment+" "+" "+time+" "+doctorID+" "+patientID);
        Map<String, String> transactionMap = ParameterBuilder.constructRequestParam(baseurl, payment, transactionID, patientID, doctorID);
//        Map<String, String> transactionMap = ParameterBuilder.constructRequestParameters();

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
    public String paymentSuccessful(@RequestParam Map<String, String> requestMap, Model model) {
        System.out.println(requestMap.get("cus_name"));
        String requestMapStr = "";
        for (String key: requestMap.keySet()) {
            System.out.println("key = " + key + " value = " + requestMap.get(key));
            String str = key =  key + " value = " + requestMap.get(key) + " \n";
            requestMapStr = requestMapStr + str;
        }
        System.out.println("This is successful page.. "+ requestMapStr);
        return "The map came into comtroller is -> " + requestMapStr + " -------------- " + requestMap.get("cus_name");
    }


}


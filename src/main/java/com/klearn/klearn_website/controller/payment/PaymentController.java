package com.klearn.klearn_website.controller.payment;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.klearn.klearn_website.service.payment.VNPayService;

import java.util.HashMap;
import java.util.Map;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@RestController
@RequestMapping("/api/payment")
public class PaymentController {
    @Autowired
    private VNPayService vnPayService;

    @PostMapping("/submitOrder")
    public ResponseEntity<Map<String, String>> submitOrder(@RequestParam("amount") int amount,
            @RequestParam("orderInfo") String orderInfo,
            HttpServletRequest request) {

        String returnUrl = "http://localhost:5173";
        String vnpayUrl = vnPayService.createOrder(amount, orderInfo, returnUrl);

        Map<String, String> response = new HashMap<>();
        response.put("redirectUrl", vnpayUrl);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/vnpay-payment")
    public ResponseEntity<Map<String, String>> handlePaymentReturn(HttpServletRequest request) {
        int paymentStatus = vnPayService.orderReturn(request);

        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");

        
        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        // LocalDateTime vnPayDate = LocalDateTime.parse( request.getParameter("vnp_PayDate"), formatter);


        Map<String, String> response = new HashMap<>();
        response.put("orderInfo", orderInfo);
        response.put("totalPrice", totalPrice);
        response.put("paymentTime", paymentTime);
        response.put("transactionId", transactionId);
        response.put("paymentStatus", paymentStatus == 1 ? "success" : "failure");

        return ResponseEntity.ok(response);
    }
}

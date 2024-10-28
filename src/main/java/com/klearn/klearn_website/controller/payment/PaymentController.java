package com.klearn.klearn_website.controller.payment;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.klearn.klearn_website.dto.dtoin.PaymentDTOIn;
import com.klearn.klearn_website.model.PaymentHistory;
import com.klearn.klearn_website.model.User;
import com.klearn.klearn_website.service.payment.PaymentHistoryService;
import com.klearn.klearn_website.service.payment.VNPayService;
import com.klearn.klearn_website.service.user.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@RestController
@RequestMapping("/api/payment")
public class PaymentController {
    
    private final UserService userService;
    private final VNPayService vnPayService;
    private final PaymentHistoryService paymentHistoryService;

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

    @PostMapping("/vnpay-payment")
    public ResponseEntity<Map<String, String>> handlePaymentReturn(@RequestBody Map<String, String> requestBody) {

        User user = userService.getAuthenticatedUser();

        String paymentStatus = vnPayService.orderReturn(requestBody) == 1 ? "success" : "failure";

        String orderInfo = requestBody.get("vnp_OrderInfo");
        String paymentTime = requestBody.get("vnp_PayDate");
        String transactionId = requestBody.get("vnp_TransactionNo");
        String totalPrice = requestBody.get("vnp_Amount");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime vnPayDate = LocalDateTime.parse(paymentTime, formatter);

        PaymentDTOIn paymentDTOIn = new PaymentDTOIn(
            user.getId(),
            Integer.parseInt(orderInfo),
            new BigDecimal(totalPrice),
            paymentStatus,
            vnPayDate
        );

        paymentHistoryService.insertPaymentHistory(paymentDTOIn);

        Map<String, String> response = new HashMap<>();
        response.put("orderInfo", orderInfo);
        response.put("totalPrice", totalPrice);
        response.put("paymentTime", paymentTime);
        response.put("transactionId", transactionId);
        response.put("paymentStatus", paymentStatus);

        return ResponseEntity.ok(response);
    }

    @GetMapping("history")
    public List<PaymentHistory> getPaymentHistory() {
        User user = userService.getAuthenticatedUser();

        return paymentHistoryService.getPaymentHistory(user.getId());
    }
}

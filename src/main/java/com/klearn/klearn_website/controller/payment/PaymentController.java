package com.klearn.klearn_website.controller.payment;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.klearn.klearn_website.dto.dtoin.PaymentDTOIn;
import com.klearn.klearn_website.dto.dtoout.MonthlySumTransactionPriceDTOOut;
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

    @GetMapping("/vnpay-payment")
    public ResponseEntity<Map<String, String>> handlePaymentReturn(HttpServletRequest request) {

        User user = userService.getAuthenticatedUser();

        String paymentStatus = vnPayService.orderReturn(request) == 1 ? "success" : "failure";

        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime vnPayDate = LocalDateTime.parse(request.getParameter("vnp_PayDate"), formatter);

        PaymentDTOIn paymentDTOIn = new PaymentDTOIn(
                user.getId(),
                Integer.parseInt(orderInfo),
                new BigDecimal(totalPrice),
                paymentStatus,
                vnPayDate);

        paymentHistoryService.insertPaymentHistory(paymentDTOIn);

        Map<String, String> response = new HashMap<>();
        response.put("orderInfo", orderInfo);
        response.put("totalPrice", totalPrice);
        response.put("paymentTime", paymentTime);
        response.put("transactionId", transactionId);
        response.put("paymentStatus", paymentStatus);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/history")
    public List<PaymentHistory> getPaymentHistory() {
        User user = userService.getAuthenticatedUser();

        return paymentHistoryService.getPaymentHistory(user.getId());
    }

    @GetMapping("/history/{id}")
    public ResponseEntity<?> getPaymentHistoryByUserId(@PathVariable Integer id) {
        User user = userService.getAuthenticatedUser();

        // Check if the user is authorized (if needed, adjust role check as per your
        // requirements)
        if (user.getRole() != 1) { // Assuming role 1 is for admin
            return new ResponseEntity<>("Unauthorized: You do not have permission to access this resource.",
                    HttpStatus.FORBIDDEN);
        }

        List<PaymentHistory> listPaymentHistory = paymentHistoryService.getPaymentHistory(id);

        return ResponseEntity.ok(listPaymentHistory);
    }

    @GetMapping("/all_transactions")
    public ResponseEntity<?> getAllActivePaymentHistory() {
        // Retrieve the authenticated user
        User user = userService.getAuthenticatedUser();

        // Check if the user is authorized
        if (user.getRole() != 1) {
            return new ResponseEntity<>("Unauthorized: You do not have permission to access this resource.",
                    HttpStatus.FORBIDDEN);
        }

        // Fetch the payment history
        List<PaymentHistory> paymentHistories = paymentHistoryService.getAllActivePaymentHistory();

        // Return the payment history in a ResponseEntity
        return ResponseEntity.ok(paymentHistories);
    }

    @GetMapping("/sum_successful_transactions")
    public ResponseEntity<?> getSumOfSuccessfulTransactions() {
        // Retrieve the authenticated user
        User user = userService.getAuthenticatedUser();

        // Check if the user is authorized (if needed, adjust role check as per your
        // requirements)
        if (user.getRole() != 1) { // Assuming role 1 is for admin
            return new ResponseEntity<>("Unauthorized: You do not have permission to access this resource.",
                    HttpStatus.FORBIDDEN);
        }

        // Calculate the sum of successful transactions
        BigDecimal sum = paymentHistoryService.getSumOfSuccessfulTransactions();

        return ResponseEntity.ok(sum);
    }

    @GetMapping("/monthly_successful_transaction_sums")
    public ResponseEntity<?> getMonthlySuccessfulTransactionSums() {
        User user = userService.getAuthenticatedUser();

        // Check if the user is authorized (if needed, adjust role check as per your
        // requirements)
        if (user.getRole() != 1) { // Assuming role 1 is for admin
            return new ResponseEntity<>("Unauthorized: You do not have permission to access this resource.",
                    HttpStatus.FORBIDDEN);
        }

        // Fetch monthly sums
        List<MonthlySumTransactionPriceDTOOut> monthlySums = paymentHistoryService
                .getMonthlySuccessfulTransactionSums();

        // Return the response
        return ResponseEntity.ok(monthlySums);
    }

}

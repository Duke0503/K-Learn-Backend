package com.klearn.klearn_website.service.payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.klearn.klearn_website.dto.dtoin.MyCourseDTOIn;
import com.klearn.klearn_website.dto.dtoin.PaymentDTOIn;
import com.klearn.klearn_website.dto.dtoout.MonthlySumTransactionPriceDTOOut;
import com.klearn.klearn_website.mapper.PaymentHistoryMapper;
import com.klearn.klearn_website.model.Course;
import com.klearn.klearn_website.model.PaymentHistory;
import com.klearn.klearn_website.model.User;
import com.klearn.klearn_website.service.course.CourseService;
import com.klearn.klearn_website.service.course.MyCourseService;
import com.klearn.klearn_website.service.user.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PaymentHistoryService {
    private final PaymentHistoryMapper paymentHistoryMapper;
    private final UserService userService;
    private final CourseService courseService;
    private final MyCourseService myCourseService;

    /**
     * 
     */
    public List<PaymentHistory> getAllActivePaymentHistory() {
        return paymentHistoryMapper.getAllActivePaymentHistory();
    }

    public void insertPaymentHistory(PaymentDTOIn paymentDTOIn) {
        // Check if user exists
        if (!checkExistedPayment(
                paymentDTOIn.getDate_transaction(),
                paymentDTOIn.getTransaction_price(),
                paymentDTOIn.getTransaction_status(),
                paymentDTOIn.getCourse_id(),
                paymentDTOIn.getUser_id())) {
            User user = userService.getUserById(paymentDTOIn.getUser_id())
                    .orElseThrow(() -> new RuntimeException("User not found with ID: " + paymentDTOIn.getUser_id()));
            // Check if course exists
            Course course = courseService.getCourseById(paymentDTOIn.getCourse_id())
                    .orElseThrow(
                            () -> new RuntimeException("Course not found with ID: " + paymentDTOIn.getCourse_id()));

            PaymentHistory paymentHistory = new PaymentHistory();
            paymentHistory.setDate_transaction(paymentDTOIn.getDate_transaction());
            paymentHistory.setTransaction_price(paymentDTOIn.getTransaction_price());
            paymentHistory.setTransaction_status(paymentDTOIn.getTransaction_status());
            paymentHistory.setLast_modified(paymentDTOIn.getDate_transaction());
            paymentHistory.setIs_deleted(false);
            paymentHistory.setCourse(course);
            paymentHistory.setUser(user);

            paymentHistoryMapper.insertPaymentHistory(paymentHistory);

            if (paymentDTOIn.getTransaction_status() == "success") {

                myCourseService.insertMyCourse(new MyCourseDTOIn(
                        paymentDTOIn.getUser_id(),
                        paymentDTOIn.getCourse_id(),
                        paymentDTOIn.getTransaction_status()));
            }
        }

    }

    public List<PaymentHistory> getPaymentHistory(Integer userId) {
        return paymentHistoryMapper.findByUserId(userId);
    }

    boolean checkExistedPayment(
            LocalDateTime dateTransaction,
            BigDecimal transactionPrice,
            String transactionStatus,
            Integer courseId,
            Integer userId) {
        return paymentHistoryMapper.existsByTransactionDetails(
                dateTransaction,
                transactionPrice,
                transactionStatus,
                courseId,
                userId);
    }

    public BigDecimal getSumOfSuccessfulTransactions() {
        return paymentHistoryMapper.getTotalSuccessfulTransactionPrice();
    }
    
    public List<MonthlySumTransactionPriceDTOOut> getMonthlySuccessfulTransactionSums() {
        return paymentHistoryMapper.getMonthlySuccessfulTransactionSums();
    }
}

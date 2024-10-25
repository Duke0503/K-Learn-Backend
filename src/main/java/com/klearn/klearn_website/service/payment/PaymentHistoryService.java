package com.klearn.klearn_website.service.payment;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.klearn.klearn_website.dto.dtoin.PaymentDTOIn;
import com.klearn.klearn_website.mapper.PaymentHistoryMapper;
import com.klearn.klearn_website.model.Course;
import com.klearn.klearn_website.model.PaymentHistory;
import com.klearn.klearn_website.model.User;
import com.klearn.klearn_website.service.course.CourseService;
import com.klearn.klearn_website.service.user.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PaymentHistoryService {
    private final PaymentHistoryMapper paymentHistoryMapper;
    private final UserService userService;
    private final CourseService courseService;

    /**
     * 
     */
    void insertPaymentHistory(PaymentDTOIn paymentDTOIn) {
        // Check if user exists
        User user = userService.getUserById(paymentDTOIn.getUser_id())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + paymentDTOIn.getUser_id()));
        // Check if course exists
        Course course = courseService.getCourseById(paymentDTOIn.getCourse_id())
                .orElseThrow(() -> new RuntimeException("Course not found with ID: " + paymentDTOIn.getCourse_id()));

        PaymentHistory paymentHistory = new PaymentHistory();
        paymentHistory.setDate_transaction(paymentDTOIn.getDate_transaction());
        paymentHistory.setTransaction_price(paymentDTOIn.getTransaction_price());
        paymentHistory.setTransaction_status(paymentHistory.getTransaction_status());
        paymentHistory.setLast_modified(LocalDateTime.now());
        paymentHistory.setIs_deleted(false);
        paymentHistory.setCourse(course);
        paymentHistory.setUser(user);

        paymentHistoryMapper.insertPaymentHistory(paymentHistory);
    }
}

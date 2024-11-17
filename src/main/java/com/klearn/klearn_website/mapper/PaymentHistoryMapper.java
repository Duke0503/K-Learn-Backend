package com.klearn.klearn_website.mapper;

import org.apache.ibatis.annotations.*;

import com.klearn.klearn_website.model.PaymentHistory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Mapper
public interface PaymentHistoryMapper {

    // Insert a new payment
    @Insert("INSERT INTO payment_history (date_transaction, transaction_price, transaction_status, last_modified, is_deleted, user_id, course_id)"
            + "VALUES (#{date_transaction}, #{transaction_price}, #{transaction_status}, #{last_modified}, #{is_deleted}, #{user.id}, #{course.id})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertPaymentHistory(PaymentHistory paymentHistory);

    // Find by Id
    @Select("SELECT ph.*, c.* FROM payment_history ph JOIN courses c ON ph.course_id = c.id WHERE ph.id = #{id} AND ph.is_deleted = false")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "date_transaction", column = "date_transaction"),
            @Result(property = "transaction_price", column = "transaction_price"),
            @Result(property = "transaction_status", column = "transaction_status"),
            @Result(property = "last_modified", column = "last_modified"),
            @Result(property = "is_deleted", column = "is_deleted"),

            @Result(property = "user.id", column = "user_id"),

            @Result(property = "course.id", column = "course_id"),
            @Result(property = "course.course_name", column = "course_name"),
            @Result(property = "course.course_level", column = "course_level"),
            @Result(property = "course.course_description", column = "course_description"),
            @Result(property = "course.course_image", column = "course_image"),
            @Result(property = "course.course_price", column = "course_price"),
            @Result(property = "course.created_at", column = "created_at"),
            @Result(property = "course.last_modified", column = "last_modified"),
            @Result(property = "course.is_deleted", column = "is_deleted")
    })
    Optional<PaymentHistory> findById(Integer id);

    // Get all transactions by userId with course data
    @Select("SELECT ph.*, c.* FROM payment_history ph " +
            "JOIN courses c ON ph.course_id = c.id " +
            "WHERE ph.user_id = #{userId} AND ph.is_deleted = false")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "date_transaction", column = "date_transaction"),
            @Result(property = "transaction_price", column = "transaction_price"),
            @Result(property = "transaction_status", column = "transaction_status"),
            @Result(property = "last_modified", column = "last_modified"),
            @Result(property = "is_deleted", column = "is_deleted"),

            // Nested mapping for user
            @Result(property = "user.id", column = "user_id"),

            // Nested mapping for course
            @Result(property = "course.id", column = "course_id"),
            @Result(property = "course.course_name", column = "course_name"),
            @Result(property = "course.course_level", column = "course_level"),
            @Result(property = "course.course_description", column = "course_description"),
            @Result(property = "course.course_image", column = "course_image"),
            @Result(property = "course.course_price", column = "course_price"),
            @Result(property = "course.created_at", column = "created_at"),
            @Result(property = "course.last_modified", column = "last_modified"),
            @Result(property = "course.is_deleted", column = "is_deleted")
    })
    List<PaymentHistory> findByUserId(Integer userId);

    // Soft Delete
    @Update("UPDATE payment_history SET is_deleted = true WHERE id = #{id}")
    void deleteById(Integer id);

    // Check if a transaction exists based on unique properties
    @Select("SELECT COUNT(1) FROM payment_history " +
            "WHERE date_transaction = #{dateTransaction} " +
            "AND transaction_price = #{transactionPrice} " +
            "AND transaction_status = #{transactionStatus} " +
            "AND course_id = #{courseId} " +
            "AND user_id = #{userId} " +
            "AND is_deleted = false")
    boolean existsByTransactionDetails(
            @Param("dateTransaction") LocalDateTime dateTransaction,
            @Param("transactionPrice") BigDecimal transactionPrice,
            @Param("transactionStatus") String transactionStatus,
            @Param("courseId") Integer courseId,
            @Param("userId") Integer userId);

    @Select("SELECT ph.*, c.*, u.* FROM payment_history " +
            "JOIN courses c ON ph.course_id = c.id " +
            "JOIN users u ON ph.user_id = u.id " +
            "WHERE ph.is_deleted = false")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "date_transaction", column = "date_transaction"),
            @Result(property = "transaction_price", column = "transaction_price"),
            @Result(property = "transaction_status", column = "transaction_status"),
            @Result(property = "last_modified", column = "last_modified"),
            @Result(property = "is_deleted", column = "is_deleted"),

            // Nested mapping for course
            @Result(property = "course.id", column = "course_id"),
            @Result(property = "course.course_name", column = "course_name"),
            @Result(property = "course.course_level", column = "course_level"),
            @Result(property = "course.course_description", column = "course_description"),
            @Result(property = "course.course_image", column = "course_image"),
            @Result(property = "course.course_price", column = "course_price"),
            @Result(property = "course.created_at", column = "created_at"),
            @Result(property = "course.last_modified", column = "last_modified"),
            @Result(property = "course.is_deleted", column = "is_deleted"),

            // Nested mapping for user
            @Result(property = "user.id", column = "user_id"),
            @Result(property = "user.fullname", column = "user_fullname")
    })
    List<PaymentHistory> getAllActivePaymentHistory();

    @Select("SELECT SUM(transaction_price) " +
            "FROM payment_history " +
            "WHERE transaction_status = 'success' AND is_deleted = false")
    BigDecimal getTotalSuccessfulTransactionPrice();

}

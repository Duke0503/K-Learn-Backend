package com.klearn.klearn_website.dto.dtoin;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.PastOrPresent;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTOIn {

    @NotNull(message = "User ID cannot be null")
    @PositiveOrZero(message = "User ID must be a positive value")
    private Integer user_id;

    @NotNull(message = "Course ID cannot be null")
    @PositiveOrZero(message = "Course ID must be a positive value")
    private Integer course_id;

    @NotNull(message = "Transaction price cannot be null")
    @PositiveOrZero(message = "Transaction price must be zero or a positive value")
    private BigDecimal transaction_price;

    @NotNull(message = "Transaction status cannot be null")
    private String transaction_status;

    @PastOrPresent(message = "Transaction date must be in the past or present")
    private LocalDateTime date_transaction;
}

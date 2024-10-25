package com.klearn.klearn_website.dto.dtoin;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTOIn {
    private Integer user_id;

    private Integer course_id;

    private BigDecimal transaction_price;

    private String transaction_status;

    private LocalDateTime date_transaction;

}

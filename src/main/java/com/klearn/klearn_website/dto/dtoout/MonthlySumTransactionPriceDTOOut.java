package com.klearn.klearn_website.dto.dtoout;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class MonthlySumTransactionPriceDTOOut {

    private int year;
    private int month;
    private BigDecimal totalAmount;
}

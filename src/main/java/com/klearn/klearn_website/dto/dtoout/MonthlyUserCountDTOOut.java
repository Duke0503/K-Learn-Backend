package com.klearn.klearn_website.dto.dtoout;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class MonthlyUserCountDTOOut {
    private int year;
    private int month;
    private long userCount;
}

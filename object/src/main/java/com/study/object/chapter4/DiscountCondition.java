package com.study.object.chapter4;

import com.study.object.chapter2.Money;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Getter @Setter
public class DiscountCondition {
    private DiscountConditionType type;
    private int sequence;
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;


    //시간 할인 조건
    public boolean isDiscountable(DayOfWeek dayOfWeek, LocalTime time) throws IllegalAccessException {
        isTypeMatched(DiscountConditionType.PERIOD);
        return this.dayOfWeek.equals(dayOfWeek) &&
                this.startTime.compareTo(time) <= 0 &&
                this.endTime.compareTo(time) >= 0;
    }

    //순서 할인 조건
    public boolean isDiscountable(int sequence) throws IllegalAccessException {
        isTypeMatched(DiscountConditionType.SEQUENCE);
        return this.sequence == sequence;
    }

    private void isTypeMatched(DiscountConditionType condition) throws IllegalAccessException {
        if (type !=condition) {
            throw new IllegalAccessException();
        }
    }
}

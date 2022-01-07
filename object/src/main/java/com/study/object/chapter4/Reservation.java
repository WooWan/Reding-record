package com.study.object.chapter4;

import com.study.object.chapter2.Money;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Reservation {

    private Customer customer;
    private Screening screening;
    private Money fee;
    private int audienceCount;

    public Reservation(Customer customer, Screening screening, Money fee, int audienceCount) {
    }
}

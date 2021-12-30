package com.study.object.chapter2;

import lombok.Getter;

@Getter
public class Reservation {

    private Customer customer;
    private Screening screening;
    private Money money;
    private int audienceCount;


    public Reservation(Customer customer, Screening screening, Money money, int audienceCount) {
        this.customer = customer;
        this.screening = screening;
        this.money = money;
        this.audienceCount = audienceCount;
    }

    public Reservation reserve(Customer customer, int audienceCount) {
        return new Reservation(customer, screening , screening.calculateFee(audienceCount), audienceCount);
    }
}

package com.study.object.chapter2.policy;

import com.study.object.chapter2.Money;
import com.study.object.chapter2.Screening;

public class NoneDiscountPolicy implements DiscountPolicy {

    @Override
    public Money calculateDiscountAmount(Screening screening) {
        return Money.ZERO;
    }
}

package com.study.object.chapter3;

import com.study.object.chapter2.Money;
import com.study.object.chapter2.Screening;
import com.study.object.chapter2.policy.DiscountPolicy;

public class Movie {
    private Money fee;
    private DiscountPolicy discountPolicy;

    public Money calculateMovieFee(Screening screening) {
        return fee.minus(discountPolicy.calculateDiscountAmount(screening));
    }
}

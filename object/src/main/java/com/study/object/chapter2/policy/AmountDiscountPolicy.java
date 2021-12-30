package com.study.object.chapter2.policy;

import com.study.object.chapter2.DiscountCondition;
import com.study.object.chapter2.Money;
import com.study.object.chapter2.Screening;

public class AmountDiscountPolicy extends DefaultDiscountPolicy {

    private Money discountAmount;

    public AmountDiscountPolicy(Money discountAmount, DiscountCondition... conditions) {
        super(conditions);
        this.discountAmount = discountAmount;
    }

    @Override
    protected Money getDiscountAmount(Screening screening) {
        return discountAmount;
    }

    @Override
    public Money calculateDiscountAmount(Screening screening) {
        return null;
    }
}

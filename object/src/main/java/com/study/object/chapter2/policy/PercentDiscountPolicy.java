package com.study.object.chapter2.policy;

import com.study.object.chapter2.policyCodition.DiscountCondition;
import com.study.object.chapter2.Money;
import com.study.object.chapter2.Screening;

public class PercentDiscountPolicy extends DefaultDiscountPolicy{

    private double percent;

    public PercentDiscountPolicy(double percent, DiscountCondition... conditions) {
        super(conditions);
        this.percent = percent;
    }

    @Override
    protected Money getDiscountAmount(Screening screening) {
        return screening.getMovieFee().times(percent);
    }
}

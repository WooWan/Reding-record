package com.study.object.chapter2.policy;

import com.study.object.chapter2.Money;
import com.study.object.chapter2.Screening;

public interface DiscountPolicy {

    Money calculateDiscountAmount(Screening screening);
}

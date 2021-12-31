package com.study.object.chapter2.policyCodition;

import com.study.object.chapter2.Screening;

public interface DiscountCondition {
    boolean isSatisfiedBy(Screening screening);
}

package com.study.object.chapter2.policyCodition;

import com.study.object.chapter2.Screening;
import com.study.object.chapter2.policyCodition.DiscountCondition;

public class SequenceCondition implements DiscountCondition {

    private int sequence;

    public SequenceCondition(int sequence) {
        this.sequence = sequence;
    }

    @Override
    public boolean isSatisfiedBy(Screening screening) {
        return screening.isSequence(sequence);
    }
}

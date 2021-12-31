package com.study.object.chapter2;

import com.study.object.chapter2.policy.PercentDiscountPolicy;
import com.study.object.chapter2.policyCodition.DiscountCondition;
import com.study.object.chapter2.policyCodition.PeriodCondition;
import com.study.object.chapter2.policyCodition.SequenceCondition;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;

@SpringBootTest
class MovieTest {


    @Test
    public void create_movie() {
        DiscountCondition discountCondition = new DiscountCondition() {
            @Override
            public boolean isSatisfiedBy(Screening screening) {
                return false;
            }
        };
        new Movie("타이타닉",
                Duration.ofMinutes(180),
                Money.wons(11000),
                new PercentDiscountPolicy(0.1,
                        new PeriodCondition(DayOfWeek.TUESDAY, LocalTime.of(14, 0), LocalTime.of(16, 59)),
                        new SequenceCondition(2),
                        new PeriodCondition(DayOfWeek.THURSDAY, LocalTime.of(10, 0), LocalTime.of(13, 59))));

        new Movie("아바타",
                Duration.ofMinutes(180),
                Money.wons(11000),
                new PercentDiscountPolicy(1000,
                        new PeriodCondition(DayOfWeek.TUESDAY, LocalTime.of(14, 0), LocalTime.of(16, 59)),
                        new SequenceCondition(2),
                        new PeriodCondition(DayOfWeek.THURSDAY, LocalTime.of(10, 0), LocalTime.of(13, 59))));

    }
}
package com.study.object.chapter2;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

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
//        new Movie("아바타", Duration.ofMinutes(10000), Money.wons(10000), new AmountDiscountPolicy(Money.wons(800), (DiscountCondition condition) -> condition.isSatisfiedBy(new Screening(
//
//        ))));
    }
}
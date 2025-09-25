package com.mercatordigital.simplecheckoutsystem.offer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TwoForOne extends MultiBuy {

    public TwoForOne(@Value("${offer.two-for-one}") String productNames) {
        super(productNames);
    }

    @Override
    protected int getMultiple() {
        return 2;
    }

    @Override
    protected int getMultiplier() {
        return 1;
    }

}

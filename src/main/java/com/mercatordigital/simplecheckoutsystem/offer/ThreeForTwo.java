package com.mercatordigital.simplecheckoutsystem.offer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ThreeForTwo extends MultiBuy {

    public ThreeForTwo(@Value("${offer.three-for-two}") String productNames) {
        super(productNames);
    }

    @Override
    protected int getMultiple() {
        return 3;
    }

    @Override
    protected int getMultiplier() {
        return 2;
    }

}

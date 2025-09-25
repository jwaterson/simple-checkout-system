package com.mercatordigital.simplecheckoutsystem.model;

import java.math.BigDecimal;

public enum Product {
    APPLE(BigDecimal.valueOf(.6)),
    ORANGE(BigDecimal.valueOf(.25));


    private final BigDecimal price;

    Product(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }

}

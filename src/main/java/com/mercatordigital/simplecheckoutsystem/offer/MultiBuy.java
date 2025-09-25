package com.mercatordigital.simplecheckoutsystem.offer;

import com.mercatordigital.simplecheckoutsystem.model.Product;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public abstract class MultiBuy implements Offer {

    protected final List<Product> applicableProducts;

    protected abstract int getMultiple();

    protected abstract int getMultiplier();

    protected MultiBuy(String productNames) {
        if (productNames == null || productNames.isBlank()) {
            throw new IllegalArgumentException("The product names cannot be null or blank");
        }
        this.applicableProducts = Arrays.stream(productNames.split(","))
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .map(String::toUpperCase)
                .map(Product::valueOf)
                .toList();
    }

    @Override
    public Predicate<Product> getPredicate() {
        return applicableProducts::contains;
    }

    @Override
    public BigDecimal apply(Product product, long quantity) {
        if (product == null) {
            throw new IllegalArgumentException("The product cannot be null");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("The quantity must be greater than zero");
        }
        long chargingQuantity = (quantity / getMultiple()) * getMultiplier() + quantity % getMultiple();
        return product.getPrice().multiply(BigDecimal.valueOf(chargingQuantity));
    }

}

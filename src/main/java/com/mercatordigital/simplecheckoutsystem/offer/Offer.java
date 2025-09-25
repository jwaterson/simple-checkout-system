package com.mercatordigital.simplecheckoutsystem.offer;

import com.mercatordigital.simplecheckoutsystem.model.Product;

import java.math.BigDecimal;
import java.util.function.Predicate;

public interface Offer {

    Predicate<Product> getPredicate();

    BigDecimal apply(Product product, long quantity);

}

package com.mercatordigital.simplecheckoutsystem.service;

import com.mercatordigital.simplecheckoutsystem.model.Product;
import com.mercatordigital.simplecheckoutsystem.model.dto.CartDTO;
import com.mercatordigital.simplecheckoutsystem.offer.Offer;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TillService {

    private final List<Offer> offers;

    public TillService(List<Offer> offers) {
        this.offers = offers;
    }

    public BigDecimal calculateTotal(CartDTO cart) {
        if (cart == null || cart.products() == null || cart.products().isEmpty()) {
            throw new IllegalArgumentException("The cart cannot be null or empty");
        }
        Map<Product, Long> productCountMap = cart.products()
                .stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return productCountMap.entrySet()
                .stream()
                .map(entry -> getPrice(entry.getKey(), entry.getValue()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal getPrice(Product product, long quantity) {
        return offers.stream()
                .filter(offer -> offer.getPredicate().test(product))
                .map(offer -> offer.apply(product, quantity))
                .min(BigDecimal::compareTo)
                .orElse(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
    }
}

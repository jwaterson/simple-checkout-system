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
        Map<Product, Long> productCountMap = cart.products()
                .stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return productCountMap.entrySet()
                .stream()
                .map(this::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

    }

    private BigDecimal getPrice(Map.Entry<Product, Long> entry) {
        return offers.stream()
                .filter(offer -> offer.getPredicate().test(entry.getKey()))
                .map(offer -> offer.apply(entry.getKey(), entry.getValue()))
                .min(BigDecimal::compareTo)
                .orElse(entry.getKey().getPrice()
                        .multiply(BigDecimal.valueOf(entry.getValue())));
    }
}

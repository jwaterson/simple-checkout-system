package com.mercatordigital.simplecheckoutsystem.service;

import com.mercatordigital.simplecheckoutsystem.model.Product;
import com.mercatordigital.simplecheckoutsystem.model.dto.CartDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TillService {

    public BigDecimal calculateTotal(CartDTO cart) {
        return cart.products()
                .stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}

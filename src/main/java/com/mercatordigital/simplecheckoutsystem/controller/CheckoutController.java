package com.mercatordigital.simplecheckoutsystem.controller;

import com.mercatordigital.simplecheckoutsystem.model.CartDTO;
import com.mercatordigital.simplecheckoutsystem.model.PriceResponseDTO;
import com.mercatordigital.simplecheckoutsystem.service.TillService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

    private final TillService tillService;

    public CheckoutController(TillService tillService) {
        this.tillService = tillService;
    }

    @PostMapping
    public PriceResponseDTO checkout(@Validated @RequestBody CartDTO cart) {
        final BigDecimal total = tillService.calculateTotal(cart);
        return new PriceResponseDTO(total.toPlainString());
    }
}

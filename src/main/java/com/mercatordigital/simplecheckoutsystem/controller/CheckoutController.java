package com.mercatordigital.simplecheckoutsystem.controller;

import com.mercatordigital.simplecheckoutsystem.model.dto.CartDTO;
import com.mercatordigital.simplecheckoutsystem.model.dto.PriceResponseDTO;
import com.mercatordigital.simplecheckoutsystem.service.TillService;
import com.mercatordigital.simplecheckoutsystem.util.PriceFormatter;
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
        final String priceString = PriceFormatter.convert(total);
        return new PriceResponseDTO(priceString);
    }
}

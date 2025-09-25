package com.mercatordigital.simplecheckoutsystem.model.dto;

import com.mercatordigital.simplecheckoutsystem.model.Product;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CartDTOTest {

    @ParameterizedTest
    @NullAndEmptySource
    void shouldThrowIllegalArgumentException_WhenNullOrEmptyProductListProvided(List<Product> products) {
        assertThrows(IllegalArgumentException.class, () -> new CartDTO(products));
    }
}
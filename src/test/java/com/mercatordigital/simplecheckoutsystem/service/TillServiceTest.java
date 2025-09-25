package com.mercatordigital.simplecheckoutsystem.service;

import com.mercatordigital.simplecheckoutsystem.model.Product;
import com.mercatordigital.simplecheckoutsystem.model.dto.CartDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.mercatordigital.simplecheckoutsystem.model.Product.APPLE;
import static com.mercatordigital.simplecheckoutsystem.model.Product.ORANGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class TillServiceTest {

    private final TillService tillService = new TillService();

    @Test
    void calculateTotal_ValidCart_ReturnsCorrectTotal() {
        // Arrange
        var cart = new CartDTO(List.of(APPLE, ORANGE, APPLE));

        // Act
        var total = tillService.calculateTotal(cart);

        // Assert
        var expectedTotal = APPLE.getPrice().add(ORANGE.getPrice()).add(APPLE.getPrice());
        assertEquals(expectedTotal, total);
    }

    @ParameterizedTest
    @NullAndEmptySource
    void calculateTotal_EmptyCart_ThrowsIllegalArgumentException(List<Product> products) {
        assertThrows(IllegalArgumentException.class, () -> new CartDTO(products));
    }

}
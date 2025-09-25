package com.mercatordigital.simplecheckoutsystem.service;

import com.mercatordigital.simplecheckoutsystem.model.Product;
import com.mercatordigital.simplecheckoutsystem.model.dto.CartDTO;
import com.mercatordigital.simplecheckoutsystem.offer.Offer;
import com.mercatordigital.simplecheckoutsystem.offer.ThreeForTwo;
import com.mercatordigital.simplecheckoutsystem.offer.TwoForOne;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.mercatordigital.simplecheckoutsystem.model.Product.APPLE;
import static com.mercatordigital.simplecheckoutsystem.model.Product.ORANGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TillServiceTest {

    @Test
    void shouldThrowIllegalArgumentException_WhenNullProvided() {
        assertThrows(IllegalArgumentException.class, () -> {
            var tillService = new TillService(null);
            tillService.calculateTotal(null);
        });
    }

    @ParameterizedTest
    @MethodSource("getValidInputs")
    void shouldCalculateTotalCorrectly_WhenValidCartProvided(List<Product> products, BigDecimal expectedTotal) {
        // Arrange
        var cart = new CartDTO(products);
        var tillService = new TillService(List.of());
        // Act
        var total = tillService.calculateTotal(cart);

        // Assert
        assertEquals(expectedTotal, total);
    }

    @SuppressWarnings("unused")
    private static Stream<Arguments> getValidInputs() {
        return Stream.of(
                Arguments.of(List.of(APPLE), APPLE.getPrice()),
                Arguments.of(List.of(ORANGE), ORANGE.getPrice()),
                Arguments.of(IntStream.range(0, 10_000_000)
                                .mapToObj(i -> i % 2 == 0 ? APPLE : ORANGE)
                                .toList(),
                        APPLE.getPrice().multiply(BigDecimal.valueOf(5_000_000))
                                .add(ORANGE.getPrice().multiply(BigDecimal.valueOf(5_000_000))))
        );
    }

    @ParameterizedTest
    @MethodSource("getValidMultiBuyOfferInputs")
    void shouldCalculateTotalCorrectly_WhenValidMultiBuyOfferProvided(List<Product> products, BigDecimal expectedTotal) {
        // Arrange
        List<Offer> offers = List.of(
                new ThreeForTwo("ORANGE,APPLE"),
                new TwoForOne("APPLE"));

        var tillService = new TillService(offers);
        var cart = new CartDTO(products);

        // Act
        var total = tillService.calculateTotal(cart);

        // Assert
        assertEquals(expectedTotal, total);

    }

    @SuppressWarnings("unused")
    private static Stream<Arguments> getValidMultiBuyOfferInputs() {
        return Stream.of(
                Arguments.of(List.of(ORANGE, ORANGE, ORANGE), ORANGE.getPrice().multiply(BigDecimal.valueOf(2))),
                Arguments.of(List.of(ORANGE, ORANGE, ORANGE, ORANGE), ORANGE.getPrice().multiply(BigDecimal.valueOf(3))),
                Arguments.of(List.of(APPLE, APPLE), APPLE.getPrice()),
                Arguments.of(List.of(APPLE, APPLE,  APPLE, APPLE, APPLE), APPLE.getPrice().multiply(BigDecimal.valueOf(3)))
        );
    }

}
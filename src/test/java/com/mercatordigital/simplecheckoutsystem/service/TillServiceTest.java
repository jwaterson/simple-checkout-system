package com.mercatordigital.simplecheckoutsystem.service;

import com.mercatordigital.simplecheckoutsystem.model.Product;
import com.mercatordigital.simplecheckoutsystem.model.dto.CartDTO;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.mercatordigital.simplecheckoutsystem.model.Product.APPLE;
import static com.mercatordigital.simplecheckoutsystem.model.Product.ORANGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class TillServiceTest {

    private final TillService tillService = new TillService();

    @ParameterizedTest
    @NullAndEmptySource
    void shouldThrowIllegalArgumentException_WhenNullOrEmptyProductListProvided(List<Product> products) {
        assertThrows(IllegalArgumentException.class, () -> new CartDTO(products));
    }

    @ParameterizedTest
    @MethodSource("getValidInputs")
    void shouldCalculateTotalCorrectly_WhenValidCartProvided(List<Product> products, BigDecimal expectedTotal) {
        // Arrange
        var cart = new CartDTO(products);

        // Act
        var total = tillService.calculateTotal(cart);

        // Assert
        assertEquals(expectedTotal, total);
    }

    private static Stream<Arguments> getValidInputs() {
        return Stream.of(
                Arguments.of(List.of(APPLE, ORANGE), APPLE.getPrice().add(ORANGE.getPrice())),
                Arguments.of(List.of(APPLE, ORANGE, APPLE), APPLE.getPrice().add(ORANGE.getPrice()).add(APPLE.getPrice())),
                Arguments.of(IntStream.range(0, 10_000_000)
                                .mapToObj(i -> i % 2 == 0 ? APPLE : ORANGE)
                                .toList(),
                        APPLE.getPrice().multiply(BigDecimal.valueOf(5_000_000))
                                .add(ORANGE.getPrice().multiply(BigDecimal.valueOf(5_000_000))))
        );
    }

}
package com.mercatordigital.simplecheckoutsystem.util;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PriceFormatterTest {

    @Test
    void shouldFormatAmountAsUKCurrency_WhenValidInputProvided() {
        // Arrange
        var input = new BigDecimal("1234.56");

        // Act
        var result = PriceFormatter.convert(input);

        // Assert
        assertEquals("£1,234.56", result);
    }

    @Test
    void shouldRoundCorrectly_WhenRequired() {
        // Arrange
        var input = BigDecimal.valueOf(1234.56789);

        // Act
        var result = PriceFormatter.convert(input);

        // Assert
        assertEquals("£1,234.57", result);
    }

    @Test
    void shouldFormatZeroAsZero_WhenZeroProvided() {
        // Arrange
        var input = BigDecimal.ZERO;

        // Act
        var result = PriceFormatter.convert(input);

        // Assert
        assertEquals("£0.00", result);
    }

    @Test
    void shouldThrowException_WhenNullProvided() {
        assertThrows(IllegalArgumentException.class, () -> PriceFormatter.convert(null));
    }

    @Test
    void shouldHandleLargeAmounts_WhenLongValueProvided() {
        // Arrange
        var input = new BigDecimal(Integer.MAX_VALUE + 1L);

        // Act
        var result = PriceFormatter.convert(input);

        // Assert
        assertEquals("£2,147,483,648.00", result);
    }

    @Test
    void convert_shouldFormatNegativeAmountAsUKCurrency() {
        // Arrange
        var input = new BigDecimal("-1234.56");

        // Act
        var result = PriceFormatter.convert(input);

        // Assert
        assertEquals("-£1,234.56", result);
    }
}
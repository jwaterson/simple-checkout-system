package com.mercatordigital.simplecheckoutsystem.util;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PriceFormatterTest {

    @Test
    void shouldFormatAmountAsUKCurrency_WhenValidInputProvided() {
        var input = new BigDecimal("1234.56");
        var result = PriceFormatter.convert(input);

        assertEquals("£1,234.56", result);
    }

    @Test
    void shouldRoundCorrectly_WhenRequired() {
        var input = BigDecimal.valueOf(1234.56789);
        var result = PriceFormatter.convert(input);

        assertEquals("£1,234.57", result);
    }

    @Test
    void shouldFormatZeroAsZero_WhenZeroProvided() {
        var input = BigDecimal.ZERO;
        var result = PriceFormatter.convert(input);

        assertEquals("£0.00", result);
    }

    @Test
    void shouldThrowException_WhenNullProvided() {
        assertThrows(IllegalArgumentException.class, () -> PriceFormatter.convert(null));
    }

    @Test
    void shouldHandleLargeAmounts_WhenLongValueProvided() {
        var input = new BigDecimal(Integer.MAX_VALUE + 1L);
        var result = PriceFormatter.convert(input);

        assertEquals("£2,147,483,648.00", result);
    }

    @Test
    void convert_shouldFormatNegativeAmountAsUKCurrency() {
        var input = new BigDecimal("-1234.56");
        var result = PriceFormatter.convert(input);

        assertEquals("-£1,234.56", result);
    }
}
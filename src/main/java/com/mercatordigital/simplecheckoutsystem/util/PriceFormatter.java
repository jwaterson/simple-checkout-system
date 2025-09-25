package com.mercatordigital.simplecheckoutsystem.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class PriceFormatter {

    private PriceFormatter() {
    }

    public static String convert(BigDecimal total) {
        if (total == null) {
            throw new IllegalArgumentException("Total cannot be null");
        }

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.UK);
        return currencyFormat.format(total);
    }

}

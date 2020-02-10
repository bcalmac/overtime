package io.github.bcalmac.overtime.server.utils;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Locale;

/**
 * BigDecimal formatter that strips trailing zeros and never uses the scientific notation.
 * <p>
 * This matches the way we want to display hours in HTML.
 */
@Component
public class PlainBigDecimalFormatter implements Formatter<BigDecimal> {
    @Override
    public BigDecimal parse(String text, Locale locale) {
        return new BigDecimal(text).stripTrailingZeros();
    }

    @Override
    public String print(BigDecimal object, Locale locale) {
        return object.stripTrailingZeros().toPlainString();
    }
}

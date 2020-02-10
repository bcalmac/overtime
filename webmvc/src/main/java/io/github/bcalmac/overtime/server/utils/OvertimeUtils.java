package io.github.bcalmac.overtime.server.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Static utility methods for this module.
 */
public class OvertimeUtils {

    /**
     * Returns a BigDecimal representing an amount of hours with a scale matching the Postgres type decimal(4, 2).
     * <p>
     * This is required for DAO test assertions. For two BigDecimals to be equal they need to have the same scale.
     */

    public static BigDecimal hours(double value) {
        // RoundingMode.UP matches the Postgres rounding convention.
        return BigDecimal.valueOf(value).setScale(2, RoundingMode.UP);
    }
}

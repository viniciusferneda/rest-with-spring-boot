package com.vferneda.restwithspringboot.calculadora.helper;

import java.math.BigDecimal;

public final class NumberHelpers {

    public static BigDecimal convertToBigDecimal(String strNumber) {
        if (strNumber == null) {
            return BigDecimal.ZERO;
        }
        final String number = strNumber.replaceAll(",", ".");
        if (isNumeric(number)) {
            return new BigDecimal(strNumber);
        }
        return BigDecimal.ZERO;
    }

    public static boolean isNumeric(String strNumber) {
        if (strNumber == null) {
            return Boolean.FALSE;
        }
        final String number = strNumber.replaceAll(",", ".");
        return number.matches("[-+]?[0-9]*\\.?[0-9]+");
    }

}

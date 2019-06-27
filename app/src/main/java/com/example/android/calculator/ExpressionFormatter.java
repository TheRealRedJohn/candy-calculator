package com.example.android.calculator;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class ExpressionFormatter {

    public static String addFormat(double number) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        symbols.setDecimalSeparator('.');
        symbols.setGroupingSeparator(',');

        DecimalFormat format = new DecimalFormat();
        format.setMaximumFractionDigits(10);
        format.setDecimalFormatSymbols(symbols);
        format.setGroupingUsed(true);

        return format.format(number);
    }

    public static String addFormatNoDecimal(double number) {
        DecimalFormat format = new DecimalFormat("#.##########");
        return format.format(number);
    }

    public static String getTokenizedExpression(String expression) {
        expression = expression.replaceAll("√(\\d+)", "sqrt($1)");
        expression = expression.replaceAll("÷", "/");
        expression = expression.replaceAll("×", "\\*");
        expression = expression.replaceAll("−", "-");
        return expression;
    }

}

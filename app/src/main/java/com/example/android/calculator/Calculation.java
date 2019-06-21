package com.example.android.calculator;

import android.text.TextUtils;

import org.javia.arity.Symbols;
import org.javia.arity.SyntaxException;

public class Calculation {

    private final Symbols symbols;
    private CalculationOutput calculationOutput;
    private String currentExpression;
    private String currentResult;

    public void setCalculationOutputListener(CalculationOutput calculationOutput) {
        this.calculationOutput = calculationOutput;
        currentExpression = "";
        currentResult = "";
    }

    interface CalculationOutput {
        void onExpressionChanged(String expression, boolean successful);

        void onResultChanged(String result);
    }

    public Calculation() {
        this.symbols = new Symbols();
    }

    /**
     * Delete a single character from currentExpression unless empty.
     */
    public void deleteCharacter() {
        if (TextUtils.isEmpty(currentExpression)) {
            calculationOutput.onExpressionChanged("Expression Already Empty", false);
        } else {
            currentExpression = currentExpression.substring(0, currentExpression.length() - 1);
            calculationOutput.onExpressionChanged(currentExpression, true);
        }

    }

    /**
     * Delete entire expression unless empty.
     */
    public void resetDisplay() {
        if (TextUtils.isEmpty(currentExpression)) {
            calculationOutput.onExpressionChanged("Display Already Empty", false);
        }
        currentExpression = "";
        currentResult = "";
        calculationOutput.onExpressionChanged(currentExpression, true);
        calculationOutput.onResultChanged(currentResult);
    }

    /**
     * Append number to currentExpression if valid.
     * "0" & number is 0 - invalid
     * "12234423223232323" - invalid
     *
     * @param number
     */
    public void appendNumber(String number) {
        if (currentExpression.equals("0") && number.equals("0")) {
            calculationOutput.onExpressionChanged("Invalid Input", false);
        }

        if (currentExpression.length() > 16) {
            calculationOutput.onExpressionChanged("Expression Too Long", false);
        }

        currentExpression += number;
        calculationOutput.onExpressionChanged(currentExpression, true);

    }

    /**
     * Append operator to currentExpression if valid.
     * "24" - valid
     * "24-" - invalid
     * "" - invalid
     * "24*2" - valid
     *
     * @param operator
     */
    public void appendOperator(String operator) {
        if (validateExpression(currentExpression)) {
            currentExpression += operator;
            calculationOutput.onExpressionChanged(currentExpression, true);
        }
    }

    /**
     * See type comment for appendOperator
     */
    public void appendDecimal() {
        if (validateExpression(currentExpression)) {

            if (currentExpression.contains(".")) {
                calculationOutput.onExpressionChanged("Too Many Decimals", false);
            }

            currentExpression += ".";
            calculationOutput.onExpressionChanged(currentExpression, true);
        }
    }

    /**
     * If currentExpression passes all checks, pass it to Symbols object. Return the result.
     */
    public void performEvaluation() {
        if (!TextUtils.isEmpty(currentExpression)) {
            try {
                Double result = symbols.eval(currentExpression);
                // currentExpression = result.toString();
                calculationOutput.onResultChanged(result.toString());
            } catch (SyntaxException e) {
                calculationOutput.onResultChanged("Invalid Input");
                e.printStackTrace();
            }
        } else {
            calculationOutput.onExpressionChanged("Expression is Empty", false);
        }
    }

    /**
     * Helper function to validate expression
     *
     * @param expression
     * @return
     */
    private boolean validateExpression(String expression) {
        if (expression.endsWith("/") || expression.endsWith("*") ||
                expression.endsWith("-") || expression.endsWith("+")) {
            calculationOutput.onExpressionChanged("Too Many Operators", false);
            return false;

        } else if (TextUtils.isEmpty(expression)) {
            calculationOutput.onExpressionChanged("Empty Expression", false);
            return false;

        } else if (expression.length() > 16) {
            calculationOutput.onExpressionChanged("Expression Too Long", false);
            return false;

        } else {
            return true;
        }
    }
}

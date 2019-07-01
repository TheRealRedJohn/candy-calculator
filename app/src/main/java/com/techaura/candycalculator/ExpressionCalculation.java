package com.techaura.candycalculator;

import android.text.TextUtils;

import org.mariuszgromada.math.mxparser.Expression;

/**
 * Performs calculations
 */
public class ExpressionCalculation {

    private Expression ep;
    private CalculationOutput calculationOutput;
    private String currentExpression;
    private String currentResult;

    private boolean hasError = false;
    private boolean clickedEvaluate = false;

    public ExpressionCalculation() {
        this.ep = new Expression();
    }

    public interface CalculationOutput {
        void onExpressionChanged(String expression);

        void onResultChanged(String result, boolean successful);
    }

    public void setCalculationOutputListener(CalculationOutput calculationOutput) {
        this.calculationOutput = calculationOutput;
        currentExpression = "";
        currentResult = "";
    }

    /**
     * Deletes a single character from currentExpression
     */
    public void deleteCharacter() {
        try {

            if (validateDelete()) {
                currentExpression = currentExpression.substring(0, currentExpression.length() - 1);
                calculationOutput.onExpressionChanged(currentExpression);
            }

        } catch (Exception e) {
            calculationOutput.onResultChanged("Problem with calculation", false);
        }
    }

    /**
     * Deletes the entire expression and clears result
     */
    public void resetDisplay() {
        try {

            if (validateResetDisplay()) {
                currentExpression = "";
                currentResult = "";
                calculationOutput.onExpressionChanged(currentExpression);
                calculationOutput.onResultChanged(currentResult, true);
                hasError = clickedEvaluate = false;
            }

        } catch (Exception e) {
            calculationOutput.onResultChanged("Problem with calculation", false);
        }
    }

    /**
     * Append number to currentExpression if valid
     *
     * @param number - number to be appended
     */
    public void appendNumber(String number) {
        try {

            validateAppendNumber();
            currentExpression += number;
            calculationOutput.onExpressionChanged(currentExpression);

        } catch (Exception e) {
            calculationOutput.onResultChanged("Problem with calculation", false);
        }
    }

    /**
     * Append operator to currentExpression if valid
     *
     * @param operator - operator to be appended
     */
    public void appendOperator(String operator) {
        try {

            if (validateAppendOperator(operator)) {
                currentExpression += operator;
                calculationOutput.onExpressionChanged(currentExpression);
            }

        } catch (Exception e) {
            calculationOutput.onResultChanged("Problem with calculation", false);
        }
    }

    /**
     * Append decimal if valid
     */
    public void appendDecimal() {
        try {

            if (validateAppendDecimal()) {
                currentExpression += ".";
                calculationOutput.onExpressionChanged(currentExpression);
            }

        } catch (Exception e) {
            calculationOutput.onResultChanged("Problem with calculation", false);
        }
    }

    /**
     * Append percent if valid
     */
    public void appendPercent() {
        try {

            if (validateAppendPercent()) {
                currentExpression += "%";
                calculationOutput.onExpressionChanged(currentExpression);
            }

        } catch (Exception e) {
            calculationOutput.onResultChanged("Problem with calculation", false);
        }
    }

    /**
     * Append power if valid
     */
    public void appendPower() {
        try {

            if (validateAppendPower()) {
                currentExpression += "^";
                calculationOutput.onExpressionChanged(currentExpression);
            }

        } catch (Exception e) {
            calculationOutput.onResultChanged("Problem with calculation", false);
        }
    }

    /**
     * Append root if valid
     */
    public void appendRoot() {
        try {

            if (validateAppendRoot()) {
                currentExpression += "√";
                calculationOutput.onExpressionChanged(currentExpression);
            }

        } catch (Exception e) {
            calculationOutput.onResultChanged("Problem with calculation", false);
        }
    }

    /**
     * Evaluate currentExpression if valid
     */
    public void evaluate() {
        try {

            if (validateEvaluate()) {
                ep.setExpressionString(ExpressionFormatter.getTokenizedExpression(currentExpression));
                double result = ep.calculate();

                if (Double.isNaN(result)) {
                    calculationOutput.onResultChanged(currentResult = "Error", false);
                    hasError = true;
                } else {
                    currentExpression = ExpressionFormatter.addFormatNoDecimal(result);
                    calculationOutput.onResultChanged(ExpressionFormatter.addFormat(result), true);
                    clickedEvaluate = true;
                }
            }

        } catch (Exception e) {
            calculationOutput.onResultChanged("Problem with calculation", false);
        }

    }

    /**
     * Helper method to deleteCharacter()
     *
     * @return true if no errors found, false otherwise
     */
    private boolean validateDelete() {
        if (hasError || clickedEvaluate) {
            resetDisplay();
        }

        return !TextUtils.isEmpty(currentExpression);
    }

    /**
     * Helper method to resetDisplay()
     *
     * @return true if no errors found, false otherwise
     */
    private boolean validateResetDisplay() {
        // do not allow when expression is empty
        return !(TextUtils.isEmpty(currentExpression) && TextUtils.isEmpty(currentResult));
    }

    /**
     * Helper method to appendNumber()
     */
    private void validateAppendNumber() {
        if (hasError || clickedEvaluate) {
            resetDisplay();
        }

        if (!TextUtils.isEmpty(currentExpression) && currentExpression.charAt(currentExpression.length() - 1) == '%') {
            appendOperator("×");
        }
    }

    /**
     * Helper method to appendOperator()
     *
     * @param operator - operator appended
     * @return true if no errors found, false otherwise
     */
    private boolean validateAppendOperator(String operator) {
        if (hasError) {
            resetDisplay();
        } else if (clickedEvaluate) {
            clickedEvaluate = false;
        }

        char charOp = operator.charAt(0);
        // don't allow multiple successive operators
        switch (charOp) {
            case '÷':
            case '×':
            case '+':
                // do not allow leading operator
                if (TextUtils.isEmpty(currentExpression)) {
                    return false;
                }

                // don't allow /* or -+ or ++ or */ etc.
                if (isOperator(currentExpression.charAt(currentExpression.length() - 1))) {
                    deleteCharacter();
                    return true;
                }

                if (currentExpression.charAt(currentExpression.length() - 1) == '^') {
                    return false;
                }

            case '−':
                // do not allow -- or +-
                if (TextUtils.isEmpty(currentExpression)) {
                    return true;
                }

                if (isAddOrSubtractOperator(currentExpression.charAt(currentExpression.length() - 1))) {
                    deleteCharacter();
                    return true;
                }
        }

        if (currentExpression.charAt(currentExpression.length() - 1) == '.') {
            appendNumber("0");
        }

        return true;
    }

    /**
     * Helper method to appendDecimal()
     *
     * @return true if no errors found, false otherwise
     */
    private boolean validateAppendDecimal() {
        if (hasError || clickedEvaluate) {
            resetDisplay();
        }

        // do not allow .5 Instead do 0.5
        if (TextUtils.isEmpty(currentExpression)) {
            appendNumber("0");
            return true;
        }

        // do not allow two decimals in the same number
        int index = currentExpression.lastIndexOf('.');

        // do not allow ..
        if (index == currentExpression.length() - 1) {
            return false;
        }

        // do not allow 6..
        return !(index != -1 && TextUtils.isDigitsOnly
                (currentExpression.substring(index + 1, currentExpression.length() - 1)));
    }

    /**
     * Helper method to appendPercent()
     *
     * @return true if no errors found, false otherwise
     */
    private boolean validateAppendPercent() {
        if (hasError) {
            resetDisplay();
        } else if (clickedEvaluate) {
            clickedEvaluate = false;
        }

        // do not allow leading function
        if (TextUtils.isEmpty(currentExpression)) {
            return false;
        }

        // do not allow *% or ^% etc.
        if (isOperatorOrFunction(currentExpression.charAt(currentExpression.length() - 1))) {
            deleteCharacter();
            return true;
        }

        // do not allow .%
        if (currentExpression.charAt(currentExpression.length() - 1) == '.') {
            appendNumber("0");
        }

        return true;
    }

    /**
     * Helper method to appendPower()
     *
     * @return true if no errors found, false otherwise
     */
    private boolean validateAppendPower() {
        if (hasError) {
            resetDisplay();
        } else if (clickedEvaluate) {
            clickedEvaluate = false;
        }

        // do not allow leading function
        if (TextUtils.isEmpty(currentExpression)) {
            return false;
        }

        // do not allow /^ or *^ or -^ or +^ etc.
        if (isOperatorOrFunctionNotPercent(currentExpression.charAt(currentExpression.length() - 1))) {
            deleteCharacter();
            return true;
        }

        // do not allow √^
        if (currentExpression.charAt(currentExpression.length() - 1) == '√') {
            return false;
        }

        // do not allow .^
        if (currentExpression.charAt(currentExpression.length() - 1) == '.') {
            appendNumber("0");
        }

        return true;
    }

    /**
     * Helper method to appendRoot()
     *
     * @return true if no errors found, false otherwise
     */
    private boolean validateAppendRoot() {
        if (hasError) {
            resetDisplay();
        } else if (clickedEvaluate) {
            clickedEvaluate = false;
        }

        // do not allow 3√5 (instead 3×√5)
        if (!TextUtils.isEmpty(currentExpression) &&
                isNumberOrPercent(currentExpression.charAt(currentExpression.length() - 1))) {
            appendOperator("×");
        }

        // do not allow .√
        if (!TextUtils.isEmpty(currentExpression) &&
                currentExpression.charAt(currentExpression.length() - 1) == '.') {
            appendNumber("0");
        }

        return true;
    }

    /**
     * Helper method to evaluate()
     *
     * @return true if no errors found, false otherwise
     */
    private boolean validateEvaluate() {
        if (hasError) {
            resetDisplay();
        } else if (clickedEvaluate) {
            clickedEvaluate = false;
        }

        if (TextUtils.isEmpty(currentExpression)) {
            return false;
        }

        // do not equate 123^. Instead do 123
        if (isOperatorOrFunctionNotPercent(currentExpression.charAt(currentExpression.length() - 1))) {
            deleteCharacter();
            return true;
        }

        return true;
    }


    /**
     * Helper method to validateAppendOperator()
     *
     * @param character - character to be checked
     * @return true if operator found, false otherwise
     */
    private boolean isOperator(char character) {
        switch (character) {
            case '÷':
            case '×':
            case '−':
            case '+':
                return true;
        }

        return false;
    }

    /**
     * Helper method to validateAppendOperator()
     *
     * @param character - character to be checked
     * @return true if + or - is found, false otherwise
     */
    private boolean isAddOrSubtractOperator(char character) {
        switch (character) {
            case '−':
            case '+':
                return true;
        }

        return false;
    }

    /**
     * Helper method to validateAppendPercent()
     *
     * @param character - character to be checked
     * @return true if character found, false otherwise
     */
    private boolean isOperatorOrFunction(char character) {
        switch (character) {
            case '÷':
            case '×':
            case '−':
            case '+':
            case '%':
            case '^':
            case '√':
                return true;
        }

        return false;
    }

    /**
     * Helper method to validateAppendPower() and validateEvaluate()
     *
     * @param character - character to be checked
     * @return true if character found, false otherwise
     */
    private boolean isOperatorOrFunctionNotPercent(char character) {
        switch (character) {
            case '÷':
            case '×':
            case '−':
            case '+':
            case '^':
            case '√':
                return true;
        }

        return false;
    }

    /**
     * Helper method to validateAppendOperator()
     *
     * @param character - character to be checked
     * @return true if operator found, false otherwise
     */
    private boolean isNumberOrPercent(char character) {
        switch (character) {
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
            case '0':
            case '%':
                return true;
        }

        return false;
    }

}

package com.techaura.candycalculator;

/**
 * Relays values from the UI to actual calculation
 */
public class CalculatorPresenter implements CalculatorContract.KeyPadToPresenter,
        ExpressionCalculation.CalculationOutput {

    private CalculatorContract.UpdateDisplayViews displayViews;
    private ExpressionCalculation calculation;


    public CalculatorPresenter(CalculatorContract.UpdateDisplayViews displayViews) {
        this.displayViews = displayViews;
        calculation = new ExpressionCalculation();
        calculation.setCalculationOutputListener(this);
    }

    @Override
    public void onDeleteShortClick() {
        calculation.deleteCharacter();
    }

    @Override
    public void onDeleteLongClick() {
        calculation.resetDisplay();
    }

    @Override
    public void onNumberClick(String number) {
        calculation.appendNumber(number);
    }

    @Override
    public void onOperatorClick(String operator) {
        calculation.appendOperator(operator);
    }

    @Override
    public void onDecimalClick() {
        calculation.appendDecimal();
    }

    @Override
    public void onPercentClick() {
        calculation.appendPercent();
    }

    @Override
    public void onPowerClick() {
        calculation.appendPower();
    }

    @Override
    public void onRootClick() {
        calculation.appendRoot();
    }

    @Override
    public void onEvaluateClick() {
        calculation.evaluate();
    }

    @Override
    public void onExpressionChanged(String output) {
        displayViews.updateExpression(output);
    }

    @Override
    public void onResultChanged(String output, boolean successful) {
        if (successful) {
            displayViews.showResult(output);
        } else {
            displayViews.showError(output);
        }
    }
}

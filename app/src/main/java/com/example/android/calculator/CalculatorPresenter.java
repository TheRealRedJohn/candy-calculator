package com.example.android.calculator;

public class CalculatorPresenter implements CalculatorContract.KeyPadToPresenter,
        Calculation.CalculationOutput {

    private CalculatorContract.UpdateDisplayViews displayViews;
    private Calculation calculation;

    public CalculatorPresenter(CalculatorContract.UpdateDisplayViews displayViews) {
        this.displayViews = displayViews;
        calculation = new Calculation();
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
        displayViews.showToast("onPercentClick");
    }

    @Override
    public void onPowerClick() {
        displayViews.showToast("onPowerClick");
    }

    @Override
    public void onRootClick() {
        displayViews.showToast("onRootClick");
    }

    @Override
    public void onEvaluateClick() {
        calculation.performEvaluation();
    }

    @Override
    public void onExpressionChanged(String output, boolean successful) {
        if (successful) {
            displayViews.updateExpression(output);
        } else {
            displayViews.showToast(output);
        }
    }

    @Override
    public void onResultChanged(String output) {
        displayViews.showResult(output);
    }
}

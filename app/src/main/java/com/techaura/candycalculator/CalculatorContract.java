package com.techaura.candycalculator;

/**
 * Contract of application
 */
public interface CalculatorContract {

    /**
     * Handled by display
     */
    interface UpdateDisplayViews {
        void updateExpression(String expression);

        void showResult(String result);

        void showError(String message);
    }

    /**
     * Events from keypad handled by presenter
     */
    interface KeyPadToPresenter {
        void onDeleteShortClick();

        void onDeleteLongClick();

        void onNumberClick(String number);

        void onOperatorClick(String operator);

        void onDecimalClick();

        void onPercentClick();

        void onPowerClick();

        void onRootClick();

        void onEvaluateClick();
    }

}

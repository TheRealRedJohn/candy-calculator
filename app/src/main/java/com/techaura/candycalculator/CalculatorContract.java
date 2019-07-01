package com.techaura.candycalculator;

public interface CalculatorContract {

    /**
     * Handled by display
     */
    interface UpdateDisplayViews {
        void updateExpression(String expression);

        void showResult(String result);

        void showError();
    }

    /**
     * Events from keypad handled by Presenter
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

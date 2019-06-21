package com.example.android.calculator;

public interface CalculatorContract {

    // Handled by DisplayFragment
    interface UpdateDisplayViews {
        void updateExpression(String expression);

        void showResult(String result);

        void showToast(String message);
    }

    // Events from KeyPadFragment handled by Presenter
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

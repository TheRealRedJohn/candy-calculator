package com.techaura.candycalculator;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

/**
 * Main entry point of application
 */
public class CalculatorActivity extends BaseActivity
        implements CalculatorContract.UpdateDisplayViews {

    // Expression
    @BindView(R.id.display_expression)
    TextView displayExpression;

    // Result
    @BindView(R.id.display_result)
    TextView displayResult;

    // Relays values to calculate
    private CalculatorContract.KeyPadToPresenter presenter;

    // Display views

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_calculator);

        ButterKnife.bind(this);
        displayExpression.setMovementMethod(new ScrollingMovementMethod());
        presenter = new CalculatorPresenter(this);
    }

    // Settings intent
    @OnClick(R.id.btn_settings)
    public void onSettingsClick() {
        Intent settingsIntent = new Intent(this, CalculatorSettings.class);
        startActivity(settingsIntent);
    }

    // Click events of buttons

    @OnClick(R.id.btn_delete)
    public void onDeleteShortClick() {
        presenter.onDeleteShortClick();
    }

    @OnLongClick(R.id.btn_delete)
    public void onDeleteLongClick() {
        presenter.onDeleteLongClick();
    }

    @OnClick({R.id.btn_number_one, R.id.btn_number_two, R.id.btn_number_three, R.id.btn_number_four,
            R.id.btn_number_five, R.id.btn_number_six, R.id.btn_number_seven, R.id.btn_number_eight,
            R.id.btn_number_nine, R.id.btn_number_zero})
    public void onNumberClick(Button button) {
        presenter.onNumberClick(button.getText().toString());
    }

    @OnClick({R.id.btn_operator_divide, R.id.btn_operator_multiply, R.id.btn_operator_subtract, R.id.btn_operator_add})
    public void onOperatorClick(Button operator) {
        presenter.onOperatorClick(operator.getText().toString());
    }

    @OnClick(R.id.btn_decimal)
    public void onDecimalClick() {
        presenter.onDecimalClick();
    }

    @OnClick(R.id.btn_percent)
    public void onPercentClick() {
        presenter.onPercentClick();
    }

    @OnClick(R.id.btn_power)
    public void onPowerClick() {
        presenter.onPowerClick();
    }

    @OnClick(R.id.btn_root)
    public void onRootClick() {
        presenter.onRootClick();
    }

    @OnClick(R.id.btn_evaluate)
    public void onEvaluateClick() {
        presenter.onEvaluateClick();
    }

    /**
     * Update display expression
     *
     * @param expression - user input
     */
    @Override
    public void updateExpression(String expression) {
        displayExpression.setText(expression);
    }

    /**
     * Update display result
     *
     * @param result - user output
     */
    @Override
    public void showResult(String result) {
        displayResult.setText(result);
        displayResult.setTextColor(isDark ?
                getResources().getColor(R.color.darkColorText) :
                getResources().getColor(R.color.colorText));
    }

    /**
     * Update display result with error
     */
    @Override
    public void showError() {
        displayResult.setText(R.string.error);
        displayResult.setTextColor(getResources().getColor(errorColor));
    }

}

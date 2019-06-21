package com.example.android.calculator;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class KeyPadFragment extends Fragment {

    private CalculatorContract.KeyPadToPresenter keyPadToPresenter;

    public void setPresenter(CalculatorContract.KeyPadToPresenter keyPadToPresenter) {
        this.keyPadToPresenter = keyPadToPresenter;
    }

    @OnClick(R.id.btn_delete)
    public void onDeleteShortClick() {
        keyPadToPresenter.onDeleteShortClick();
    }

    @OnLongClick(R.id.btn_delete)
    public void onDeleteLongClick() {
        keyPadToPresenter.onDeleteLongClick();
    }

    @OnClick({R.id.btn_number_one, R.id.btn_number_two, R.id.btn_number_three, R.id.btn_number_four,
            R.id.btn_number_five, R.id.btn_number_six, R.id.btn_number_seven, R.id.btn_number_eight,
            R.id.btn_number_nine, R.id.btn_number_zero})
    public void onNumberClick(Button button) {
        keyPadToPresenter.onNumberClick(button.getText().toString());
    }

    @OnClick({R.id.btn_operator_divide, R.id.btn_operator_multiply, R.id.btn_operator_subtract, R.id.btn_operator_add})
    public void onOperatorClick(Button operator) {
        keyPadToPresenter.onOperatorClick(operator.getText().toString());
    }

    @OnClick(R.id.btn_decimal)
    public void onDecimalClick() {
        keyPadToPresenter.onDecimalClick();
    }

    @OnClick(R.id.btn_percent)
    public void onPercentClick() {
        keyPadToPresenter.onPercentClick();
    }

    @OnClick(R.id.btn_power)
    public void onPowerClick() {
        keyPadToPresenter.onPowerClick();
    }

    @OnClick(R.id.btn_root)
    public void onRootClick() {
        keyPadToPresenter.onRootClick();
    }

    @OnClick(R.id.btn_evaluate)
    public void onEvaluateClick() {
        keyPadToPresenter.onEvaluateClick();
    }

    private KeyPadFragment() {
        // Required empty public constructor
    }

    public static KeyPadFragment newInstance() {
        return new KeyPadFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_key_pad, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

}

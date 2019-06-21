package com.example.android.calculator;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class DisplayFragment extends Fragment implements CalculatorContract.UpdateDisplayViews {

    @BindView(R.id.display_expression)
    TextView displayExpression;

    @BindView(R.id.display_result)
    TextView displayResult;

    private DisplayFragment() {
        // Required empty public constructor
    }

    public static DisplayFragment newInstance() {
        return new DisplayFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_display, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void updateExpression(String expression) {
        displayExpression.setText(expression);
    }

    @Override
    public void showResult(String result) {
        displayResult.setText(result);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}

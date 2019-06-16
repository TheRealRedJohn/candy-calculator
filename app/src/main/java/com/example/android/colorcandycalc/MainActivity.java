package com.example.android.colorcandycalc;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * Driver class responsible for performing operations and displaying keystrokes and results
 */
public class MainActivity extends AppCompatActivity {

    private final static short ADDITION = 1;
    private final static short SUBTRACTION = 2;
    private final static short MULTIPLICATION = 3;
    private final static short DIVISION = 4;

    private static short operator = 0;
    private static int opLocation = 0;
    private boolean hasOperator = false;

    private TextView inputEditText; // input on display
    private TextView outputTextView; // output on display

    private String operand1 = "";
    private String operand2 = "";
    private double answer = 0.0;

    private DecimalFormat decimalFormat;

    private boolean clickedDelete = false;
    private boolean _ignore = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        symbols.setDecimalSeparator('.');
        symbols.setGroupingSeparator(',');

        decimalFormat = new DecimalFormat();
        decimalFormat.setMaximumFractionDigits(12);
        decimalFormat.setGroupingUsed(true);

        // Calculator display
        inputEditText = findViewById(R.id.text_view_input);
        outputTextView = findViewById(R.id.text_view_output);

        clear();
        updateOutputScreen();

        // Numbers
        MaterialButton buttonOne = findViewById(R.id.button_one);
        MaterialButton buttonTwo = findViewById(R.id.button_two);
        MaterialButton buttonThree = findViewById(R.id.button_three);
        MaterialButton buttonFour = findViewById(R.id.button_four);
        MaterialButton buttonFive = findViewById(R.id.button_five);
        MaterialButton buttonSix = findViewById(R.id.button_six);
        MaterialButton buttonSeven = findViewById(R.id.button_seven);
        MaterialButton buttonEight = findViewById(R.id.button_eight);
        MaterialButton buttonNine = findViewById(R.id.button_nine);
        MaterialButton buttonZero = findViewById(R.id.button_zero);
        MaterialButton buttonDecimal = findViewById(R.id.button_decimal);

        buttonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputEditText.append("1");
            }
        });
        buttonTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputEditText.append("2");
            }
        });
        buttonThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputEditText.append("3");
            }
        });
        buttonFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputEditText.append("4");
            }
        });
        buttonFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputEditText.append("5");
            }
        });
        buttonSix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputEditText.append("6");
            }
        });
        buttonSeven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputEditText.append("7");
            }
        });
        buttonEight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputEditText.append("8");
            }
        });
        buttonNine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputEditText.append("9");
            }
        });
        buttonZero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputEditText.append("0");
            }
        });
        buttonDecimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String appendExpr = inputEditText.getText().toString();

                if (TextUtils.isEmpty(appendExpr)) inputEditText.append("0");
                // don't allow two decimals in the same number
                final int index = appendExpr.lastIndexOf('.');
                if (index != -1 && TextUtils.isDigitsOnly(appendExpr.substring(index + 1))) {
                    return;
                }
                inputEditText.append(".");
            }
        });

        // Functions
        MaterialButton buttonPercentage = findViewById(R.id.button_percentage);
        MaterialButton buttonPower = findViewById(R.id.button_power);
        MaterialButton buttonRoot = findViewById(R.id.button_root);

        // Operations
        MaterialButton buttonAddition = findViewById(R.id.button_addition);
        MaterialButton buttonSubtraction = findViewById(R.id.button_subtraction);
        MaterialButton buttonMultiplication = findViewById(R.id.button_multiplication);
        MaterialButton buttonDivision = findViewById(R.id.button_division);

        buttonAddition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(inputEditText.getText())) inputEditText.append("0");
                inputEditText.append("+");
            }
        });
        buttonSubtraction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(inputEditText.getText())) inputEditText.append("0");
                inputEditText.append("−");
            }
        });
        buttonMultiplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(inputEditText.getText())) inputEditText.append("0");
                inputEditText.append("×");
            }
        });
        buttonDivision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(inputEditText.getText())) inputEditText.append("0");
                inputEditText.append("÷");
            }
        });

        // Clear display
        MaterialButton buttonDelete = findViewById(R.id.button_delete);

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickDelete();
            }
        });
        buttonDelete.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onLongClickDelete();
                return true;
            }
        });

        // Solve problem
        MaterialButton buttonSolve = findViewById(R.id.button_solve);

        buttonSolve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickEqual();
                updateOutputScreen();
                clear();
            }
        });

        inputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (_ignore) return;
                operand1 = s.toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s) && isOperator(s.charAt(start))) {
                    opLocation = start;
                    hasOperator = true;
                    _ignore = true;
                }

                if (clickedDelete && (TextUtils.isEmpty(s) || isOperator(s.charAt(start)))) {
                    hasOperator = false;
                    _ignore = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (hasOperator) {
                    operand2 = s.toString().substring(opLocation + 1);

                    if (!TextUtils.isEmpty(operand1) && !TextUtils.isEmpty(operand2)) {
                        calculate(operand1, operand2);
                        updateOutputScreen();
                        operand1 = formatToString(answer);
                    }
                }

            }
        });

    }

    private void updateOutputScreen() {
        outputTextView.setText(doubleToString(answer));
    }

    private void onClickDelete() {
        if (TextUtils.isEmpty(inputEditText.getText())) return;
        inputEditText.setText(inputEditText.getText().subSequence(0, inputEditText.getText().length() - 1));
        clickedDelete = true;
    }

    private void onLongClickDelete() {
        clear();
        updateOutputScreen();
        inputEditText.setText("");
    }

    private void calculate(String a, String b) {

        switch (operator) {
            case ADDITION:
                answer = Operation.addition(Double.parseDouble(a), Double.parseDouble(b));
                break;
            case SUBTRACTION:
                answer = Operation.subtraction(Double.parseDouble(a), Double.parseDouble(b));
                break;
            case MULTIPLICATION:
                answer = Operation.multiplication(Double.parseDouble(a), Double.parseDouble(b));
                break;
            case DIVISION:
                answer = Operation.division(Double.parseDouble(a), Double.parseDouble(b));
                break;
            default:
                answer = 0.0;
        }
    }

    private void onClickEqual() {

    }

    private boolean isOperator(char character) {
        switch (character) {
            case '+':
                operator = ADDITION;
                return true;
            case '−':
                operator = SUBTRACTION;
                return true;
            case '×':
                operator = MULTIPLICATION;
                return true;
            case '÷':
                operator = DIVISION;
                return true;
            default:
                return false;
        }
    }

    private void clear() {
        operand1 = "";
        operand2 = "";
        answer = 0.0;
        operator = 0;
        hasOperator = false;
        clickedDelete = false;
        _ignore = false;
    }

    private String doubleToString(double d) {
        return decimalFormat.format(d);
    }

    private String formatToString(double s) {
        return Double.toString(s).replace(",", "");
    }

}

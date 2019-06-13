package com.example.android.colorcandycalc;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import java.util.regex.Pattern;

/**
 * Driver class responsible for performing operations and displaying keystrokes and results
 */
public class MainActivity extends AppCompatActivity {

    private final static short ADDITION = 1;
    private final static short SUBTRACTION = 2;
    private final static short MULTIPLICATION = 3;
    private final static short DIVISION = 4;

    private static int currentOperator = 0;
    private boolean hasOperator = false;

    private TextView inputEditText; // input on display
    private TextView outputTextView; // output on display

    private String display = "";
    private String prevAnswer = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Calculator display
        inputEditText = findViewById(R.id.text_view_input);
        outputTextView = findViewById(R.id.text_view_output);

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
                onClickKey('1');
            }
        });
        buttonTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickKey('2');
            }
        });
        buttonThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickKey('3');
            }
        });
        buttonFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickKey('4');
            }
        });
        buttonFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickKey('5');
            }
        });
        buttonSix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickKey('6');
            }
        });
        buttonSeven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickKey('7');
            }
        });
        buttonEight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickKey('8');
            }
        });
        buttonNine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickKey('9');
            }
        });
        buttonZero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickKey('0');
            }
        });
        buttonDecimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickKey('.');
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
                onClickOperator(ADDITION);
            }
        });
        buttonSubtraction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickOperator(SUBTRACTION);
            }
        });
        buttonMultiplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickOperator(MULTIPLICATION);
            }
        });
        buttonDivision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickOperator(DIVISION);
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

        updateInputScreen();
        updateOutputScreen();

    }

    private void updateInputScreen() {
        inputEditText.setText(display);
    }

    private void updateOutputScreen() {
        outputTextView.setText(prevAnswer);
    }

    private void onClickKey(char key) {
        display += key;
        updateInputScreen();

    }

    private void onClickOperator(short operator) {

        if (TextUtils.isEmpty(display)) {
            return;
        }

        char operatorSym = getSym(operator);

        if (!TextUtils.isEmpty(prevAnswer)) {
            display = prevAnswer;

        } else if (hasOperator) {
            onClickEqual();
            hasOperator = false;
            display = prevAnswer;
            prevAnswer = "";
        }

        display += operatorSym;
        currentOperator = operator;
        hasOperator = true;

        updateInputScreen();

    }

    private void onClickDelete() {
        if (!TextUtils.isEmpty(display)) {

            if (isOperator(display.charAt(display.length() - 1))) {
                hasOperator = false;
            }

            display = display.substring(0, display.length() - 1);
            updateInputScreen();
        }

    }

    private void onLongClickDelete() {
        clear();
        updateInputScreen();
        updateOutputScreen();
    }

    private double calculate(String a, String b) {

        switch (currentOperator) {
            case ADDITION:
                return Operation.addition(Double.parseDouble(a), Double.parseDouble(b));
            case SUBTRACTION:
                return Operation.subtraction(Double.parseDouble(a), Double.parseDouble(b));
            case MULTIPLICATION:
                return Operation.multiplication(Double.parseDouble(a), Double.parseDouble(b));
            case DIVISION:
                return Operation.division(Double.parseDouble(a), Double.parseDouble(b));
            default:
                return -1;
        }
    }

    private void onClickEqual() {

        if (TextUtils.isEmpty(display)) {
            return;
        }

        if (!hasOperator) {
            prevAnswer = display;
            return;
        }

        String[] operands = display.split(Pattern.quote(String.valueOf(getSym(currentOperator))));
        if (operands.length < 2) {
            return;
        }

        prevAnswer = String.valueOf(calculate(operands[0], operands[1]));
    }

    private char getSym(int operator) {
        switch (operator) {
            case ADDITION:
                return '+';
            case SUBTRACTION:
                return '-';
            case MULTIPLICATION:
                return '*';
            case DIVISION:
                return '/';
            default:
                return 0;
        }
    }

    private boolean isOperator(char character) {
        switch (character) {
            case '+':
            case '-':
            case '*':
            case '/':
                return true;
            default:
                return false;
        }
    }

    private void clear() {
        display = "";
        prevAnswer = "";
        currentOperator = 0;
    }

}

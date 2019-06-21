package com.example.android.calculator;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class CalculatorActivity extends AppCompatActivity {

    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = getSupportFragmentManager();

        DisplayFragment displayFragment = DisplayFragment.newInstance();
        KeyPadFragment keyPadFragment = KeyPadFragment.newInstance();

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.fragment_display, displayFragment);
        transaction.add(R.id.fragment_key_pad, keyPadFragment);
        transaction.commit();

        CalculatorPresenter presenter = new CalculatorPresenter(displayFragment);
        keyPadFragment.setPresenter(presenter);
    }
}

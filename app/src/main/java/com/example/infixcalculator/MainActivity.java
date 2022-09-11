package com.example.infixcalculator;

import android.os.Bundle;

import com.example.infixcalculator.algorithm.InfixCalculator;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.infixcalculator.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private TextView expression, result;
    private InfixCalculator calculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        expression = findViewById(R.id.txt_expression);
        result = findViewById(R.id.txt_result);

        calculator = new InfixCalculator();

        initButtonListener();

    }

    private void initButtonListener() {
        View.OnClickListener onNumberButtonClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button b = (Button) view;
                String text = b.getText().toString();

                expression.append(text);
            }
        };

        findViewById(R.id.zero).setOnClickListener(onNumberButtonClick);
        findViewById(R.id.one).setOnClickListener(onNumberButtonClick);
        findViewById(R.id.two).setOnClickListener(onNumberButtonClick);
        findViewById(R.id.three).setOnClickListener(onNumberButtonClick);
        findViewById(R.id.four).setOnClickListener(onNumberButtonClick);
        findViewById(R.id.five).setOnClickListener(onNumberButtonClick);
        findViewById(R.id.six).setOnClickListener(onNumberButtonClick);
        findViewById(R.id.seven).setOnClickListener(onNumberButtonClick);
        findViewById(R.id.eight).setOnClickListener(onNumberButtonClick);
        findViewById(R.id.nine).setOnClickListener(onNumberButtonClick);
        findViewById(R.id.coma).setOnClickListener(onNumberButtonClick);


        View.OnClickListener onOperandButtonClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button b = (Button) view;
                String text = b.getText().toString();

                expression.append(" " + text + " ");
            }
        };

        findViewById(R.id.open_apos).setOnClickListener(onOperandButtonClick);
        findViewById(R.id.close_apos).setOnClickListener(onOperandButtonClick);
        findViewById(R.id.subtract).setOnClickListener(onOperandButtonClick);
        findViewById(R.id.div).setOnClickListener(onOperandButtonClick);
        findViewById(R.id.addition).setOnClickListener(onOperandButtonClick);
        findViewById(R.id.multiply).setOnClickListener(onOperandButtonClick);

        findViewById(R.id.equal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result.setText(evaluate());
                expression.setText("");
            }
        });

        findViewById(R.id.c).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result.setText("");
                expression.setText("");
            }
        });

        findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String expression_str = expression.getText().toString();
                if (expression_str.length() - 1 >= 0) {
                    expression.setText(expression_str.substring(0, expression_str.length() - 1));
                }
            }
        });

        findViewById(R.id.minus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expression.append("-");
            }
        });
    }

    private String evaluate() {
        String expression_str = expression.getText().toString(), result;
        System.out.println(expression_str);

        try {
            result = calculator.evaluate(expression_str);
        } catch (Exception e) {
            result = e.getMessage();
        }
        return result;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
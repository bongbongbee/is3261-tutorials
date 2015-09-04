package com.example.tysonquek.tysoncalculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private float result;
    private String currentOperator;
    private TextView calculatorDisplay;
    private float currentNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result = 0;
        currentOperator = "";
        calculatorDisplay = (TextView) findViewById(R.id.calculatorDisplay);
        currentNumber = 0;
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

    public void digitsOnClick(View view) {
        float numberPressed = Float.parseFloat((((Button) view)).getText().toString());
        currentNumber = currentNumber * 10 + numberPressed;
        if (currentOperator.equals("=")) result = currentNumber;
        calculatorDisplay.setText(String.valueOf(currentNumber));
    }

    public void operatorOnClick(View view) {
        if (currentOperator == "") {
            result = currentNumber;

        } else {
            if (currentOperator.equals("+")) result += currentNumber;
            else if (currentOperator.equals("-")) result -= currentNumber;
            else if (currentOperator.equals("×")) result *= currentNumber;
            else if (currentOperator.equals("÷")) result /= currentNumber;
        }
        currentNumber = 0;
        System.out.printf("result : %f \n", result);
        calculatorDisplay.setText(String.valueOf(result));
        currentOperator = ((Button) view).getText().toString();
    }

    public void clear(View view) {
        currentNumber = 0;
        calculatorDisplay.setText(String.valueOf(currentNumber));
    }

    public void allClear(View view) {
        currentNumber = 0;
        currentOperator = "";
        result = 0;
        calculatorDisplay.setText(String.valueOf(result));
    }

}

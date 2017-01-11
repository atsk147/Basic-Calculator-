package com.atsk.basiccalculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.regex.Pattern;

//TODO DOT BUTTON

public class MainActivity extends AppCompatActivity {

    private String defaultOperator = "";
    private String result = "";
    private String display = "";
    private TextView screen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        screen = (TextView)findViewById(R.id.inputNumber);
        screen.setText(display);
    }

    private void updateScreen(){
        screen.setText(display);
    }

    private void clear(){
        display = "";
        defaultOperator = "";
        result = "";
    }

    private boolean getResult(){
        if(defaultOperator == "") return false;
        String[] operation = display.split(Pattern.quote(defaultOperator));
        if(operation.length < 2) return false;
        result = String.valueOf(operate(operation[0], operation[1], defaultOperator));
        return true;
    }

    private boolean isOperator(char op){
        switch (op){
            case '.':return true;
            case '+':return true;
            case '-':return true;
            case 'x':return true;
            case '/':return true;
            default: return false;
        }
    }

    private double operate(String a, String b, String op){

        String dot = ".";
        switch (op){
            case ".": return Double.valueOf(dot);
            case "+": return Double.valueOf(a) + Double.valueOf(b);
            case "-": return Double.valueOf(a) - Double.valueOf(b);
            case "x": return Double.valueOf(a) * Double.valueOf(b);
            case "/": try{
                return Double.valueOf(a) / Double.valueOf(b);
            }catch (Exception e){
                Log.d("calculation error", e.getMessage());
            }
            default: return -1;
        }
    }

    public void onClickNumber(View v){
        if(result != ""){
            clear();
            updateScreen();
        }
        Button b = (Button) v;
        display += b.getText();
        updateScreen();
    } //CLOSE NUMBERS

    public void onClickOperator(View v){
        if(display == "")
            return;

        Button b = (Button) v;

        if(result != ""){
            String display2 = result;
            clear();
            display = display2;
        }

        if(defaultOperator != ""){
            Log.d("calculationTWO", "" + display.charAt(display.length()-1));
            if(isOperator(display.charAt(display.length()-1))){
                display = display.replace(display.charAt(display.length()-1), b.getText().charAt(0));
                updateScreen();
                return;
            }else{
                getResult();
                display = result;
                result = "";
            }
            defaultOperator = b.getText().toString();
        }

        display += b.getText();
        defaultOperator = b.getText().toString();
        updateScreen();
    } //CLOSE OPERATORS

    public void onClickEqual(View v){
        if(display == "")
            return;
        if(!getResult())
            return;
        screen.setText(display + "\n" + String.valueOf(result));
    } //CLOSE EQUALS



    public void onClickClear(View v){
        clear();
        updateScreen();
    } //CLOSE CLEAR

}


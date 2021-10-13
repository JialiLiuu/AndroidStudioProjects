package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;

import com.example.base.BaseActivity;

public class MainActivity3 extends BaseActivity {
    EditText dollarText;
    EditText euroText;
    EditText wonText;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        Intent intent = getIntent();

        dollarText = (EditText)findViewById(R.id.dollar_rate_new);
        euroText = (EditText)findViewById(R.id.euro_rate_new);
        wonText = (EditText)findViewById(R.id.won_rate_new);

        dollarText.setText(String.valueOf(intent.getFloatExtra("dollar_rate",0.35f)));
        euroText.setText(String.valueOf(intent.getFloatExtra("euro_rate",0.28f)));
        wonText.setText(String.valueOf(intent.getFloatExtra("won_rate",501)));
    }

    public void save(View btn){
//        dollarText = (EditText)findViewById(R.id.dollar_rate_new);
//        euroText = (EditText)findViewById(R.id.euro_rate_new);
//        wonText = (EditText)findViewById(R.id.won_rate_new);

        float newDollar = Float.parseFloat(dollarText.getText().toString());
        float newEuro = Float.parseFloat(euroText.getText().toString());
        float newWon = Float.parseFloat(wonText.getText().toString());

        Intent intent = getIntent();

        Bundle bdl = new Bundle();
        bdl.putFloat("dollar_key",newDollar);
        bdl.putFloat("euro_key",newEuro);
        bdl.putFloat("won_key",newWon);

        intent.putExtras(bdl);

        setResult(5,intent);

        finish();
    }

}
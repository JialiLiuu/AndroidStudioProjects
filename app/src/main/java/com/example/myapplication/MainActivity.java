package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onMyClick(View v){
        TextView text1 = findViewById(R.id.show_1);
        TextView text2 = findViewById(R.id.show_2);

        int score1 = Integer.valueOf(text1.getText().toString());
        int score2 = Integer.valueOf(text2.getText().toString());

        if(v.getId()==R.id.btn_1){
            score1+=1;
            text1.setText(String.valueOf(score1));
        }
        else if(v.getId()==R.id.btn_2){
            score1+=2;
            text1.setText(String.valueOf(score1));
        }
        else if(v.getId()==R.id.btn_3){
            score1+=3;
            text1.setText(String.valueOf(score1));
        }
        else if(v.getId()==R.id.btn_4){
            score2+=1;
            text2.setText(String.valueOf(score2));
        }
        else if(v.getId()==R.id.btn_5){
            score2+=2;
            text2.setText(String.valueOf(score2));
        }
        else if(v.getId()==R.id.btn_6){
            score2+=3;
            text2.setText(String.valueOf(score2));
        }
        else if(v.getId()==R.id.btn_reset){
            score1=0;
            text1.setText(String.valueOf(score1));
            score2=0;
            text2.setText(String.valueOf(score2));
        }
    }
}
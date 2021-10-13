package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity6 extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);

        Intent intent = getIntent();
        EditText editText = (EditText) findViewById(R.id.input_value);
        TextView view = (TextView) findViewById(R.id.textView);
        String title = String.valueOf(intent.getStringExtra("ItemTitle"));
        view.setText(title);
        String rate = String.valueOf(intent.getStringExtra("ItemDetail"));
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            //输入时的调用
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("MainActivity6", "onTextChanged() returned: ");
                mHandler.removeCallbacks(mRunnable);
                //800毫秒没有输入认为输入完毕
                mHandler.postDelayed(mRunnable, 800);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!TextUtils.isEmpty(editable)){
                    Float in = Float.parseFloat(rate) * Float.parseFloat(editable.toString());
                    view.setText(title + "--->" + String.valueOf(in));
                    Log.d("MainActivity6", "修改");
                }
                else{
                    view.setText(title);
                    Log.d("MainActivity6", "为空");
                }


            }
        });


    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (111 == msg.what) {
                Log.d("MainActivity6", "handleMessage() returned:输入完成 ");
            }
        }
    };
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            mHandler.sendEmptyMessage(111);
        }
    };

}
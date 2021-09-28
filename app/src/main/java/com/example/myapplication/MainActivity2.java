package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.HeaderViewListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.base.BaseActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity2 extends BaseActivity implements Runnable {
    float dollar_rate=0.35f,euro_rate=0.28f,won_rote=501;
    TextView show;
    Handler handler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        show=findViewById(R.id.show);

        loadFromSP();

        handler=new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(Message message){
                Log.i("MainActivity1","收到消息");
                if(message.what==6){
                    String str = (String) message.obj;
                    show.setText(str);
                }
                super.handleMessage(message);
            }
        };

        Thread thread = new Thread(this);
        thread.start();
    }

    private void loadFromSP(){
        SharedPreferences sharedPreferences = getSharedPreferences("myrate",Activity.MODE_PRIVATE);
        dollar_rate = sharedPreferences.getFloat("dollar_rate",0.11f);
        euro_rate = sharedPreferences.getFloat("euro_rate",0.22f);
        won_rote = sharedPreferences.getFloat("won_rote",0.33f);
    }

    public void click(View btn){
        EditText rmb=findViewById(R.id.input_rmb);
        TextView show=findViewById(R.id.show);
        if(rmb.getText().toString().length()>0){
            float num=Float.parseFloat(rmb.getText().toString());
            if(btn.getId()==R.id.button_dollar){
                num*=dollar_rate;
            }
            else if(btn.getId()==R.id.button_euro){
                num*=euro_rate;
            }
            else if(btn.getId()==R.id.button_won){
                num*=won_rote;
            }
            show.setText(String.valueOf(num));
        }
        else
        {
            Toast.makeText(this,"金额输入不能为空",Toast.LENGTH_SHORT).show();
        }
    }

    public void open(View v){
        Intent rate = new Intent(this,MainActivity3.class);
        rate.putExtra("dollar_rate",dollar_rate);
        rate.putExtra("euro_rate",euro_rate);
        rate.putExtra("won_rate",won_rote);
        startActivityForResult(rate,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==1 && resultCode==5){
//            dollar_rate = data.getFloatExtra("dollar_key",0.1f);
//            euro_rate = data.getFloatExtra("euro_key",0.1f);
//            won_rote = data.getFloatExtra("won_key",0.1f);
            Bundle bundle = data.getExtras();
            dollar_rate = bundle.getFloat("dollar_key",0.1f);
            euro_rate = bundle.getFloat("euro_key",0.1f);
            won_rote = bundle.getFloat("won_key",0.1f);

            SharedPreferences sp = getSharedPreferences("myrate",Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putFloat("dollar_rate",dollar_rate);
            editor.putFloat("euro_rate",euro_rate);
            editor.putFloat("won_rote",won_rote);
            editor.apply();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void run() {
        try {
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        URL url = null;
        try{
            url = new URL("https://www.usd-cny.com/bankofchina.htm");
            HttpsURLConnection http = (HttpsURLConnection) url.openConnection();
            InputStream in = http.getInputStream();
            String html = inputStream2String(in);
            Log.i("MainActivity1",html);
        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

        Message message = handler.obtainMessage(6);

        message.obj="hello from run";
        handler.sendMessage(message);

    }

    private String inputStream2String(InputStream inputStream) throws IOException{
        final int bufferSize=1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(inputStream,"gb2312");
        while(true){
            int rsz = in.read(buffer,0,buffer.length);
            if(rsz<0)
                break;
            out.append(buffer,0,rsz);
        }
        return out.toString();
    }
}
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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.annotation.Documented;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

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
                Log.i("MainActivity2","收到消息");

                if(message.what==6){
                    Bundle bundle = (Bundle) message.obj;
                    dollar_rate=bundle.getFloat("dollar_rate");
                    euro_rate=bundle.getFloat("euro_rate");
                    won_rote=bundle.getFloat("won_rote");

                    Toast.makeText(MainActivity2.this,"汇率更新",Toast.LENGTH_SHORT).show();
                }
                super.handleMessage(message);
            }
        };

        Thread thread = new Thread(this);
        thread.start();
    }

    public static boolean isThisTime(long time, String pattern) {
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String param = sdf.format(date);//参数时间
        Log.i("MainActivity2","参数时间："+param);
        String now = sdf.format(new Date());//当前时间
        Log.i("MainActivity2","当前时间："+now);
        if (param.equals(now)) {
            Log.i("汇率已经修改","当前时间："+now);
            return true;
        }
        Log.i("汇率还未修改","当前时间："+now);
        return false;
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

//        URL url = null;
//        try{
//            url = new URL("https://www.usd-cny.com/bankofchina.htm");
//            HttpsURLConnection http = (HttpsURLConnection) url.openConnection();
//            InputStream in = http.getInputStream();
//            String html = inputStream2String(in);
//            Log.i("MainActivity","run: html="+html);
//        }catch (MalformedURLException e){
//            e.printStackTrace();
//        }catch (IOException e){
//            e.printStackTrace();
//        }
        Bundle bundle = new Bundle();

        SharedPreferences sharedPreferences = getSharedPreferences("myrate",Activity.MODE_PRIVATE);
        long time = sharedPreferences.getLong("data_2",0);
        Log.i("MainActivity2","日期是="+time);

        if(isThisTime(time,"yyyy-MM-dd")){  //是今天
            loadFromSP();
            bundle.putFloat("dollar_rate",dollar_rate);
            bundle.putFloat("euro_rate",euro_rate);
            bundle.putFloat("won_rote",won_rote);
        }
        else{   //不是今天
            try {
                Document doc = Jsoup.connect("https://usd-cny.com/").get();
                Element firstTable = doc.getElementsByTag("table").first();
                Elements trs = firstTable.getElementsByTag("tr");
                trs.remove(0);
                for(Element tr : trs){
                    Elements tds = tr.getElementsByTag("td");
                    Element td1 = tds.get(0);
                    Element td2 = tds.get(4);
//                Log.i("汇率",""+td1.text()+"------"+td2.text());
                    if("美元".equals(td1.text())){
                        Log.i("美元",""+td1.text()+"------"+td2.text());
                        bundle.putFloat("dollar_rate",Float.valueOf(td2.text()));
                    }
                    else if("欧元".equals(td1.text())){
                        bundle.putFloat("euro_rate",Float.valueOf(td2.text()));
                    }
                    else if("韩币".equals(td1.text())){
                        bundle.putFloat("won_rote",Float.valueOf(td2.text()));
                    }
                }
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putLong("data_2",Long.valueOf(System.currentTimeMillis()));
                editor.apply();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        Message message = handler.obtainMessage(6);

        message.obj=bundle;
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
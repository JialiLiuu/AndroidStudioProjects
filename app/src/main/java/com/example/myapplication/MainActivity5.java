package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity5 extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView mylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

//        ArrayList<HashMap<String, String>> listItems = new ArrayList<HashMap<String, String>>();
//        for (int i = 0; i < 10; i++) {
//            HashMap<String, String> map = new HashMap<String, String>();
//            map.put("ItemTitle", "rate:" + i);
//            map.put("ItemDetail", "detail:" + i);
//            listItems.add(map);
//        }
//
//        SimpleAdapter adapter = new SimpleAdapter(this, listItems,
//                R.layout.list_item,
//                new String[]{"ItemTitle", "ItemDetail"},
//                new int[]{R.id.itemTitle, R.id.itemDetail}
//        );

        mylist = findViewById(R.id.mylist2);
        mylist.setOnItemClickListener(this);
//        mylist.setAdapter(adapter);

        Handler handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message message){
                super.handleMessage(message);
                if(message.what==9){
                    ArrayList<HashMap<String, String>> listItems = (ArrayList<HashMap<String, String>>)message.obj;
                    SimpleAdapter adapter = new SimpleAdapter(MainActivity5.this, listItems,
                            R.layout.list_item,
                            new String[]{"ItemTitle", "ItemDetail"},
                            new int[]{R.id.itemTitle, R.id.itemDetail}
                    );
                    mylist.setAdapter(adapter);
                }
            }
        };
        Task1 task = new Task1();
        task.setHandler(handler);
        Thread t = new Thread(task);
        t.start();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Object itemAtPosition = mylist.getItemAtPosition(i);
        HashMap<String,String> map = (HashMap<String, String>) itemAtPosition;
        String title=map.get("ItemTitle");
        String detail=map.get("ItemDetail");
        Log.i("xx",title+"---"+"detail");

        Intent rate = new Intent(this,MainActivity6.class);
        rate.putExtra("ItemTitle",title);
        rate.putExtra("ItemDetail",detail);
        startActivityForResult(rate,166);
    }
}
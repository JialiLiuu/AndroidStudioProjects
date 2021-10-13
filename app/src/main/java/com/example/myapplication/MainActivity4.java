package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity4 extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<String> list1 = new ArrayList<String>();
        for (int i = 1; i < 100; i++) {
            list1.add("list" + i);
        }
        String[] list_data = {"one", "tow", "three", "four"};
        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, list_data);
        setListAdapter(adapter);

        Handler handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message message) {
                if (message.what == 9) {
                    ArrayList<String> list2 = (ArrayList<String>) message.obj;
                    ListAdapter adapter = new ArrayAdapter<String>(MainActivity4.this, android.R.layout.simple_list_item_activated_1, list2);
                    setListAdapter(adapter);
                } else if (message.what == 6) {
                    Bundle bd = (Bundle) message.obj;
                    Log.i("444:","  "+bd.getFloat("dollar_rate"));
                }
            }
        };
        Task task = new Task();
        task.setHandler(handler);
        Thread t = new Thread(task);
        t.start();
//        MainActivity2 two = new MainActivity2();
//        Thread t2 = new Thread(two);
//        t2.start();
    }
}
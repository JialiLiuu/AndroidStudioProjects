package com.example.myapplication;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.os.Handler;

public class Task1 implements Runnable {

    private Handler handler;

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        ArrayList<HashMap<String, String>> retlist = new ArrayList<HashMap<String, String>>();
        try {
            Document doc = Jsoup.connect("https://www.boc.cn/sourcedb/whpj/").get();
            Element tt = (((doc.getElementsByTag("div").first()).getElementsByTag("div").get(5)).getElementsByTag("div").get(2));
            //Log.i("x1",""+tt);
            Element firstTable = tt.getElementsByTag("table").get(1);
            //Log.i("x2",""+firstTable);
            Elements trs = firstTable.getElementsByTag("tr");
            trs.remove(0);
            for (Element tr : trs) {
                HashMap<String, String> map = new HashMap<String, String>();
                Elements tds = tr.getElementsByTag("td");
                Element td1 = tds.get(0);
                Element td2 = tds.get(4);
                map.put("ItemTitle", "" + td1.text());
                map.put("ItemDetail", "" + td2.text());
                retlist.add(map);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Message message = handler.obtainMessage(9,retlist);
        handler.sendMessage(message);
    }
}

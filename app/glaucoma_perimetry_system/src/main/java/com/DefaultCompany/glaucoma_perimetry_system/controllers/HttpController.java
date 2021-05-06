package com.DefaultCompany.glaucoma_perimetry_system.controllers;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class HttpController {

    private int TIME_OUT = 10000; // 超时时间.
    // 连接服务器的url.
    private String url = "http://172.20.2.28:8000/";
    private InputStream is;
    private JSONObject jo;

    public JSONObject selectVal(String id, String eye) throws ExecutionException, InterruptedException {

        //创建StringBuffer对象用于存储所有数据
        StringBuffer sb = new StringBuffer();
        Callable callable = new Callable() {
            @Override
            public JSONObject call() throws Exception {
                try {
                    HttpURLConnection conn = (HttpURLConnection) new URL(url + "select?Id=" + id + "&eye=" + eye)
                            .openConnection();
                    conn.setConnectTimeout(TIME_OUT);
                    conn.setRequestMethod("GET"); // GET是大小写敏感的.
                    Log.i("get请求",url + "select?Id=" + id + "&eye=" + eye);
                    //得到输入流
                    is = conn.getInputStream();
                    //创建包装流
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    //定义String类型用于储存单行数据
                    String line = null;

                    while ((line = br.readLine()) != null) {
                        Log.i("数据",line);
                        sb.append(line);
                    }
                    Log.i("sb",sb.toString());
                    jo = new JSONObject(sb.toString());
                    Log.i("转json",jo.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.w("", "连接不上服务器");
                }
                return jo;
            }
        };
       FutureTask<JSONObject> futureTask=new FutureTask<>(callable);
        Thread thread=new Thread(futureTask);
        thread.start();

        while (!futureTask.isDone()) {

        }
        return futureTask.get();
    }

    public void insertVal(String id, String str) {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url + "insert?val=" + id + "," + str)
                    .openConnection();
            conn.setConnectTimeout(TIME_OUT);
            System.out.println(url + "insert?" + id + "," + str);
            conn.setRequestMethod("GET"); // GET是大小写敏感的.
            System.out.println(conn.getResponseCode());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

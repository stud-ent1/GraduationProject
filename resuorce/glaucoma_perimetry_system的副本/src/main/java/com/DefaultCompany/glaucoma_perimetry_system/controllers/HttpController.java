package com.DefaultCompany.glaucoma_perimetry_system.controllers;

import android.util.Log;

import com.DefaultCompany.glaucoma_perimetry_system.enums.RequestType;

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
import java.util.concurrent.FutureTask;

public class HttpController {

    private int TIME_OUT = 10000; // 超时时间.
    // 连接服务器的url.
    private String url = "http://42.193.97.77:8000/";

    synchronized JSONObject selectVal(JSONObject jsonObject) throws IOException, JSONException {
        HttpURLConnection conn = (HttpURLConnection) new URL(url + "select?val=" + jsonObject)
                .openConnection();
        conn.setConnectTimeout(TIME_OUT);
        conn.setRequestMethod("GET"); // GET是大小写敏感的.
        Log.i("查询请求", url + "select?val=" + jsonObject);
        //得到输入流
        InputStream inputStream = conn.getInputStream();
        //创建包装流
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        //定义String类型用于储存单行数据
        String line = br.readLine();

        Log.i("数据", line);

        JSONObject rJsonObject = new JSONObject(line);
        Log.i("转json", rJsonObject.toString());

        return rJsonObject;
    }


    public JSONObject checkVal(JSONObject jsonObject) throws IOException, JSONException {


        HttpURLConnection conn = (HttpURLConnection) new URL(url + "check?val=" + jsonObject)
                .openConnection();
        conn.setConnectTimeout(TIME_OUT);
        conn.setRequestMethod("GET"); // GET是大小写敏感的.
        Log.i("校验请求", url + "check?val=" + jsonObject);
        //得到输入流
        InputStream inputStream = conn.getInputStream();
        //创建包装流
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        //定义String类型用于储存单行数据
        String line = br.readLine();

        Log.i("数据", line);
        JSONObject rJsonObject = new JSONObject(line);
        Log.i("转json", rJsonObject.toString());
        return rJsonObject;

    }

    synchronized JSONObject registerVal(JSONObject jsonObject) throws IOException, JSONException {
        HttpURLConnection conn = (HttpURLConnection) new URL(url + "register?val=" + jsonObject)
                .openConnection();
        conn.setConnectTimeout(TIME_OUT);
        Log.i("注册请求", url + "register?val=" + jsonObject);
        conn.setRequestMethod("GET"); // GET是大小写敏感的.
        InputStream inputStream = conn.getInputStream();
        //创建包装流
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        //定义String类型用于储存单行数据
        String line = br.readLine();
        Log.i("数据", line);
        JSONObject rJsonObject = new JSONObject(line);
        Log.i("转json", rJsonObject.toString());
        return rJsonObject;
    }
    public JSONObject insertVal(JSONObject jsonObject) throws IOException, JSONException {
            HttpURLConnection conn = (HttpURLConnection) new URL(url + "insert?val=" +jsonObject)
                    .openConnection();
            conn.setConnectTimeout(TIME_OUT);
            Log.i("插入请求",url + "insert?val=" +jsonObject);
            conn.setRequestMethod("GET"); // GET是大小写敏感的.
            InputStream inputStream = conn.getInputStream();
            //创建包装流
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            //定义String类型用于储存单行数据
            String line = br.readLine();
            Log.i("数据", line);
            JSONObject rJsonObject = new JSONObject(line);
            Log.i("转json", rJsonObject.toString());
            return rJsonObject;


    }

    public JSONObject execRequest(RequestType requestType, JSONObject jsonObject) throws ExecutionException, InterruptedException {
        Callable callable = new Callable() {
            @Override
            public Object call() throws Exception {
                switch (requestType) {
                    case CHECK:
                        return checkVal(jsonObject);
                    case INSERT:
                        return insertVal(jsonObject);
                    case SELECT:
                        return selectVal(jsonObject);
                    case REGISTER:
                        return registerVal(jsonObject);
                    default:
                        break;
                }
                return null;
            }
        };
        FutureTask<JSONObject> futureTask = new FutureTask<>(callable);
        Thread thread = new Thread(futureTask);
        thread.start();
        return futureTask.get();
    }


}

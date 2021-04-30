package com.DefaultCompany.glaucoma_perimetry_system;

import android.os.Handler;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class SendData {

    private int TIME_OUT = 10000; // 超时时间.
    // 连接服务器的url.
    private String url = "http://192.168.1.101:8000/";
    private InputStream is;

    public String selectVal(String id, String eye) {
        //创建StringBuffer对象用于存储所有数据
        StringBuffer sb = new StringBuffer();
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url + "select?Id=" + id + "&eye=" + eye)
                    .openConnection();
            conn.setConnectTimeout(TIME_OUT);
            conn.setRequestMethod("GET"); // GET是大小写敏感的.
            System.out.println(url + "select?Id=" + id + "&eye=" + eye);
            //得到输入流
            is = conn.getInputStream();
            //创建包装流
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            //定义String类型用于储存单行数据
            String line = null;

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (Exception e) {
            return "暂无数据,暂无数据,暂无数据";
        }
        return sb.toString();
    }

    public void insertVal(String id, String L, String N, String P, String eye){
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url + "insert?Id=" + id+ "&eye=" + eye + "&sightingLoseRatio=" + L + "&falseNegativeRatio=" + N + "&falsePositiveRatio=" + P )
                    .openConnection();
            conn.setConnectTimeout(TIME_OUT);
            System.out.println(url + "insert?Id=" + id + "&eye=" + eye + "&sightingLoseRatio=" + L + "&falseNegativeRatio=" + N + "&falsePositiveRatio=" + P);
            conn.setRequestMethod("GET"); // GET是大小写敏感的.
            System.out.println(conn.getResponseCode());
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}

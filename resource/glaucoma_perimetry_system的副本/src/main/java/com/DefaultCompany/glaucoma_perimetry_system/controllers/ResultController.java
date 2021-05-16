package com.DefaultCompany.glaucoma_perimetry_system.controllers;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.DefaultCompany.glaucoma_perimetry_system.entitys.GlobalVal;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static com.unity3d.splash.services.core.properties.ClientProperties.getActivity;

public class ResultController {
    private Context context;
    private DrawView drawView;
    private String resourcePath = "/data/data/com.DefaultCompany.glaucoma_perimetry_system/files";
    public ResultController(Context context) {
        this.context = context;
    }

    public Bitmap getImg(String img) {
        Bitmap bitmap = BitmapFactory.decodeFile(resourcePath + "/" + img);
        return bitmap;
    }

    public void mergeBitmap(GlobalVal globalVal) throws IOException {
        Map<String, Object> map = globalVal.getMap();
        Bitmap firstBitmap = getImg("/左眼GrayScale.png");
        Bitmap secondBitmap = getImg("/右眼GrayScale.png");
        Bitmap thirdBitmap = getImg("/左眼RatioImage.png");
        Bitmap fourthBitmap = getImg("/右眼RatioImage.png");
        Bitmap bitmap;
        int w2 = 1300;
        int h2 = 1400;
        bitmap = Bitmap.createBitmap(w2 * 2, h2 * 2, Bitmap.Config.RGB_565);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(100);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.GRAY);
        if (firstBitmap == null) {
            canvas.drawText("数据缺失", 0, h2 / 6, paint);
        } else {
            canvas.drawBitmap(firstBitmap, -w2 / 2, h2 / 6, null);
            canvas.drawText("左眼", 0, h2, paint);
            canvas.drawText("固视丢失率 " + map.get("sightingLoseRatioL"), 0, h2 / 10, paint);
            canvas.drawText("假阴性率" + map.get("falseNegativeRatioL"), 0, h2 / 5, paint);
            canvas.drawText("假阳性率" + map.get("falsePositiveRatioL"), 0, h2 / 10 * 3, paint);
        }
        if (secondBitmap == null) {
            canvas.drawText("数据缺失", w2, h2 / 6, paint);
        } else {
            canvas.drawBitmap(secondBitmap, w2 / 2, h2 / 6, null);
            canvas.drawText("右眼", w2, h2, paint);
            canvas.drawText("固视丢失率 " + map.get("sightingLoseRatioR"), w2, h2 / 10, paint);
            canvas.drawText("假阴性率" + map.get("falseNegativeRatioR"), w2, h2 / 5, paint);
            canvas.drawText("假阳性率" + map.get("falsePositiveRatioR"), w2, h2 / 10 * 3, paint);
        }
        if (thirdBitmap == null) {
            canvas.drawText("数据缺失", 0, h2 * 7 / 6, paint);
        } else {
            canvas.drawBitmap(thirdBitmap, 0, h2, null);
        }
        if (fourthBitmap == null) {
            canvas.drawText("数据缺失", w2, h2 * 7 / 6, paint);
        } else {
            canvas.drawBitmap(fourthBitmap, w2, h2, null);
        }
        File path = new File(context.getFilesDir(), "shareImage.png");
        OutputStream os = new FileOutputStream(path);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, os);
        os.close();
    }

    public void onPress(GlobalVal globalVal) {

        try {
            mergeBitmap(globalVal);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Intent shareInt = new Intent(Intent.ACTION_SEND);

        shareInt.setType("image/*");

        shareInt.putExtra(Intent.EXTRA_SUBJECT, "选择分享方式");

        File shareFile = new File(context.getFilesDir(), "shareImage.png");
        Uri uri = FileProvider.getUriForFile(
                context, context.getPackageName() + ".fileprovider", shareFile
        );
        shareInt.putExtra(Intent.EXTRA_STREAM, uri);

        shareInt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


        context.startActivity(Intent.createChooser(shareInt, null));

    }

    Map<String, Object> initMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sightingLoseRatioL", "无数据");
        map.put("falseNegativeRatioL", "无数据");
        map.put("falsePositiveRatioL", "无数据");
        map.put("sightingLoseRatioR", "无数据");
        map.put("falseNegativeRatioR", "无数据");
        map.put("falsePositiveRatioR", "无数据");
        return map;
    }

    public GlobalVal updateVal(GlobalVal globalVal) {
        HttpController httpController = new HttpController();
        if (globalVal.getMap().isEmpty()) {
            globalVal.setMap(initMap());
        }
        Map<String, Object> map = globalVal.getMap();
        Log.i("map", map.toString());
        if (globalVal.getId() != null) {
            Log.i("", "id不为空");
            JSONObject leftEyeJson = null;
            String[] leftEyeVal = new String[0];
            try {
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("id",globalVal.getId());
                jsonObject.put("eye","左眼");
                leftEyeJson = httpController.selectVal(jsonObject);

                leftEyeVal = leftEyeJson.get("GrayScale").toString().replace("\"", "").split(",");
                String[] leftEyeRatio = leftEyeJson.get("RatioScale").toString().replace("\"", "").split(",");
                if (leftEyeRatio.length > 1) {
                    Log.i("leftEyeRatio有值", leftEyeRatio.toString());
                    drawView = new DrawView(context, leftEyeRatio);
                }
                if (leftEyeVal.length > 1) {
                    Log.i("leftEyeVal有值", leftEyeVal.toString());
                    map.put("sightingLoseRatioL", leftEyeVal[2]);
                    map.put("falseNegativeRatioL", leftEyeVal[3]);
                    map.put("falsePositiveRatioL", leftEyeVal[4]);

                }
            } catch (Exception e) {
                Log.i("leftEyeVal无值", leftEyeVal.toString());
                map.put("sightingLoseRatioL", "无数据");
                map.put("falseNegativeRatioL", "无数据");
                map.put("falsePositiveRatioL", "无数据");
                e.printStackTrace();
            }
            JSONObject rightEyeJson = null;
            String[] rightEyeVal = new String[0];
            try {
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("id",globalVal.getId());
                jsonObject.put("eye","右眼");
                rightEyeJson = httpController.selectVal(jsonObject);
                rightEyeVal = rightEyeJson.get("GrayScale").toString().replace("\"", "").split(",");
                String[] rightEyeRatio = rightEyeJson.get("RatioScale").toString().replace("\"", "").split(",");


                if (rightEyeVal.length > 1) {
                    Log.i("rightEyeVal有值", rightEyeVal.toString());
                    map.put("sightingLoseRatioR", rightEyeVal[2]);
                    map.put("falseNegativeRatioR", rightEyeVal[3]);
                    map.put("falsePositiveRatioR", rightEyeVal[4]);

                }
                if (rightEyeRatio.length > 1) {
                    Log.i("rightEyeRatio有值", rightEyeRatio.toString());
                    drawView = new DrawView(context, rightEyeRatio);
                }
            } catch (Exception e) {
                Log.i("rightEyeVal无值", rightEyeVal.toString());
                map.put("sightingLoseRatioR", "无数据");
                map.put("falseNegativeRatioR", "无数据");
                map.put("falsePositiveRatioR", "无数据");
                e.printStackTrace();
            }
        }
        globalVal.setMap(map);
        Log.i("globalValMap", globalVal.getMap().toString());
        return globalVal;
    }

}

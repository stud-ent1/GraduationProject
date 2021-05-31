package com.DefaultCompany.glaucoma_perimetry_system.service;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.DefaultCompany.glaucoma_perimetry_system.entitys.GlobalVal;
import com.DefaultCompany.glaucoma_perimetry_system.enums.RequestType;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class ResultDeal {
    private Context context;
    private DrawRatioImage drawRatioImage;
    private DrawGrayImage drawGrayImage;
    private String resourcePath = "/data/data/com.DefaultCompany.glaucoma_perimetry_system/files";

    public ResultDeal(Context context) {
        this.context = context;
    }

    public Bitmap getImg(String img) {
        Bitmap bitmap = BitmapFactory.decodeFile(resourcePath + "/" + img);
        return bitmap;
    }

    public void mergeBitmap(GlobalVal globalVal) throws IOException {
        Map<String, Object> map = globalVal.getMap();
        Bitmap firstBitmap = getImg("/左眼GrayImage.png");
        Bitmap secondBitmap = getImg("/右眼GrayImage.png");
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
            canvas.drawBitmap(firstBitmap, 0, h2 / 6, null);
            canvas.drawText("左眼", 0, h2, paint);
            canvas.drawText("固视丢失率 " + map.get("sightingLoseRatioL"), 0, h2 / 10, paint);
            canvas.drawText("假阴性率" + map.get("falseNegativeRatioL"), 0, h2 / 5, paint);
            canvas.drawText("假阳性率" + map.get("falsePositiveRatioL"), 0, h2 / 10 * 3, paint);
        }
        if (secondBitmap == null) {
            canvas.drawText("数据缺失", w2, h2 / 6, paint);
        } else {
            canvas.drawBitmap(secondBitmap, w2, h2 / 6, null);
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

public void delFile(File directory){
    if (directory != null && directory.exists() && directory.isDirectory()) {
        for (File item : directory.listFiles()) {
            item.delete();
        }
    }
}
    public GlobalVal updateVal(GlobalVal globalVal) {
        HttpService httpController = new HttpService();
        Log.i("globalValMap", globalVal.getMap().toString());
        Map<String, Object> map = globalVal.getMap();
        Log.i("map", map.toString());
        if (globalVal.getId() != null) {
            Log.i("", "id不为空");
            JSONObject leftEyeJson = null;
            String[] leftEyeVal = new String[0];
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", globalVal.getId());
                jsonObject.put("eye", "左眼");
                jsonObject.put("program", globalVal.getProgram());
                leftEyeJson = httpController.execRequest(RequestType.SELECT, jsonObject);

                leftEyeVal = leftEyeJson.get("GrayScale").toString().replace("\"", "").split(",");
                String[] leftEyeRatio = leftEyeJson.get("RatioScale").toString().replace("\"", "").split(",");
                if (leftEyeRatio.length > 1) {
                    Log.i("leftEyeRatio有值", leftEyeRatio.toString());
                    drawRatioImage = new DrawRatioImage(context, globalVal, leftEyeRatio);
                }
                if (leftEyeVal.length > 1) {
                    Log.i("leftEyeVal有值", leftEyeVal.toString());
                    drawGrayImage = new DrawGrayImage(context, globalVal, leftEyeVal);
                    map.put("sightingLoseRatioL", leftEyeVal[2]);
                    map.put("falseNegativeRatioL", leftEyeVal[3]);
                    map.put("falsePositiveRatioL", leftEyeVal[4]);

                }
            } catch (Exception e) {
                Log.i("leftEyeVal无值", leftEyeVal.toString());
                e.printStackTrace();
            }
            JSONObject rightEyeJson = null;
            String[] rightEyeVal = new String[0];
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", globalVal.getId());
                jsonObject.put("eye", "右眼");
                jsonObject.put("program", globalVal.getProgram());
                rightEyeJson = httpController.execRequest(RequestType.SELECT, jsonObject);
                rightEyeVal = rightEyeJson.get("GrayScale").toString().replace("\"", "").split(",");
                String[] rightEyeRatio = rightEyeJson.get("RatioScale").toString().replace("\"", "").split(",");


                if (rightEyeVal.length > 1) {
                    Log.i("rightEyeVal有值", rightEyeVal.toString());
                    drawGrayImage = new DrawGrayImage(context, globalVal, rightEyeVal);
                    map.put("sightingLoseRatioR", rightEyeVal[2]);
                    map.put("falseNegativeRatioR", rightEyeVal[3]);
                    map.put("falsePositiveRatioR", rightEyeVal[4]);

                }
                if (rightEyeRatio.length > 1) {
                    Log.i("rightEyeRatio有值", rightEyeRatio.toString());
                    drawRatioImage = new DrawRatioImage(context, globalVal, rightEyeRatio);
                }
            } catch (Exception e) {
                Log.i("rightEyeVal无值", rightEyeVal.toString());
                e.printStackTrace();
            }
        }else {
            Log.i("", "id为空");
            if(globalVal.getMap().get("grayScaleL")!=null){
                Log.i("grayScaleL不为空", globalVal.getMap().get("grayScaleL").toString());
                drawGrayImage = new DrawGrayImage(context, globalVal, globalVal.getMap().get("grayScaleL").toString().split(","));
            }
            if(globalVal.getMap().get("grayScaleR")!=null) {
                Log.i("grayScaleR不为空", globalVal.getMap().get("grayScaleR").toString());
                drawGrayImage = new DrawGrayImage(context, globalVal, globalVal.getMap().get("grayScaleR").toString().split(","));
            }

        }
        globalVal.setMap(map);
        Log.i("globalValMap", globalVal.getMap().toString());
        return globalVal;
    }

}

package com.DefaultCompany.glaucoma_perimetry_system;

import android.app.Fragment;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class ResultFragment extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private Button share;
    private View resultLayout;
    private CheckBox leftCheck, rightCheck;
    private ImageView imageView;
    private TextView sightingLose, falsePositive, falseNegative, notFind;
    private Map<String, Object> map = new HashMap<String, Object>();
    private String sightingLoseRatioL, falsePositiveRatioL, falseNegativeRatioL, sightingLoseRatioR, falsePositiveRatioR, falseNegativeRatioR;
    private GlobalVal globalVal;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        resultLayout = inflater.inflate(R.layout.fragment_result, container, false);

        return resultLayout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        share = resultLayout.findViewById(R.id.share);
        leftCheck = resultLayout.findViewById(R.id.leftEye);
        rightCheck = resultLayout.findViewById(R.id.rightEye);
        imageView = resultLayout.findViewById(R.id.grayImg);
        sightingLose = resultLayout.findViewById(R.id.sightingLose);
        falsePositive = resultLayout.findViewById(R.id.falsePositive);
        falseNegative = resultLayout.findViewById(R.id.falseNegative);
        notFind = resultLayout.findViewById(R.id.notFind);
        leftCheck.setOnCheckedChangeListener(this);
        rightCheck.setOnCheckedChangeListener(this);
        share.setOnClickListener(this);
        globalVal=(GlobalVal) getActivity().getApplication();
    }

    Bitmap getImg(String img) {
        String resourcePath = "/data/data/com.DefaultCompany.glaucoma_perimetry_system/files";
        Bitmap bitmap = BitmapFactory.decodeFile(resourcePath + "/" + img);
        return bitmap;
    }

    void setImageView(Bitmap bitmap) {
        if (bitmap != null) {
            notFind.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageBitmap(bitmap);
        } else {
            imageView.setVisibility(View.GONE);
            notFind.setVisibility(View.VISIBLE);
        }

    }


    void mergeBitmap() throws IOException {
        String resourcePath = "/data/data/com.DefaultCompany.glaucoma_perimetry_system/files";
        Bitmap firstBitmap = BitmapFactory.decodeFile(resourcePath + "/左眼GrayScale.png");
        Bitmap secondBitmap = BitmapFactory.decodeFile(resourcePath + "/右眼GrayScale.png");
        Bitmap bitmap;
        int w1 = firstBitmap.getWidth();
        int h1 = firstBitmap.getHeight();
        int w2 = secondBitmap.getWidth();
        int h2 = secondBitmap.getHeight();
        bitmap = Bitmap.createBitmap(w1 + w2, h1, firstBitmap.getConfig());
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(100);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawRGB(255, 255, 255);
        canvas.drawBitmap(firstBitmap, new Matrix(), null);
        canvas.drawText("左眼", w1 / 3, h1 / 6 * 5, paint);
        canvas.drawText("固视丢失率 " +  sightingLoseRatioL, w1 / 6, h1 / 10, paint);
        canvas.drawText("假阴性率" +  falseNegativeRatioL , w1 / 6, h1 / 5, paint);
        canvas.drawText("假阳性率" +  falsePositiveRatioL, w1 / 6, h1 / 10 * 3, paint);
        canvas.drawBitmap(secondBitmap, w1, 0, null);
        canvas.drawText("右眼", w1 + w2 / 3, h2 / 6 * 5, paint);
        canvas.drawText("固视丢失率 " +  sightingLoseRatioR , w1 + w2 / 6, h2 / 10, paint);
        canvas.drawText("假阴性率" + falseNegativeRatioR , w1 + w2 / 6, h2 / 5, paint);
        canvas.drawText("假阳性率" + falsePositiveRatioR , w1 + w2 / 6, h2 / 10 * 3, paint);
        File path = new File(getActivity().getFilesDir(), "shareImage.png");
        OutputStream os = new FileOutputStream(path);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, os);
        os.close();
    }

    void onPress() {


        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Intent shareInt = new Intent(Intent.ACTION_SEND);

        shareInt.setType("image/*");

        shareInt.putExtra(Intent.EXTRA_SUBJECT, "选择分享方式");
        // shareInt.putExtra(Intent.EXTRA_TEXT, msg); // 要分享的内容

        File shareFile = new File(getActivity().getFilesDir(), "shareImage.png");
        Uri uri = FileProvider.getUriForFile(
                getActivity(), getActivity().getPackageName() + ".fileprovider", shareFile
        );
        shareInt.putExtra(Intent.EXTRA_STREAM, uri);

        shareInt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


        startActivity(Intent.createChooser(shareInt, getActivity().getTitle()));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.share:
                    onPress();
                break;
            default:
                break;
        }
    }

    void updateVal(GlobalVal globalVal) {
        SendData sendData = new SendData();
        Map<String, Object> map = globalVal.getMap();
        if (globalVal.getId() != null) {
            System.out.println("id不为空");
            String[] leftEyeVal = sendData.selectVal(globalVal.getId(), "左眼").replace("\"","").split(",");
            String[] rightEyeVal = sendData.selectVal(globalVal.getId(), "右眼").replace("\"","").split(",");
            if (leftEyeVal.length==5) {
                sightingLoseRatioL = leftEyeVal[2];
                falseNegativeRatioL = leftEyeVal[3];
                falsePositiveRatioL = leftEyeVal[4];
            }
            if (rightEyeVal.length==5) {
                sightingLoseRatioR = rightEyeVal[2];
                falseNegativeRatioR = rightEyeVal[3];
                falsePositiveRatioR = rightEyeVal[4];
            }

        } else if (map != null) {
            System.out.println("map不为空");
            try {
                sightingLoseRatioL = map.get("sightingLoseRatioL").toString();
                falseNegativeRatioL = map.get("falseNegativeRatioL").toString();
                falsePositiveRatioL = map.get("falsePositiveRatioL").toString();
            } catch (NullPointerException e) {
                sightingLoseRatioL = "无数据";
                falseNegativeRatioL = "无数据";
                falsePositiveRatioL = "无数据";
            }
            try {
                sightingLoseRatioR = map.get("sightingLoseRatioR").toString();
                falseNegativeRatioR = map.get("falseNegativeRatioR").toString();
                falsePositiveRatioR = map.get("falsePositiveRatioR").toString();
            } catch (NullPointerException e) {
                sightingLoseRatioR = "无数据";
                falseNegativeRatioR = "无数据";
                falsePositiveRatioR = "无数据";
            }
        } else {
            System.out.println("获取不到数据");
            sightingLoseRatioL = "无数据";
            falseNegativeRatioL = "无数据";
            falsePositiveRatioL = "无数据";
            sightingLoseRatioR = "无数据";
            falseNegativeRatioR = "无数据";
            falsePositiveRatioR = "无数据";
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        new Thread(new Runnable(){
            @Override
            public void run() {
                updateVal(globalVal);
            }
        }).start();
        switch (buttonView.getId()) {
            case R.id.leftEye:
                if (isChecked) {
                    if (rightCheck.isChecked()) {
                        sightingLose.setText("左眼：" + sightingLoseRatioL + "右眼：" + sightingLoseRatioR);
                        falsePositive.setText("左眼：" + falsePositiveRatioL + "右眼：" + falsePositiveRatioR);
                        falseNegative.setText("左眼：" + falseNegativeRatioL + "右眼：" + falseNegativeRatioR);
                        try {
                            mergeBitmap();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        setImageView(getImg("shareImage.png"));
                    } else {
                        sightingLose.setText("左眼：" + sightingLoseRatioL);
                        falsePositive.setText("左眼：" + falsePositiveRatioL);
                        falseNegative.setText("左眼：" + falseNegativeRatioL);
                        setImageView(getImg("左眼GrayScale.png"));
                    }
                } else {
                    if (rightCheck.isChecked()) {
                        sightingLose.setText("右眼：" + sightingLoseRatioR);
                        falsePositive.setText("右眼：" + falsePositiveRatioR);
                        falseNegative.setText("右眼：" + falseNegativeRatioR);
                        setImageView(getImg("右眼GrayScale.png"));
                    } else {
                        sightingLose.setText("无数据");
                        falsePositive.setText("无数据");
                        falseNegative.setText("无数据");
                        setImageView(null);
                    }
                }
                break;
            case R.id.rightEye:
                if (isChecked) {


                    if (leftCheck.isChecked()) {
                        sightingLose.setText("左眼：" + sightingLoseRatioL + "右眼：" + sightingLoseRatioR);
                        falsePositive.setText("左眼：" + falsePositiveRatioL + "右眼：" + falsePositiveRatioR);
                        falseNegative.setText("左眼：" + falseNegativeRatioL + "右眼：" + falseNegativeRatioR);
                        try {
                            mergeBitmap();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        setImageView(getImg("shareImage.png"));
                    } else {
                        sightingLose.setText("右眼：" + sightingLoseRatioR);
                        falsePositive.setText("右眼：" + falsePositiveRatioR);
                        falseNegative.setText("右眼：" + falseNegativeRatioR);
                        setImageView(getImg("右眼GrayScale.png"));
                    }
                } else {
                    if (leftCheck.isChecked()) {
                        sightingLose.setText("左眼：" + sightingLoseRatioL);
                        falsePositive.setText("左眼：" + falsePositiveRatioL);
                        falseNegative.setText("左眼：" + falseNegativeRatioL);
                        setImageView(getImg("左眼GrayScale.png"));
                    } else {
                        sightingLose.setText("无数据");
                        falsePositive.setText("无数据");
                        falseNegative.setText("无数据");
                        setImageView(null);
                    }
                }
                break;


            default:
                break;
        }
    }
}



package com.DefaultCompany.glaucoma_perimetry_system.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import com.DefaultCompany.glaucoma_perimetry_system.entitys.GlobalVal;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class DrawRatioImage extends View {

    private boolean[][] timeSightingPostDisplayStatus;
    private boolean[] sightingPostDisplayStatus;
    private Bitmap bitmap;
    private String[] arr;
    private String program;
    private int width, height;
    private  GlobalVal globalVal;
    public DrawRatioImage(Context context, GlobalVal globalVal, String[] arr) {
        super(context);
        this.arr = arr;
        this.globalVal=globalVal;
        this.program = globalVal.getProgram();
        bitmap = Bitmap.createBitmap(1300, 1400, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        draw(canvas);
        File path = new File(context.getFilesDir(), arr[0] + "RatioImage.png");
        try {
            OutputStream os = new FileOutputStream(path);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, os);

            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawColor(Color.GRAY);
        Paint p = new Paint();
        p.setColor(Color.BLACK);
        p.setStyle(Paint.Style.FILL);
        p.setStrokeWidth(4);
        canvas.translate(100, 300);
        if (program.equals("24-2(适用于前期)")) {
            timeSightingPostDisplayStatus = globalVal.getEarlySightingPostDisplayStatus();
            width = 900;
            height = 800;
        } else {
            timeSightingPostDisplayStatus = globalVal.getLateSightingPostDisplayStatus();
            width = 1000;
            height = 1000;
        }
        if (arr[0].equals("左眼")) {
            sightingPostDisplayStatus = timeSightingPostDisplayStatus[0];
        } else {
            sightingPostDisplayStatus = timeSightingPostDisplayStatus[1];
        }
        int count = 0;
        for (int i = 0; i < height; i += 100) {
            for (int j = 0; j < width; j += 100) {
                canvas.translate(j, i);
                if (sightingPostDisplayStatus[count]) {


                    if (Double.parseDouble(arr[count]) >= 0.6) {

                        canvas.drawPoints(small(), p);//画多个点
                    } else if (Double.parseDouble(arr[count]) < 0.6 && Double.parseDouble(arr[count]) >= 0.3) {

                        canvas.drawPoints(middle(), p);//画多个点
                    } else if (Double.parseDouble(arr[count]) < 0.3) {

                        canvas.drawPoints(big(), p);//画多个点
                    }
                }
                canvas.translate(-j, -i);
                count++;
            }
        }
        addSign(canvas, p);

    }

    float[] small() {
        int w = 100;
        int h = 100;
        float[] arr = new float[200];
        int count = 0;
        for (int i = 0; i < w; i += 10) {
            for (int j = 0; j < h; j += 10) {
                arr[count] = i;
                arr[count + 1] = j;
                count = count + 2;
            }
        }
        return arr;
    }

    float[] middle() {
        int w = 100;
        int h = 100;
        float[] arr = new float[50];
        int count = 0;
        for (int i = 0; i < w; i += 20) {
            for (int j = 0; j < h; j += 20) {
                arr[count] = i;
                arr[count + 1] = j;
                count = count + 2;
            }
        }
        return arr;
    }

    float[] big() {
        int w = 100;
        int h = 100;
        float[] arr = new float[8];
        int count = 0;
        for (int i = 0; i < w; i += 50) {
            for (int j = 0; j < h; j += 50) {
                arr[count] = i;
                arr[count + 1] = j;
                count = count + 2;
            }
        }
        return arr;
    }

    void addSign(Canvas canvas, Paint p) {

        p.setTextSize(50);
        canvas.translate(900, 800);
        canvas.drawPoints(small(), p);//画多个点
        canvas.drawText(">80% ", 100, 50, p);
        canvas.translate(0, 100);
        canvas.drawPoints(middle(), p);//画多个点
        canvas.drawText(">40% ", 100, 50, p);
        canvas.translate(0, 100);
        canvas.drawPoints(big(), p);//画多个点
        canvas.drawText(">20% ", 100, 50, p);
    }
}

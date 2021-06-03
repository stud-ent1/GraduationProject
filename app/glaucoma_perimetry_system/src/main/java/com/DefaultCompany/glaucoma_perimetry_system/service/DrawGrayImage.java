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

public class DrawGrayImage extends View{

        private boolean[][] timeSightingPostDisplayStatus;
        private boolean[] sightingPostDisplayStatus;
        private Bitmap bitmap;
        private String[] arr;
        private String program;
        private int width, height;
        private GlobalVal globalVal;
        public DrawGrayImage(Context context, GlobalVal globalVal, String[] arr) {
            super(context);
            this.arr = arr;
            this.globalVal=globalVal;
            this.program = globalVal.getProgram();
            bitmap = Bitmap.createBitmap(1300, 1400, Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(bitmap);
            draw(canvas);
            File path = new File(context.getFilesDir(), arr[1] + "GrayImage.png");
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
            canvas.drawColor(Color.parseColor("#F5F6CE"));
            Paint p = new Paint();

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
            if (arr[1].equals("左眼")) {
                sightingPostDisplayStatus = timeSightingPostDisplayStatus[0];
            } else {
                sightingPostDisplayStatus = timeSightingPostDisplayStatus[1];
            }
            int count = 0;
            for (int i = 0; i < height; i += 100) {
                for (int j = 0; j < width; j += 100) {
                    canvas.translate(j, i);
                    if (sightingPostDisplayStatus[count]) {
                       float color= Float.parseFloat(arr[count+5]);
                        if(color>=3){
                            p.setColor(Color.parseColor("#FAFAFA"));
                        }else if(color>2&&color<3){
                            p.setColor(Color.parseColor("#D8D8D8"));
                        }else if(color==2){
                            p.setColor(Color.parseColor("#848484"));
                        }else if(color>1&&color<2){
                            p.setColor(Color.parseColor("#585858"));
                        }else if(color==1){
                            p.setColor(Color.parseColor("#2E2E2E"));
                        }


                        canvas.drawRect(0,0,100,100,p);
                    }
                    canvas.translate(-j, -i);
                    count++;
                }
            }
        }

    }


package com.DefaultCompany.glaucoma_perimetry_system;

import com.unity3d.player.*;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class UnityPlayerActivity extends Activity
{
    protected UnityPlayer mUnityPlayer; // don't change the name of this variable; referenced from native code

    // Override this in your custom UnityPlayerActivity to tweak the command line arguments passed to the Unity Android Player
    // The command line arguments are passed as a string, separated by spaces
    // UnityPlayerActivity calls this from 'onCreate'
    // Supported: -force-gles20, -force-gles30, -force-gles31, -force-gles31aep, -force-gles32, -force-gles, -force-vulkan
    // See https://docs.unity3d.com/Manual/CommandLineArguments.html
    // @param cmdLine the current command line arguments, may be null
    // @return the modified command line string or null
    protected String updateUnityCommandLineArguments(String cmdLine)
    {
        return cmdLine;
    }

    // Setup activity layout
    @Override protected void onCreate(Bundle savedInstanceState)
    {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        String cmdLine = updateUnityCommandLineArguments(getIntent().getStringExtra("unity"));
        getIntent().putExtra("unity", cmdLine);

        mUnityPlayer = new UnityPlayer(this);
        setContentView(mUnityPlayer);
        mUnityPlayer.requestFocus();
    }
    public void onPress(String msg) throws IOException {

        mergeBitmap("/data/data/com.DefaultCompany.glaucoma_perimetry_system/files");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Intent shareInt = new Intent(Intent.ACTION_SEND);

        shareInt.setType("image/*");

        shareInt.putExtra(Intent.EXTRA_SUBJECT, "选择分享方式");
        // shareInt.putExtra(Intent.EXTRA_TEXT, msg); // 要分享的内容

        File shareFile = new File(getCacheDir(),"shareImage.png");
        Uri uri= FileProvider.getUriForFile(
                this,UnityPlayerActivity.this.getPackageName()+ ".fileprovider",shareFile
        );
        shareInt.putExtra(Intent.EXTRA_STREAM,uri);

        shareInt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);



        startActivity(Intent.createChooser(shareInt, getTitle()));

    }
    private void mergeBitmap(String resourcePath) throws IOException {
        Bitmap firstBitmap = BitmapFactory.decodeFile(resourcePath+"/左眼GrayScale.png");
        System.out.println(firstBitmap);
        Bitmap secondBitmap = BitmapFactory.decodeFile(resourcePath+"/右眼GrayScale.png");
        System.out.println(secondBitmap);
        Bitmap bitmap;
        if(firstBitmap!=null&&secondBitmap!=null){
        int w1 = firstBitmap.getWidth();
        int h1 = firstBitmap.getHeight();
        int w2 = secondBitmap.getWidth();
        int h2 = secondBitmap.getHeight();
        bitmap= Bitmap.createBitmap(w1+w2, h1 , firstBitmap.getConfig());
        Paint paint=new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(100);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawRGB(255, 255, 255);
        canvas.drawBitmap(firstBitmap, new Matrix(), null);
        canvas.drawText("左眼",w1/3, h1/6*5, paint);
        canvas.drawBitmap(secondBitmap, w1, 0, null);
        canvas.drawText("右眼", w1+w2/2, h2/6*5, paint);
        }
        else {
            bitmap=(firstBitmap!=null)?firstBitmap:secondBitmap;
            int w = bitmap.getWidth();
            int h = bitmap.getHeight();
            bitmap= Bitmap.createBitmap(w,h , bitmap.getConfig());
            String eye=(firstBitmap!=null)?"左眼":"右眼";
            Paint paint=new Paint();
            paint.setStyle(Paint.Style.FILL);
            paint.setTextSize(100);
            Canvas canvas = new Canvas(bitmap);
            canvas.drawRGB(255, 255, 255);
            canvas.drawBitmap(firstBitmap, new Matrix(), null);
            canvas.drawText(eye,w/3, h/6*5, paint);
        }
        File path = new File(getCacheDir(),"shareImage.png");
        OutputStream os = new FileOutputStream(path);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, os);
        os.close();
    }
    @Override protected void onNewIntent(Intent intent)
    {
        // To support deep linking, we need to make sure that the client can get access to
        // the last sent intent. The clients access this through a JNI api that allows them
        // to get the intent set on launch. To update that after launch we have to manually
        // replace the intent with the one caught here.
        setIntent(intent);
    }

    // Quit Unity
    @Override protected void onDestroy ()
    {
        mUnityPlayer.destroy();
        super.onDestroy();
    }

    // Pause Unity
    @Override protected void onPause()
    {
        super.onPause();
        mUnityPlayer.pause();
    }

    // Resume Unity
    @Override protected void onResume()
    {
        super.onResume();
        mUnityPlayer.resume();
    }

    @Override protected void onStart()
    {
        super.onStart();
        mUnityPlayer.start();
    }

    @Override protected void onStop()
    {
        super.onStop();
        mUnityPlayer.stop();
    }

    // Low Memory Unity
    @Override public void onLowMemory()
    {
        super.onLowMemory();
        mUnityPlayer.lowMemory();
    }

    // Trim Memory Unity
    @Override public void onTrimMemory(int level)
    {
        super.onTrimMemory(level);
        if (level == TRIM_MEMORY_RUNNING_CRITICAL)
        {
            mUnityPlayer.lowMemory();
        }
    }

    // This ensures the layout will be correct.
    @Override public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        mUnityPlayer.configurationChanged(newConfig);
    }

    // Notify Unity of the focus change.
    @Override public void onWindowFocusChanged(boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);
        mUnityPlayer.windowFocusChanged(hasFocus);
    }

    // For some reason the multiple keyevent type is not supported by the ndk.
    // Force event injection by overriding dispatchKeyEvent().
    @Override public boolean dispatchKeyEvent(KeyEvent event)
    {
        if (event.getAction() == KeyEvent.ACTION_MULTIPLE)
            return mUnityPlayer.injectEvent(event);
        return super.dispatchKeyEvent(event);
    }

    // Pass any events not handled by (unfocused) views straight to UnityPlayer
    @Override public boolean onKeyUp(int keyCode, KeyEvent event)     { return mUnityPlayer.injectEvent(event); }
    @Override public boolean onKeyDown(int keyCode, KeyEvent event)   {
        if(KeyEvent.KEYCODE_HEADSETHOOK == keyCode){
            UnityPlayer.UnitySendMessage("LeftEyeButton", "ifStartCheck","");
        }
        if(KeyEvent.ACTION_UP==keyCode){
            UnityPlayer.UnitySendMessage("Background", "ActionUp","");
        }
        return mUnityPlayer.injectEvent(event);
    }
    @Override public boolean onTouchEvent(MotionEvent event)          { return mUnityPlayer.injectEvent(event); }
    /*API12*/ public boolean onGenericMotionEvent(MotionEvent event)  { return mUnityPlayer.injectEvent(event); }
}
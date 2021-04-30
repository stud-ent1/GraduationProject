package com.DefaultCompany.glaucoma_perimetry_system;

import com.unity3d.player.*;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UnityPlayerActivity extends Activity
{
    protected UnityPlayer mUnityPlayer; // don't change the name of this variable; referenced from native code
    private Map<String,Object> map=new HashMap<String, Object>();
    private GlobalVal globalVal;
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
        globalVal=(GlobalVal) getApplication();
        mUnityPlayer = new UnityPlayer(this);
        setContentView(mUnityPlayer);
        mUnityPlayer.requestFocus();
    }
    void updateMap(float sightingLose, float falseNegative, float falsePositive, String eye) throws IOException {
        System.out.println("插入数据");
        if (eye.equals("左眼")) {
            map.put("sightingLoseRatioL", sightingLose/108);
            map.put("falseNegativeRatioL", falseNegative/108);
            map.put("falsePositiveRatioL", falsePositive/108);
        } else {
            map.put("sightingLoseRatioR", sightingLose/108);
            map.put("falseNegativeRatioR", falseNegative/108);
            map.put("falsePositiveRatioR", falsePositive/108);
        }
        globalVal.setMap(map);
        if(globalVal.getId()!=null){
            new Thread(new Runnable(){
                @Override
                public void run() {
                    SendData sendData=new SendData();
                    sendData.insertVal(globalVal.getId(),String.valueOf(sightingLose/108),String.valueOf(falseNegative/108),String.valueOf(falsePositive/108),eye);
                }
            }).start();

        }

    }
    void jumpToMain(){
        Intent i = new Intent(UnityPlayerActivity.this,MainActivity.class); //切换窗口
        startActivity(i);
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
        if(keyCode==KeyEvent.KEYCODE_HEADSETHOOK){
            Intent intent=getIntent();
            String eye=intent.getStringExtra("eye");
                UnityPlayer.UnitySendMessage("MainCamera", "chooseEye", eye);
                UnityPlayer.UnitySendMessage("MainCamera", "ifStartCheck","");
        }
        if(keyCode==KeyEvent.KEYCODE_VOLUME_UP){
            UnityPlayer.UnitySendMessage("MainCamera", "quicken","");
        }
        return mUnityPlayer.injectEvent(event); }
    @Override public boolean onTouchEvent(MotionEvent event)          { return mUnityPlayer.injectEvent(event); }
    /*API12*/ public boolean onGenericMotionEvent(MotionEvent event)  { return mUnityPlayer.injectEvent(event); }
}

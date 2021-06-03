package com.DefaultCompany.glaucoma_perimetry_system.activity;

import com.DefaultCompany.glaucoma_perimetry_system.service.UnityPlayerDeal;
import com.DefaultCompany.glaucoma_perimetry_system.entitys.GlobalVal;
import com.unity3d.player.*;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.Toast;

import java.io.IOException;

public class UnityPlayerActivity extends Activity {
    protected UnityPlayer mUnityPlayer; // don't change the name of this variable; referenced from native code
    private GlobalVal globalVal;
    private SensorManager mSensorManager;
    private Sensor accelerometer;
    private Sensor magnetic;
    private float[] accelerometerValues = new float[3];
    private float[] magneticFieldValues = new float[3];
    private boolean ifExistCamera;
    private UnityPlayerDeal unityPlayerController;
    private static final int UPDATE_INTERVAL = 200;
    private long mLastUpdateTime;
    private float mLastX, mLastY, mLastZ;
    // Override this in your custom UnityPlayerActivity to tweak the command line arguments passed to the Unity Android Player
    // The command line arguments are passed as a string, separated by spaces
    // UnityPlayerActivity calls this from 'onCreate'
    // Supported: -force-gles20, -force-gles30, -force-gles31, -force-gles31aep, -force-gles32, -force-gles, -force-vulkan
    // See https://docs.unity3d.com/Manual/CommandLineArguments.html
    // @param cmdLine the current command line arguments, may be null
    // @return the modified command line string or null
    protected String updateUnityCommandLineArguments(String cmdLine) {
        return cmdLine;
    }

    // Setup activity layout
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        String cmdLine = updateUnityCommandLineArguments(getIntent().getStringExtra("unity"));
        getIntent().putExtra("unity", cmdLine);
        globalVal = (GlobalVal) getApplication();
        mUnityPlayer = new UnityPlayer(this);
        setContentView(mUnityPlayer);
        mUnityPlayer.requestFocus();
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetic = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        unityPlayerController=new UnityPlayerDeal(this);

    }

    private SensorEventListener mSensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {

            if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                magneticFieldValues = sensorEvent.values;
            }
            if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                accelerometerValues = sensorEvent.values;
                float[] values = new float[3];
                float[] R = new float[9];
                //为什么使用加速度传感器与地磁传感器
                //加速度的方向竖直向下，而不考虑环境影响，磁场的方向是水平朝南北，这样对加速度和磁场坐差乘，就能得到一个水平东西方向的向量，那么就能得到一个三位的向量买就可以用来计算设备的方向
                SensorManager.getRotationMatrix(R, null, accelerometerValues,
                        magneticFieldValues);
                SensorManager.getOrientation(R, values);

                long currentTime = System.currentTimeMillis();
                long diffTime = currentTime - mLastUpdateTime;
                if (diffTime < UPDATE_INTERVAL) {
                    return;
                }
                mLastUpdateTime = currentTime;
                float z = (float) Math.toDegrees(values[0]);

                float x = (float) Math.toDegrees(values[1]);

                float y = (float) Math.toDegrees(values[2]);

                float deltaX = x - mLastX;
                float deltaY = y - mLastY;
                float deltaZ = z - mLastZ;
                mLastX = x;
                mLastY = y;
                mLastZ = z;
                if(deltaY>30&&deltaX<5&&ifExistCamera){
                        Log.i("","检测到点头");
                        if (globalVal.isIfStartCheck()){
                            Intent intent = getIntent();
                            String eye = intent.getStringExtra("eye");
                            String program = intent.getStringExtra("program");
                            UnityPlayer.UnitySendMessage("MainCamera", "chooseProgram", program);
                            UnityPlayer.UnitySendMessage("MainCamera", "chooseEye", eye);
                            UnityPlayer.UnitySendMessage("MainCamera", "ifStartCheck", "");
                            globalVal.setIfStartCheck(false);
                        }else {
                            UnityPlayer.UnitySendMessage("Background", "ActionUp", "");
                        }
                }
//                if(deltaX<5&&ifExistCamera&&deltaY<5&&deltaZ>30){
//                        Log.i("","检测到摇头");
//                        UnityPlayer.UnitySendMessage("MainCamera", "onPause", "");
//                }

            }


        }


        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };
    void setCameraStatus(){
        ifExistCamera=true;
    }
    void updateMap(String str) throws IOException {
  globalVal=unityPlayerController.updateGlobalVal(str,globalVal);
    }

    void jumpToMain() {
        globalVal.setIfStartCheck(true);
        Intent i = new Intent(UnityPlayerActivity.this, MainActivity.class); //切换窗口
        startActivity(i);
        mSensorManager.unregisterListener(mSensorEventListener);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // To support deep linking, we need to make sure that the client can get access to
        // the last sent intent. The clients access this through a JNI api that allows them
        // to get the intent set on launch. To update that after launch we have to manually
        // replace the intent with the one caught here.
        setIntent(intent);
    }

    // Quit Unity
    @Override
    protected void onDestroy() {
        mUnityPlayer.destroy();
        super.onDestroy();
    }

    // Pause Unity
    @Override
    protected void onPause() {
        super.onPause();
        mUnityPlayer.pause();
    }

    // Resume Unity
    @Override
    protected void onResume() {
        super.onResume();
        globalVal.setIfStartCheck(true);
        mSensorManager.registerListener(mSensorEventListener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(mSensorEventListener, magnetic, SensorManager.SENSOR_DELAY_NORMAL);
        mUnityPlayer.resume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mUnityPlayer.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mUnityPlayer.stop();
    }

    // Low Memory Unity
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mUnityPlayer.lowMemory();
    }

    // Trim Memory Unity
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (level == TRIM_MEMORY_RUNNING_CRITICAL) {
            mUnityPlayer.lowMemory();
        }
    }

    // This ensures the layout will be correct.
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mUnityPlayer.configurationChanged(newConfig);
    }

    // Notify Unity of the focus change.
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        mUnityPlayer.windowFocusChanged(hasFocus);
    }

    // For some reason the multiple keyevent type is not supported by the ndk.
    // Force event injection by overriding dispatchKeyEvent().
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_MULTIPLE)
            return mUnityPlayer.injectEvent(event);
        return super.dispatchKeyEvent(event);
    }

    // Pass any events not handled by (unfocused) views straight to UnityPlayer
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return mUnityPlayer.injectEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            UnityPlayer.UnitySendMessage("Background", "ActionUp", "");
            return true;
        }
//        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
//            UnityPlayer.UnitySendMessage("MainCamera", "quicken", "");
//            return true;
//        }
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            UnityPlayer.UnitySendMessage("MainCamera", "onPause", "");
            return true;
        }
        if(keyCode==KeyEvent.KEYCODE_BACK){
            UnityPlayer.UnitySendMessage("MainCamera", "restart", "");
            return true;
        }
        return mUnityPlayer.injectEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mUnityPlayer.injectEvent(event);
    }

    /*API12*/
    public boolean onGenericMotionEvent(MotionEvent event) {
        return mUnityPlayer.injectEvent(event);
    }
}

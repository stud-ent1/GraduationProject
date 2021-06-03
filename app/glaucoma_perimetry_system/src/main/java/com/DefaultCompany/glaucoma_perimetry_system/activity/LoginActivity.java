package com.DefaultCompany.glaucoma_perimetry_system.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.DefaultCompany.glaucoma_perimetry_system.R;
import com.DefaultCompany.glaucoma_perimetry_system.enums.AlterType;
import com.DefaultCompany.glaucoma_perimetry_system.service.Alter;
import com.DefaultCompany.glaucoma_perimetry_system.service.HttpService;
import com.DefaultCompany.glaucoma_perimetry_system.service.CheckId;
import com.DefaultCompany.glaucoma_perimetry_system.entitys.GlobalVal;
import com.DefaultCompany.glaucoma_perimetry_system.enums.RequestType;
import com.DefaultCompany.glaucoma_perimetry_system.listeners.OnSwipeTouchListener;
import com.DefaultCompany.glaucoma_perimetry_system.ui.helper.LoginComponent;
import com.DefaultCompany.glaucoma_perimetry_system.ui.helper.LoginCompontent2;
import com.binioter.guideview.Guide;
import com.binioter.guideview.GuideBuilder;

import org.json.JSONObject;


public class LoginActivity extends Activity implements View.OnClickListener {
    private GlobalVal globalVal;
    private CheckId checkId = new CheckId();
    private HttpService httpService = new HttpService();
    private EditText Pid, passWord;
    private Button noSend, sendId;
    private Alter alter = new Alter(this);
    ImageView imageView;
    TextView textView;
    int count = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        sendId = findViewById(R.id.send_id);
        noSend = findViewById(R.id.no_send);
        Button registerId = findViewById(R.id.register_id);
        Pid = findViewById(R.id.Pid);
        passWord = findViewById(R.id.passWord);
        sendId.setOnClickListener(this);
        noSend.setOnClickListener(this);
        alter.getAlter(AlterType.INFO);
        noSend.post(new Runnable() {
            @Override
            public void run() {
                showGuideView();
            }
        });
        registerId.setOnClickListener(this);
        globalVal = (GlobalVal) getApplication();
        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);
        imageView.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext()) {
            public void onSwipeTop() {
            }

            public void onSwipeRight() {
                if (count == 0) {
                    imageView.setImageResource(R.drawable.good_night_img);
                    textView.setText("Night");
                    count = 1;
                } else {
                    imageView.setImageResource(R.drawable.good_morning_img);
                    textView.setText("Morning");
                    count = 0;
                }
            }

            public void onSwipeLeft() {
                if (count == 0) {
                    imageView.setImageResource(R.drawable.good_night_img);
                    textView.setText("Night");
                    count = 1;
                } else {
                    imageView.setImageResource(R.drawable.good_morning_img);
                    textView.setText("Morning");
                    count = 0;
                }
            }

            public void onSwipeBottom() {
            }

        });
    }

    private void showGuideView() {
        GuideBuilder builder = new GuideBuilder();
        builder.setTargetView(noSend)
                .setAlpha(150)
                .setHighTargetCorner(20)
                .setHighTargetPadding(10);
        builder.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() {
            @Override
            public void onShown() {
            }

            @Override
            public void onDismiss() {
                showGuideView2();
            }
        });

        builder.addComponent(new LoginComponent());
        Guide guide = builder.createGuide();
        guide.show(LoginActivity.this);
    }

    public void showGuideView2() {
        final GuideBuilder builder1 = new GuideBuilder();
        builder1.setTargetView(sendId)
                .setAlpha(150)
                .setHighTargetCorner(20)
                .setHighTargetPadding(10);
        builder1.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() {
            @Override
            public void onShown() {
            }

            @Override
            public void onDismiss() {

            }
        });

        builder1.addComponent(new LoginCompontent2());
        Guide guide = builder1.createGuide();
        guide.show(LoginActivity.this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            finish();
            System.exit(0);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        try {
            Intent intent = new Intent(this, MainActivity.class); //切换窗口
            String id = Pid.getText().toString();
            String pw = passWord.getText().toString();
            switch (v.getId()) {
                case R.id.send_id:

                    if (checkId.editTextCheck(id)) {
                        Log.i("用户id", id);
                        globalVal.setId(id);
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("id", id);
                        jsonObject.put("passWord", pw);
                        if (httpService.execRequest(RequestType.CHECK, jsonObject).get("status").equals(false)) {
                            alter.getAlter(AlterType.idError);
                            return;
                        }
                    } else {
                        alter.getAlter(AlterType.idIllegal);
                        return;
                    }
                    break;
                case R.id.no_send:
                    globalVal.setId(null);
                    break;
                case R.id.register_id:
                    if (checkId.editTextCheck(id)) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id", id);
                    jsonObject.put("passWord", pw);
                    if (httpService.execRequest(RequestType.REGISTER, jsonObject).get("status").equals("fail")) {
                        alter.getAlter(AlterType.idRegister);
                        return;
                    }
                    } else {
                        alter.getAlter(AlterType.idIllegal);
                        return;
                    }
                    break;
                default:
                    break;
            }
            startActivity(intent);
        } catch (Exception e) {
            alter.getAlter(AlterType.notConnect);
            e.printStackTrace();
        }
    }
}

package com.DefaultCompany.glaucoma_perimetry_system.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.DefaultCompany.glaucoma_perimetry_system.R;
import com.DefaultCompany.glaucoma_perimetry_system.controllers.HttpController;
import com.DefaultCompany.glaucoma_perimetry_system.controllers.LoginController;
import com.DefaultCompany.glaucoma_perimetry_system.entitys.GlobalVal;
import com.DefaultCompany.glaucoma_perimetry_system.enums.RequestType;
import com.DefaultCompany.glaucoma_perimetry_system.listeners.OnSwipeTouchListener;

import org.json.JSONException;
import org.json.JSONObject;

import static java.nio.file.Paths.get;

public class LoginActivity extends Activity implements View.OnClickListener {
    private GlobalVal globalVal;
    private LoginController loginController = new LoginController();
    private HttpController httpController = new HttpController();
    private EditText Pid, passWord;
    private TextView alterText;
    ImageView imageView;
    TextView textView;
    int count = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        Button sendId = findViewById(R.id.send_id);
        Button noSend = findViewById(R.id.no_send);
        Button registerId = findViewById(R.id.register_id);
        Pid = findViewById(R.id.Pid);
        passWord = findViewById(R.id.passWord);
        alterText = findViewById(R.id.alterText);
        sendId.setOnClickListener(this);
        noSend.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        try {
            Intent intent = new Intent(this, MainActivity.class); //切换窗口
            String id = Pid.getText().toString();
            String pw = passWord.getText().toString();
            switch (v.getId()) {
                case R.id.send_id:

                    if (loginController.editTextCheck(id)) {
                        Log.i("用户id", id);
                        globalVal.setId(id);
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("id",id);
                        jsonObject.put("passWord",pw);
                        if (httpController.execRequest(RequestType.CHECK,jsonObject).get("status").equals(false)) {
                            alterText.setVisibility(View.VISIBLE);
                            alterText.setText("用户名或者密码不正确");
                            return;
                        }
                        alterText.setVisibility(View.GONE);
                    } else {
                        alterText.setVisibility(View.VISIBLE);
                        alterText.setText("id不合法,必须是身份证，电话号码，邮箱等可以唯一标识用户的值");
                        return;
                    }
                    break;
                case R.id.no_send:
                    globalVal.setId(null);
                    break;
                case R.id.register_id:
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id", id);
                    jsonObject.put("passWord", pw);
                    if(httpController.execRequest(RequestType.REGISTER, jsonObject).get("status").equals("fail")){
                        alterText.setVisibility(View.VISIBLE);
                        alterText.setText("该用户已被注册");
                        return;
                    }
                    break;
                default:
                    break;
            }
            startActivity(intent);
        } catch (Exception e) {
            alterText.setVisibility(View.VISIBLE);
            alterText.setText("连不上服务器");
            e.printStackTrace();
        }
    }
}

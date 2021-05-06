package com.DefaultCompany.glaucoma_perimetry_system.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.DefaultCompany.glaucoma_perimetry_system.R;
import com.DefaultCompany.glaucoma_perimetry_system.controllers.LoginController;
import com.DefaultCompany.glaucoma_perimetry_system.entitys.GlobalVal;

public class LoginActivity extends Activity implements View.OnClickListener {
    private GlobalVal globalVal;
    private LoginController loginController=new LoginController();
    private EditText Pid;
    private TextView alterText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button sendId = findViewById(R.id.send_id);
        Button noSend = findViewById(R.id.no_send);
        Pid = findViewById(R.id.Pid);
        alterText=findViewById(R.id.alterText);
        sendId.setOnClickListener(this);
        noSend.setOnClickListener(this);
        globalVal=(GlobalVal) getApplication();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, MainActivity.class); //切换窗口
        switch (v.getId()) {
            case R.id.send_id:
                String id = Pid.getText().toString();
                if (loginController.editTextCheck(id)) {
                    Log.i("用户id", id);
                    globalVal.setId(id);
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
            default:
                break;
        }
            startActivity(intent);
    }
}

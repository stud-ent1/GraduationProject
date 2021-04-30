package com.DefaultCompany.glaucoma_perimetry_system;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity implements View.OnClickListener {
    private GlobalVal globalVal;
    private Button sendId, noSend;
    private EditText Pid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sendId = findViewById(R.id.send_id);
        noSend = findViewById(R.id.no_send);
        Pid = findViewById(R.id.Pid);
        sendId.setOnClickListener(this);
        noSend.setOnClickListener(this);
        globalVal=(GlobalVal) getApplication();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, MainActivity.class); //切换窗口
        switch (v.getId()) {
            case R.id.send_id:
                System.out.println(Pid.getText().toString());
                globalVal.setId(Pid.getText().toString());
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

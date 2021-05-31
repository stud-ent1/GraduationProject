package com.DefaultCompany.glaucoma_perimetry_system.service;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.DefaultCompany.glaucoma_perimetry_system.R;
import com.DefaultCompany.glaucoma_perimetry_system.enums.AlterType;


public class Alter {
    private Context context;
    public Alter(Context context){
        this.context=context;
    }
   public void getAlter(AlterType alterType){

       View view= View.inflate(context, R.layout.alter, null);
       TextView textView=view.findViewById(R.id.alterText);
        setText(textView,alterType);
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setView(view)
                .create();
        alertDialog.show();
    }
    void setText(TextView textView,AlterType alterType){
        switch (alterType){
            case INFO:
                textView.setText("感谢使用本测试系统，输入身份证，电话号码，邮箱作为唯一标识，这将为您和他人提供更好的帮助");
                textView.setTextColor(Color.WHITE);
                break;
            case idIllegal:
                textView.setText("id不合法,必须是身份证，电话号码，邮箱等可以唯一标识用户的值");
                textView.setTextColor(Color.RED);
                break;
            case idError:
                textView.setText("用户名或者密码不正确");
                textView.setTextColor(Color.RED);
                break;
            case idRegister:
                textView.setText("该用户已被注册");
                textView.setTextColor(Color.RED);
                break;
            case notConnect:
                textView.setText("连不上服务器");
                textView.setTextColor(Color.RED);
                break;
            case notAir:
                textView.setText("未检测到耳机接入，请插入耳机");
                textView.setTextColor(Color.RED);
                break;
            case beginCheck:
                textView.setText("即将开始进入到检测界面，请将手机放入到VR眼镜中并佩戴，期间如果看到闪烁光点，请按下耳机中键进行响应，如需暂停检测，请按下耳机后退键");
                textView.setTextColor(Color.BLUE);
                break;
    }
    }


}

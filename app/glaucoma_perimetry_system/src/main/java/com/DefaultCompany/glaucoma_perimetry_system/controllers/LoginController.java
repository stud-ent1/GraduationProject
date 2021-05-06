package com.DefaultCompany.glaucoma_perimetry_system.controllers;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginController {
    //假设18位身份证号码:41000119910101123X  410001 19910101 123X
    //^开头
    //[1-9] 第一位1-9中的一个      4
    //\\d{5} 五位数字           10001（前六位省市县地区）
    //(18|19|20)                19（现阶段可能取值范围18xx-20xx年）
    //\\d{2}                    91（年份）
    //((0[1-9])|(10|11|12))     01（月份）
    //(([0-2][1-9])|10|20|30|31)01（日期）
    //\\d{3} 三位数字            123（第十七位奇数代表男，偶数代表女）
    //[0-9Xx] 0123456789Xx其中的一个 X（第十八位为校验值）
    //$结尾

    //假设15位身份证号码:410001910101123  410001 910101 123
    //^开头
    //[1-9] 第一位1-9中的一个      4
    //\\d{5} 五位数字           10001（前六位省市县地区）
    //\\d{2}                    91（年份）
    //((0[1-9])|(10|11|12))     01（月份）
    //(([0-2][1-9])|10|20|30|31)01（日期）
    //\\d{3} 三位数字            123（第十五位奇数代表男，偶数代表女），15位身份证不含X
    //$结尾
    private static final String ID = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|" +
            "(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";


    public Boolean editTextCheck(String str) {
        Pattern pattern = Pattern.compile(ID);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

}

package com.DefaultCompany.glaucoma_perimetry_system.entitys;

import android.app.Application;

import java.util.HashMap;
import java.util.Map;

public class GlobalVal extends Application {
    //定义早期视标显示状态数组
    private boolean[][] earlySightingPostDisplayStatus = {
            {
                    false, false, true, true, true, true, false, false, false,
                    false, true, true, true, true, true, true, false, false,
                    true, true, true, true, true, true, true, true, false,
                    true, true, true, true, true, true, true, true, true,
                    true, true, true, true, true, true, true, true, true,
                    true, true, true, true, true, true, true, true, false,
                    false, true, true, true, true, true, true, false, false,
                    false, false, true, true, true, true, false, false, false},
            {
                    false, false, false, true, true, true, true, false, false,
                    false, false, true, true, true, true, true, true, false,
                    false, true, true, true, true, true, true, true, true,
                    true, true, true, true, true, true, true, true, true,
                    true, true, true, true, true, true, true, true, true,
                    false, true, true, true, true, true, true, true, true,
                    false, false, true, true, true, true, true, true, false,
                    false, false, false, true, true, true, true, false, false}
    };


    //定义晚期视标检测状态数组
    private boolean[][] lateSightingPostDisplayStatus =
            {
                    {
                            false, false, false, false, true, true, false, false, false, false,
                            false, false, true, true, true, true, true, true, false, false,
                            false, true, true, true, true, true, true, true, true, false,
                            false, true, true, true, true, true, true, true, true, false,
                            true, true, true, true, true, true, true, true, true, true,
                            true, true, true, true, true, true, true, true, true, true,
                            false, true, true, true, true, true, true, true, true, false,
                            false, true, true, true, true, true, true, true, true, false,
                            false, false, true, true, true, true, true, true, false, false,
                            false, false, false, false, true, true, false, false, false, false},
                    {
                            false, false, false, false, true, true, false, false, false, false,
                            false, false, true, true, true, true, true, true, false, false,
                            false, true, true, true, true, true, true, true, true, false,
                            false, true, true, true, true, true, true, true, true, false,
                            true, true, true, true, true, true, true, true, true, true,
                            true, true, true, true, true, true, true, true, true, true,
                            false, true, true, true, true, true, true, true, true, false,
                            false, true, true, true, true, true, true, true, true, false,
                            false, false, true, true, true, true, true, true, false, false,
                            false, false, false, false, true, true, false, false, false, false}
            };

    private boolean ifFirstToSee=true;
    private String id;
    private Map<String, Object> map = new HashMap<String, Object>();
    private String eye = "左眼";
    private String program = "24-2(适用于前期)";

    public String getEye() {
        return eye;
    }

    public void setEye(String eye) {
        this.eye = eye;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map.putAll(map);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean[][] getEarlySightingPostDisplayStatus() {
        return earlySightingPostDisplayStatus;
    }

    public boolean[][] getLateSightingPostDisplayStatus() {
        return lateSightingPostDisplayStatus;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        map.put("sightingLoseRatioL", "无数据");
        map.put("falseNegativeRatioL", "无数据");
        map.put("falsePositiveRatioL", "无数据");
        map.put("sightingLoseRatioR", "无数据");
        map.put("falseNegativeRatioR", "无数据");
        map.put("falsePositiveRatioR", "无数据");
    }

    public boolean isIfFirstToSee() {
        return ifFirstToSee;
    }

    public void setIfFirstToSee(boolean ifFirstToSee) {
        this.ifFirstToSee = ifFirstToSee;
    }
}

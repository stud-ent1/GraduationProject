package com.DefaultCompany.glaucoma_perimetry_system.entitys;

import android.app.Application;

import java.util.HashMap;
import java.util.Map;

public class GlobalVal extends Application {
    private String id;
    private Map<String, Object> map = new HashMap<String, Object>();

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

    @Override
    public void onCreate() {
        super.onCreate();
    }
}

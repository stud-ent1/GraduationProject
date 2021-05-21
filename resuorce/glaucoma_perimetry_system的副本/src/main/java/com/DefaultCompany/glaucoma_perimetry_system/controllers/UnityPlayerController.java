package com.DefaultCompany.glaucoma_perimetry_system.controllers;

import android.content.Context;

import com.DefaultCompany.glaucoma_perimetry_system.entitys.GlobalVal;
import com.DefaultCompany.glaucoma_perimetry_system.enums.RequestType;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UnityPlayerController {
private Context context;
private HttpController httpController;
public UnityPlayerController(Context context){
    this.context=context;
}
public GlobalVal updateGlobalVal(String str,GlobalVal globalVal){
    Map<String, Object> map = new HashMap<String, Object>();
    String[] arr = str.split(",");
    String[] arr1 = new String[0];
    System.out.println("插入数据");
    if (arr[0].equals("左眼")) {
        map.put("sightingLoseRatioL", arr[1]);
        map.put("falseNegativeRatioL", arr[2]);
        map.put("falsePositiveRatioL", arr[3]);
        map.put("grayScaleL","匿名,"+str);
    } else {
        map.put("sightingLoseRatioR", arr[1]);
        map.put("falseNegativeRatioR", arr[2]);
        map.put("falsePositiveRatioR", arr[3]);
        map.put("grayScaleR","匿名,"+str);
    }
    globalVal.setMap(map);
    if (globalVal.getId() != null) {
        try {
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("val",globalVal.getId()+","+str);
            jsonObject.put("program",globalVal.getProgram());
            httpController.execRequest(RequestType.INSERT, jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    return globalVal;
}
}

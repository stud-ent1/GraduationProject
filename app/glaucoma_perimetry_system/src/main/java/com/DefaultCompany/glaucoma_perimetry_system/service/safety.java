package com.DefaultCompany.glaucoma_perimetry_system.service;


import android.util.Base64;

public class safety {
    String encode(String string){
            return Base64.encodeToString(string.getBytes(),Base64.DEFAULT);
    }
    String decode(String string){
        return String.valueOf(Base64.decode(string,Base64.DEFAULT));
    }
}

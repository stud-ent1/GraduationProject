package com.DefaultCompany.glaucoma_perimetry_system.controllers;

import android.content.Context;
import android.media.AudioManager;



public class ChoiceController {
    private Context context;
     public ChoiceController(Context context){
         this.context=context;
     }
    /**
     * 检查耳机是否插入
     */
    public boolean ifExistAir() {
        AudioManager localAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        if (localAudioManager.isWiredHeadsetOn()) {
            return true;
        } else {
            return false;
        }
    }
}

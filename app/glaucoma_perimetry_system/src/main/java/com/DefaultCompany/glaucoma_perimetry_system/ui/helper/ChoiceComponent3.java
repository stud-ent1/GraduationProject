package com.DefaultCompany.glaucoma_perimetry_system.ui.helper;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.DefaultCompany.glaucoma_perimetry_system.R;
import com.binioter.guideview.Component;

public class ChoiceComponent3 implements Component {

    @Override
    public View getView(LayoutInflater inflater) {
        LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.component_choice3, null);
        ll.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
            }
        });
        return ll;
    }

    @Override
    public int getAnchor() {
        return Component.ANCHOR_BOTTOM;
    }

    @Override
    public int getFitPosition() {
        return Component.FIT_END;
    }

    @Override
    public int getXOffset() {
        return 100;
    }

    @Override
    public int getYOffset() {
        return 0;
    }
}

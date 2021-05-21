package com.DefaultCompany.glaucoma_perimetry_system.views;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.DefaultCompany.glaucoma_perimetry_system.R;
import com.DefaultCompany.glaucoma_perimetry_system.controllers.ResultDeal;
import com.DefaultCompany.glaucoma_perimetry_system.entitys.GlobalVal;

import java.util.Map;

public class ResultFragment extends Fragment implements View.OnClickListener {
    private View resultLayout;
    private ImageView garyImgL, garyImgR, ratioImgL, ratioImgR;
    private TextView sightingLoseL, falsePositiveL, falseNegativeL, sightingLoseR, falsePositiveR, falseNegativeR, notFindL, notFindR, notFindL1, notFindR1;
    private GlobalVal globalVal;
    private ResultDeal resultController;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        resultLayout = inflater.inflate(R.layout.fragment_result, container, false);

        return resultLayout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button share = resultLayout.findViewById(R.id.share);
        garyImgL = resultLayout.findViewById(R.id.grayImgL);
        garyImgR = resultLayout.findViewById(R.id.grayImgR);
        ratioImgL = resultLayout.findViewById(R.id.ratioImgL);
        ratioImgR = resultLayout.findViewById(R.id.ratioImgR);
        sightingLoseL = resultLayout.findViewById(R.id.sightingLoseL);
        falsePositiveL = resultLayout.findViewById(R.id.falsePositiveL);
        falseNegativeL = resultLayout.findViewById(R.id.falseNegativeL);
        sightingLoseR = resultLayout.findViewById(R.id.sightingLoseR);
        falsePositiveR = resultLayout.findViewById(R.id.falsePositiveR);
        falseNegativeR = resultLayout.findViewById(R.id.falseNegativeR);
        notFindL = resultLayout.findViewById(R.id.notFindL);
        notFindR = resultLayout.findViewById(R.id.notFindR);
        notFindL1 = resultLayout.findViewById(R.id.notFindL1);
        notFindR1 = resultLayout.findViewById(R.id.notFindR1);
        share.setOnClickListener(this);
        globalVal = (GlobalVal) getActivity().getApplication();
        resultController = new ResultDeal(getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        resultController.delFile(getActivity().getFilesDir());
        globalVal = resultController.updateVal(globalVal);
        Map<String, Object> map = globalVal.getMap();
        sightingLoseL.setText(map.get("sightingLoseRatioL").toString());
        falsePositiveL.setText(map.get("falsePositiveRatioL").toString());
        falseNegativeL.setText(map.get("falseNegativeRatioL").toString());
        sightingLoseR.setText(map.get("sightingLoseRatioR").toString());
        falsePositiveR.setText(map.get("falsePositiveRatioR").toString());
        falseNegativeR.setText(map.get("falseNegativeRatioR").toString());
        setImageView(resultController.getImg("左眼GrayImage.png"), garyImgL, notFindL);
        setImageView(resultController.getImg("右眼GrayImage.png"), garyImgR, notFindR);
        setImageView(resultController.getImg("左眼RatioImage.png"), ratioImgL, notFindL1);
        setImageView(resultController.getImg("右眼RatioImage.png"), ratioImgR, notFindR1);

    }

    void setImageView(Bitmap bitmap, ImageView imageView, TextView textView) {
        if (bitmap != null) {
            textView.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageBitmap(bitmap);
        } else {
            imageView.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.share:
                resultController.onPress(globalVal);
                break;
            default:
                break;
        }
    }
}



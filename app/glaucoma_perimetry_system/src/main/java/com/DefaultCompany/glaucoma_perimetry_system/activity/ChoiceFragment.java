package com.DefaultCompany.glaucoma_perimetry_system.activity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.DefaultCompany.glaucoma_perimetry_system.R;
import com.DefaultCompany.glaucoma_perimetry_system.enums.AlterType;
import com.DefaultCompany.glaucoma_perimetry_system.service.Alter;
import com.DefaultCompany.glaucoma_perimetry_system.service.CheckAir;
import com.DefaultCompany.glaucoma_perimetry_system.entitys.GlobalVal;
import com.DefaultCompany.glaucoma_perimetry_system.ui.helper.ChoiceComponent;
import com.DefaultCompany.glaucoma_perimetry_system.ui.helper.ChoiceComponent2;
import com.DefaultCompany.glaucoma_perimetry_system.ui.helper.ChoiceComponent3;
import com.binioter.guideview.Guide;
import com.binioter.guideview.GuideBuilder;

public class ChoiceFragment extends Fragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    private View choiceLayout;
    private RadioGroup programsRadioGroup, eyesRadioGroup;
    private CheckAir choiceController;
    private RadioButton r;
    private GlobalVal globalVal;
    private Alter alter;
    private Button beginCheck;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        choiceLayout = inflater.inflate(R.layout.fragment_choice, container, false);


        return choiceLayout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        beginCheck = choiceLayout.findViewById(R.id.beginCheck);
        programsRadioGroup = choiceLayout.findViewById(R.id.programsRadioGroup);
        eyesRadioGroup = choiceLayout.findViewById(R.id.eyesRadioGroup);
        beginCheck.setOnClickListener(this);
        alter=new Alter(getActivity());
        programsRadioGroup.setOnCheckedChangeListener(this);
        eyesRadioGroup.setOnCheckedChangeListener(this);
        choiceController = new CheckAir(getActivity());
        globalVal=(GlobalVal) getActivity().getApplication();
    }

    @Override
    public void onClick(View v) {
        // 步骤1:通过getArgments()获取从Activity传过来的全部值
        Intent i= new Intent(getActivity(), UnityPlayerActivity.class); //切换窗口
        i.putExtra("eye", globalVal.getEye());
        i.putExtra("program", globalVal.getProgram());

//       if (choiceController.ifExistAir()) {
           alter.getAlter(AlterType.beginCheck);
           new Thread(){
               @Override
               public void run() {
                   try {
                       Thread.sleep(10000);
                       startActivity(i);
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
               }
           }.start();
//        }else {
//           alter.getAlter(AlterType.notAir);
//       }

    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        r = (RadioButton) choiceLayout.findViewById(checkedId);
        switch (r.getId()) {
            case R.id.leftEye:
            case R.id.rightEye:
                globalVal.setEye((String) r.getText());
                break;
            case R.id.earlyRadioButton:
            case R.id.lateRadioButton:
                globalVal.setProgram((String) r.getText());
                break;
            default:
                break;
        }
    }
    public void showGuideView() {
        GuideBuilder builder = new GuideBuilder();
        builder.setTargetView(programsRadioGroup)
                .setAlpha(150)
                .setHighTargetCorner(20)
                .setHighTargetPadding(10);
        builder.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() {
            @Override
            public void onShown() {
            }

            @Override
            public void onDismiss() {
                showGuideView2();
            }
        });

        builder.addComponent(new ChoiceComponent());
        Guide guide = builder.createGuide();
        guide.show(getActivity());
    }

    public void showGuideView2() {
        final GuideBuilder builder1 = new GuideBuilder();
        builder1.setTargetView(eyesRadioGroup)
                .setAlpha(150)
                .setHighTargetCorner(20)
                .setHighTargetPadding(10);
        builder1.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() {
            @Override
            public void onShown() {
            }

            @Override
            public void onDismiss() {
                showGuideView3();
            }
        });

        builder1.addComponent(new ChoiceComponent2());
        Guide guide = builder1.createGuide();
        guide.show(getActivity());
    }
    public void showGuideView3() {
        final GuideBuilder builder2 = new GuideBuilder();
        builder2.setTargetView(beginCheck)
                .setAlpha(150)
                .setHighTargetCorner(20)
                .setHighTargetPadding(10);
        builder2.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() {
            @Override
            public void onShown() {
            }

            @Override
            public void onDismiss() {

            }
        });

        builder2.addComponent(new ChoiceComponent3());
        Guide guide = builder2.createGuide();
        guide.show(getActivity());
    }
}

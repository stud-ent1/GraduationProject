using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.EventSystems;
using UnityEngine.SceneManagement;
using UnityEngine.UI;
using UnityEngine.XR;

public class ChooseEye : MonoBehaviour
{
    public static bool ifClickButton;
    public static bool ifCheckDone;
    //定义视标显示状态数组(左眼)
    public static bool[] sightingPostDisplayStatusLeft = new bool[72]
    {
        false,false,true,true,true,true,false,false,false,
        false,true,true,true,true,true,true,false,false,
        true,true,true,true,true,true,true,true,false,
        true,true,true,true,true,true,true,true,true,
        true,true,true,true,true,true,true,true,true,
        true,true,true,true,true,true,true,true,false,
        false,true,true,true,true,true,true,false,false,
        false,false,true,true,true,true,false,false,false
    };
    //定义视标显示状态数组(右眼)
    public static bool[] sightingPostDisplayStatusRight = new bool[72]
   {
        false,false,false,true,true,true,true,false,false,
        false,false,true,true,true,true,true,true,false,
        false,true,true,true,true,true,true,true,true,
        true,true,true,true,true,true,true,true,true,
        true,true,true,true,true,true,true,true,true,
        false,true,true,true,true,true,true,true,true,
        false,false,true,true,true,true,true,true,false,
        false,false,false,true,true,true,true,false,false
   };
    public static string eye;
    //初始化
    void Start()
    {
        //设置vr模式为true
        XRSettings.enabled = true;
        //设置是否点击按钮为false
        ifClickButton = false;
    }
    //监听选择的按钮
    void chooseEye(string eye)
    {
        switch (eye)
        {
            case "左眼":
                ResultDisplay.sightingPostDisplayStatus = sightingPostDisplayStatusLeft;
                print("////////////////////////////////////////");
                ChooseEye.eye = eye;
                break;
            case "右眼":
                ResultDisplay.sightingPostDisplayStatus = sightingPostDisplayStatusRight;
                print("////////////////////////////////////////");
                ChooseEye.eye = eye;
                break;
            default:
                break;
        }
    }
    void ifStartCheck()
    {
        //已经选择的按钮
        ifClickButton = true;
        //设置横屏
        SwitchTheScreen.switchHorizontal();
    }
}

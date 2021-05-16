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
    //定义早期视标显示状态数组
    public static bool[][] earlySightingPostDisplayStatus = new bool[2][]{
        new bool[72]{
        false,false,true,true,true,true,false,false,false,
        false,true,true,true,true,true,true,false,false,
        true,true,true,true,true,true,true,true,false,
        true,true,true,true,true,true,true,true,true,
        true,true,true,true,true,true,true,true,true,
        true,true,true,true,true,true,true,true,false,
        false,true,true,true,true,true,true,false,false,
        false,false,true,true,true,true,false,false,false},
        new bool[72]{
        false,false,false,true,true,true,true,false,false,
        false,false,true,true,true,true,true,true,false,
        false,true,true,true,true,true,true,true,true,
        true,true,true,true,true,true,true,true,true,
        true,true,true,true,true,true,true,true,true,
        false,true,true,true,true,true,true,true,true,
        false,false,true,true,true,true,true,true,false,
        false,false,false,true,true,true,true,false,false}
        };
    //定义晚期视标检测状态数组
    public static bool[][] lateSightingPostDisplayStatus = new bool[2][]
    {
        new bool[100]{
        false,false,false,false,true,true,false,false,false,false,
        false,false,true,true,true,true,true,true,false,false,
        false,true,true,true,true,true,true,true,true,false,
        false,true,true,true,true,true,true,true,true,false,
        true,true,true,true,true,true,true,true,true,true,
        true,true,true,true,true,true,true,true,true,true,
        false,true,true,true,true,true,true,true,true,false,
        false,true,true,true,true,true,true,true,true,false,
        false,false,true,true,true,true,true,true,false,false,
        false,false,false,false,true,true,false,false,false,false },
        new bool[100]{
        false,false,false,false,true,true,false,false,false,false,
        false,false,true,true,true,true,true,true,false,false,
        false,true,true,true,true,true,true,true,true,false,
        false,true,true,true,true,true,true,true,true,false,
        true,true,true,true,true,true,true,true,true,true,
        true,true,true,true,true,true,true,true,true,true,
        false,true,true,true,true,true,true,true,true,false,
        false,true,true,true,true,true,true,true,true,false,
        false,false,true,true,true,true,true,true,false,false,
        false,false,false,false,true,true,false,false,false,false }
    };
    //定义早期视标位置数组
    public static float[,] earlySightingPostLocation = new float[72, 2]{
        { -4,3.5f},{ -3,3.5f},{-2,3.5f},{-1,3.5f },{0,3.5f },{1,3.5f },{2,3.5f },{3,3.5f },{4,3.5f },
        { -4,2.5f},{ -3,2.5f},{-2,2.5f },{-1,2.5f },{0,2.5f },{1,2.5f },{2,2.5f },{3,2.5f },{4,2.5f },
        { -4,1.5f},{ -3,1.5f},{-2,1.5f },{-1,1.5f },{0,1.5f },{1,1.5f },{2,1.5f },{3,1.5f },{4,1.5f },
        { -4,0.5f},{ -3,0.5f},{-2,0.5f },{-1,0.5f },{0,0.5f },{1,0.5f },{2,0.5f },{3,0.5f },{4,0.5f },
        { -4,-0.5f},{ -3,-0.5f},{-2,-0.5f },{-1,-0.5f },{0,-0.5f },{1,-0.5f },{2,-0.5f },{3,-0.5f },{4,-0.5f },
        { -4,-1.5f},{ -3,-1.5f},{-2,-1.5f },{-1,-1.5f },{0,-1.5f },{1,-1.5f },{2,-1.5f },{3,-1.5f },{4,-1.5f },
        { -4,-2.5f},{ -3,-2.5f},{-2,-2.5f },{-1,-2.5f },{0,-2.5f },{1,-2.5f },{2,-2.5f },{3,-2.5f },{4,-2.5f },
        { -4,-3.5f},{ -3,-3.5f},{-2,-3.5f },{-1,-3.5f },{0,-3.5f },{1,-3.5f },{2,-3.5f },{3,-3.5f },{4,-3.5f }
    };
    //定义晚期视标位置数组
    public static float[,] lateSightingPostLocation = new float[100, 2]{
        { -4.5f,4.5f},{ -3.5f,4.5f},{-2.5f,4.5f},{-1.5f,4.5f },{-0.5f,4.5f },{0.5f,4.5f },{1.5f,4.5f },{2.5f,4.5f },{3.5f,4.5f },{4.5f,4.5f },
        { -4.5f,3.5f},{ -3.5f,3.5f},{-2.5f,3.5f},{-1.5f,3.5f },{-0.5f,3.5f },{0.5f,3.5f },{1.5f,3.5f },{2.5f,3.5f },{3.5f,3.5f },{4.5f,3.5f },
        { -4.5f,2.5f},{ -3.5f,2.5f},{-2.5f,2.5f},{-1.5f,2.5f },{-0.5f,2.5f },{0.5f,2.5f },{1.5f,2.5f },{2.5f,2.5f },{3.5f,2.5f },{4.5f,2.5f },
        { -4.5f,1.5f},{ -3.5f,1.5f},{-2.5f,1.5f},{-1.5f,1.5f },{-0.5f,1.5f },{0.5f,1.5f },{1.5f,1.5f },{2.5f,1.5f },{3.5f,1.5f },{4.5f,1.5f },
        { -4.5f,0.5f},{ -3.5f,0.5f},{-2.5f,0.5f},{-1.5f,0.5f },{-0.5f,0.5f },{0.5f,0.5f },{1.5f,0.5f },{2.5f,0.5f },{3.5f,0.5f },{4.5f,0.5f },
        { -4.5f,-0.5f},{ -3.5f,-0.5f},{-2.5f,-0.5f},{-1.5f,-0.5f },{-0.5f,-0.5f },{0.5f,-0.5f },{1.5f,-0.5f },{2.5f,-0.5f },{3.5f,-0.5f },{4.5f,-0.5f },
        { -4.5f,-1.5f},{ -3.5f,-1.5f},{-2.5f,-1.5f},{-1.5f,-1.5f },{-0.5f,-1.5f },{0.5f,-1.5f },{1.5f,-1.5f },{2.5f,-1.5f },{3.5f,-1.5f },{4.5f,-1.5f },
        { -4.5f,-2.5f},{ -3.5f,-2.5f},{-2.5f,-2.5f},{-1.5f,-2.5f },{-0.5f,-2.5f },{0.5f,-2.5f },{1.5f,-2.5f },{2.5f,-2.5f },{3.5f,-2.5f },{4.5f,-2.5f },
        { -4.5f,-3.5f},{ -3.5f,-3.5f},{-2.5f,-3.5f},{-1.5f,-3.5f },{-0.5f,-3.5f },{0.5f,-3.5f },{1.5f,-3.5f },{2.5f,-3.5f },{3.5f,-3.5f },{4.5f,-3.5f },
        { -4.5f,-4.5f},{ -3.5f,-4.5f},{-2.5f,-4.5f},{-1.5f,-4.5f },{-0.5f,-4.5f },{0.5f,-4.5f },{1.5f,-4.5f },{2.5f,-4.5f },{3.5f,-4.5f },{4.5f,-4.5f }
    };
    //定义早期视野阈值数组
    public static float[][,] viewScale = new float[2][,]
    {
        new float[8,9]
        {
        { 3,3,3,3,3,3,3,3,3 },
        { 3,3,3,3,3,3,3,3,3 },
        { 3,3,3,3,3,3,3,3,3 },
        { 3,3,3,3,3,3,3,3,3 },
        { 3,3,3,3,3,3,3,3,3 },
        { 3,3,3,3,3,3,3,3,3 },
        { 3,3,3,3,3,3,3,3,3 },
        { 3,3,3,3,3,3,3,3,3 }
        },
        new float[10,10]
        {
        { 3,3,3,3,3,3,3,3,3,3 },
        { 3,3,3,3,3,3,3,3,3,3 },
        { 3,3,3,3,3,3,3,3,3,3 },
        { 3,3,3,3,3,3,3,3,3,3 },
        { 3,3,3,3,3,3,3,3,3,3 },
        { 3,3,3,3,3,3,3,3,3,3 },
        { 3,3,3,3,3,3,3,3,3,3 },
        { 3,3,3,3,3,3,3,3,3,3 },
        { 3,3,3,3,3,3,3,3,3,3 },
        { 3,3,3,3,3,3,3,3,3,3 }
        }
    };
    //定义晚期视野阈值数组
    public static string eye;
    public static string program;
    public static bool[][] sightingPostDisplayStatus;
    //定义最大随机数
    public static int maxRandomX,maxRandomY;
    //定义最大有效次数
    public static int maxCheck;
    public static int CV;
    //初始化
    void Start()
    {
        //设置vr模式为true
        XRSettings.enabled = true;
        //设置是否点击按钮为false
        ifClickButton = false;
        setCameraStatus();
    }
    //监听选择的眼睛
    void chooseEye(string eye)
    {
        switch (eye)
        {
            case "左眼":
                ThresholdCalculate.sightingPostDisplayStatus = sightingPostDisplayStatus[0];
                ChooseEye.eye = eye;
                break;
            case "右眼":
                ThresholdCalculate.sightingPostDisplayStatus = sightingPostDisplayStatus[1];
                ChooseEye.eye = eye;
                break;
            default:
                break;
        }
    }
    //监听选择的检测程序
    void chooseProgram(string program)
    {
        switch (program)
        {
            case "24-2(适用于前期)":
                SightingPostLocationDeal.sightingPostLocation = earlySightingPostLocation;
                SightingPostLocationDeal.sightingPost.transform.localScale = new Vector3(0.007504916f,0.007504916f, 0.007504916f);
                sightingPostDisplayStatus=earlySightingPostDisplayStatus;
                ThresholdCalculate.viewScale = viewScale[0];
                ThresholdCalculate.sightingPostStatus = new float[72];
                maxRandomX = 8;
                maxRandomY = 9;
                maxCheck = 108;
                CV = 1;
                ChooseEye.program = program;
                break;
            case "10-2(适用于后期)":
                SightingPostLocationDeal.sightingPostLocation = lateSightingPostLocation;
                SightingPostLocationDeal.sightingPost.transform.localScale = new Vector3(0.0300197f, 0.0300197f, 0.0300197f);
                sightingPostDisplayStatus =lateSightingPostDisplayStatus;
                ThresholdCalculate.viewScale = viewScale[1];
                ThresholdCalculate.sightingPostStatus = new float[100];
                maxRandomX = 10;
                maxRandomY = 10;
                maxCheck = 136;
                CV = 3;
                ChooseEye.program = program;
                break;
            default:
                break;
        }
    }
    void setCameraStatus()
    {
        AndroidJavaClass jc = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
        AndroidJavaObject jo = jc.GetStatic<AndroidJavaObject>("currentActivity");
        jo.Call("setCameraStatus");

    }
    void ifStartCheck()
    {
        //已经选择的按钮
        ifClickButton = true;
        //设置横屏
        SwitchTheScreen.switchHorizontal();
    }
}

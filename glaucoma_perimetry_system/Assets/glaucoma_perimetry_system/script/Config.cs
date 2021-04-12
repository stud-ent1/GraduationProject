using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Config : MonoBehaviour
{
    //配置文件
    //固视丢失次数
    public static int sightingLoseNumber;
    //假阴性次数
    public static int falseNegativeNumber;
    //假阳性次数
    public static int falsePositiveNumber;
    //视标是否显示
    public static bool ifSightingDisplay;
    //是否对视标进行响应
    public static bool ifClick;
    //初始化
    void Start()
    {
        sightingLoseNumber = 0;
        falseNegativeNumber = 0;
        falsePositiveNumber = 0;
        ifSightingDisplay = false;
        ifClick = false;
    }

}

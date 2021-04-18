using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Config : MonoBehaviour
{
    //配置文件
    //固视丢失次数
    public static int sightingLoseNumber = 0;
    //假阴性次数
    public static int falseNegativeNumber = 0;
    //假阳性次数
    public static int falsePositiveNumber = 0;
    //定义视标状态
    public static float[] sightingPostStatus = new float[72];
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

}

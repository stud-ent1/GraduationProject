using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class ThresholdCalculate : MonoBehaviour
{
    //定义视野阈值数组
    public static float[] viewScale;

    //定义检测进度计数器
    public static int processConut;

    public static float sightingLoseNumber;
    public static float falseNegativeNumber;
    public static float falsePositiveNumber;
    public static float[] sightingPostStatus;
    public static bool ifClick;
    public static bool[] sightingPostDisplayStatus;
    void Start()
    {
        processConut = 0;
        sightingLoseNumber = Config.sightingLoseNumber;
        falseNegativeNumber = Config.falseNegativeNumber;
        falsePositiveNumber = Config.falsePositiveNumber;
        sightingPostStatus = Config.sightingPostStatus;
       

        viewScale = new float[72] {
        3,3,3,3,3,3,3,3,3,
        3,3,3,3,3,3,3,3,3,
        3,3,3,3,3,3,3,3,3,
        3,3,3,3,3,3,3,3,3,
        3,3,3,3,3,3,3,3,3,
        3,3,3,3,3,3,3,3,3,
        3,3,3,3,3,3,3,3,3,
        3,3,3,3,3,3,3,3,3
    };
    }
    private void Update()
    {
        checkSightingLose();
        checkFalsePositive();
    }
    void ActionUp()
    {
        ifClick = true;

    }
    //对于此段逻辑处理的解释，首先有一个由一维数组组成的视野阈值数组viewScale，一个由一维数组组成的视标状态数组sightingPostDisplayStatus，
    //如果进行响应，那么与其对应的视野阈值则不会降低，如果不响应，不进行响应，那么会先现判视野状态中的值，如果为true，则表示此位置为有效视野点，
    //且视野存在缺陷，此时视野阈值会降低，为了加快检测速度，他周围的八个位点也会降低（如果存在，⬆️⬇️⬅️➡️↖️↗️↙️↘️），但是，如果结果为flase，
    //那么此位置上为无效视野点，但其周围的八个位点可能为有效视野点，那么有效视野点又该不该降低呢？
   public static void thresholdCalculate( int spld)
    {
        sightingPostDisplayStatus = ResultDisplay.sightingPostDisplayStatus;
        if (ifClick == false&&sightingPostDisplayStatus[spld])
        {
            if (viewScale[spld] >1)
            {
                viewScale[spld] -= 1;
            }
            else
            {
                viewScale[spld] -= 1;
                sightingPostStatus[spld] +=1;
                processConut+=1;
            }


            if (spld - 10 >= 0)
            {
                if (viewScale[spld - 10]>1)
                {
                    viewScale[spld - 10] -= 1;
                }
                else
                {
                    viewScale[spld-10] -= 1;
                    sightingPostStatus[spld-10] += 1;
                    processConut += 1;
                }
               
            }
            if (spld - 9 >= 0)
            {
                if(viewScale[spld - 9]>1)
                {
                    viewScale[spld - 9] -= 1;
                }
                else
                {
                    viewScale[spld-9] -= 1;
                    sightingPostStatus[spld-9] += 1;
                    processConut += 1;
                }
                
            }
            if (spld - 8 >= 0)
            {
                if(viewScale[spld - 8] >1)
                {
                    viewScale[spld - 8] -= 1;
                }
                else
                {
                    viewScale[spld-8] -= 1;
                    sightingPostStatus[spld-8] += 1;
                    processConut += 1;
                }
               
            }
            if (spld - 1 >= 0)
            {
                if (viewScale[spld - 1] >1)
                {
                    viewScale[spld - 1] -= 1;
                }
                else
                {
                    viewScale[spld-1] -= 1;
                    sightingPostStatus[spld-1] += 1;
                    processConut += 1;
                }  
            }
            if (spld + 1 < viewScale.Length)
            {
                if (viewScale[spld + 1]>1)
                {
                    viewScale[spld + 1] -= 1;
                }
                else
                {
                    viewScale[spld+1] -= 1;
                    sightingPostStatus[spld+1] += 1;
                    processConut += 1;
                }
               
            }
            if (spld + 8 < viewScale.Length)
            {
                if (viewScale[spld + 8]>1)
                {
                    viewScale[spld + 8] -= 1;
                }
                else
                {
                    viewScale[spld+8] -= 1;
                    sightingPostStatus[spld+8] += 1;
                    processConut += 1;
                }
               
            }
            if (spld + 9 < viewScale.Length)
            {
                if (viewScale[spld + 9]>1)
                {
                    viewScale[spld + 9] -= 1;
                }
                else
                {
                    viewScale[spld+9] -= 1;
                    sightingPostStatus[spld+9] += 1;
                    processConut += 1;
                }
                
            }
            if (spld + 10 < viewScale.Length)
            {
                if (viewScale[spld + 10]>1)
                {
                    viewScale[spld + 10] -= 1;
                }
                else
                {
                    viewScale[spld+10] -= 1;
                    sightingPostStatus[spld+10] += 1;
                    processConut += 1;
                }
                
            }
        }else if (ifClick && sightingPostDisplayStatus[spld] == true)
        {
            processConut += 1;
            sightingPostStatus[spld] += 1;
        }
        else if (ifClick == false && sightingPostDisplayStatus[spld] == false)
        {
            sightingPostStatus[spld] += 1;
        }
        ifClick = false;
    }
    //对于固视丢失的定义是，在检查过程中，不时在生理盲点中央呈现高刺激强度的光标，如果受检者有反应，则记录一次固视丢失
    //对应的逻辑就是，当视标状态为true时，若响应状态为true，但是sightingPostDisplayStatus状态为false，则固视丢失次数+1
    void checkSightingLose()
    {
        if (SightingPostLocationDeal.ifSightingDisplay&& ifClick&&sightingPostDisplayStatus[SightingPostLocationDeal.random] ==false) {
            sightingLoseNumber += 1;
        }
    }
    //对于假阴性的定义是，当患者对于某一位置的光标刺激没有反应，此之前位置上更弱的刺激却能看见，则记为假阴性
    //对应的逻辑就是，当一个点响应次数(sightingPostStatus[random])>0,且响应状态为false
    public static void checkFalseNegative()
    {
        if (ifClick==false&&sightingPostStatus[SightingPostLocationDeal.random]>0) {
            falseNegativeNumber += 1;
        }
    }
    //对于假阳性的定义是，当患者在没有光标刺激存在时却表示能看见，则记录为假阳性
    //对应的逻辑就是，当视标状态为false时，若响应状态为true，则假阳性次数+1
     void checkFalsePositive()
    {
        if (SightingPostLocationDeal.ifSightingDisplay == false && ifClick )
        {
            falsePositiveNumber += 1;
        }
    }


}

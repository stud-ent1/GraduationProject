using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class ThresholdCalculate : MonoBehaviour
{
    //定义视野阈值数组
    public static float[] viewScale;

    //定义检测进度计数器
    public static int processConut;
    //定义视标状态
    public static bool[] sightingPostStatus ;
    //定义是否进行响应标志
    public static bool ifClick;
    void Start()
    {
        processConut = 0;
        sightingPostStatus = new bool[72];
        ifClick = false;
        viewScale= new float[72] {
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
        if (ifClick==false&&ResultDisplay.sightingPostDisplayStatus[spld] ==true)
        {
            if (viewScale[spld] >1)
            {
                viewScale[spld] -= 1;
            }
            else
            {
                viewScale[spld] -= 1;
                sightingPostStatus[spld] = true;
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
                    sightingPostStatus[spld-10] = true;
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
                    sightingPostStatus[spld-9] = true;
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
                    sightingPostStatus[spld-8] = true;
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
                    sightingPostStatus[spld-1] = true;
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
                    sightingPostStatus[spld+1] = true;
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
                    sightingPostStatus[spld+8] = true;
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
                    sightingPostStatus[spld+9] = true;
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
                    sightingPostStatus[spld+10] = true;
                    processConut += 1;
                }
                
            }
        }else if (ifClick == true&& ResultDisplay.sightingPostDisplayStatus[spld] == true)
        {
            processConut += 1;
            sightingPostStatus[spld] = true;
        }
        else if (ifClick == false && ResultDisplay.sightingPostDisplayStatus[spld] == false)
        {
            sightingPostStatus[spld] = true;
        }
        ifClick = false;
    }
    
       
}

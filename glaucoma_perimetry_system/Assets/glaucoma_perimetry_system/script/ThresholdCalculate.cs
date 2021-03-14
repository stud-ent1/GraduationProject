using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class ThresholdCalculate : MonoBehaviour
{
    //定义视野阈值数组
    float[] viewScale = new float[72] {
        9,9,9,9,9,9,9,9,9,
        9,9,9,9,9,9,9,9,9,
        9,9,9,9,9,9,9,9,9,
        9,9,9,9,9,9,9,9,9,
        9,9,9,9,9,9,9,9,9,
        9,9,9,9,9,9,9,9,9,
        9,9,9,9,9,9,9,9,9,
        9,9,9,9,9,9,9,9,9
    };
    //定义检测进度计数器
    int processConut=0;
    //定义是否进行响应标志
    bool ifClick = false;
    // Start is called before the first frame update
    void Start()
    {
        
    }

    // Update is called once per frame
    void Update()
    {

        if (processConut!=54)
        {
            thresholdCalculate();
        }
    }
    private void OnMouseDown()
    {
        ifClick = true;
    }
    void thresholdCalculate()
    {
        
    }
}

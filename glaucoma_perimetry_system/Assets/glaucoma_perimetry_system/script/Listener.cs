using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Listener : MonoBehaviour
{
    //不断刷新
    void Update()
    {
        ThresholdCalculate.checkSightingLose();
    }
}

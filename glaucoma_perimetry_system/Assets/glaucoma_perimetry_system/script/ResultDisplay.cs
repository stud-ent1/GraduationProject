using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;
using UnityEngine.XR;
public class ResultDisplay : MonoBehaviour
{
    public Material material;
    //定义绘制结束标志
    public static bool drawEnd;

    //定义视标显示状态数组
    public static bool[] sightingPostDisplayStatus;

    void Start()
    {
        drawEnd = false;
    }
    // Update is called once per frame
    void Update()
    {
        //之后可以改为协程
        OnPostRender();

}

       

    void OnPostRender()
    {
        if (ThresholdCalculate.processConut >= 108)
        {
            XRSettings.enabled = false;
            GL.Clear(true, true, Color.gray);
            GL.PushMatrix();
            //GL.LoadOrtho();
            material.SetPass(0);
            GL.Begin(GL.QUADS);

            for (var k = 0; k < SightingPostLocationDeal.sightingPostLocation.GetLength(0); k++)
            {
                if (sightingPostDisplayStatus[k]==true) {
                   
                    float hd = ThresholdCalculate.viewScale[k]/3;
             
                    GL.Color(new Color(hd, hd, hd));
                
                   
                    GL.Vertex3(SightingPostLocationDeal.sightingPostLocation[k, 0] - 0.5f, SightingPostLocationDeal.sightingPostLocation[k, 1] - 0.5f, 0);

                    GL.Vertex3(SightingPostLocationDeal.sightingPostLocation[k, 0] + 0.5f, SightingPostLocationDeal.sightingPostLocation[k, 1] - 0.5f, 0);

                    GL.Vertex3(SightingPostLocationDeal.sightingPostLocation[k, 0] + 0.5f, SightingPostLocationDeal.sightingPostLocation[k, 1] + 0.5f, 0);

                    GL.Vertex3(SightingPostLocationDeal.sightingPostLocation[k, 0] - 0.5f, SightingPostLocationDeal.sightingPostLocation[k, 1] + 0.5f, 0);
                   
                }
                else
                {
                    continue;
                }

            }
            GL.End();
            GL.PopMatrix();
            drawEnd = true;
            ChooseEye.ifCheckDone = true;
            sendMerge();
            //重新加载场景
            Invoke("restart", 2);

        }
    }
    //调用android mergeBitmap()合并结果，并传递固视丢失次数，假阴性次数，假阳性次数，以及测试眼睛
    public void sendMerge()
    {

        AndroidJavaClass jc = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
        AndroidJavaObject jo = jc.GetStatic<AndroidJavaObject>("currentActivity");
        jo.Call("insertText", ThresholdCalculate.sightingLoseNumber, ThresholdCalculate.falseNegativeNumber, ThresholdCalculate.falsePositiveNumber, ChooseEye.eye );
    }
    private void restart()
    {
        SceneManager.LoadScene(0);
    }
}

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
        OnPostRender();

}
    void OnPostRender()
    {
        if (ThresholdCalculate.processConut > 108)
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
            updateMap();
            //重新加载场景
            restart();
            jumpToMain();

        }
    }
    //调用android mergeBitmap()合并结果，并传递固视丢失次数，假阴性次数，假阳性次数，以及测试眼睛
    public void updateMap()
    {

        AndroidJavaClass jc = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
        AndroidJavaObject jo = jc.GetStatic<AndroidJavaObject>("currentActivity");
        string str = ChooseEye.eye + "," + (ThresholdCalculate.sightingLoseNumber/108)+"," +(ThresholdCalculate.falseNegativeNumber/108)+","+ (ThresholdCalculate.falsePositiveNumber/108)+","+string.Join(",", ThresholdCalculate.viewScale);
        jo.Call("updateMap", str);
    }
    public void jumpToMain()
    {
        AndroidJavaClass jc = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
        AndroidJavaObject jo = jc.GetStatic<AndroidJavaObject>("currentActivity");
        jo.Call("jumpToMain");

    }
    private void restart()
    {
        SceneManager.LoadScene(0);
    }
}

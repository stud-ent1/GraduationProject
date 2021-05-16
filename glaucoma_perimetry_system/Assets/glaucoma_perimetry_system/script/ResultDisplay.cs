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
        if (ThresholdCalculate.processConut > ChooseEye.maxCheck)
        {
            XRSettings.enabled = false;
            GL.Clear(true, true, Color.gray);
            GL.PushMatrix();
            //GL.LoadOrtho();
            material.SetPass(0);
            GL.Begin(GL.QUADS);

            for (var k = 0; k < SightingPostLocationDeal.sightingPostLocation.GetLength(0); k++)
            {
                //if (sightingPostDisplayStatus[k]==true) {
                   
                //    float hd = ThresholdCalculate.viewScale[k]/3;
             
                //    GL.Color(new Color(hd, hd, hd));
                
                   
                //    GL.Vertex3(SightingPostLocationDeal.sightingPostLocation[k, 0] - 0.5f, SightingPostLocationDeal.sightingPostLocation[k, 1] - 0.5f, 0);

                //    GL.Vertex3(SightingPostLocationDeal.sightingPostLocation[k, 0] + 0.5f, SightingPostLocationDeal.sightingPostLocation[k, 1] - 0.5f, 0);

                //    GL.Vertex3(SightingPostLocationDeal.sightingPostLocation[k, 0] + 0.5f, SightingPostLocationDeal.sightingPostLocation[k, 1] + 0.5f, 0);

                //    GL.Vertex3(SightingPostLocationDeal.sightingPostLocation[k, 0] - 0.5f, SightingPostLocationDeal.sightingPostLocation[k, 1] + 0.5f, 0);
                   
                //}
                //else
                //{
                //    continue;
                //}

            }
            GL.End();
            GL.PopMatrix();
            drawEnd = true;


        }
    }

}

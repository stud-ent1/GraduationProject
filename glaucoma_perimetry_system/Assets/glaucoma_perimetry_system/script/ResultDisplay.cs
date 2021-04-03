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
        if (ThresholdCalculate.processConut >= 54)
        {
            //XRSettings.enabled = false;
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
            //重新加载场景
            SceneManager.LoadScene(0);

        }
    }
}

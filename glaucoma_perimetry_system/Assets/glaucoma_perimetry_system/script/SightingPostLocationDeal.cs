using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class SightingPostLocationDeal : MonoBehaviour
{
    //定义视标位置数组
    public static float[,] sightingPostLocation =new float[72,2]{
        { -4,4},{ -3,4},{-2,4 },{-1,4 },{0,4 },{1,4 },{2,4 },{3,4 },{4,4 },
        { -4,3},{ -3,3},{-2,3 },{-1,3 },{0,3 },{1,3 },{2,3 },{3,3 },{4,3 },
        { -4,2},{ -3,2},{-2,2 },{-1,2 },{0,2 },{1,2 },{2,2 },{3,2 },{4,2 },
        { -4,1},{ -3,1},{-2,1 },{-1,1 },{0,1 },{1,1 },{2,1 },{3,1 },{4,1 },
        { -4,-1},{ -3,-1},{-2,-1 },{-1,-1 },{0,-1 },{1,-1 },{2,-1 },{3,-1 },{4,-1 },
        { -4,-2},{ -3,-2},{-2,-2 },{-1,-2 },{0,-2 },{1,-2 },{2,-2 },{3,-2 },{4,-2 },
        { -4,-3},{ -3,-3},{-2,-3 },{-1,-3 },{0,-3 },{1,-3 },{2,-3 },{3,-3 },{4,-3 },
        { -4,-4},{ -3,-4},{-2,-4 },{-1,-4 },{0,-4 },{1,-4 },{2,-4 },{3,-4 },{4,-4 }
    };
    //定义背景板的位置
    float backgroundX, backgroundY, backgroundZ;
    //定义视标状态
    bool[] sightingPostStatus = new bool[72];
    //定义计数器
    public static int count = 0;
    //定义随机数
    int random;
    //定义视标对象
    GameObject sightingPost;
    //定义视标的显示时间
    float sightingPostDisplayTime = 0.2f;
    // Start is called before the first frame update
    void Start()
    {
        sightingPost = GameObject.Find("SightingPost");
    }

    // Update is called once per frame
    void Update()
    {
        if (Time.frameCount % 120 == 0)
        {
            if (count != 72)
            {


                random = Random.Range(0, 72);
                while (true)
                {
                    if (sightingPostStatus[random] == false)
                    {
                        sightingPostStatus[random] = true;
                        count++;
                        break;
                    }
                    else
                    {
                        random = Random.Range(0, 72);
                    }
                }
            }
            backgroundX = GameObject.Find("Background").GetComponent<Transform>().localPosition.x;
            backgroundY = GameObject.Find("Background").GetComponent<Transform>().localPosition.y;
            backgroundZ = GameObject.Find("Background").GetComponent<Transform>().localPosition.z;
            sightingPost.SetActive(true);
            sightingPost.transform.localPosition = new Vector3(backgroundX + sightingPostLocation[random,0], backgroundY + sightingPostLocation[random,1], backgroundZ - 0.1f);
            Invoke("CloseShow", sightingPostDisplayTime);
            //onClick = 0;

        }
    }
    void CloseShow()
    {
        print("我执行了2");
        sightingPost.SetActive(false);
    }

}

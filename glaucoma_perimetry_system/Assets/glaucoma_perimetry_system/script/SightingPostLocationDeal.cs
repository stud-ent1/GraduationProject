using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class SightingPostLocationDeal : MonoBehaviour
{
    //定义视标位置数组
    public static float[,] sightingPostLocation =new float[72,2]{
        { -4,3.5f},{ -3,3.5f},{-2,3.5f},{-1,3.5f },{0,3.5f },{1,3.5f },{2,3.5f },{3,3.5f },{4,3.5f },
        { -4,2.5f},{ -3,2.5f},{-2,2.5f },{-1,2.5f },{0,2.5f },{1,2.5f },{2,2.5f },{3,2.5f },{4,2.5f },
        { -4,1.5f},{ -3,1.5f},{-2,1.5f },{-1,1.5f },{0,1.5f },{1,1.5f },{2,1.5f },{3,1.5f },{4,1.5f },
        { -4,0.5f},{ -3,0.5f},{-2,0.5f },{-1,0.5f },{0,0.5f },{1,0.5f },{2,0.5f },{3,0.5f },{4,0.5f },
        { -4,-0.5f},{ -3,-0.5f},{-2,-0.5f },{-1,-0.5f },{0,-0.5f },{1,-0.5f },{2,-0.5f },{3,-0.5f },{4,-0.5f },
        { -4,-1.5f},{ -3,-1.5f},{-2,-1.5f },{-1,-1.5f },{0,-1.5f },{1,-1.5f },{2,-1.5f },{3,-1.5f },{4,-1.5f },
        { -4,-2.5f},{ -3,-2.5f},{-2,-2.5f },{-1,-2.5f },{0,-2.5f },{1,-2.5f },{2,-2.5f },{3,-2.5f },{4,-2.5f },
        { -4,-3.5f},{ -3,-3.5f},{-2,-3.5f },{-1,-3.5f },{0,-3.5f },{1,-3.5f },{2,-3.5f },{3,-3.5f },{4,-3.5f }
    };
    //定义背景板的位置
    float backgroundX, backgroundY, backgroundZ;
    //定义视标对象
    GameObject sightingPost;
    //定义背景对象
    GameObject background;
    //定义根节点
    public static GameObject root;
    //定义视标的显示时间
    float sightingPostDisplayTime = 0.2f;
    //定义随机数
    public static int random;
    //视标是否显示
    public static bool ifSightingDisplay;
    // Start is called before the first frame update
    void Start()
    {
        sightingPost = GameObject.Find("SightingPost");
        background = GameObject.Find("Background");
        root = GameObject.Find("MainCamera");
        sightingPost.SetActive(false);
        background.SetActive(false);

    }

    // 此处遇到的问题表述一下，这个undate的作用是控制视标的位置，每当时间过去两秒或者ThresholdCalculate中的ifClick的状态为true时(即对视标作出了响应)，
    //在ThresholdCalculate中的thresholdCalculate方法内(用于阈值计算)，有使ifClick重新变为false的操作，目的是为了保证每点击一次视标移动一次，进行一次阈值计算，
    //但是在阈值计算期间，ifClick将一直保持为true的状态，而如果ifClick为true，update会一直执行这段代码，以至于阈值值计算了一次，但视标位置却发生了多次变化，
    //如何保证一致性成为一个问题？
    void Update()
    {
        if (ChooseEye.ifClickButton && Time.frameCount % 120 == 0)
        {
            checking();
        }
    }
    //更新逻辑：加速，点击按钮可以调用检测，每隔2秒也可以调用检测，当检测进行时，如又遇到了检测请求，提供两种策略，一是判断当前的检测状态，如果还未结束，就不进行执行，
    //二是利用协程，每次请求都响应，但是会等待到检测结束，才执行响应的请求，但这样可能会造成堆积又或是直接抛出异常，故先采用第一种方式

    //点击耳机对应时进行调用
    void quicken()
    {
        checking();
    }
    //判断是否正在进行一次检测，如果进行，则不调用
    void checking()
    {
        if (ifSightingDisplay ==false)
        {
        sightingPost.SetActive(true);
        background.SetActive(true);
        ifSightingDisplay = true;
        sightingPostMove();
        ifSightingDisplay = false;
        }
    }
    void sightingPostMove()
    {
        if (ThresholdCalculate.processConut <= 108)
        {

            random = Random.Range(0, 71);
            while (true)
            {
                if (ThresholdCalculate.sightingPostStatus[random] < 2)
                {
                    break;
                }
                else
                {
                    random = Random.Range(0, 71);
                }
            }
            backgroundX = GameObject.Find("Background").GetComponent<Transform>().localPosition.x;
            backgroundY = GameObject.Find("Background").GetComponent<Transform>().localPosition.y;
            backgroundZ = GameObject.Find("Background").GetComponent<Transform>().localPosition.z;
            sightingPost.SetActive(true);
            //设置光点的变化
            float colorFactor=ThresholdCalculate.viewScale[random];
            sightingPost.GetComponent<MeshRenderer>().material.color = new Color(1.5f/colorFactor,1.5f/colorFactor,1.5f/colorFactor);
            sightingPost.transform.localPosition = new Vector3(backgroundX + sightingPostLocation[random, 0], backgroundY + sightingPostLocation[random, 1], backgroundZ - 0.1f);
            Invoke("CloseShow", sightingPostDisplayTime);
            ThresholdCalculate.checkFalseNegative();
            ThresholdCalculate.thresholdCalculate(random);
        }
    }
    //设置视标不可见
    void CloseShow()
    {
        sightingPost.SetActive(false);
    }

}

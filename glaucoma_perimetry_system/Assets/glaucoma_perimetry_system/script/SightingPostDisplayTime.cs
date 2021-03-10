using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class SightingPostDisplayTime : MonoBehaviour
{
    //定义视标的显示时间
    float sightingPostDisplayTime = 0.2f;
    //定义视标对象
    GameObject sightingPost;
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
            ActiveShow();
            Invoke("CloseShow",sightingPostDisplayTime);
        }
        }
    void ActiveShow()
    {
        print("我执行了1");
        sightingPost.SetActive(true);
    }
    void CloseShow()
    {
        print("我执行了2");
        sightingPost.SetActive(false);
    }
}

using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class ShareResult : MonoBehaviour
{
    
    public void shareResult()
    {
        AndroidJavaClass jc = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
        AndroidJavaObject jo = jc.GetStatic<AndroidJavaObject>("currentActivity");
        jo.Call("onPress", new object[] { "分享成功" });
    }
}

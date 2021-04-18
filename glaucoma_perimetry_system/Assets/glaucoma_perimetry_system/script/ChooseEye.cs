using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.EventSystems;
using UnityEngine.SceneManagement;
using UnityEngine.UI;
using UnityEngine.XR;

public class ChooseEye : MonoBehaviour
{
    public static bool ifClickButton;
    public static GameObject button;
   
    Text text;
    public static bool ifCheckDone;
    //定义选择测试的眼睛
    public static string eye;

    void Start()
    {
        XRSettings.enabled = false;
        SwitchTheScreen.switchVertical();
        ifClickButton = false;
        
        text = GameObject.Find("Canvas/Text").GetComponent<Text>();
        if (ifCheckDone) {
            GameObject.Find("Share").SetActive(true);
            text.text = "请选择需要进行测试的眼睛,如需分享结果，请点击右上角";
        }
        else
        {
            GameObject.Find("Share").SetActive(false);
        }

    }
    public void chooseEye()
    {
        button = EventSystem.current.currentSelectedGameObject;
        switch (button.name)
        {
            case "LeftEyeButton":
                ResultDisplay.sightingPostDisplayStatus = Config.sightingPostDisplayStatusLeft;
                eye = "左眼";
                break;
            case "RightEyeButton":
                ResultDisplay.sightingPostDisplayStatus = Config.sightingPostDisplayStatusRight;
                eye = "右眼";
                break;
        }
        text.text = "请点击耳机暂停/开始键,开始进行检测";
    }
    void ifStartCheck()
    {
        ifClickButton = true;
        XRSettings.enabled = true;
        SwitchTheScreen.switchHorizontal();
    }
}

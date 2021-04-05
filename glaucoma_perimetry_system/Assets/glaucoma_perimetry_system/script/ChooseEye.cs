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
    public static string eye;
    Text text;
    void Start()
    {
        XRSettings.enabled = false;
        ifClickButton = false;
        eye = "";
        text = GameObject.Find("Canvas/Text").GetComponent<Text>();

    }
    public void chooseEye()
    {
        button = EventSystem.current.currentSelectedGameObject;
        switch (button.name)
        {
            case "LeftEyeButton":
                ResultDisplay.sightingPostDisplayStatus = ResultDisplay.sightingPostDisplayStatusLeft;
                eye = "左眼";
                break;
            case "RightEyeButton":
                ResultDisplay.sightingPostDisplayStatus = ResultDisplay.sightingPostDisplayStatusRight;
                eye = "右眼";
                break;
        }
        text.text = "请点击耳机暂停/开始键,开始进行检测";
    }
    void ifStartCheck()
    {
        ifClickButton = true;
        XRSettings.enabled = true;
    }
}

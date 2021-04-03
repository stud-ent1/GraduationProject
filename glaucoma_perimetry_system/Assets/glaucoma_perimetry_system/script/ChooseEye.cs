using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.EventSystems;
using UnityEngine.SceneManagement;

public class ChooseEye : MonoBehaviour
{
    public static bool ifClickButton;
    public static GameObject button;
    public static string eye;
    void Start()
    {
        ifClickButton = false;
        eye = "";
    }
    public void chooseEye()
    {
        button = EventSystem.current.currentSelectedGameObject;
        switch (button.name)
        {
            case "LeftEyeButton":
                ResultDisplay.sightingPostDisplayStatus = ResultDisplay.sightingPostDisplayStatusLeft;
                eye = "左眼";
                ifClickButton = true;
                break;
            case "RightEyeButton":
                ResultDisplay.sightingPostDisplayStatus = ResultDisplay.sightingPostDisplayStatusRight;
                eye = "右眼";
                ifClickButton = true;
                break;
        }
    }
}

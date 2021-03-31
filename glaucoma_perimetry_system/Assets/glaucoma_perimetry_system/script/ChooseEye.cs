using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.EventSystems;

public class ChooseEye : MonoBehaviour
{
    public static bool ifClickButton = false;
    public static GameObject button;
    public void chooseEye()
    {
        button = EventSystem.current.currentSelectedGameObject;
        switch (button.name)
        {
            case "LeftEyeButton":
                ResultDisplay.sightingPostDisplayStatus = ResultDisplay.sightingPostDisplayStatusLeft;
                ifClickButton = true;
                break;
            case "RightEyeButton":
                ResultDisplay.sightingPostDisplayStatus = ResultDisplay.sightingPostDisplayStatusRight;
                ifClickButton = true;
                break;
                
        }
    }
}

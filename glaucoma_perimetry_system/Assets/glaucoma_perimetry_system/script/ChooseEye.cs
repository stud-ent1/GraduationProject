using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.EventSystems;

public class ChooseEye : MonoBehaviour
{
    public static bool ifClickButton = false;
    public void chooseEye()
    {
        GameObject button = EventSystem.current.currentSelectedGameObject;
        switch (button.name)
        {
            case "LeftEyeButton":
                ifClickButton = true;
                break;
            case "RightEyeButton":
                ifClickButton = true;
                break;
                
        }
    }
}

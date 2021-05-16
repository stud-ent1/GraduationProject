using System.Collections;
using System.Collections.Generic;
using System.IO;
using UnityEngine;
using UnityEngine.SceneManagement;

public class ResultStore : MonoBehaviour
{
	// Update is called once per frame
	void Update()
    {
        if (ResultDisplay.drawEnd==true)
        {
			StartCoroutine(DrawResult());
			ResultDisplay.drawEnd = false;

		}
    }
	IEnumerator DrawResult()
	{
		yield return new WaitForEndOfFrame();
		int width = Screen.width;
		int height = Screen.height;
		string resultPath = "/data/data/com.DefaultCompany.glaucoma_perimetry_system/files";
		Texture2D tex = new Texture2D(width, height, TextureFormat.RGB24, false);
		tex.ReadPixels(new Rect(0, 0, width, height), 0, 0);
		tex.Apply();
		byte[] bytes = tex.EncodeToPNG();
		File.WriteAllBytes(resultPath +"/"+ChooseEye.eye+"GrayScale.png", bytes);
		ChooseEye.ifCheckDone = true;
		updateMap();
		//重新加载场景
		restart();
		jumpToMain();
	}
	//调用android mergeBitmap()合并结果，并传递固视丢失次数，假阴性次数，假阳性次数，以及测试眼睛
	public void updateMap()
	{

		AndroidJavaClass jc = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
		AndroidJavaObject jo = jc.GetStatic<AndroidJavaObject>("currentActivity");
		string str = ChooseEye.eye + "," + (ThresholdCalculate.sightingLoseNumber / ChooseEye.maxCheck) + "," + (ThresholdCalculate.falseNegativeNumber / ChooseEye.maxCheck) + "," + (ThresholdCalculate.falsePositiveNumber / ChooseEye.maxCheck) + "," + string.Join(",", ThresholdCalculate.viewScale);
		jo.Call("updateMap", str);
	}
	public void jumpToMain()
	{
		AndroidJavaClass jc = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
		AndroidJavaObject jo = jc.GetStatic<AndroidJavaObject>("currentActivity");
		jo.Call("jumpToMain");

	}
	private void restart()
	{
		SceneManager.LoadScene(0);
	}
}



using System.Collections;
using System.Collections.Generic;
using System.IO;
using UnityEngine;

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
        #if UNITY_EDITOR
		UnityEditor.AssetDatabase.Refresh();
        #endif
	}
}

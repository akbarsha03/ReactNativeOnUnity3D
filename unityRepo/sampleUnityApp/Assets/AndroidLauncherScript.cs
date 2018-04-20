using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class AndroidLauncherScript : MonoBehaviour {
	void OnGUI() {
		if (GUI.Button(new Rect(10, 70, 200, 30), "Launch react native activity")) {
			Debug.Log("Button clicked");
			
			var unityPlayerClass = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
			var currentActivity = unityPlayerClass.GetStatic<AndroidJavaObject>("currentActivity");

			var reactNativeActivity = new AndroidJavaClass("com.akbarsha03.reactmodule.ReactNativeActivity");
			reactNativeActivity.CallStatic("launchReactActivity", currentActivity);
		}
	}
}
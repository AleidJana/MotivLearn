package com.example.jana.motivlearn.multiplayer;

import android.app.Activity;
import android.widget.Toast;


public class Utils {

	public static String userName = "";

	public static void showToastAlert(Activity ctx, String alertMessage){
		Toast.makeText(ctx, alertMessage, Toast.LENGTH_SHORT).show();
	}
	
	public static void showToastOnUIThread(final Activity ctx, final String alertMessage){
		ctx.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(ctx, alertMessage, Toast.LENGTH_SHORT).show();
				
			}
		});
	}
	
}

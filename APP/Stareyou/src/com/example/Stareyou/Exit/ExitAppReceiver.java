package com.example.Stareyou.Exit;

import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;

public class ExitAppReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if (context != null) {
			if (context instanceof Activity) {
				System.out.println("¹Ø±Õ");
				android.os.Process.killProcess(android.os.Process.myPid());
			} else if (context instanceof FragmentActivity) {
				System.out.println("ÒÑ¹Ø±Õ");
				android.os.Process.killProcess(android.os.Process.myPid());
			} else if (context instanceof Service) {
				((Service) context).stopSelf();
			}
		}
	}
}
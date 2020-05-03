package com.example.Stareyou.Exit;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class BaseActivity extends FragmentActivity {
	private ExitAppReceiver exitReceiver = new ExitAppReceiver();
	// 自定义退出应用Action,实际应用中应该放到整个应用的Constant类中.
	public static final String EXIT_APP_ACTION = "com.example.Stareyou.exit_app";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		System.out.println("准备注册");
		registerExitReceiver();
		
	}

	private void registerExitReceiver() {
		IntentFilter exitFilter = new IntentFilter();
		exitFilter.addAction(EXIT_APP_ACTION);
		registerReceiver(exitReceiver, exitFilter);
		System.out.println("已经注册");
		
	}

	private void unRegisterExitReceiver() {
		unregisterReceiver(exitReceiver);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unRegisterExitReceiver();
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}
}
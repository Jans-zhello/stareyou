package com.example.Stareyou.Exit;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class BaseActivity extends FragmentActivity {
	private ExitAppReceiver exitReceiver = new ExitAppReceiver();
	// �Զ����˳�Ӧ��Action,ʵ��Ӧ����Ӧ�÷ŵ�����Ӧ�õ�Constant����.
	public static final String EXIT_APP_ACTION = "com.example.Stareyou.exit_app";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		System.out.println("׼��ע��");
		registerExitReceiver();
		
	}

	private void registerExitReceiver() {
		IntentFilter exitFilter = new IntentFilter();
		exitFilter.addAction(EXIT_APP_ACTION);
		registerReceiver(exitReceiver, exitFilter);
		System.out.println("�Ѿ�ע��");
		
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
package com.example.Stareyou;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class StareyouInternetActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.internet);
		WebView wv = (WebView) findViewById(R.id.wv);
		wv.loadUrl("http://stareyou.com/about.html");
	}
}

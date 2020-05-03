package com.example.Stareyou;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

import com.example.Stareyou.Exit.BaseActivity;

public class LogoActivity extends BaseActivity {
	public static Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.logo);
		intent = new Intent("com.angel.Android.MUSIC");
		//startService(intent);
		ImageView iv = (ImageView) this.findViewById(R.id.img_logo);
		AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
		alphaAnimation.setDuration(3000);
		alphaAnimation.setRepeatCount(1);
		alphaAnimation.setFillAfter(true);
		alphaAnimation.setRepeatMode(AlphaAnimation.REVERSE);
		alphaAnimation.setAnimationListener(new AnimationListener() {

			public void onAnimationEnd(Animation arg0) {
				Intent intent = new Intent(LogoActivity.this,
						MainActivity.class);
				startActivity(intent);
				finish();
			}

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
		});

		iv.setAnimation(alphaAnimation);
	}
}

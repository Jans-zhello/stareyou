package com.example.Stareyou.Media;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.Stareyou.R;

public class PlayImageActivity extends Activity {
	private ImageView play_img;
	private String imgurl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image);
		initWidget();
	}

	public void initWidget() {
		play_img = (ImageView) findViewById(R.id.play_img);
		imgurl = this.getIntent().getStringExtra("img_url");
		if (imgurl != null && !"".equals(imgurl)) {
			play_img.setImageBitmap(MediaUtils.getLoacalBitmap(MediaUtils
					.getDiskCacheDir(PlayImageActivity.this)
					+ "/stareyou_img/"
					+ imgurl.substring(imgurl.lastIndexOf("/") + 1)));
		}
	}
}

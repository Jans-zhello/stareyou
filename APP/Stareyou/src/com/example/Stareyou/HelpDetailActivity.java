package com.example.Stareyou;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Stareyou.Exit.BaseActivity;
import com.example.Stareyou.Media.MediaUtils;
import com.example.Stareyou.Media.PlayImageActivity;
import com.example.Stareyou.Media.PlayMovieActivity;
import com.example.Stareyou.model.Help;
import com.example.Stareyou.model.Users;
import com.example.Stareyou.server.MainClientService;
import com.example.Stareyou.util.Tools;
import com.example.Stareyou.util.WaitDialog;

@SuppressLint("ShowToast")
public class HelpDetailActivity extends BaseActivity {
	private ImageView d_back;
	private ImageView head_icon_detail;
	private TextView user_name;
	private EditText content_detail;
	private ImageView tupian_detail;
	private ImageView shipin_detail;
	private ImageView shipin_detail_play;
	private TextView shoujihao_detail;
	private TextView weizhi_detail;
	private Button buganxingqu;
	private Button quwenwen;

	//
	private WaitDialog w;

	// 判断是否点击了quwenwen按钮
	public static boolean flag = true;
	// 同步更新时间
	private Handler handler = new Handler();
	public static int dismissDelay = 19;
	// Helpid
	private long helpid;

	private UpdateTime updateTime = new UpdateTime();

	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.helpdetail);
		initWidget();
		handlerIntentData();
		// 如下两行代码解决socket连接不上的问题
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
	}

	// 初始化控件
	public void initWidget() {
		d_back = (ImageView) findViewById(R.id.d_back);
		head_icon_detail = (ImageView) findViewById(R.id.head_icon_detail);
		user_name = (TextView) findViewById(R.id.user_name);
		content_detail = (EditText) findViewById(R.id.content_detail);
		tupian_detail = (ImageView) findViewById(R.id.tupian_detail);
		shipin_detail = (ImageView) findViewById(R.id.shipin_detail);
		shipin_detail_play = (ImageView) findViewById(R.id.shipin_detail_play);
		shoujihao_detail = (TextView) findViewById(R.id.shoujihao_detail);
		weizhi_detail = (TextView) findViewById(R.id.weizhi_detail);
		buganxingqu = (Button) findViewById(R.id.buganxingqu);
		quwenwen = (Button) findViewById(R.id.quwenwen);
		buganxingqu.setOnClickListener(new BuGanOnclickListener());
		d_back.setOnClickListener(new DbackOnclickListener());
		quwenwen.setOnClickListener(new QuOnclickListener());
	}

	// 接受Intent传过来的参数
	@SuppressWarnings("unchecked")
	public void handlerIntentData() {
		ArrayList<Help> v = (ArrayList<Help>) this.getIntent()
				.getSerializableExtra("data");
		helpid = Long.parseLong(this.getIntent().getStringExtra("helpid"));
		for (final Help help : v) {
			if (help.getHelpid() == helpid) {
				// 登记被聊用户
				Tools.chatted_userid = help.getUserid() + "";
				// 用户头像
				try {// 60
					head_icon_detail.setImageBitmap(MediaUtils
							.getImageThumbnail(
									Tools.urlimg + help.getHelp_icon(),
									HelpDetailActivity.this, 55, 55));
					head_icon_detail.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							Intent intent = new Intent(HelpDetailActivity.this,
									PlayImageActivity.class);
							intent.putExtra("img_url",
									Tools.urlimg + help.getHelp_icon());
							startActivity(intent);
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
					head_icon_detail.setImageDrawable(getResources()
							.getDrawable(R.drawable.boy));
				}
				// 用户名称
				user_name.setText(help.getUsername());
				// 帮助内容
				String[] s = help.getHelp_content().split("%%");
				Log.i("getdatat", "s.length" + s.length);
				if (s.length == 3) {
					content_detail.setText(s[0]);
					if (!"".equals(s[1])) {
						tupian_detail.setImageBitmap(MediaUtils
								.getImageThumbnail(Tools.urlimg + s[1],
										HelpDetailActivity.this, 60, 60));
						tupian_detail.setOnClickListener(new TuOnclickListener(
								s[1]));
					} else {
						tupian_detail.setImageDrawable(getResources()
								.getDrawable(R.drawable.noimg));

					}
					if (!"".equals(s[2])) {
						shipin_detail_play.setVisibility(View.VISIBLE);
						shipin_detail.setImageBitmap(MediaUtils
								.getVideoThumbnail(Tools.urlvideo + s[2], 60,
										60));
						shipin_detail_play
								.setOnClickListener(new ShiOnclickListener(
										Tools.urlvideo + s[2]));
					} else {
						shipin_detail.setImageDrawable(getResources()
								.getDrawable(R.drawable.novideo));
						shipin_detail_play.setVisibility(View.GONE);
					}
				} else if (s.length == 2) {
					content_detail.setText(s[0]);
					if (!"".equals(s[1])) {
						tupian_detail.setImageBitmap(MediaUtils
								.getImageThumbnail(Tools.urlimg + s[1],
										HelpDetailActivity.this, 60, 60));
						tupian_detail.setOnClickListener(new TuOnclickListener(
								s[1]));
					} else {
						tupian_detail.setImageDrawable(getResources()
								.getDrawable(R.drawable.noimg));
					}
					shipin_detail.setImageDrawable(getResources().getDrawable(
							R.drawable.novideo));
					shipin_detail_play.setVisibility(View.GONE);

				} else {
					content_detail.setText(s[0]);
					tupian_detail.setImageDrawable(getResources().getDrawable(
							R.drawable.noimg));
					shipin_detail.setImageDrawable(getResources().getDrawable(
							R.drawable.novideo));
					shipin_detail_play.setVisibility(View.GONE);
				}

				// 手机号
				Users u = MainClientService.viewUsers(help.getUserid() + "");
				if (u != null) {
					shoujihao_detail.setText(u.getPhone());
				} else {
					shoujihao_detail.setText("无手机号信息");
				}
				// 位置信息
				if (help.getHelp_location() != null) {
					weizhi_detail.setText(help.getHelp_location());
				} else {
					weizhi_detail.setText("无位置信息");
				}
			}
		}
	}

	// 为buganxingqutianjia添加点击事件
	class BuGanOnclickListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			finish();
		}

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		finish();
	}

	// 为d_back添加点击事件
	class DbackOnclickListener implements OnClickListener {
		@Override
		public void onClick(View arg0) {
			finish();
		}
	}

	// 为quwenwen添加点击事件
	class QuOnclickListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			String chatted_username = MainClientService.viewUsers(
					Tools.chatted_userid).getUsername();
			MainClientService.amaze(helpid + "", Tools.userid, Tools.username,
					Tools.chatted_userid, chatted_username);
			w = WaitDialog.showProgressDialog(HelpDetailActivity.this,
					"正在等待对方进入聊天窗口,若无应答,20秒自动关闭");
			refreshTime();
		}
	}

	// 为点击获取图片添加点击事件
	class TuOnclickListener implements OnClickListener {
		public String img;

		public TuOnclickListener(String img) {
			super();
			this.img = img;
		}

		@Override
		public void onClick(View arg0) {
			Intent intent = new Intent(HelpDetailActivity.this,
					PlayImageActivity.class);
			intent.putExtra("img_url", Tools.urlimg + img);
			startActivity(intent);
		}

	}

	// 为点击获取视频添加点击事件
	class ShiOnclickListener implements OnClickListener {
		public String video;

		public ShiOnclickListener(String video) {
			super();
			this.video = video;
		}

		public void onClick(View arg0) {
			startActivity(new Intent(HelpDetailActivity.this,
					PlayMovieActivity.class).putExtra("video_url", video));
		}

	}

	// 刷新时间
	private void refreshTime() {
		handler.postDelayed(updateTime, 1000);
	}

	class UpdateTime implements Runnable {
		public void run() {
			dismissDelay--;
			if (MainClientService.viewAmaze(Tools.chatted_userid) == null) {
				Users u = MainClientService.viewUsers(Tools.chatted_userid);
				Intent i = new Intent(HelpDetailActivity.this,
						LiaoTianWindowActivity.class);
				i.putExtra("amazed_username", u.getUsername());
				i.putExtra("helpid", helpid + "");
				startActivity(i);
				w.dismiss();
				finish();
				return;
			}
			if (dismissDelay == -1) {
				Toast.makeText(HelpDetailActivity.this, "对方暂时无应答",
						Toast.LENGTH_LONG).show();
				dismissDelay = 19;
			} else {
				handler.postDelayed(updateTime, 1000);
			}

		}
	}
}

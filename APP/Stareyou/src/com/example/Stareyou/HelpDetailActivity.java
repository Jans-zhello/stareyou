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

	// �ж��Ƿ�����quwenwen��ť
	public static boolean flag = true;
	// ͬ������ʱ��
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
		// �������д�����socket���Ӳ��ϵ�����
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
	}

	// ��ʼ���ؼ�
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

	// ����Intent�������Ĳ���
	@SuppressWarnings("unchecked")
	public void handlerIntentData() {
		ArrayList<Help> v = (ArrayList<Help>) this.getIntent()
				.getSerializableExtra("data");
		helpid = Long.parseLong(this.getIntent().getStringExtra("helpid"));
		for (final Help help : v) {
			if (help.getHelpid() == helpid) {
				// �ǼǱ����û�
				Tools.chatted_userid = help.getUserid() + "";
				// �û�ͷ��
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
				// �û�����
				user_name.setText(help.getUsername());
				// ��������
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

				// �ֻ���
				Users u = MainClientService.viewUsers(help.getUserid() + "");
				if (u != null) {
					shoujihao_detail.setText(u.getPhone());
				} else {
					shoujihao_detail.setText("���ֻ�����Ϣ");
				}
				// λ����Ϣ
				if (help.getHelp_location() != null) {
					weizhi_detail.setText(help.getHelp_location());
				} else {
					weizhi_detail.setText("��λ����Ϣ");
				}
			}
		}
	}

	// Ϊbuganxingqutianjia��ӵ���¼�
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

	// Ϊd_back��ӵ���¼�
	class DbackOnclickListener implements OnClickListener {
		@Override
		public void onClick(View arg0) {
			finish();
		}
	}

	// Ϊquwenwen��ӵ���¼�
	class QuOnclickListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			String chatted_username = MainClientService.viewUsers(
					Tools.chatted_userid).getUsername();
			MainClientService.amaze(helpid + "", Tools.userid, Tools.username,
					Tools.chatted_userid, chatted_username);
			w = WaitDialog.showProgressDialog(HelpDetailActivity.this,
					"���ڵȴ��Է��������촰��,����Ӧ��,20���Զ��ر�");
			refreshTime();
		}
	}

	// Ϊ�����ȡͼƬ��ӵ���¼�
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

	// Ϊ�����ȡ��Ƶ��ӵ���¼�
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

	// ˢ��ʱ��
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
				Toast.makeText(HelpDetailActivity.this, "�Է���ʱ��Ӧ��",
						Toast.LENGTH_LONG).show();
				dismissDelay = 19;
			} else {
				handler.postDelayed(updateTime, 1000);
			}

		}
	}
}

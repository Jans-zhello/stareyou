package com.example.Stareyou;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.Vector;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Stareyou.Adapter.LiaoTianWindowAdapter;
import com.example.Stareyou.Exit.BaseActivity;
import com.example.Stareyou.model.Chat;
import com.example.Stareyou.model.Help;
import com.example.Stareyou.model.Users;
import com.example.Stareyou.server.InformClient;
import com.example.Stareyou.server.MainClientService;
import com.example.Stareyou.util.Tools;

public class LiaoTianWindowActivity extends BaseActivity {
	/**
	 * 请求识别码
	 */
	private static final int CODE_CAMERA_PHOTO_REQUEST = 0xa1;
	private static final int CODE_CAMERA_VIDEO_REQUEST = 0xa2;
	// 页面主控件
	private LinearLayout bottom;
	private Button jiahao_button;
	private ImageView c_back;// 返回键
	private TextView chatted_user;// 聊天对象
	private ListView liaotian_list;
	private TextView liaotian_text_tip;
	private ImageButton img_arm;
	private ImageButton img_img;
	private ImageButton img_video;
	private EditText input_text;
	private Button send;
	// 相关Helpid
	private String helpid;
	// 安卓本机内存卡
	File extDir = Environment.getExternalStorageDirectory();
	// 到数据库的音频名字
	private String amrname;
	// 到数据库的图片名字
	private String imgname;
	// 到数据库的视频名字
	private String videoname;
	// 拍摄的照片名字
	private String IMAGE_FILE_NAME = "temp.jpg";
	// 录制的视频名字
	private String VIDEO_FILE_NAME = "temp.mp4";
	// 上传控件
	public ProgressBar mPgBar;
	public TextView mTvProgress;
	public AlertDialog alertdialog;

	// 订单询问flag
	public static boolean flag = false;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.liaotianwindow);
		initWidget();
		handlerIntentData();
		// 如下两行代码解决socket连接不上的问题
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
	}

	// 初始化控件
	public void initWidget() {
		bottom = (LinearLayout) findViewById(R.id.bottom);
		jiahao_button = (Button) findViewById(R.id.jiahao_button);
		c_back = (ImageView) findViewById(R.id.c_back);
		chatted_user = (TextView) findViewById(R.id.chatted_user);
		liaotian_list = (ListView) findViewById(R.id.liaotian_list);
		liaotian_text_tip = (TextView) findViewById(R.id.liaotian_text_tip);
		img_arm = (ImageButton) findViewById(R.id.img_arm);
		img_img = (ImageButton) findViewById(R.id.img_img);
		img_video = (ImageButton) findViewById(R.id.img_video);
		input_text = (EditText) findViewById(R.id.input_text);
		send = (Button) findViewById(R.id.send);
		InformClient.chat = this;
		img_arm.setOnTouchListener(new ArmOnTouchListener());
		img_img.setOnClickListener(new ImgOnclickListener());
		img_video.setOnClickListener(new VideoOnclickListener());
		jiahao_button.setOnClickListener(new JiaClickListener());
		c_back.setOnClickListener(new CbackOnclickListener());
		send.setOnClickListener(new SendOnclickListener());
	}

	// 处理intent接受过来的数据
	private void handlerIntentData() {
		String amazeid = this.getIntent().getStringExtra("amazeid");
		String username = this.getIntent().getStringExtra("username");
		String userid1 = this.getIntent().getStringExtra("amaze_userid");
		String help_id = this.getIntent().getStringExtra("helpid");
		if (amazeid != null) {
			flag = true;
			chatted_user.setText(username);
			String msg = MainClientService.deleteAmaze(amazeid);
			Log.i("msg", msg);
			helpid = help_id;
			Tools.chatted_userid = userid1;
			new InformClient().start();

		} else if (this.getIntent().getStringExtra("amazed_username") != null
				&& this.getIntent().getStringExtra("helpid") != null) {
			flag = false;
			String amazed_username = this.getIntent().getStringExtra(
					"amazed_username");
			helpid = this.getIntent().getStringExtra("helpid");
			chatted_user.setText(amazed_username);
			Log.i("msg", "主动方进入聊天室");
			new InformClient().start();
		} else if (this.getIntent().getStringExtra("userid") != null) {
			flag = false;
			// 从联系人页面过来
			String userid = this.getIntent().getStringExtra("userid");
			Tools.chatted_userid = userid;
			Users u = MainClientService.viewUsers(userid);
			chatted_user.setText(u.getUsername());
			new InformClient().start();
		} else {
			flag = false;
			// 从聊天页面过来
			if (this.getIntent().getStringExtra("liaotian_id") != null) {
				long liaotian_id = Long.parseLong(this.getIntent()
						.getStringExtra("liaotian_id"));
				Chat c = MainClientService.viewchatbyid(liaotian_id + "");
				if (!(c.getUserid() + "").equals(Tools.userid)) {
					Tools.chatted_userid = c.getUserid() + "";
					Users u = MainClientService.viewUsers(c.getUserid() + "");
					chatted_user.setText(u.getUsername());
				} else {
					Tools.chatted_userid = c.getChatted_userid() + "";
					Users u = MainClientService.viewUsers(c.getChatted_userid()
							+ "");
					chatted_user.setText(u.getUsername());
				}
			}
			new InformClient().start();

		}
	}

	// 为jiahao_button添加点击事件
	class JiaClickListener implements OnClickListener {
		boolean flag = true;

		public void onClick(View arg0) {
			if (flag) {
				bottom.setVisibility(View.VISIBLE);
				jiahao_button.setText("━");
				flag = false;
			} else {
				bottom.setVisibility(View.GONE);
				jiahao_button.setText("╋");
				flag = true;
			}
		}

	}

	// 为c_back添加监听
	class CbackOnclickListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			if (flag) {
				showDingDanDialog();
			} else {
				finish();
			}
		}

	}

	// 弹出订单对话框,询问是否生成订单
	public void showDingDanDialog() {
		AlertDialog.Builder bb = new AlertDialog.Builder(
				LiaoTianWindowActivity.this);
		bb.setTitle("是否生成爱帮订单?");
		bb.setMessage("对方是否与您建立爱帮关系");
		bb.setNeutralButton("确定建立", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Help h = MainClientService.find_order(helpid);
				MainClientService.order(Tools.chatted_userid, Tools.userid,
						helpid, h.getHelp_title());
				// 发送广播
				Bundle b = new Bundle();
				b.putString("jumpid", "4");
				Intent i = new Intent("com.angel.Android.Jump");
				i.putExtras(b);
				sendBroadcast(i);
				Toast.makeText(LiaoTianWindowActivity.this, "已生成订单，请在订单列表中查看",
						Toast.LENGTH_LONG).show();
				finish();
			}
		});

		bb.setNegativeButton("不确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// 发送广播
				Bundle b = new Bundle();
				b.putString("jumpid", "3");
				Intent i = new Intent("com.angel.Android.Jump");
				i.putExtras(b);
				sendBroadcast(i);
				finish();

			}
		});
		bb.create().show();

	}

	// 向服务器端发送数据
	public void sendData() {
		String sendtext = input_text.getText().toString().trim();
		if (sendtext != null) {
			if (helpid == null) {
				helpid = "0";
			}
			MainClientService.chat(Tools.userid, Tools.chatted_userid, helpid,
					sendtext, Integer.parseInt(Tools.userid));
			input_text.setText("");
		}
	}

	// 为send添加点击事件
	class SendOnclickListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			sendData();
		}

	}

	/**
	 * 发表消息后下载显示在客户端，并通知其他客户端也显示
	 */
	public Vector<Chat> vc = null;
	public Handler hand = new Handler() {

		public void handleMessage(android.os.Message msg) {
			loadData(vc);
		}

		// 更新内容到客户端
		private void loadData(Vector<Chat> v) {
			LiaoTianWindowAdapter lw = new LiaoTianWindowAdapter(v,
					LiaoTianWindowActivity.this);
			liaotian_list.setAdapter(lw);
			liaotian_list.setEmptyView(liaotian_text_tip);// 设置当listview为空的时候显示text_tip
		};
	};

	// 为img_amr发送录音监听
	class ArmOnTouchListener implements OnTouchListener {

		MediaRecorder recorder = null;
		String filename = "";
		File dir = null;

		@Override
		public boolean onTouch(View arg0, MotionEvent arg1) {
			// TODO Auto-generated method stub

			if (arg1.getAction() == 0) {
				/**
				 * 录音先保存到本地
				 */
				input_text.setText("正在录音...");
				filename = new Date().getTime() + ".amr";
				dir = new File(extDir + "/stareyou_amrs");
				if (!dir.exists()) {
					dir.mkdir();
				}
				try {
					recorder = new MediaRecorder();
					recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
					recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
					recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
					recorder.setOutputFile(dir + File.separator + filename);
					recorder.prepare();
					recorder.start();
				} catch (IllegalStateException e) {
					System.out.println("出错了");
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("IO出错了");
				}

			} else if (arg1.getAction() == 1) {
				/**
				 * 后上传到服务器
				 */
				try {
					input_text.setText("");
					recorder.stop();
					recorder.reset();
					File f = new File(dir + File.separator + filename);
					Socket socket = new Socket(Tools.IP, Tools.PORT_3);
					InputStream in = socket.getInputStream();
					OutputStream out = socket.getOutputStream();
					// 客户端:就这种类型的文件,我要上传
					out.write(("upload,amr," + f.length()).getBytes());
					out.flush();
					// 客户端:收到你发的名字了
					byte[] b = new byte[1024 * 10];
					in.read(b);
					String serverInfo = new String(b).trim();
					amrname = serverInfo.split(",")[1];
					// 客户端:我要往你服务器那发了
					FileInputStream fin = new FileInputStream(f);
					int len = 0;
					while ((len = fin.read(b)) != -1) {
						out.write(b, 0, len);
						out.flush();
					}
					fin.close();
					out.close();
					in.close();
					// 上传到数据库
					if (helpid == null) {
						helpid = "0";
					}
					MainClientService.chat(Tools.userid, Tools.chatted_userid,
							helpid, amrname, Integer.parseInt(Tools.userid));
					f.delete();
					Toast.makeText(LiaoTianWindowActivity.this, "上传成功",
							Toast.LENGTH_SHORT).show();
				} catch (Exception e) {
					Toast.makeText(LiaoTianWindowActivity.this, "上传失败!",
							Toast.LENGTH_SHORT).show();
				}
			}
			return true;
		}

	}

	// 为img_img发送图片添加监听
	class ImgOnclickListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			chooseFromCameraPhoto();
		}

	}

	// 为img_video发送视频添加监听
	class VideoOnclickListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			chooseFromCameraVideo();
		}

	}

	// 从摄像头中照相
	public void chooseFromCameraPhoto() {
		Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri
				.fromFile(new File(Environment.getExternalStorageDirectory(),
						IMAGE_FILE_NAME)));
		startActivityForResult(intentFromCapture, CODE_CAMERA_PHOTO_REQUEST);
	}

	// 从摄像头中录像
	public void chooseFromCameraVideo() {
		Intent intentFromCapture = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
		intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri
				.fromFile(new File(Environment.getExternalStorageDirectory(),
						VIDEO_FILE_NAME)));
		startActivityForResult(intentFromCapture, CODE_CAMERA_VIDEO_REQUEST);
	}

	/**
	 * 初始化视频上传进度控件
	 */
	private void init() {
		View upView = getLayoutInflater().inflate(
				R.layout.filebrowser_uploading, null);
		mPgBar = (ProgressBar) upView
				.findViewById(R.id.pb_filebrowser_uploading);
		mTvProgress = (TextView) upView
				.findViewById(R.id.tv_filebrowser_uploading);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("上传进度");
		builder.setView(upView);
		alertdialog = builder.create();
		alertdialog.show();
	}

	// 处理拍照或录像完成的工作
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {

		// 用户没有进行有效的设置操作，返回
		if (resultCode == RESULT_CANCELED) {
			Toast.makeText(getApplication(), "取消", Toast.LENGTH_LONG).show();
			return;
		}
		switch (requestCode) {

		case CODE_CAMERA_PHOTO_REQUEST:// 启动相机拍摄照片
			File tempFile = new File(Environment.getExternalStorageDirectory(),
					IMAGE_FILE_NAME);
			init();
			new MyTask().execute(tempFile.getAbsolutePath());
			break;
		case CODE_CAMERA_VIDEO_REQUEST:// 启动相机录制视频
			File tempFile2 = new File(
					Environment.getExternalStorageDirectory(), VIDEO_FILE_NAME);
			init();
			new MyTask().execute(tempFile2.getAbsolutePath());
			break;
		}

		super.onActivityResult(requestCode, resultCode, intent);
	}

	/**
	 * 开启异步任务上传图片或视频
	 * 
	 * @author Administrator
	 * 
	 */
	class MyTask extends AsyncTask<String, Integer, String> {
		public static final long MAX_VIDEO_SIZE = 50 * 1024 * 1024;
		public static final long MAX_PICTURE_SIZE = 3 * 1024 * 1024;

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result == null) {
				alertdialog.dismiss();
			}
		}

		@Override
		protected void onPreExecute() {
			mTvProgress.setText("loading...");
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			mPgBar.setProgress(values[0]);
			mTvProgress.setText("loading..." + values[0] + "%");
		}

		@Override
		protected String doInBackground(String... params) {
			try {
				// 获取path内容
				String path = params[0];
				// 上传图片
				if (path.endsWith(".jpg") || path.endsWith(".jpeg")
						|| path.endsWith(".png")) {
					File f = new File(path);
					Socket socket = new Socket(Tools.IP, Tools.PORT_3);
					InputStream in = socket.getInputStream();
					OutputStream out = socket.getOutputStream();
					// 客户端:就这种类型的文件,我要上传
					out.write(("upload,image," + f.length()).getBytes());
					out.flush();
					// 客户端:收到你发的名字了
					byte[] b = new byte[1024 * 10];
					in.read(b);
					String serverInfo = new String(b).trim();
					imgname = serverInfo.split(",")[1];
					// 客户端:我要往你服务器那发了
					FileInputStream fin = new FileInputStream(f);
					long total = fin.available();
					int len = 0;
					int num = 0;
					while ((len = fin.read(b)) != -1) {
						out.write(b, 0, len);
						num += len;
						publishProgress((int) ((num / (float) total) * 100));
						out.flush();
					}
					fin.close();
					out.close();
					in.close();
					// 上传到数据库
					if (helpid == null) {
						helpid = "0";
					}
					MainClientService.chat(Tools.userid, Tools.chatted_userid,
							helpid, imgname, Integer.parseInt(Tools.userid));
				} else if (path.endsWith(".mp4") || path.endsWith(".3pg")) {
					File f = new File(path);
					Socket socket = new Socket(Tools.IP, Tools.PORT_3);
					InputStream in = socket.getInputStream();
					OutputStream out = socket.getOutputStream();
					// 客户端:就这种类型的文件,我要上传
					out.write(("upload,video," + f.length()).getBytes());
					out.flush();
					// 客户端:收到你发的名字了
					byte[] b = new byte[1024 * 10];
					in.read(b);
					String serverInfo = new String(b).trim();
					videoname = serverInfo.split(",")[1];
					// 客户端:我要往你服务器那发了
					FileInputStream fin = new FileInputStream(f);
					long total = fin.available();
					int len = 0;
					int num = 0;
					while ((len = fin.read(b)) != -1) {
						out.write(b, 0, len);
						num += len;
						publishProgress((int) ((num / (float) total) * 100));
						out.flush();
					}
					fin.close();
					out.close();
					in.close();
					// 上传到数据库
					if (helpid == null) {
						helpid = "0";
					}
					MainClientService.chat(Tools.userid, Tools.chatted_userid,
							helpid, videoname, Integer.parseInt(Tools.userid));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;

		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		// super.onBackPressed();
		if (flag) {
			showDingDanDialog();
		} else {
			// 发送广播
			Bundle b = new Bundle();
			b.putString("jumpid", "3");
			Intent i = new Intent("com.angel.Android.Jump");
			i.putExtras(b);
			sendBroadcast(i);
			finish();
		}
	}
}

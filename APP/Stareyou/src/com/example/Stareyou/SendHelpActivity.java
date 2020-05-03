package com.example.Stareyou;

import java.io.File;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.example.Stareyou.Media.MediaUtils;
import com.example.Stareyou.Media.UpLoadMediaService.MyBinner;
import com.example.Stareyou.server.MainClientService;
import com.example.Stareyou.util.Tools;

public class SendHelpActivity extends Activity {
	// 获取地理位置信息
	private LocationClient mLocationClient;
	private BDLocationListener mBDLocationListener;
	// 记录即时位置信息
	private String locationdetail;

	// 正在获取位置dialog
	private ProgressDialog progressDialog = null;

	// 主要控件
	private EditText input_title_help;
	private EditText input_content_help;
	private ImageView send_help_else;
	private ImageView location_img;
	private EditText getshouji;
	private TextView location_msg_text;
	private Button send_help_btn;
	private ImageView s_back;
	// 切换位置图片的标识符
	private int flag = 0;

	// 从底端弹出对话框
	private Dialog dialog;
	private View inflate;
	private TextView sendPhoto;
	private TextView sendVideo;
	private TextView choosePic;
	private TextView cancel;
	/* 请求识别码 */
	private static final int CODE_GALLERY_REQUEST = 0xa0;
	private static final int CODE_CAMERA_PHOTO_REQUEST = 0xa1;
	private static final int CODE_CAMERA_VIDEO_REQUEST = 0xa2;
	// 添加照片和视频的控件
	private ImageView send_help_else_photo;
	private ImageView send_help_else_video;
	private FrameLayout send_help_frame;
	// 添加临时资源的路径
	private String resourcepath;
	// 上传到数据库的资源名字
	private static String resourcename = "";
	private static String imgname = "";
	private static String videoname = "";
	// 上传头像的名字
	private String usericon = "";

	// 拍摄的照片名字
	private String IMAGE_FILE_NAME = "temp.jpg";
	// 录制的视频名字
	private String VIDEO_FILE_NAME = "temp.mp4";

	// 绑定服务
	private MyConn conn;
	private MyBinner binner;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sendhelp);
		initWidget();
		// 如下两行代码解决socket连接不上的问题
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		// 声明LocationClient类
		mLocationClient = new LocationClient(getApplicationContext());
		mBDLocationListener = new MyBDLocationListener();
		// 注册监听
		mLocationClient.registerLocationListener(mBDLocationListener);
	}

	/** 获得所在位置经纬度及详细地址 */
	public void getLocation() {
		// 声明定位参数
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// 设置定位模式 高精度
		option.setCoorType("bd09ll");// 设置返回定位结果是百度经纬度 默认gcj02
		option.setScanSpan(5000);// 设置发起定位请求的时间间隔 单位ms
		option.setIsNeedAddress(true);// 设置定位结果包含地址信息
		option.setNeedDeviceDirect(true);// 设置定位结果包含手机机头 的方向
		// 设置定位参数
		mLocationClient.setLocOption(option);
		// 启动定位
		mLocationClient.start();

	}

	@Override
	protected void onDestroy() {
		// 取消监听函数
		if (mLocationClient != null) {
			mLocationClient.unRegisterLocationListener(mBDLocationListener);
		}
		if (!"".equals(resourcename) || !"".equals(imgname)
				|| !"".equals(videoname)) {
			resourcename = "";
			imgname = "";
			videoname = "";
		}
		super.onDestroy();

	}

	class MyBDLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// 非空判断
			if (location != null) {
				// 根据BDLocation 对象获得经纬度以及详细地址信息
				double latitude = location.getLatitude();
				double longitude = location.getLongitude();
				locationdetail = location.getAddrStr();
				location_msg_text.setText(locationdetail);
				hideDialog();
				if (mLocationClient.isStarted()) {
					// 获得位置之后停止定位
					mLocationClient.stop();
				}
			}
		}
	}

	// 初始化控件
	public void initWidget() {
		input_title_help = (EditText) findViewById(R.id.input_title_help);
		input_content_help = (EditText) findViewById(R.id.input_content_help);
		send_help_else = (ImageView) findViewById(R.id.send_help_else);
		getshouji = (EditText) findViewById(R.id.getshouji);
		send_help_btn = (Button) findViewById(R.id.send_help_btn);
		s_back = (ImageView) findViewById(R.id.s_back);
		if (!Tools.phone.equals("")) {
			getshouji.setText(Tools.phone);
		}
		location_img = (ImageView) findViewById(R.id.location_img);
		location_msg_text = (TextView) findViewById(R.id.location_msg_text);
		send_help_frame = (FrameLayout) findViewById(R.id.send_help_frame);
		// 按钮变颜色
		input_title_help.addTextChangedListener(mTextWatcher);
		input_content_help.addTextChangedListener(mTextWatcher);
		// 所添加的一个照片一部视频的控件
		send_help_else_photo = (ImageView) findViewById(R.id.send_help_else_photo);
		send_help_else_video = (ImageView) findViewById(R.id.send_help_else_video);
		send_help_else.setOnClickListener(new SendElseOnclickListener());
		location_img.setOnClickListener(new LocationOnclickListener());
		send_help_btn.setOnClickListener(new SendOnclickListener());
		s_back.setOnClickListener(new SbackOnclickListener());

	}

	/**
	 * 按钮变颜色
	 */
	TextWatcher mTextWatcher = new TextWatcher() {
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			if (s.toString() != "") {
				send_help_btn.setBackgroundColor(getResources().getColor(
						R.color.color_text_radio));

			} else {
				send_help_btn.setBackgroundColor(getResources().getColor(
						R.color.loan_butBackground));
			}

		}

		@Override
		public void afterTextChanged(Editable s) {
			if (TextUtils.isEmpty(input_title_help.getText().toString())
					|| TextUtils.isEmpty(input_content_help.getText()
							.toString())) {
				send_help_btn.setBackgroundColor(getResources().getColor(
						R.color.loan_butBackground));
			} else {
				send_help_btn.setBackgroundColor(getResources().getColor(
						R.color.color_text_radio));
			}
		}
	};

	// 为send_help_else添加点击事件
	class SendElseOnclickListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			showDialog();
		}

	}

	// 为location_img添加点击事件
	class LocationOnclickListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			if (flag % 2 == 0) {
				location_img.setImageDrawable(getResources().getDrawable(
						R.drawable.on));
				flag = flag + 1;
				showLocationDialog();
				// 显示具体位置信息
				getLocation();
			} else {
				location_img.setImageDrawable(getResources().getDrawable(
						R.drawable.off));
				flag = flag + 1;
				// 不显示具体位置信息
				location_msg_text.setText("");
			}

		}

	}

	// 为 send_help_btn添加点击事件
	class SendOnclickListener implements OnClickListener {
		@Override
		public void onClick(View arg0) {
			help();
		}
	}

	// 为s_back添加点击事件
	class SbackOnclickListener implements OnClickListener {

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

	// 显示底部弹出对话框
	public void showDialog() {
		dialog = new Dialog(this, R.style.ActionSheetDialogStyle);
		// 填充对话框的布局
		inflate = LayoutInflater.from(this).inflate(R.layout.bottom_send, null);
		// 初始化控件
		bindDialogEvent();
		// 将布局设置给Dialog
		dialog.setContentView(inflate);
		// 获取当前Activity所在的窗体
		Window dialogWindow = dialog.getWindow();
		// 设置Dialog从窗体底部弹出
		dialogWindow.setGravity(Gravity.BOTTOM);
		// 获得窗体的属性
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		lp.y = 20;// 设置Dialog距离底部的距离
		// 将属性设置给窗体
		dialogWindow.setAttributes(lp);
		dialog.show();// 显示对话框
	}

	// 初始化底部对话框组件
	private void bindDialogEvent() {
		choosePic = (TextView) inflate.findViewById(R.id.choosePic);
		sendVideo = (TextView) inflate.findViewById(R.id.sendVideo);
		sendPhoto = (TextView) inflate.findViewById(R.id.sendPhoto);
		cancel = (TextView) inflate.findViewById(R.id.sendcancel);

		// 从相册中选取
		choosePic.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				chooseFromGrally();
				dialog.dismiss();
			}
		});
		// 从摄像头中拍照
		sendPhoto.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				chooseFromCameraPhoto();
				dialog.dismiss();
			}
		});
		// 从摄像头中录像
		sendVideo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				chooseFromCameraVideo();
				dialog.dismiss();

			}
		});
		// 取消
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
			}
		});
	}

	// 从相册中选取图片或者视频
	public void chooseFromGrally() {
		Intent intentFromGallery = new Intent();
		// 设置文件类型
		intentFromGallery.setType("video/*;image/*");
		intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(intentFromGallery, CODE_GALLERY_REQUEST);
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

	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {

		// 用户没有进行有效的设置操作，返回
		if (resultCode == RESULT_CANCELED) {
			Toast.makeText(getApplication(), "取消", Toast.LENGTH_LONG).show();
			return;
		}

		switch (requestCode) {
		case CODE_GALLERY_REQUEST:// 从相册中选取资源
			Uri selectedVideo = intent.getData();
			String[] filePathColumn = { MediaStore.Video.Media.DATA };
			Cursor cursor = getContentResolver().query(selectedVideo,
					filePathColumn, null, null, null);
			cursor.moveToFirst();
			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			resourcepath = cursor.getString(columnIndex);
			if (resourcepath.endsWith(".mp4") || resourcepath.endsWith(".3gp")) {
				Intent i = new Intent("com.angel.Android.UpLoad");
				Bundle b = new Bundle();
				b.putString("resourcepath", resourcepath);
				i.putExtras(b);
				conn = new MyConn();
				bindService(i, conn, BIND_AUTO_CREATE);
				Log.i("videotest", resourcepath);
				send_help_frame.setVisibility(View.VISIBLE);
				send_help_else_video.setImageBitmap(MediaUtils
						.getVideoThumbnail(resourcepath, 50, 50));
			} else if (resourcepath.endsWith(".jpg")
					|| resourcepath.endsWith(".png")
					|| resourcepath.endsWith(".jpeg")) {
				Intent i = new Intent("com.angel.Android.UpLoad");
				Bundle b = new Bundle();
				b.putString("resourcepath", resourcepath);
				i.putExtras(b);
				conn = new MyConn();
				bindService(i, conn, BIND_AUTO_CREATE);
				Log.i("imgtest", resourcepath);
				send_help_else_photo.setVisibility(View.VISIBLE);
				send_help_else_photo.setImageBitmap(MediaUtils
						.getLocalImageThumbnail(resourcepath,
								SendHelpActivity.this, 50, 50));
			}
			cursor.close();
			break;

		case CODE_CAMERA_PHOTO_REQUEST:// 启动相机拍摄照片
			File tempFile = new File(Environment.getExternalStorageDirectory(),
					IMAGE_FILE_NAME);
			resourcepath = tempFile.getAbsolutePath();
			Intent i = new Intent("com.angel.Android.UpLoad");
			Bundle b = new Bundle();
			b.putString("resourcepath", resourcepath);
			i.putExtras(b);
			conn = new MyConn();
			bindService(i, conn, BIND_AUTO_CREATE);
			send_help_else_photo.setVisibility(View.VISIBLE);
			send_help_else_photo.setImageBitmap(MediaUtils
					.getLocalImageThumbnail(resourcepath,
							SendHelpActivity.this, 50, 50));
			break;
		case CODE_CAMERA_VIDEO_REQUEST:// 启动相机录制视频
			File tempFile2 = new File(
					Environment.getExternalStorageDirectory(), VIDEO_FILE_NAME);
			resourcepath = tempFile2.getAbsolutePath();
			Intent i2 = new Intent("com.angel.Android.UpLoad");
			Bundle b2 = new Bundle();
			b2.putString("resourcepath", resourcepath);
			i2.putExtras(b2);
			conn = new MyConn();
			bindService(i2, conn, BIND_AUTO_CREATE);
			send_help_frame.setVisibility(View.VISIBLE);
			send_help_else_video.setImageBitmap(MediaUtils.getVideoThumbnail(
					resourcepath, 50, 50));
			break;
		}

		super.onActivityResult(requestCode, resultCode, intent);
	}

	// 显示dialog
	public void showLocationDialog() {
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(SendHelpActivity.this);
		}
		progressDialog.setMessage(getResources()
				.getString(R.string.huoquweizhi));
		progressDialog.show();

	}

	// 隐藏dialog
	public void hideDialog() {
		if (progressDialog != null) {
			progressDialog.dismiss();
		}

	}

	// 发布
	public void help() {
		String help_title = input_title_help.getText().toString();
		String help_content = input_content_help.getText().toString()
				+ resourcename;
		System.out.println("help_title-------->" + help_title);
		if (help_title.equals("") || help_content.equals("")) {
			Toast.makeText(SendHelpActivity.this, "请填写完整需求", Toast.LENGTH_LONG)
					.show();
		} else {
			usericon = MediaUtils.UpLoadUserIcno();
			if (!usericon.equals("")) {
				String msg = MainClientService.help(Tools.userid,
						Tools.username, help_title, help_content, 0, usericon,
						locationdetail);
				if (msg.equals("发表成功")) {
					Toast.makeText(SendHelpActivity.this, "发表成功",
							Toast.LENGTH_LONG).show();
					// 发送广播
					Bundle b = new Bundle();
					b.putString("jumpid", "0");
					Intent i = new Intent("com.angel.Android.Jump");
					i.putExtras(b);
					sendBroadcast(i);
					finish();
				} else {
					Toast.makeText(SendHelpActivity.this, "发表失败,请重新发表",
							Toast.LENGTH_LONG).show();
				}
			} else {
				Toast.makeText(SendHelpActivity.this, "服务器出错",
						Toast.LENGTH_LONG).show();
			}

		}

	}

	public static Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (((String) msg.obj).contains(".jpg")
					|| ((String) msg.obj).contains(".png")) {
				imgname = ((String) msg.obj);
			}
			if (((String) msg.obj).contains(".mp4")
					|| ((String) msg.obj).contains(".3gp")) {
				videoname = ((String) msg.obj);
			}
			resourcename = "%%" + imgname + "%%" + videoname;
			Log.i("getname", resourcename);
		};
	};

	// 当服务被连接的时候调用 服务成功 绑定的时候调用
	private class MyConn implements ServiceConnection {
		// 4. 当服务被连接的时候调用 服务别成功 绑定的时候调用
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			binner = (MyBinner) service;
			if (binner != null) {
				binner.startUpLoad(SendHelpActivity.this);
				unbindService(conn);

			}
		}

		// 当服务失去连接的时候调用（一般进程挂了，服务被异常杀死）
		@Override
		public void onServiceDisconnected(ComponentName name) {
		}
	}
}

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
	// ��ȡ����λ����Ϣ
	private LocationClient mLocationClient;
	private BDLocationListener mBDLocationListener;
	// ��¼��ʱλ����Ϣ
	private String locationdetail;

	// ���ڻ�ȡλ��dialog
	private ProgressDialog progressDialog = null;

	// ��Ҫ�ؼ�
	private EditText input_title_help;
	private EditText input_content_help;
	private ImageView send_help_else;
	private ImageView location_img;
	private EditText getshouji;
	private TextView location_msg_text;
	private Button send_help_btn;
	private ImageView s_back;
	// �л�λ��ͼƬ�ı�ʶ��
	private int flag = 0;

	// �ӵ׶˵����Ի���
	private Dialog dialog;
	private View inflate;
	private TextView sendPhoto;
	private TextView sendVideo;
	private TextView choosePic;
	private TextView cancel;
	/* ����ʶ���� */
	private static final int CODE_GALLERY_REQUEST = 0xa0;
	private static final int CODE_CAMERA_PHOTO_REQUEST = 0xa1;
	private static final int CODE_CAMERA_VIDEO_REQUEST = 0xa2;
	// �����Ƭ����Ƶ�Ŀؼ�
	private ImageView send_help_else_photo;
	private ImageView send_help_else_video;
	private FrameLayout send_help_frame;
	// �����ʱ��Դ��·��
	private String resourcepath;
	// �ϴ������ݿ����Դ����
	private static String resourcename = "";
	private static String imgname = "";
	private static String videoname = "";
	// �ϴ�ͷ�������
	private String usericon = "";

	// �������Ƭ����
	private String IMAGE_FILE_NAME = "temp.jpg";
	// ¼�Ƶ���Ƶ����
	private String VIDEO_FILE_NAME = "temp.mp4";

	// �󶨷���
	private MyConn conn;
	private MyBinner binner;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sendhelp);
		initWidget();
		// �������д�����socket���Ӳ��ϵ�����
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		// ����LocationClient��
		mLocationClient = new LocationClient(getApplicationContext());
		mBDLocationListener = new MyBDLocationListener();
		// ע�����
		mLocationClient.registerLocationListener(mBDLocationListener);
	}

	/** �������λ�þ�γ�ȼ���ϸ��ַ */
	public void getLocation() {
		// ������λ����
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// ���ö�λģʽ �߾���
		option.setCoorType("bd09ll");// ���÷��ض�λ����ǰٶȾ�γ�� Ĭ��gcj02
		option.setScanSpan(5000);// ���÷���λ�����ʱ���� ��λms
		option.setIsNeedAddress(true);// ���ö�λ���������ַ��Ϣ
		option.setNeedDeviceDirect(true);// ���ö�λ��������ֻ���ͷ �ķ���
		// ���ö�λ����
		mLocationClient.setLocOption(option);
		// ������λ
		mLocationClient.start();

	}

	@Override
	protected void onDestroy() {
		// ȡ����������
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
			// �ǿ��ж�
			if (location != null) {
				// ����BDLocation �����þ�γ���Լ���ϸ��ַ��Ϣ
				double latitude = location.getLatitude();
				double longitude = location.getLongitude();
				locationdetail = location.getAddrStr();
				location_msg_text.setText(locationdetail);
				hideDialog();
				if (mLocationClient.isStarted()) {
					// ���λ��֮��ֹͣ��λ
					mLocationClient.stop();
				}
			}
		}
	}

	// ��ʼ���ؼ�
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
		// ��ť����ɫ
		input_title_help.addTextChangedListener(mTextWatcher);
		input_content_help.addTextChangedListener(mTextWatcher);
		// ����ӵ�һ����Ƭһ����Ƶ�Ŀؼ�
		send_help_else_photo = (ImageView) findViewById(R.id.send_help_else_photo);
		send_help_else_video = (ImageView) findViewById(R.id.send_help_else_video);
		send_help_else.setOnClickListener(new SendElseOnclickListener());
		location_img.setOnClickListener(new LocationOnclickListener());
		send_help_btn.setOnClickListener(new SendOnclickListener());
		s_back.setOnClickListener(new SbackOnclickListener());

	}

	/**
	 * ��ť����ɫ
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

	// Ϊsend_help_else��ӵ���¼�
	class SendElseOnclickListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			showDialog();
		}

	}

	// Ϊlocation_img��ӵ���¼�
	class LocationOnclickListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			if (flag % 2 == 0) {
				location_img.setImageDrawable(getResources().getDrawable(
						R.drawable.on));
				flag = flag + 1;
				showLocationDialog();
				// ��ʾ����λ����Ϣ
				getLocation();
			} else {
				location_img.setImageDrawable(getResources().getDrawable(
						R.drawable.off));
				flag = flag + 1;
				// ����ʾ����λ����Ϣ
				location_msg_text.setText("");
			}

		}

	}

	// Ϊ send_help_btn��ӵ���¼�
	class SendOnclickListener implements OnClickListener {
		@Override
		public void onClick(View arg0) {
			help();
		}
	}

	// Ϊs_back��ӵ���¼�
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

	// ��ʾ�ײ������Ի���
	public void showDialog() {
		dialog = new Dialog(this, R.style.ActionSheetDialogStyle);
		// ���Ի���Ĳ���
		inflate = LayoutInflater.from(this).inflate(R.layout.bottom_send, null);
		// ��ʼ���ؼ�
		bindDialogEvent();
		// ���������ø�Dialog
		dialog.setContentView(inflate);
		// ��ȡ��ǰActivity���ڵĴ���
		Window dialogWindow = dialog.getWindow();
		// ����Dialog�Ӵ���ײ�����
		dialogWindow.setGravity(Gravity.BOTTOM);
		// ��ô��������
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		lp.y = 20;// ����Dialog����ײ��ľ���
		// ���������ø�����
		dialogWindow.setAttributes(lp);
		dialog.show();// ��ʾ�Ի���
	}

	// ��ʼ���ײ��Ի������
	private void bindDialogEvent() {
		choosePic = (TextView) inflate.findViewById(R.id.choosePic);
		sendVideo = (TextView) inflate.findViewById(R.id.sendVideo);
		sendPhoto = (TextView) inflate.findViewById(R.id.sendPhoto);
		cancel = (TextView) inflate.findViewById(R.id.sendcancel);

		// �������ѡȡ
		choosePic.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				chooseFromGrally();
				dialog.dismiss();
			}
		});
		// ������ͷ������
		sendPhoto.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				chooseFromCameraPhoto();
				dialog.dismiss();
			}
		});
		// ������ͷ��¼��
		sendVideo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				chooseFromCameraVideo();
				dialog.dismiss();

			}
		});
		// ȡ��
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
			}
		});
	}

	// �������ѡȡͼƬ������Ƶ
	public void chooseFromGrally() {
		Intent intentFromGallery = new Intent();
		// �����ļ�����
		intentFromGallery.setType("video/*;image/*");
		intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(intentFromGallery, CODE_GALLERY_REQUEST);
	}

	// ������ͷ������
	public void chooseFromCameraPhoto() {
		Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri
				.fromFile(new File(Environment.getExternalStorageDirectory(),
						IMAGE_FILE_NAME)));
		startActivityForResult(intentFromCapture, CODE_CAMERA_PHOTO_REQUEST);
	}

	// ������ͷ��¼��
	public void chooseFromCameraVideo() {
		Intent intentFromCapture = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
		intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri
				.fromFile(new File(Environment.getExternalStorageDirectory(),
						VIDEO_FILE_NAME)));
		startActivityForResult(intentFromCapture, CODE_CAMERA_VIDEO_REQUEST);
	}

	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {

		// �û�û�н�����Ч�����ò���������
		if (resultCode == RESULT_CANCELED) {
			Toast.makeText(getApplication(), "ȡ��", Toast.LENGTH_LONG).show();
			return;
		}

		switch (requestCode) {
		case CODE_GALLERY_REQUEST:// �������ѡȡ��Դ
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

		case CODE_CAMERA_PHOTO_REQUEST:// �������������Ƭ
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
		case CODE_CAMERA_VIDEO_REQUEST:// �������¼����Ƶ
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

	// ��ʾdialog
	public void showLocationDialog() {
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(SendHelpActivity.this);
		}
		progressDialog.setMessage(getResources()
				.getString(R.string.huoquweizhi));
		progressDialog.show();

	}

	// ����dialog
	public void hideDialog() {
		if (progressDialog != null) {
			progressDialog.dismiss();
		}

	}

	// ����
	public void help() {
		String help_title = input_title_help.getText().toString();
		String help_content = input_content_help.getText().toString()
				+ resourcename;
		System.out.println("help_title-------->" + help_title);
		if (help_title.equals("") || help_content.equals("")) {
			Toast.makeText(SendHelpActivity.this, "����д��������", Toast.LENGTH_LONG)
					.show();
		} else {
			usericon = MediaUtils.UpLoadUserIcno();
			if (!usericon.equals("")) {
				String msg = MainClientService.help(Tools.userid,
						Tools.username, help_title, help_content, 0, usericon,
						locationdetail);
				if (msg.equals("����ɹ�")) {
					Toast.makeText(SendHelpActivity.this, "����ɹ�",
							Toast.LENGTH_LONG).show();
					// ���͹㲥
					Bundle b = new Bundle();
					b.putString("jumpid", "0");
					Intent i = new Intent("com.angel.Android.Jump");
					i.putExtras(b);
					sendBroadcast(i);
					finish();
				} else {
					Toast.makeText(SendHelpActivity.this, "����ʧ��,�����·���",
							Toast.LENGTH_LONG).show();
				}
			} else {
				Toast.makeText(SendHelpActivity.this, "����������",
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

	// ���������ӵ�ʱ����� ����ɹ� �󶨵�ʱ�����
	private class MyConn implements ServiceConnection {
		// 4. ���������ӵ�ʱ����� �����ɹ� �󶨵�ʱ�����
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			binner = (MyBinner) service;
			if (binner != null) {
				binner.startUpLoad(SendHelpActivity.this);
				unbindService(conn);

			}
		}

		// ������ʧȥ���ӵ�ʱ����ã�һ����̹��ˣ������쳣ɱ����
		@Override
		public void onServiceDisconnected(ComponentName name) {
		}
	}
}

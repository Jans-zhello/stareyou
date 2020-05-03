package com.example.Stareyou;

import java.io.File;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Stareyou.Exit.BaseActivity;
import com.example.Stareyou.Media.MediaUtils;
import com.example.Stareyou.homelayout.AccountFragment;
import com.example.Stareyou.model.Users;
import com.example.Stareyou.server.MainClientService;
import com.example.Stareyou.util.SaveLoginUserUtils;
import com.example.Stareyou.util.Tools;

public class LoginActivity extends BaseActivity {
	private ImageView dl_back;
	private TextView new_user;
	private ImageView img_user_head;
	private Button login;
	private EditText account_edit;
	private EditText password_edit;
	private TextView forget_password;
	private ProgressDialog progressDialog = null;

	private List<Users> u;
	private boolean flag = true;// 判断是否存在头像
	// 底部弹出对话框
	private View inflate;
	private TextView choosePhoto;
	private TextView takePhoto;
	private TextView cancel;
	private Dialog dialog;

	/* 头像文件 */
	private static final String IMAGE_FILE_NAME = "temp_head_image.jpg";
	public static Bitmap photo;
	/* 请求识别码 */
	private static final int CODE_GALLERY_REQUEST = 0xa0;
	private static final int CODE_CAMERA_REQUEST = 0xa1;
	private static final int CODE_RESULT_REQUEST = 0xa2;

	// 裁剪后图片的宽(X)和高(Y),480 X 480的正方形。
	private static int output_X = 80;
	private static int output_Y = 80;

	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		HandlerIntentValue();
		initWidget();
		// 如下两行代码解决socket连接不上的问题
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
	}

	// 获取RegisterActivity的Intent过来的值并处理
	public void HandlerIntentValue() {
		String userid = this.getIntent().getStringExtra("userid");
		String username = this.getIntent().getStringExtra("username");
		String password = this.getIntent().getStringExtra("password");
		String phone = this.getIntent().getStringExtra("phone");
		if (userid == null && username == null && password == null) {
			u = new SaveLoginUserUtils(this).getAllUsers();
			Tools.userid = u.get(0).getUserid() + "";
			Tools.username = u.get(0).getUsername();
			Tools.phone = u.get(0).getPhone();
			Tools.password = u.get(0).getPassword();
			Tools.usericon = MediaUtils.Bytes2Bimap(u.get(0).getUsericon());
		} else {
			Tools.userid = userid;
			Tools.username = username;
			Tools.password = password;
			Tools.phone = phone;
		}

	}

	// 初始化控件
	public void initWidget() {
		new_user = (TextView) findViewById(R.id.new_user);
		img_user_head = (ImageView) findViewById(R.id.img_user_head);
		account_edit = (EditText) findViewById(R.id.account);
		password_edit = (EditText) findViewById(R.id.password);
		login = (Button) findViewById(R.id.login);
		forget_password = (TextView) findViewById(R.id.forget_password);
		dl_back = (ImageView) findViewById(R.id.dl_back);
		dl_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		account_edit.setText(Tools.userid);
		if (Tools.usericon != null) {
			img_user_head.setImageBitmap(Tools.usericon);
			flag = false;
		}
		// 按钮变颜色
		password_edit.addTextChangedListener(mTextWatcher);

		new_user.setOnClickListener(new NewOnclickListener());
		forget_password.setOnClickListener(new ForgetPasswordOnclickListener());
		img_user_head.setOnClickListener(new ImgUserHeadOnclickListener());
		login.setOnClickListener(new LoginOnclickListener());
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
				login.setBackgroundColor(getResources().getColor(
						R.color.color_text_radio));

			} else {
				login.setBackgroundColor(getResources().getColor(
						R.color.loan_butBackground));
			}

		}

		@Override
		public void afterTextChanged(Editable s) {
			if (TextUtils.isEmpty(password_edit.getText().toString())) {
				login.setBackgroundColor(getResources().getColor(
						R.color.loan_butBackground));
			} else {
				login.setBackgroundColor(getResources().getColor(
						R.color.color_text_radio));
			}
		}
	};

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		finish();
	}

	// 为new_user添加点击事件
	class NewOnclickListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
			startActivity(i);
		}

	}

	// 为forget_password添加点击事件

	class ForgetPasswordOnclickListener implements OnClickListener {
		@Override
		public void onClick(View arg0) {
			if (u == null) {
				showWaitingDialog();
				String msg = MainClientService.findPassword(Tools.userid);
				if (msg == null || msg.contains("查找失败,服务器出错")) {
					Toast.makeText(LoginActivity.this, "服务器端出错",
							Toast.LENGTH_LONG).show();
					hideWaitingDialog();
				} else {
					String[] s = msg.split("\\*");
					String password = s[1];
					AlertDialog.Builder bb = new AlertDialog.Builder(
							LoginActivity.this);
					bb.setTitle("密码查找成功!");
					bb.setMessage("您的密码是：" + password);
					bb.setNeutralButton("马上登录",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									startActivity(new Intent(
											LoginActivity.this,
											LoginActivity.class));
								}
							});
					bb.setNegativeButton("退出",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {

									startActivity(new Intent(
											LoginActivity.this,
											LoginActivity.class));

								}
							});
					bb.create().show();
					hideWaitingDialog();

				}

			} else if (u != null) {
				showWaitingDialog();
				String find_password = u.get(0).getPassword();
				AlertDialog.Builder bb = new AlertDialog.Builder(
						LoginActivity.this);
				bb.setTitle("密码查找成功!");
				bb.setMessage("您的密码是：" + find_password);
				bb.setNeutralButton("马上登录",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								startActivity(new Intent(LoginActivity.this,
										LoginActivity.class));
							}
						});

				bb.setNegativeButton("退出",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								startActivity(new Intent(LoginActivity.this,
										LoginActivity.class));
							}
						});
				bb.create().show();
				hideWaitingDialog();
			}

		}
	}

	// 为img_user_head添加头像
	class ImgUserHeadOnclickListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			showDialog();
		}
	}

	// 为login添加点击事件
	class LoginOnclickListener implements OnClickListener {

		public void onClick(View arg0) {

			if (password_edit.getText().toString().trim().equals("")
					|| password_edit.getText().toString().trim() == null) {
				Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_LONG)
						.show();
			} else if (flag) {
				Toast.makeText(LoginActivity.this, "请添加头像", Toast.LENGTH_LONG)
						.show();
			} else {// 准备登录
				showProcessDialog();
				String msg = MainClientService.login(account_edit.getText()
						.toString().trim(), password_edit.getText().toString()
						.trim());
				if (msg == null) {
					hideProcessDialog();
					Toast.makeText(LoginActivity.this, "服务器端出错",
							Toast.LENGTH_LONG).show();
				} else if (msg.equals("您尚未注册")) {
					hideProcessDialog();
					Toast.makeText(LoginActivity.this, "您尚未注册",
							Toast.LENGTH_LONG).show();
				} else if (msg.equals("密码错误")) {
					hideProcessDialog();
					Toast.makeText(LoginActivity.this, "密码错误",
							Toast.LENGTH_LONG).show();
				} else {
					Tools.flag = false;
					// 保存userid,username,password,usericon到SharePreferencesUtils中
					if (u == null) {
						new SaveLoginUserUtils(LoginActivity.this).inserUser(
								account_edit.getText().toString().trim(),
								Tools.username, password_edit.getText()
										.toString().trim(), Tools.phone, photo);
					}
					MediaUtils.saveBitmapFile(Tools.usericon);
					// 发送广播
					Bundle b = new Bundle();
					b.putString("jumpid", "0");
					Intent i = new Intent("com.angel.Android.Jump");
					i.putExtras(b);
					sendBroadcast(i);
					finish();
				}
			}
		}
	}

	// 显示正在登录dialog
	public void showProcessDialog() {
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(LoginActivity.this);
		}
		progressDialog.setMessage(getResources().getString(R.string.dengluing));
		progressDialog.show();

	}

	// 隐藏正在登录dialog
	public void hideProcessDialog() {
		if (progressDialog != null) {
			progressDialog.dismiss();
		}

	}

	// 显示请稍等dialog
	public void showWaitingDialog() {
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(LoginActivity.this);
		}
		progressDialog.setMessage(getResources().getString(R.string.waiting));
		progressDialog.show();

	}

	// 隐藏请稍等dialog
	public void hideWaitingDialog() {
		if (progressDialog != null) {
			progressDialog.dismiss();
		}

	}

	// 点击头像从底部弹出对话框
	public void showDialog() {
		dialog = new Dialog(this, R.style.ActionSheetDialogStyle);
		// 填充对话框的布局
		inflate = LayoutInflater.from(this).inflate(R.layout.bottom_dialog,
				null);
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
		choosePhoto = (TextView) inflate.findViewById(R.id.choosePhoto);
		takePhoto = (TextView) inflate.findViewById(R.id.takePhoto);
		cancel = (TextView) inflate.findViewById(R.id.cancel);
		choosePhoto.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				choseHeadImageFromGallery();
				dialog.dismiss();
			}
		});
		takePhoto.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				choseHeadImageFromCameraCapture();
				dialog.dismiss();
			}
		});
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
			}
		});
	}

	// 从本地相册选取图片作为头像
	private void choseHeadImageFromGallery() {
		Intent intentFromGallery = new Intent();
		// 设置文件类型
		intentFromGallery.setType("image/*");
		intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(intentFromGallery, CODE_GALLERY_REQUEST);
	}

	// 启动手机相机拍摄照片作为头像
	private void choseHeadImageFromCameraCapture() {
		Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		// 判断存储卡是否可用，存储照片文件
		if (hasSdcard()) {
			intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri
					.fromFile(new File(Environment
							.getExternalStorageDirectory(), IMAGE_FILE_NAME)));
		}

		startActivityForResult(intentFromCapture, CODE_CAMERA_REQUEST);
	}

	// 头像设置核心处理方法
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {

		// 用户没有进行有效的设置操作，返回
		if (resultCode == RESULT_CANCELED) {
			Toast.makeText(getApplication(), "取消", Toast.LENGTH_LONG).show();
			return;
		}

		switch (requestCode) {
		case CODE_GALLERY_REQUEST:
			cropRawPhoto(intent.getData());
			break;

		case CODE_CAMERA_REQUEST:
			if (hasSdcard()) {
				File tempFile = new File(
						Environment.getExternalStorageDirectory(),
						IMAGE_FILE_NAME);
				cropRawPhoto(Uri.fromFile(tempFile));
			} else {
				Toast.makeText(getApplication(), "没有SDCard!", Toast.LENGTH_LONG)
						.show();
			}

			break;

		case CODE_RESULT_REQUEST:
			if (intent != null) {
				setImageToHeadView(intent);
			}

			break;
		}

		super.onActivityResult(requestCode, resultCode, intent);
	}

	/**
	 * 裁剪原始的图片
	 */
	public void cropRawPhoto(Uri uri) {

		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");

		// 设置裁剪
		intent.putExtra("crop", "true");

		// aspectX , aspectY :宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);

		// outputX , outputY : 裁剪图片宽高
		intent.putExtra("outputX", output_X);
		intent.putExtra("outputY", output_Y);
		intent.putExtra("return-data", true);// 回调方法data.getExtras().getParcelable("data")返回数据不为空

		startActivityForResult(intent, CODE_RESULT_REQUEST);
	}

	/**
	 * 提取保存裁剪之后的图片数据，并设置头像部分的View
	 */
	private void setImageToHeadView(Intent intent) {
		Bundle extras = intent.getExtras();
		if (extras != null) {
			photo = extras.getParcelable("data");
			Tools.usericon = photo;
			img_user_head.setImageBitmap(photo);
			flag = false;
		}
	}

	/**
	 * 检查设备是否存在SDCard的工具方法
	 */
	public static boolean hasSdcard() {
		String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			// 有存储的SDCard
			return true;
		} else {
			return false;
		}
	}

}

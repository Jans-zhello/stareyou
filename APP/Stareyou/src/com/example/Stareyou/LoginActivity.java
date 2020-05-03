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
	private boolean flag = true;// �ж��Ƿ����ͷ��
	// �ײ������Ի���
	private View inflate;
	private TextView choosePhoto;
	private TextView takePhoto;
	private TextView cancel;
	private Dialog dialog;

	/* ͷ���ļ� */
	private static final String IMAGE_FILE_NAME = "temp_head_image.jpg";
	public static Bitmap photo;
	/* ����ʶ���� */
	private static final int CODE_GALLERY_REQUEST = 0xa0;
	private static final int CODE_CAMERA_REQUEST = 0xa1;
	private static final int CODE_RESULT_REQUEST = 0xa2;

	// �ü���ͼƬ�Ŀ�(X)�͸�(Y),480 X 480�������Ρ�
	private static int output_X = 80;
	private static int output_Y = 80;

	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		HandlerIntentValue();
		initWidget();
		// �������д�����socket���Ӳ��ϵ�����
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
	}

	// ��ȡRegisterActivity��Intent������ֵ������
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

	// ��ʼ���ؼ�
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
		// ��ť����ɫ
		password_edit.addTextChangedListener(mTextWatcher);

		new_user.setOnClickListener(new NewOnclickListener());
		forget_password.setOnClickListener(new ForgetPasswordOnclickListener());
		img_user_head.setOnClickListener(new ImgUserHeadOnclickListener());
		login.setOnClickListener(new LoginOnclickListener());
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

	// Ϊnew_user��ӵ���¼�
	class NewOnclickListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
			startActivity(i);
		}

	}

	// Ϊforget_password��ӵ���¼�

	class ForgetPasswordOnclickListener implements OnClickListener {
		@Override
		public void onClick(View arg0) {
			if (u == null) {
				showWaitingDialog();
				String msg = MainClientService.findPassword(Tools.userid);
				if (msg == null || msg.contains("����ʧ��,����������")) {
					Toast.makeText(LoginActivity.this, "�������˳���",
							Toast.LENGTH_LONG).show();
					hideWaitingDialog();
				} else {
					String[] s = msg.split("\\*");
					String password = s[1];
					AlertDialog.Builder bb = new AlertDialog.Builder(
							LoginActivity.this);
					bb.setTitle("������ҳɹ�!");
					bb.setMessage("���������ǣ�" + password);
					bb.setNeutralButton("���ϵ�¼",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									startActivity(new Intent(
											LoginActivity.this,
											LoginActivity.class));
								}
							});
					bb.setNegativeButton("�˳�",
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
				bb.setTitle("������ҳɹ�!");
				bb.setMessage("���������ǣ�" + find_password);
				bb.setNeutralButton("���ϵ�¼",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								startActivity(new Intent(LoginActivity.this,
										LoginActivity.class));
							}
						});

				bb.setNegativeButton("�˳�",
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

	// Ϊimg_user_head���ͷ��
	class ImgUserHeadOnclickListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			showDialog();
		}
	}

	// Ϊlogin��ӵ���¼�
	class LoginOnclickListener implements OnClickListener {

		public void onClick(View arg0) {

			if (password_edit.getText().toString().trim().equals("")
					|| password_edit.getText().toString().trim() == null) {
				Toast.makeText(LoginActivity.this, "����������", Toast.LENGTH_LONG)
						.show();
			} else if (flag) {
				Toast.makeText(LoginActivity.this, "�����ͷ��", Toast.LENGTH_LONG)
						.show();
			} else {// ׼����¼
				showProcessDialog();
				String msg = MainClientService.login(account_edit.getText()
						.toString().trim(), password_edit.getText().toString()
						.trim());
				if (msg == null) {
					hideProcessDialog();
					Toast.makeText(LoginActivity.this, "�������˳���",
							Toast.LENGTH_LONG).show();
				} else if (msg.equals("����δע��")) {
					hideProcessDialog();
					Toast.makeText(LoginActivity.this, "����δע��",
							Toast.LENGTH_LONG).show();
				} else if (msg.equals("�������")) {
					hideProcessDialog();
					Toast.makeText(LoginActivity.this, "�������",
							Toast.LENGTH_LONG).show();
				} else {
					Tools.flag = false;
					// ����userid,username,password,usericon��SharePreferencesUtils��
					if (u == null) {
						new SaveLoginUserUtils(LoginActivity.this).inserUser(
								account_edit.getText().toString().trim(),
								Tools.username, password_edit.getText()
										.toString().trim(), Tools.phone, photo);
					}
					MediaUtils.saveBitmapFile(Tools.usericon);
					// ���͹㲥
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

	// ��ʾ���ڵ�¼dialog
	public void showProcessDialog() {
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(LoginActivity.this);
		}
		progressDialog.setMessage(getResources().getString(R.string.dengluing));
		progressDialog.show();

	}

	// �������ڵ�¼dialog
	public void hideProcessDialog() {
		if (progressDialog != null) {
			progressDialog.dismiss();
		}

	}

	// ��ʾ���Ե�dialog
	public void showWaitingDialog() {
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(LoginActivity.this);
		}
		progressDialog.setMessage(getResources().getString(R.string.waiting));
		progressDialog.show();

	}

	// �������Ե�dialog
	public void hideWaitingDialog() {
		if (progressDialog != null) {
			progressDialog.dismiss();
		}

	}

	// ���ͷ��ӵײ������Ի���
	public void showDialog() {
		dialog = new Dialog(this, R.style.ActionSheetDialogStyle);
		// ���Ի���Ĳ���
		inflate = LayoutInflater.from(this).inflate(R.layout.bottom_dialog,
				null);
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

	// �ӱ������ѡȡͼƬ��Ϊͷ��
	private void choseHeadImageFromGallery() {
		Intent intentFromGallery = new Intent();
		// �����ļ�����
		intentFromGallery.setType("image/*");
		intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(intentFromGallery, CODE_GALLERY_REQUEST);
	}

	// �����ֻ����������Ƭ��Ϊͷ��
	private void choseHeadImageFromCameraCapture() {
		Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		// �жϴ洢���Ƿ���ã��洢��Ƭ�ļ�
		if (hasSdcard()) {
			intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri
					.fromFile(new File(Environment
							.getExternalStorageDirectory(), IMAGE_FILE_NAME)));
		}

		startActivityForResult(intentFromCapture, CODE_CAMERA_REQUEST);
	}

	// ͷ�����ú��Ĵ�����
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {

		// �û�û�н�����Ч�����ò���������
		if (resultCode == RESULT_CANCELED) {
			Toast.makeText(getApplication(), "ȡ��", Toast.LENGTH_LONG).show();
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
				Toast.makeText(getApplication(), "û��SDCard!", Toast.LENGTH_LONG)
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
	 * �ü�ԭʼ��ͼƬ
	 */
	public void cropRawPhoto(Uri uri) {

		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");

		// ���òü�
		intent.putExtra("crop", "true");

		// aspectX , aspectY :��ߵı���
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);

		// outputX , outputY : �ü�ͼƬ���
		intent.putExtra("outputX", output_X);
		intent.putExtra("outputY", output_Y);
		intent.putExtra("return-data", true);// �ص�����data.getExtras().getParcelable("data")�������ݲ�Ϊ��

		startActivityForResult(intent, CODE_RESULT_REQUEST);
	}

	/**
	 * ��ȡ����ü�֮���ͼƬ���ݣ�������ͷ�񲿷ֵ�View
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
	 * ����豸�Ƿ����SDCard�Ĺ��߷���
	 */
	public static boolean hasSdcard() {
		String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			// �д洢��SDCard
			return true;
		} else {
			return false;
		}
	}

}

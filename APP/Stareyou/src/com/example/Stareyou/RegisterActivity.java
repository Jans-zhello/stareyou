package com.example.Stareyou;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import com.example.Stareyou.Exit.BaseActivity;
import com.example.Stareyou.server.MainClientService;
import com.example.Stareyou.util.Tools;
import com.mob.MobSDK;

public class RegisterActivity extends BaseActivity {
	private ImageView r_back;// ���ذ�ť

	private EditText username_edit;// �û���
	private EditText password_edit;// ����
	private RadioGroup radioGroup_gender;// �Ա�
	private EditText input_phone;// �ֻ���
	private EditText input_Code;// ��֤��
	private Button get_Code;// ��ȡ��֤��
	private Button register_btn;// ����ע��
	private TextView zongzhi;

	private String username;
	private String password;
	private String gender = "Ů";
	private String phone;
	private String code;

	private int time = 60;// 60��
	private boolean flag = true;
	private boolean flag2 = true;
	private ProgressDialog progressDialog = null;

	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		initWidget();
		// �������д�����socket���Ӳ��ϵ�����
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		/**
		 * ������֤��
		 */
		MobSDK.init(this, "242e1fac4d8f5", "deef88d4dfc79a58d49e5dbfc140993c");
		EventHandler eh = new EventHandler() {

			@Override
			public void afterEvent(int event, int result, Object data) {

				Message msg = new Message();
				msg.arg1 = event;
				msg.arg2 = result;
				msg.obj = data;
				handler.sendMessage(msg);
			}

		};
		SMSSDK.registerEventHandler(eh);
	}

	// ��֤���ͳɹ�����ʾ����
	private void reminderText() {
		handlerText.sendEmptyMessageDelayed(1, 1000);
	}

	// ʵ����֤���ѷ��Ͷ�����
	Handler handlerText = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				if (time > 0) {
					get_Code.setEnabled(false);
					get_Code.setText(time + "������·���");
					time--;
					handlerText.sendEmptyMessageDelayed(1, 1000);
				} else {
					time = 60;
					get_Code.setEnabled(true);
					get_Code.setText("��ȡ��֤��");
				}
			} else {
				get_Code.setEnabled(true);
				if (time <= 0) {
					time = 60;
				}
				get_Code.setText("��ȡ��֤��");
			}
		};
	};

	// handler����EventHandler����������msg
	Handler handler = new Handler() {

		@SuppressLint("ShowToast")
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			int event = msg.arg1;
			int result = msg.arg2;
			Object data = msg.obj;
			if (result == SMSSDK.RESULT_COMPLETE) {
				// ����ע��ɹ��󣬷���MainActivity,Ȼ����ʾ�º���
				if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {// �ύ��֤��ɹ�,��֤ͨ��
					flag = false;
					handlerText.sendEmptyMessage(2);
					// �������볤��
					if (password_edit.getText().toString().trim().length() < 6) {
						Toast.makeText(RegisterActivity.this, "���볤�Ȳ�����Ҫ��",
								Toast.LENGTH_LONG).show();
						password_edit.requestFocus();
					} else {
						/**
						 * ׼�����ݶԽ�MainClientServer
						 */
						if (!flag) {

							username = username_edit.getText().toString()
									.trim();
							password = password_edit.getText().toString()
									.trim();
							phone = input_phone.getText().toString().trim();
							showDialog();
							String returnmsg = MainClientService.register(
									username, password, phone, gender);
							System.out
									.println("RegisterActivitymsg----------->"
											+ returnmsg);
							if (returnmsg == null
									|| returnmsg.contains("����������,ע��ʧ��")) {
								hideDialog();
								Toast.makeText(RegisterActivity.this, "�������˳���",
										Toast.LENGTH_LONG).show();
							} else if (returnmsg.contains("���û�����ע��")) {
								hideDialog();
								Toast.makeText(RegisterActivity.this,
										"���û�����ע��", Toast.LENGTH_LONG).show();
							} else {
								String[] array = returnmsg.split("\\*");
								String userid = array[1];
								Intent i = new Intent(RegisterActivity.this,
										LoginActivity.class);
								i.putExtra("userid", userid);
								i.putExtra("username", username);
								i.putExtra("password", password);
								i.putExtra("phone", phone);
								startActivity(i);
								finish();
							}

						}
					}
				} else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {// ��������֤�뷢�ͳɹ�
					reminderText();
					Toast.makeText(getApplicationContext(), "��֤���Ѿ�����",
							Toast.LENGTH_SHORT).show();
					flag2 = false;
				} else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {// ����֧�ַ�����֤��Ĺ����б�
					Toast.makeText(getApplicationContext(), "��ȡ�����б�ɹ�",
							Toast.LENGTH_SHORT).show();
				}
			} else {
				if (flag && flag2) {
					((Throwable) data).printStackTrace();
					Toast.makeText(RegisterActivity.this, "��֤���ȡʧ�ܣ������»�ȡ",
							Toast.LENGTH_SHORT).show();
					input_phone.requestFocus();
				} else if (flag && !flag2) {
					((Throwable) data).printStackTrace();
					Toast.makeText(RegisterActivity.this, "��֤�����",
							Toast.LENGTH_SHORT).show();
					input_Code.selectAll();
				}

			}

		}

	};

	// ��ʼ���ؼ� ���󶨼����¼�
	public void initWidget() {
		r_back = (ImageView) findViewById(R.id.r_back);
		username_edit = (EditText) findViewById(R.id.username_edit);
		password_edit = (EditText) findViewById(R.id.password_edit);
		radioGroup_gender = (RadioGroup) findViewById(R.id.radioGroup_gender);
		input_phone = (EditText) findViewById(R.id.input_phone);
		input_Code = (EditText) findViewById(R.id.input_Code);
		get_Code = (Button) findViewById(R.id.get_Code);
		register_btn = (Button) findViewById(R.id.register_btn);
		zongzhi = (TextView) findViewById(R.id.zongzhi);

		// ��ť����ɫ
		input_phone.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (s.toString() != "") {
					get_Code.setBackgroundColor(getResources().getColor(
							R.color.color_text_radio));
				} else {
					get_Code.setBackgroundColor(getResources().getColor(
							R.color.loan_butBackground));
				}

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				if (!TextUtils.isEmpty(input_phone.getText().toString())) {
					get_Code.setBackgroundColor(getResources().getColor(
							R.color.color_text_radio));
				} else {
					get_Code.setBackgroundColor(getResources().getColor(
							R.color.loan_butBackground));
				}

			}
		});
		input_Code.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (s.toString() != "") {
					register_btn.setBackgroundColor(getResources().getColor(
							R.color.color_text_radio));
				} else {
					register_btn.setBackgroundColor(getResources().getColor(
							R.color.loan_butBackground));
				}

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				if (!TextUtils.isEmpty(input_Code.getText().toString())) {
					register_btn.setBackgroundColor(getResources().getColor(
							R.color.color_text_radio));
				} else {
					register_btn.setBackgroundColor(getResources().getColor(
							R.color.loan_butBackground));
				}

			}
		});
		// �󶨼����¼�
		get_Code.setOnClickListener(new RGOnclickListener());
		register_btn.setOnClickListener(new RGOnclickListener());
		radioGroup_gender
				.setOnCheckedChangeListener(new GenderOnCheckedChangeListener());
		r_back.setOnClickListener(new RbackOnclickListener());
		zongzhi.setOnClickListener(new ZongZhiOnclickListener());
	}

	// Ϊr_back��ӵ���¼�
	class RbackOnclickListener implements OnClickListener {

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

	// Ϊgender_radiogroup��Ӽ����¼�
	class GenderOnCheckedChangeListener implements OnCheckedChangeListener {

		@Override
		public void onCheckedChanged(RadioGroup arg0, int arg1) {
			RadioButton rb = (RadioButton) findViewById(arg0
					.getCheckedRadioButtonId());
			gender = rb.getText().toString();

		}

	}

	// Ϊ��ּ��Ӽ����¼�
	class ZongZhiOnclickListener implements OnClickListener {
		@Override
		public void onClick(View arg0) {
			startActivity(new Intent(RegisterActivity.this,
					StareyouInternetActivity.class));
		}

	}

	/**
	 * ���������д����,�Խ�MainClientServer
	 * 
	 * @author Administrator
	 * 
	 */
	// Ϊregister_btn��get_code��ӹ�ͬ����¼�
	class RGOnclickListener implements OnClickListener {

		public void onClick(View arg0) {
			switch (arg0.getId()) {
			case R.id.get_Code:
				if (!TextUtils.isEmpty(input_phone.getText().toString().trim())) {
					if (input_phone.getText().toString().trim().length() == 11) {
						phone = input_phone.getText().toString().trim();
						SMSSDK.getVerificationCode("86", phone);
						input_Code.requestFocus();
					} else {
						Toast.makeText(RegisterActivity.this, "�����������绰����",
								Toast.LENGTH_LONG).show();
						input_phone.requestFocus();
					}
				} else {
					Toast.makeText(RegisterActivity.this, "���������ĵ绰����",
							Toast.LENGTH_LONG).show();
					input_phone.requestFocus();
				}
				break;

			case R.id.register_btn:
				if ((!TextUtils
						.isEmpty(input_phone.getText().toString().trim()))
						&& (!TextUtils.isEmpty(username_edit.getText()
								.toString().trim()))
						&& (!TextUtils.isEmpty(password_edit.getText()
								.toString().trim()))
						&& (!TextUtils.isEmpty(gender.trim()))
						&& (!TextUtils.isEmpty(input_Code.getText().toString()
								.trim()))) {
					// ������֤�볤��
					if (input_Code.getText().toString().trim().length() == 4) {
						code = input_Code.getText().toString().trim();
						SMSSDK.submitVerificationCode("86", phone, code);
					} else {
						Toast.makeText(RegisterActivity.this, "������������֤��",
								Toast.LENGTH_LONG).show();
						input_Code.requestFocus();
					}

				} else {
					Toast.makeText(RegisterActivity.this, "������������Ϣ",
							Toast.LENGTH_LONG).show();
				}
				break;

			default:
				break;
			}
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		SMSSDK.unregisterAllEventHandler();
	}

	// ��ʾdialog
	public void showDialog() {
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(RegisterActivity.this);
		}
		progressDialog.setMessage(getResources().getString(R.string.zhuceing));
		progressDialog.show();

	}

	// ����dialog
	public void hideDialog() {
		if (progressDialog != null) {
			progressDialog.dismiss();
		}

	}

}

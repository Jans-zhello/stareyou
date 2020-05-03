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
	private ImageView r_back;// 返回按钮

	private EditText username_edit;// 用户名
	private EditText password_edit;// 密码
	private RadioGroup radioGroup_gender;// 性别
	private EditText input_phone;// 手机号
	private EditText input_Code;// 验证码
	private Button get_Code;// 获取验证码
	private Button register_btn;// 申请注册
	private TextView zongzhi;

	private String username;
	private String password;
	private String gender = "女";
	private String phone;
	private String code;

	private int time = 60;// 60秒
	private boolean flag = true;
	private boolean flag2 = true;
	private ProgressDialog progressDialog = null;

	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		initWidget();
		// 如下两行代码解决socket连接不上的问题
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		/**
		 * 发送验证码
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

	// 验证码送成功后提示文字
	private void reminderText() {
		handlerText.sendEmptyMessageDelayed(1, 1000);
	}

	// 实现验证码已发送多少秒
	Handler handlerText = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				if (time > 0) {
					get_Code.setEnabled(false);
					get_Code.setText(time + "秒后重新发送");
					time--;
					handlerText.sendEmptyMessageDelayed(1, 1000);
				} else {
					time = 60;
					get_Code.setEnabled(true);
					get_Code.setText("获取验证码");
				}
			} else {
				get_Code.setEnabled(true);
				if (time <= 0) {
					time = 60;
				}
				get_Code.setText("获取验证码");
			}
		};
	};

	// handler处理EventHandler反馈过来的msg
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
				// 短信注册成功后，返回MainActivity,然后提示新好友
				if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {// 提交验证码成功,验证通过
					flag = false;
					handlerText.sendEmptyMessage(2);
					// 检验密码长度
					if (password_edit.getText().toString().trim().length() < 6) {
						Toast.makeText(RegisterActivity.this, "密码长度不符合要求",
								Toast.LENGTH_LONG).show();
						password_edit.requestFocus();
					} else {
						/**
						 * 准备数据对接MainClientServer
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
									|| returnmsg.contains("服务器出错,注册失败")) {
								hideDialog();
								Toast.makeText(RegisterActivity.this, "服务器端出错",
										Toast.LENGTH_LONG).show();
							} else if (returnmsg.contains("该用户名已注册")) {
								hideDialog();
								Toast.makeText(RegisterActivity.this,
										"该用户名已注册", Toast.LENGTH_LONG).show();
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
				} else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {// 服务器验证码发送成功
					reminderText();
					Toast.makeText(getApplicationContext(), "验证码已经发送",
							Toast.LENGTH_SHORT).show();
					flag2 = false;
				} else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {// 返回支持发送验证码的国家列表
					Toast.makeText(getApplicationContext(), "获取国家列表成功",
							Toast.LENGTH_SHORT).show();
				}
			} else {
				if (flag && flag2) {
					((Throwable) data).printStackTrace();
					Toast.makeText(RegisterActivity.this, "验证码获取失败，请重新获取",
							Toast.LENGTH_SHORT).show();
					input_phone.requestFocus();
				} else if (flag && !flag2) {
					((Throwable) data).printStackTrace();
					Toast.makeText(RegisterActivity.this, "验证码错误",
							Toast.LENGTH_SHORT).show();
					input_Code.selectAll();
				}

			}

		}

	};

	// 初始化控件 并绑定监听事件
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

		// 按钮变颜色
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
		// 绑定监听事件
		get_Code.setOnClickListener(new RGOnclickListener());
		register_btn.setOnClickListener(new RGOnclickListener());
		radioGroup_gender
				.setOnCheckedChangeListener(new GenderOnCheckedChangeListener());
		r_back.setOnClickListener(new RbackOnclickListener());
		zongzhi.setOnClickListener(new ZongZhiOnclickListener());
	}

	// 为r_back添加点击事件
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

	// 为gender_radiogroup添加监听事件
	class GenderOnCheckedChangeListener implements OnCheckedChangeListener {

		@Override
		public void onCheckedChanged(RadioGroup arg0, int arg1) {
			RadioButton rb = (RadioButton) findViewById(arg0
					.getCheckedRadioButtonId());
			gender = rb.getText().toString();

		}

	}

	// 为宗旨添加监听事件
	class ZongZhiOnclickListener implements OnClickListener {
		@Override
		public void onClick(View arg0) {
			startActivity(new Intent(RegisterActivity.this,
					StareyouInternetActivity.class));
		}

	}

	/**
	 * 汇聚所有填写数据,对接MainClientServer
	 * 
	 * @author Administrator
	 * 
	 */
	// 为register_btn、get_code添加共同点击事件
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
						Toast.makeText(RegisterActivity.this, "请输入完整电话号码",
								Toast.LENGTH_LONG).show();
						input_phone.requestFocus();
					}
				} else {
					Toast.makeText(RegisterActivity.this, "请输入您的电话号码",
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
					// 检验验证码长度
					if (input_Code.getText().toString().trim().length() == 4) {
						code = input_Code.getText().toString().trim();
						SMSSDK.submitVerificationCode("86", phone, code);
					} else {
						Toast.makeText(RegisterActivity.this, "请输入完整验证码",
								Toast.LENGTH_LONG).show();
						input_Code.requestFocus();
					}

				} else {
					Toast.makeText(RegisterActivity.this, "请完善填入信息",
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

	// 显示dialog
	public void showDialog() {
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(RegisterActivity.this);
		}
		progressDialog.setMessage(getResources().getString(R.string.zhuceing));
		progressDialog.show();

	}

	// 隐藏dialog
	public void hideDialog() {
		if (progressDialog != null) {
			progressDialog.dismiss();
		}

	}

}

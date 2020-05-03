package com.example.Stareyou;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.Stareyou.Exit.BaseActivity;
import com.example.Stareyou.server.MainClientService;
import com.example.Stareyou.util.Tools;

public class SuggestionActivity extends BaseActivity {
	private ImageView suggestion_back;
	private EditText suggestion_input;
	private Button suggestion_out;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.suggestion);
		initWidget();
		// 如下两行代码解决socket连接不上的问题
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
	}

	public void initWidget() {
		suggestion_back = (ImageView) findViewById(R.id.suggestion_back);
		suggestion_input = (EditText) findViewById(R.id.suggestion_input);
		suggestion_out = (Button) findViewById(R.id.suggestion_out);
		// 按钮变颜色
		suggestion_input.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (s.toString() != "") {
					suggestion_out.setBackgroundColor(getResources().getColor(
							R.color.color_text_radio));
				} else {
					suggestion_out.setBackgroundColor(getResources().getColor(
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
				if (TextUtils.isEmpty(suggestion_input.getText().toString())) {
					suggestion_out.setBackgroundColor(getResources().getColor(
							R.color.loan_butBackground));
				} else {
					suggestion_out.setBackgroundColor(getResources().getColor(
							R.color.color_text_radio));
				}

			}
		});

		suggestion_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();

			}
		});
		suggestion_out.setOnClickListener(new SuggestionOnclickListener());
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		finish();
	}

	public void sendData() {
		String msg = suggestion_input.getText().toString();
		String backmsg = MainClientService.suggestion(Tools.userid, msg);
		Toast.makeText(SuggestionActivity.this, backmsg, Toast.LENGTH_LONG)
				.show();
		if (backmsg.equals("谢谢您的建议")) {
			finish();
		}
	}

	class SuggestionOnclickListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			sendData();
		}

	}
}

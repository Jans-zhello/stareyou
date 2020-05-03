package com.example.Stareyou;

import com.example.Stareyou.Exit.BaseActivity;
import com.example.Stareyou.model.Users;
import com.example.Stareyou.server.MainClientService;
import com.example.Stareyou.util.Tools;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ViewUserInfoActivity extends BaseActivity {
	private ImageView v_back;
	private ImageView view_user_info_icon_detail;
	private TextView view_user_info_name_detail;
	private TextView view_user_info_account_detail;
	private TextView view_user_info_sex_detail;
	private TextView view_user_info_phone_detail;
	private TextView view_user_info_date_detail;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewuserinfo);
		initWidget();
		// 如下两行代码解决socket连接不上的问题
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		setData(Tools.userid);
	}

	// 初始化控件
	public void initWidget() {
		v_back = (ImageView) this.findViewById(R.id.v_back);
		view_user_info_icon_detail = (ImageView) this
				.findViewById(R.id.view_user_info_icon_detail);
		view_user_info_name_detail = (TextView) this
				.findViewById(R.id.view_user_info_name_detail);
		view_user_info_account_detail = (TextView) this
				.findViewById(R.id.view_user_info_account_detail);
		view_user_info_sex_detail = (TextView) this
				.findViewById(R.id.view_user_info_sex_detail);
		view_user_info_phone_detail = (TextView) this
				.findViewById(R.id.view_user_info_phone_detail);
		view_user_info_date_detail = (TextView) this
				.findViewById(R.id.view_user_info_date_detail);
		v_back.setOnClickListener(new VbackOnclickLitener());
	}

	public void setData(String userid) {
		view_user_info_icon_detail.setImageBitmap(Tools.usericon);
		Users u = MainClientService.viewUsers(userid);
		view_user_info_name_detail.setText(u.getUsername());
		view_user_info_account_detail.setText(u.getUserid() + "");
		view_user_info_sex_detail.setText(u.getSex());
		view_user_info_phone_detail.setText(u.getPhone());
		view_user_info_date_detail.setText(u.getDate());
	}

	// 为v_back添加监听事件
	class VbackOnclickLitener implements OnClickListener {

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
}

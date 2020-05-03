package com.example.Stareyou.homelayout;

import java.util.Vector;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.Stareyou.LiaoTianWindowActivity;
import com.example.Stareyou.R;
import com.example.Stareyou.Adapter.LiaoTianAdapter;
import com.example.Stareyou.model.Chat;
import com.example.Stareyou.model.Help;
import com.example.Stareyou.server.MainClientService;
import com.example.Stareyou.util.Tools;

public class LiaotianFragment extends Fragment {

	private View empty_text_tip_liaotian;
	private ListView lv_liaotianmsg;
	private Vector<Chat> v;
	private View view = null;

	private static final LiaotianFragment liaotianfragment = new LiaotianFragment();

	public static LiaotianFragment newInstance() {
		return liaotianfragment;
	}

	@SuppressLint("NewApi")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.liaotian, container, false);
		lv_liaotianmsg = (ListView) view.findViewById(R.id.lv_liaotianmsg);
		empty_text_tip_liaotian = view
				.findViewById(R.id.empty_text_tip_liaotian);
		fillAdapterContent();
		// 如下两行代码解决socket连接不上的问题
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		return view;
	}

	// 填充适配器内容

	public void fillAdapterContent() {
		v = getData();
		if (v != null) {
			LiaoTianAdapter lta = new LiaoTianAdapter(v, getActivity());
			lv_liaotianmsg.setAdapter(lta);
			lv_liaotianmsg.setEmptyView(empty_text_tip_liaotian);
			lv_liaotianmsg.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					Intent i = new Intent(getActivity(),
							LiaoTianWindowActivity.class);
					i.putExtra("liaotian_id", arg3 + "");
					startActivity(i);
				}
			});
		}
	}

	// 获取数据
	public Vector<Chat> getData() {
		if (!"".equals(Tools.userid)) {
			Vector<Chat> vector = MainClientService
					.chatWindowData(Tools.userid);
			if (vector != null) {
				return vector;
			}
		}
		return null;
	}
}

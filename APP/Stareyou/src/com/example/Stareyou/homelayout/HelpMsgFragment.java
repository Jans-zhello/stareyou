package com.example.Stareyou.homelayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ListView;

import com.example.Stareyou.R;
import com.example.Stareyou.Adapter.HelpMsgAdapter;
import com.example.Stareyou.model.Help;
import com.example.Stareyou.model.Order;
import com.example.Stareyou.server.MainClientService;
import com.example.Stareyou.util.Tools;
import com.example.countdowmtimer.CountDownTimeInfo;

public class HelpMsgFragment extends Fragment {
	// 主控件
	private ListView lv;
	private View empty_text_tip_helpmsg;
	private HelpMsgAdapter la;
	private Vector<Order> v;
	// 倒计时
	List<CountDownTimeInfo> mList = new ArrayList<CountDownTimeInfo>();

	private View view = null;

	private static final HelpMsgFragment helpmsgfragment = new HelpMsgFragment();

	public static HelpMsgFragment newInstance() {
		return helpmsgfragment;
	}

	@SuppressLint("NewApi")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.helpmsg, null);
		lv = (ListView) view.findViewById(R.id.lv_helpmsg);
		empty_text_tip_helpmsg = view.findViewById(R.id.empty_text_tip_helpmsg);
		FillAdapterContent();
		// 如下两行代码解决socket连接不上的问题
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		return view;
	}

	// 填充适配器内容
	public void FillAdapterContent() {
		v = getData();
		if (v != null) {
			for (int i = 0; i < v.size(); i++) {
				CountDownTimeInfo info = new CountDownTimeInfo();
				info.time = 432000;
				mList.add(info);
			}
			if (la == null) {
				la = new HelpMsgAdapter(v, getActivity(), mList);
				lv.setAdapter(la);
			} else {
				la.UpdateUi(v);
				lv.setAdapter(la);
			}
			lv.setEmptyView(empty_text_tip_helpmsg);
		}

	}

	// 获取数据
	public Vector<Order> getData() {
		if (!"".equals(Tools.userid)) {
			Vector<Order> vector = MainClientService.viewOrder();
			if (vector != null) {
				return vector;
			}
		}
		return null;
	}
}

package com.example.Stareyou.homelayout;

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
import com.example.Stareyou.Adapter.GoodPersonAdapter;
import com.example.Stareyou.Adapter.HelpMsgAdapter;
import com.example.Stareyou.model.Send;
import com.example.Stareyou.server.MainClientService;
import com.example.Stareyou.util.Tools;

public class GoodPersonFragment extends Fragment {

	// 主控件
	private ListView lv;
	// 获取内容
	private Vector<Send> v;
	private View empty_text_tip_goodperson;
	private GoodPersonAdapter la;
	private static final GoodPersonFragment goodpersonfragment = new GoodPersonFragment();

	private View view = null;

	public static GoodPersonFragment newInstance() {
		return goodpersonfragment;
	}

	@SuppressLint("NewApi")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.goodperson, null);
		lv = (ListView) view.findViewById(R.id.lv_goodperson);
		empty_text_tip_goodperson = view
				.findViewById(R.id.empty_text_tip_goodperson);
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
			if (la == null) {
				la = new GoodPersonAdapter(v, getActivity());
				lv.setAdapter(la);
			} else {
				la.UpdateUI(v);
				lv.setAdapter(la);
			}
			lv.setEmptyView(empty_text_tip_goodperson);
		}
	}

	// 获取数据
	public Vector<Send> getData() {
		if (!"".equals(Tools.userid)) {
			Vector<Send> vector = MainClientService.getShare();
			if (vector != null) {
				return vector;
			}
		}
		return null;
	}

}

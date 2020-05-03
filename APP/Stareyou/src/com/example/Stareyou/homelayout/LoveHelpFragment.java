package com.example.Stareyou.homelayout;

import java.util.ArrayList;
import java.util.Vector;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.Stareyou.HelpDetailActivity;
import com.example.Stareyou.R;
import com.example.Stareyou.Adapter.LoveHelpAdapter;
import com.example.Stareyou.model.Help;
import com.example.Stareyou.server.MainClientService;
import com.example.Stareyou.util.Tools;

public class LoveHelpFragment extends Fragment {

	// 主控件
	private ListView lv;
	private View empty_text_tip_lovehelp;
	// 获取内容
	private Vector<Help> v;
	private ArrayList<Help> list = new ArrayList<Help>();
	private LoveHelpAdapter la;
	private static final LoveHelpFragment lovehelpfragment = new LoveHelpFragment();

	//
	private View view = null;

	public static LoveHelpFragment newInstance() {
		return lovehelpfragment;
	}

	@SuppressLint("NewApi")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.lovehelp, null);
		lv = (ListView) view.findViewById(R.id.lv_lovehelp);
		empty_text_tip_lovehelp = view
				.findViewById(R.id.empty_text_tip_lovehelp);
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
			// 遍历到list中去
			for (Help help : v) {
				list.add(help);
			}
			if (la == null) {
				la = new LoveHelpAdapter(v, getActivity());
				lv.setAdapter(la);
			} else {
				la.UpdateUI(v);
				lv.setAdapter(la);
			}
			lv.setEmptyView(empty_text_tip_lovehelp);
			lv.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// arg2是item的位置,arg3是helpid
					Intent i = new Intent(getActivity(),
							HelpDetailActivity.class);
					i.putExtra("data", list);
					i.putExtra("helpid", arg3 + "");
					startActivity(i);
				}
			});
		}

	}

	// 获取数据
	public Vector<Help> getData() {
		if (!"".equals(Tools.userid)) {
			Vector<Help> vector = MainClientService.viewHelp();
			if (vector != null) {
				return vector;
			}
		}
		return null;
	}

}

package com.example.Stareyou.homelayout;

import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ListView;
import android.widget.TextView;

import com.example.Stareyou.R;
import com.example.Stareyou.Adapter.LoveHelpAdapter;
import com.example.Stareyou.Adapter.ShareMsgAdapter;
import com.example.Stareyou.model.Action;
import com.example.Stareyou.server.MainClientService;
import com.example.Stareyou.util.Tools;

public class ShareMsgFragment extends Fragment {

	// 主控件
	private ListView lv;
	private List<Action> list;
	private View empty_text_tip_sharemsg;
	private ShareMsgAdapter la;
	private static final ShareMsgFragment shareMsgFragment = new ShareMsgFragment();
	private View view = null;

	public static ShareMsgFragment newInstance() {
		return shareMsgFragment;
	}

	@SuppressLint("NewApi")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.sharemsg, null);
		lv = (ListView) view.findViewById(R.id.lv_sharemsg);
		empty_text_tip_sharemsg = view
				.findViewById(R.id.empty_text_tip_sharemsg);
		FillAdapterContent();
		// 如下两行代码解决socket连接不上的问题
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		return view;
	}

	// 填充适配器内容
	public void FillAdapterContent() {
		list = getData();
		if (list != null) {
			if (la == null) {
				la = new ShareMsgAdapter(list, getActivity());
				lv.setAdapter(la);
			} else {
				la.UpdateUI(list);
				lv.setAdapter(la);
			}
			lv.setEmptyView(empty_text_tip_sharemsg);
		}
	}

	// 获取数据
	public List<Action> getData() {
		if (!"".equals(Tools.userid)) {
			List<Action> list = MainClientService.getNewsdata(Tools.userid);
			if (list != null) {
				return list;
			}
		}
		return null;
	}

}

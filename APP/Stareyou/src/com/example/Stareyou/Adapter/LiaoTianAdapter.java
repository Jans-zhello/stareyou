package com.example.Stareyou.Adapter;

import com.example.Stareyou.R;
import com.example.Stareyou.model.Chat;
import com.example.Stareyou.server.MainClientService;
import com.example.Stareyou.util.ShowTime;
import com.example.Stareyou.util.Tools;

import java.util.Vector;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LiaoTianAdapter extends BaseAdapter {
	private Vector<Chat> list;
	private Context context;

	public LiaoTianAdapter(Vector<Chat> v, Context context) {
		super();
		this.list = v;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list == null ? 0 : list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list == null ? null : list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0).getChatid();
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		ViewHolder holder;
		if (arg1 == null) {
			holder = new ViewHolder();
			arg1 = LayoutInflater.from(context).inflate(
					R.layout.liaotian_content, null);
			holder.liaotian__content = (TextView) arg1
					.findViewById(R.id.liaotian__content);
			holder.liaotian_username = (TextView) arg1
					.findViewById(R.id.liaotian_username);
			holder.liaotian_time = (TextView) arg1
					.findViewById(R.id.liaotian_time);
			arg1.setTag(holder);
		} else {
			holder = (ViewHolder) arg1.getTag();
		}
		// 获取内容
		Chat chat = list.get(arg0);
		// 嵌入用户名
		if (!(chat.getUserid() + "").equals(Tools.userid)) {
			holder.liaotian_username.setText(MainClientService.viewUsers(
					chat.getUserid() + "").getUsername());
		} else {
			holder.liaotian_username.setText(MainClientService.viewUsers(
					chat.getChatted_userid() + "").getUsername());
		}
		// 嵌入最新聊天记录
		holder.liaotian__content.setText(chat.getChat_content());
		// 嵌入时间
		holder.liaotian_time.setText(ShowTime.getInterval(ShowTime
				.conversion(chat.getChat_date())));

		return arg1;
	}

	class ViewHolder {
		TextView liaotian_username;
		TextView liaotian__content;
		TextView liaotian_time;
	}

}

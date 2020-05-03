package com.example.Stareyou.Adapter;

import java.util.List;
import java.util.Vector;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.Stareyou.R;
import com.example.Stareyou.model.Action;
import com.example.Stareyou.model.Help;
import com.example.Stareyou.util.ShowTime;
import com.example.countdowmtimer.CountDownTimerUtil;

public class ShareMsgAdapter extends BaseAdapter {
	private List<Action> list;
	private Context context;

	public ShareMsgAdapter(List<Action> list, Context context) {
		super();
		this.list = list;
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
		return 0;
	}

	public void UpdateUI(List<Action> l) {
		list = l;
		this.notifyDataSetChanged();
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (arg1 == null) {
			holder = new ViewHolder();
			arg1 = LayoutInflater.from(context).inflate(
					R.layout.sharemsg_content, null);
			holder.otheruser = (TextView) arg1.findViewById(R.id.otheruser);
			holder.zainidewenzhang = (TextView) arg1
					.findViewById(R.id.zainidewenzhang);
			holder.article_content = (TextView) arg1
					.findViewById(R.id.article_content);
			holder.action = (TextView) arg1.findViewById(R.id.action);
			holder.reviewcontent = (TextView) arg1
					.findViewById(R.id.reviewcontent);
			holder.time_news = (TextView) arg1.findViewById(R.id.time_news);
			arg1.setTag(holder);
		} else {
			holder = (ViewHolder) arg1.getTag();
		}
		// 加载内容
		Action a = list.get(arg0);
		if (a.getUsername() != null) {
			holder.otheruser.setText(a.getUsername());
		}
		if (a.getArticle_content() != null) {
			String content = "";
			if (a.getArticle_content().length() > 3) {
				content = a.getArticle_content().substring(0, 3) + "......";
			} else {
				content = a.getArticle_content();
			}
			holder.article_content.setText(content);
		}
		if (a.getAction() != null) {
			holder.action.setText(a.getAction());
		}
		if (a.getReview_content() != null) {
			holder.reviewcontent.setText(a.getReview_content());
		}
		if (a.getDate() != null) {
			holder.time_news.setText(ShowTime.getInterval(ShowTime.conversion(a
					.getDate())));
		}
		return arg1;
	}

	class ViewHolder {
		// 主要内容控件
		TextView otheruser;
		TextView zainidewenzhang;
		TextView article_content;
		TextView reviewcontent;
		TextView action;
		TextView time_news;
	}
}

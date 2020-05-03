package com.example.Stareyou.Adapter;

import java.util.Vector;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.Stareyou.R;
import com.example.Stareyou.model.Review;
import com.example.Stareyou.util.ShowTime;

public class LoadReviewContentAdapter extends BaseAdapter {
	private Vector<Review> list;
	private Context context;

	public LoadReviewContentAdapter(Vector<Review> list, Context context) {
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
		return list.get(arg0).getReviewid();
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (arg1 == null) {
			holder = new ViewHolder();
			arg1 = LayoutInflater.from(context).inflate(
					R.layout.loadreview_content, null);
			holder.redirect_username = (TextView) arg1
					.findViewById(R.id.redirect_username);
			holder.redirect_content = (TextView) arg1
					.findViewById(R.id.redirect_content);
			holder.redirect_time = (TextView) arg1
					.findViewById(R.id.redirect_time);
			arg1.setTag(holder);
		} else {
			holder = (ViewHolder) arg1.getTag();
		}
		// º”‘ÿ ƒ⁄»›
		Review r = list.get(arg0);
		holder.redirect_username.setText(r.getUsername() + "£∫");
		holder.redirect_content.setText(r.getReview_content());
		holder.redirect_time.setText(ShowTime.getInterval(ShowTime.conversion(r
				.getReview_date())));
		return arg1;
	}

	class ViewHolder {
		TextView redirect_username;
		TextView redirect_content;
		TextView redirect_time;
	}

}

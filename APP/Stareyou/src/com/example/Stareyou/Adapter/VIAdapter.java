package com.example.Stareyou.Adapter;

import java.util.Vector;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.Stareyou.R;
import com.example.Stareyou.Media.MediaUtils;
import com.example.Stareyou.Media.PlayMovieActivity;
import com.example.Stareyou.model.Send;
import com.example.Stareyou.util.Tools;

public class VIAdapter extends BaseAdapter {
	ViewHolder holder;
	// 获取内容
	private Vector<Send> list;
	// 获取上下文
	private Context context;

	public VIAdapter(Vector<Send> list, Context context) {
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
		return list.get(arg0).getSendid();
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		if (arg1 == null) {
			holder = new ViewHolder();
			arg1 = LayoutInflater.from(context).inflate(
					R.layout.video_img_content, null);
			holder.iv_id = (ImageView) arg1.findViewById(R.id.iv_id);
			holder.iv_loc = (TextView) arg1.findViewById(R.id.iv_loc);
			holder.video_frame = (FrameLayout) arg1
					.findViewById(R.id.video_frame);
			holder.video_list_play = (ImageView) arg1
					.findViewById(R.id.video_list_play);
			arg1.setTag(holder);
		} else {
			holder = (ViewHolder) arg1.getTag();
		}
		// 获取内容
		Send s = list.get(arg0);

		// 显示图片或者视频
		if (s.getSend_content().contains(".mp4")) {
			// 显示时间
			holder.iv_loc.setVisibility(View.VISIBLE);
			holder.iv_id.setVisibility(View.VISIBLE);
			holder.iv_loc.setText(s.getSend_date());
			String[] string = s.getSend_content().split("%%");
			holder.video_frame.setVisibility(View.VISIBLE);
			holder.iv_id.setImageBitmap(MediaUtils.getVideoThumbnail(
					Tools.urlvideo + string[2], 80, 80));
			holder.video_list_play.setOnClickListener(new ShiOnclickListener(
					Tools.urlvideo + string[2]));
		} else {
			holder.iv_loc.setVisibility(View.GONE);
			holder.video_frame.setVisibility(View.GONE);
		}

		return arg1;
	}

	class ViewHolder {
		// 主要内容控件
		ImageView iv_id;
		TextView iv_loc;
		FrameLayout video_frame;
		ImageView video_list_play;
	}

	// 为点击获取视频添加点击事件
	class ShiOnclickListener implements OnClickListener {
		public String video;

		public ShiOnclickListener(String video) {
			super();
			this.video = video;
		}

		public void onClick(View arg0) {
			context.startActivity(new Intent(context, PlayMovieActivity.class)
					.putExtra("video_url", video));
		}

	}
}

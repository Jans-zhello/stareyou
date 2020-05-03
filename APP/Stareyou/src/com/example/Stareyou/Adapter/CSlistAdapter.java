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
import com.example.Stareyou.Media.PlayImageActivity;
import com.example.Stareyou.Media.PlayMovieActivity;
import com.example.Stareyou.model.Collection;
import com.example.Stareyou.util.Tools;

public class CSlistAdapter extends BaseAdapter {
	ViewHolder holder;

	// 获取内容
	private Vector<Collection> list;
	// 获取上下文
	private Context context;

	public CSlistAdapter(Vector<Collection> list, Context context) {
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
		return list.get(arg0).getCollectionid();
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		if (arg1 == null) {
			holder = new ViewHolder();
			arg1 = LayoutInflater.from(context).inflate(
					R.layout.collectionlist_content, null);
			holder.center_date = (TextView) arg1.findViewById(R.id.center_date);
			holder.center_icon = (ImageView) arg1
					.findViewById(R.id.center_icon);
			holder.center_usrname = (TextView) arg1
					.findViewById(R.id.center_usrname);
			holder.sendlist_content = (TextView) arg1
					.findViewById(R.id.sendlist_content);
			holder.sendlist_img1 = (ImageView) arg1
					.findViewById(R.id.sendlist_img1);
			holder.sendlist_video1 = (ImageView) arg1
					.findViewById(R.id.sendlist_video1);
			holder.shoucang_frame = (FrameLayout) arg1
					.findViewById(R.id.shoucang_frame);
			holder.collectionlist_content_video_play = (ImageView) arg1
					.findViewById(R.id.collectionlist_content_video_play);
			holder.sendlist_loc = (TextView) arg1
					.findViewById(R.id.sendlist_loc);
			arg1.setTag(holder);
		} else {
			holder = (ViewHolder) arg1.getTag();
		}
		// 获取内容
		final Collection s = list.get(arg0);
		// 显示用户头像
		if (s.getIcon() != null || !s.getIcon().equals("")) {
			try {
				holder.center_icon.setImageBitmap(MediaUtils.getImageThumbnail(
						Tools.urlimg + s.getIcon(), context, 50, 50));
			} catch (Exception e) {
				e.printStackTrace();
				holder.center_icon.setImageDrawable(context.getResources()
						.getDrawable(R.drawable.boy));
			}
		}

		// 显示用户名
		if (s.getUsername() != null || !s.getUsername().equals("")) {
			holder.center_usrname.setText(s.getUsername());
		}
		// 显示正文包括图片视频
		String[] string = s.getSend_content().split("%%");
		if (string.length == 3) {
			holder.sendlist_content.setText(string[0]);
			if (!"".equals(string[1])) {
				holder.sendlist_img1.setImageBitmap(MediaUtils
						.getImageThumbnail(Tools.urlimg + string[1], context,
								60, 60));
				holder.sendlist_img1.setOnClickListener(new TuOnclickListener(
						string[1]));
			} else {
				holder.sendlist_img1.setVisibility(View.GONE);
			}
			if (!"".equals(string[2])) {
				holder.shoucang_frame.setVisibility(View.VISIBLE);
				holder.sendlist_video1.setImageBitmap(MediaUtils
						.getVideoThumbnail(Tools.urlvideo + string[2], 60, 60));
				holder.collectionlist_content_video_play
						.setOnClickListener(new ShiOnclickListener(
								Tools.urlvideo + string[2]));
			} else {
				holder.shoucang_frame.setVisibility(View.GONE);
			}
		} else if (string.length == 2) {
			holder.sendlist_content.setText(string[0]);
			if (!"".equals(string[1])) {
				holder.sendlist_img1.setImageBitmap(MediaUtils
						.getImageThumbnail(Tools.urlimg + string[1], context,
								60, 60));
				holder.sendlist_img1.setOnClickListener(new TuOnclickListener(
						string[1]));
			} else {
				holder.sendlist_img1.setVisibility(View.GONE);
			}
			holder.shoucang_frame.setVisibility(View.GONE);
		} else {
			holder.sendlist_content.setText(string[0]);
			holder.sendlist_img1.setVisibility(View.GONE);
			holder.shoucang_frame.setVisibility(View.GONE);
		}
		// 位置不显示
		holder.sendlist_loc.setVisibility(View.GONE);
		// 显示时间
		holder.center_date.setText(s.getCollection_date());
		return arg1;
	}

	class ViewHolder {
		// 主要内容控件
		ImageView center_icon;// 用户头像
		TextView center_usrname;// 用户名
		TextView sendlist_content;// 正文内容
		ImageView sendlist_img1;// 发表的图片
		ImageView sendlist_video1;// 发表的视频
		FrameLayout shoucang_frame;
		ImageView collectionlist_content_video_play;
		TextView center_date;// 时间
		TextView sendlist_loc;// 位置
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

	// 为获取图片添加点击事件
	class TuOnclickListener implements OnClickListener {
		public String img;

		public TuOnclickListener(String img) {
			super();
			this.img = img;
		}

		@Override
		public void onClick(View arg0) {
			Intent intent = new Intent(context, PlayImageActivity.class);
			intent.putExtra("img_url", Tools.urlimg + img);
			context.startActivity(intent);
		}

	}

}

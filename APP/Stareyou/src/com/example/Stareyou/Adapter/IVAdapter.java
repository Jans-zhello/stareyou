package com.example.Stareyou.Adapter;

import java.util.Vector;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.Stareyou.R;
import com.example.Stareyou.Media.MediaUtils;
import com.example.Stareyou.Media.PlayImageActivity;
import com.example.Stareyou.model.Send;
import com.example.Stareyou.util.Tools;

public class IVAdapter extends BaseAdapter {
	ViewHolder holder;

	// 获取内容
	private Vector<Send> list;
	// 获取上下文
	private Context context;
	// 下载进度框
	private ProgressBar mPgBar;
	private TextView mTvProgress;
	public AlertDialog alertdialog;
	private ProgressDialog pdDialog;

	public IVAdapter(Vector<Send> list, Context context) {
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
					R.layout.img_video_content, null);
			holder.iv_id = (ImageView) arg1.findViewById(R.id.iv_id);
			holder.iv_loc = (TextView) arg1.findViewById(R.id.iv_loc);
			arg1.setTag(holder);
		} else {
			holder = (ViewHolder) arg1.getTag();
		}
		// 获取内容
		Send s = list.get(arg0);

		// 显示图片
		if (s.getSend_content().contains(".jpg")) {
			try {
				// 显示时间
				holder.iv_loc.setVisibility(View.VISIBLE);
				holder.iv_id.setVisibility(View.VISIBLE);
				holder.iv_loc.setText(s.getSend_date());
				String[] string = s.getSend_content().split("%%");
				holder.iv_id.setImageBitmap(MediaUtils.getImageThumbnail(
						Tools.urlimg + string[1], context, 80, 80));
				holder.iv_id
						.setOnClickListener(new TuOnclickListener(string[1]));
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			holder.iv_loc.setVisibility(View.GONE);
			holder.iv_id.setVisibility(View.GONE);
		}

		return arg1;
	}

	class ViewHolder {
		// 主要内容控件
		ImageView iv_id;
		TextView iv_loc;
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

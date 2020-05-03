package com.example.Stareyou.Adapter;

import java.util.Vector;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.Stareyou.R;
import com.example.Stareyou.Media.MediaUtils;
import com.example.Stareyou.model.Help;
import com.example.Stareyou.util.ShowTime;
import com.example.Stareyou.util.Tools;

public class LoveHelpAdapter extends BaseAdapter {
	// 获取内容
	private Vector<Help> list;
	// 获取上下文
	private Context context;

	public LoveHelpAdapter(Vector<Help> list, Context context) {
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

	public void UpdateUI(Vector<Help> ve) {
		list = ve;
		this.notifyDataSetChanged();
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub

		return list.get(arg0).getHelpid();
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (arg1 == null) {
			holder = new ViewHolder();
			arg1 = LayoutInflater.from(context).inflate(
					R.layout.lovehelp_content, null);
			holder.help_usericon = (ImageView) arg1
					.findViewById(R.id.help_usericon);
			holder.needhelp_username = (TextView) arg1
					.findViewById(R.id.needhelp_username);
			holder.help_title_content = (TextView) arg1
					.findViewById(R.id.help_title_content);
			holder.help_time = (TextView) arg1.findViewById(R.id.help_time);
			holder.helpuser_number = (TextView) arg1
					.findViewById(R.id.helpuser_number);
			holder.detail_assess = (TextView) arg1
					.findViewById(R.id.detail_assess);
			holder.detail_location = (TextView) arg1
					.findViewById(R.id.detail_location);
			arg1.setTag(holder);
		} else {
			holder = (ViewHolder) arg1.getTag();
		}
		Help help = list.get(arg0);
		Log.i("getdata", "准备加载图片");
		// 显示用户头像
		if (help.getHelp_icon() != null || !help.getHelp_icon().equals("")) {
			try {
				holder.help_usericon.setImageBitmap(MediaUtils
						.getImageThumbnail(Tools.urlimg + help.getHelp_icon(),
								context, 55, 55));
			} catch (Exception e) {
				e.printStackTrace();
				holder.help_usericon.setImageDrawable(context.getResources()
						.getDrawable(R.drawable.boy));
			}
			// holder.help_usericon.setImageDrawable(context.getResources()
			// .getDrawable(R.drawable.ic_launcher));
		}
		Log.i("getdata", "加载完图片");
		// 显示用户名
		if (help.getUsername() != null || !help.getUsername().equals("")) {
			holder.needhelp_username.setText(help.getUsername());
		}
		// 显示帮助标题
		if (help.getHelp_title() != null || !help.getHelp_title().equals("")) {
			holder.help_title_content.setText(help.getHelp_title());
		}
		// 显示帮助时间
		if (help.getHelp_date() != null || !help.getHelp_date().equals("")) {
			holder.help_time.setText(ShowTime.getInterval(ShowTime
					.conversion(help.getHelp_date())));
		}
		// 显示位置
		if (help.getHelp_location() != null) {
			holder.detail_location.setText(help.getHelp_location());
		} else {
			holder.detail_location.setText("未给出位置信息");
		}
		// 显示帮助人数量
		if (help.getAssesses() == null || help.getAssesses().size() == 0) {
			holder.helpuser_number.setText(0 + "");
		} else {
			holder.helpuser_number.setText(help.getAssesses().get(0)
					.getHelper_number()
					+ "");
		}
		// 显示好评度
		if (help.getAssesses() == null || help.getAssesses().size() == 0) {
			holder.detail_assess.setText(0 + "");
		} else {
			String s = String.format("%.2f", help.getAssesses().get(0)
					.getSatisfied());
			holder.detail_assess.setText((Double.parseDouble(s) * 100) + "");
		}
		return arg1;
	}

	class ViewHolder {
		// 主要内容控件
		ImageView help_usericon;
		TextView needhelp_username;
		TextView help_title_content;
		TextView help_time;
		TextView helpuser_number;
		TextView detail_assess;
		TextView detail_location;
	}
}

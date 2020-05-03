package com.example.Stareyou.Adapter;

import java.util.Vector;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.Stareyou.R;
import com.example.Stareyou.Media.MediaUtils;
import com.example.Stareyou.Media.PlayImageActivity;
import com.example.Stareyou.Media.PlayMovieActivity;
import com.example.Stareyou.model.Collection;
import com.example.Stareyou.model.Concern;
import com.example.Stareyou.model.Help;
import com.example.Stareyou.model.Praise;
import com.example.Stareyou.model.Send;
import com.example.Stareyou.model.SendRedirect;
import com.example.Stareyou.server.MainClientService;
import com.example.Stareyou.util.ShowTime;
import com.example.Stareyou.util.Tools;

public class GoodPersonAdapter extends BaseAdapter {
	ViewHolder holder;

	// 获取内容
	private Vector<Send> list;
	// 获取上下文
	private Context context;
	// 从底端弹出对话框
	private Dialog dialog;
	private View inflate;

	public GoodPersonAdapter(Vector<Send> list, Context context) {
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

	public void UpdateUI(Vector<Send> ve) {
		list = ve;
		this.notifyDataSetChanged();
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		if (arg1 == null) {
			holder = new ViewHolder();
			arg1 = LayoutInflater.from(context).inflate(
					R.layout.goodperson_content, null);
			holder.share_usericon = (ImageView) arg1
					.findViewById(R.id.share_usericon);
			holder.share_username = (TextView) arg1
					.findViewById(R.id.share_username);
			holder.goodperson_content = (TextView) arg1
					.findViewById(R.id.goodperson_content);
			holder.goodperson_content_img = (ImageView) arg1
					.findViewById(R.id.goodperson_content_img);
			holder.goodperson_content_video = (ImageView) arg1
					.findViewById(R.id.goodperson_content_video);
			holder.haoren_frame = (FrameLayout) arg1
					.findViewById(R.id.haoren_frame);
			holder.goodperson_content_video_play = (ImageView) arg1
					.findViewById(R.id.goodperson_content_video_play);
			holder.praise = (TextView) arg1.findViewById(R.id.praise);
			holder.zhuanfa = (TextView) arg1.findViewById(R.id.zhuanfa);
			holder.pinglun = (TextView) arg1.findViewById(R.id.pinglun);
			holder.shoucang = (TextView) arg1.findViewById(R.id.shoucang);
			holder.share_time = (TextView) arg1.findViewById(R.id.share_time);
			holder.detail_location = (TextView) arg1
					.findViewById(R.id.detail_location);
			holder.first = (RelativeLayout) arg1.findViewById(R.id.part3_1);
			holder.second = (RelativeLayout) arg1.findViewById(R.id.part3_2);
			holder.third = (RelativeLayout) arg1.findViewById(R.id.part3_3);
			holder.forth = (RelativeLayout) arg1.findViewById(R.id.part3_4);
			holder.praise_user = (TextView) arg1.findViewById(R.id.praise_user);
			holder.praise_num = (TextView) arg1.findViewById(R.id.praise_num);
			holder.shoucang_user = (TextView) arg1
					.findViewById(R.id.shoucang_user);
			holder.shoucang_num = (TextView) arg1
					.findViewById(R.id.shoucang_num);
			holder.guanzhu_user = (TextView) arg1
					.findViewById(R.id.guanzhu_user);
			holder.guanzhu_num = (TextView) arg1.findViewById(R.id.guanzhu_num);
			holder.zhuanfa_user = (TextView) arg1
					.findViewById(R.id.zhuanfa_user);
			holder.zhuanfa_num = (TextView) arg1.findViewById(R.id.zhuanfa_num);
			holder.lv_reviewperson = (ListView) arg1
					.findViewById(R.id.lv_reviewperson);

			arg1.setTag(holder);
		} else {
			holder = (ViewHolder) arg1.getTag();
		}
		// 获取内容
		final Send s = list.get(arg0);
		// 显示用户头像
		if (s.getSend_icon() != null || !s.getSend_icon().equals("")) {
			try {
				holder.share_usericon.setImageBitmap(MediaUtils
						.getImageThumbnail(Tools.urlimg + s.getSend_icon(),
								context, 55, 55));
			} catch (Exception e) {
				e.printStackTrace();
				holder.share_usericon.setImageDrawable(context.getResources()
						.getDrawable(R.drawable.boy));
			}
		}
		// 点击头像底部弹出对话框
		holder.share_usericon.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dialog = new Dialog(context, R.style.ActionSheetDialogStyle);
				// 填充对话框的布局
				inflate = LayoutInflater.from(context).inflate(
						R.layout.bottom_concern, null);
				// 初始化控件
				TextView concern_selection = (TextView) inflate
						.findViewById(R.id.concern_selection);
				TextView cancel = (TextView) inflate
						.findViewById(R.id.c_cancel);
				// 关注按钮
				concern_selection.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						String msg = MainClientService.concern(Tools.userid,
								s.getUserid() + "");
						// 发送广播
						Bundle b = new Bundle();
						b.putString("jumpid", "2");
						Intent i = new Intent("com.angel.Android.Jump");
						i.putExtras(b);
						context.sendBroadcast(i);
						Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
						dialog.dismiss();
					}
				});
				// 取消
				cancel.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						dialog.dismiss();
					}
				});
				// 将布局设置给Dialog
				dialog.setContentView(inflate);
				// 获取当前Activity所在的窗体
				Window dialogWindow = dialog.getWindow();
				// 设置Dialog从窗体底部弹出
				dialogWindow.setGravity(Gravity.BOTTOM);
				// 获得窗体的属性
				WindowManager.LayoutParams lp = dialogWindow.getAttributes();
				lp.y = 20;// 设置Dialog距离底部的距离
				// 将属性设置给窗体
				dialogWindow.setAttributes(lp);
				dialog.show();// 显示对话框
			}
		});

		// 显示用户名
		if (s.getUsername() != null || !s.getUsername().equals("")) {
			holder.share_username.setText(s.getUsername());
		}
		// 显示正文包括图片视频
		String[] string = s.getSend_content().split("%%");
		if (string.length == 3) {
			holder.goodperson_content.setText(string[0]);
			if (!"".equals(string[1])) {
				holder.goodperson_content_img.setVisibility(View.VISIBLE);
				holder.goodperson_content_img.setImageBitmap(MediaUtils
						.getImageThumbnail(Tools.urlimg + string[1], context,
								60, 60));
				holder.goodperson_content_img
						.setOnClickListener(new TuOnclickListener(string[1]));
			} else {
				holder.goodperson_content_img.setVisibility(View.GONE);
			}
			if (!"".equals(string[2])) {
				holder.haoren_frame.setVisibility(View.VISIBLE);
				holder.goodperson_content_video.setImageBitmap(MediaUtils
						.getVideoThumbnail(Tools.urlvideo + string[2], 60, 60));
				holder.goodperson_content_video_play
						.setOnClickListener(new ShiOnclickListener(
								Tools.urlvideo + string[2]));
			} else {
				holder.haoren_frame.setVisibility(View.GONE);
			}
		} else if (string.length == 2) {
			holder.goodperson_content.setText(string[0]);
			if (!"".equals(string[1])) {
				holder.goodperson_content_img.setVisibility(View.VISIBLE);
				holder.goodperson_content_img.setImageBitmap(MediaUtils
						.getImageThumbnail(Tools.urlimg + string[1], context,
								60, 60));
				holder.goodperson_content_img
						.setOnClickListener(new TuOnclickListener(string[1]));
			} else {
				holder.goodperson_content_img.setVisibility(View.GONE);
			}

			holder.haoren_frame.setVisibility(View.GONE);
		} else {
			holder.goodperson_content.setText(s.getSend_content());
			holder.goodperson_content_img.setVisibility(View.GONE);
			holder.haoren_frame.setVisibility(View.GONE);
		}
		// 显示位置
		if (s.getSend_location() != null) {
			holder.detail_location.setText(s.getSend_location());
		} else {
			holder.detail_location.setText("未给出位置信息");
		}
		// 显示时间
		if (s.getSend_date() != null || !s.getSend_date().equals("")) {
			holder.share_time.setText(ShowTime.getInterval(ShowTime
					.conversion(s.getSend_date())));
		}
		// 点赞事件监听
		holder.praise.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String message = MainClientService.praise(Tools.userid,
						s.getUserid() + "", s.getSendid() + "");
				// 发送广播
				Bundle b = new Bundle();
				b.putString("jumpid", "1");
				Intent i = new Intent("com.angel.Android.Jump");
				i.putExtras(b);
				context.sendBroadcast(i);
				Toast.makeText(context, message, Toast.LENGTH_LONG).show();
			}
		});
		// 收藏事件监听
		holder.shoucang.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String message = MainClientService.collection(Tools.userid,
						s.getUserid() + "", s.getSendid() + "");
				Bundle b = new Bundle();
				b.putString("jumpid", "1");
				Intent i = new Intent("com.angel.Android.Jump");
				i.putExtras(b);
				context.sendBroadcast(i);
				Toast.makeText(context, message, Toast.LENGTH_LONG).show();
			}
		});
		// 评论事件监听
		holder.pinglun.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialog = new Dialog(context, R.style.ActionSheetDialogStyle);
				// 填充对话框的布局
				inflate = LayoutInflater.from(context).inflate(
						R.layout.bottom_review, null);
				// 初始化控件
				final EditText add_review_content = (EditText) inflate
						.findViewById(R.id.add_review_content);
				Button send_share = (Button) inflate
						.findViewById(R.id.send_share);
				send_share.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						String content = add_review_content.getText()
								.toString();
						String msg = MainClientService.review(s.getSendid()
								+ "", Tools.userid, s.getUserid() + "",
								content, 0);
						Bundle b = new Bundle();
						b.putString("jumpid", "1");
						Intent i = new Intent("com.angel.Android.Jump");
						i.putExtras(b);
						context.sendBroadcast(i);
						Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
						add_review_content.setText("");
						dialog.dismiss();
					}
				});
				// 将布局设置给Dialog
				dialog.setContentView(inflate);
				// 获取当前Activity所在的窗体
				Window dialogWindow = dialog.getWindow();
				// 设置Dialog从窗体底部弹出
				dialogWindow.setGravity(Gravity.BOTTOM);
				// 获得窗体的属性
				WindowManager.LayoutParams lp = dialogWindow.getAttributes();
				lp.y = 20;// 设置Dialog距离底部的距离
				// 将属性设置给窗体
				dialogWindow.setAttributes(lp);
				dialog.show();// 显示对话框
			}
		});
		// 转发事件监听
		holder.zhuanfa.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String msg = MainClientService.redirect(Tools.userid,
						Tools.username, s.getSendid() + "",
						s.getSend_content(), 0, s.getSend_icon(),
						s.getSend_location());
				Bundle b = new Bundle();
				b.putString("jumpid", "1");
				Intent i = new Intent("com.angel.Android.Jump");
				i.putExtras(b);
				context.sendBroadcast(i);
				Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
			}
		});
		// 加载点赞用户名以及对应人数

		Vector<Praise> vp = s.getPe();
		String praisename = "";
		if (vp != null && vp.size() > 0) {
			for (Praise praise : vp) {
				praisename += praise.getUsername() + "、";
			}
			holder.first.setVisibility(View.VISIBLE);
			Log.i("selectionname", praisename);
			holder.praise_user.setText(praisename);
			holder.praise_num.setText(vp.get(0).getNumber() + "");
		} else {
			holder.first.setVisibility(View.GONE);
		}
		// 加载收藏用户名以及对应人数
		Vector<Collection> vp2 = s.getCl();
		String collectionname = "";
		if (vp2 != null && vp2.size() > 0) {
			for (Collection collection : vp2) {
				collectionname += collection.getUsername() + "、";
			}
			holder.second.setVisibility(View.VISIBLE);
			holder.shoucang_user.setText(collectionname);
			holder.shoucang_num.setText(vp2.get(0).getNumber() + "");
		} else {
			holder.second.setVisibility(View.GONE);
		}
		// 加载关注用户名以及对应人数
		Vector<Concern> vp3 = s.getCo();
		String concernnname = "";
		if (vp3 != null && vp3.size() > 0) {
			for (Concern concern : vp3) {
				concernnname += concern.getUsername() + "、";
			}
			holder.third.setVisibility(View.VISIBLE);
			holder.guanzhu_user.setText(concernnname);
			holder.guanzhu_num.setText(vp3.get(0).getNumber() + "");
		} else {
			holder.third.setVisibility(View.GONE);
		}

		// 加载转发用户名以及对应人数
		Vector<SendRedirect> vp4 = s.getSr();
		String redirectame = "";
		if (vp4 != null && vp4.size() > 0) {
			for (SendRedirect sendredirect : vp4) {
				redirectame += sendredirect.getUsername() + "、";
			}
			holder.forth.setVisibility(View.VISIBLE);
			holder.zhuanfa_user.setText(redirectame);
			holder.zhuanfa_num.setText(vp4.get(0).getNumber() + "");

		} else {
			holder.forth.setVisibility(View.GONE);
		}

		// 加载评论内容
		LoadReviewContentAdapter lrc = new LoadReviewContentAdapter(s.getRw(),
				context);
		holder.lv_reviewperson.setAdapter(lrc);
		return arg1;
	}

	class ViewHolder {
		// 主要内容控件
		ImageView share_usericon;// 用户头像
		TextView share_username;// 用户名
		TextView goodperson_content;// 正文内容
		ImageView goodperson_content_img;// 发表的图片
		ImageView goodperson_content_video;// 发表的视频
		FrameLayout haoren_frame;
		ImageView goodperson_content_video_play;
		TextView praise;// 点赞事件
		TextView zhuanfa;// 转发
		TextView pinglun;// 评论
		TextView shoucang;// 收藏
		TextView share_time;// 时间
		TextView detail_location;// 位置
		TextView praise_user;// 点赞用户名
		TextView praise_num;// 点赞人数
		TextView shoucang_user;// 收藏用户名
		TextView shoucang_num;// 收藏人数
		TextView guanzhu_user;// 关注用户名
		TextView guanzhu_num;// 关注人数
		TextView zhuanfa_user;// 转发用户名
		TextView zhuanfa_num;// 转发人数
		ListView lv_reviewperson;// 加载评论内容
		RelativeLayout first;
		RelativeLayout second;
		RelativeLayout third;
		RelativeLayout forth;
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

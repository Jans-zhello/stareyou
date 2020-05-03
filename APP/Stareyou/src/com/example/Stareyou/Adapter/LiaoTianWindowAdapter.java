package com.example.Stareyou.Adapter;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Vector;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.Stareyou.R;
import com.example.Stareyou.Media.MediaUtils;
import com.example.Stareyou.Media.PlayImageActivity;
import com.example.Stareyou.Media.PlayMovieActivity;
import com.example.Stareyou.model.Chat;
import com.example.Stareyou.util.Tools;

public class LiaoTianWindowAdapter extends BaseAdapter {
	// 获取内容
	private Vector<Chat> list;
	// 获取上下文
	private Context context;
	// 主控件显示
	private LinearLayout left_layout;
	private LinearLayout right_Layout;
	private TextView left_msg;
	private TextView right_msg;
	private ImageView left_image_msg;
	private ImageView right_image_msg;
	private ImageView left_video_msg;
	private ImageView right_video_msg;
	private ImageView left_amr_msg;
	private ImageView right_amr_msg;
	public LiaoTianWindowAdapter(Vector<Chat> list, Context context) {
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
		return list.get(arg0).getChatid();
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		View view = null;
		if (arg1 != null) {
			view = arg1;
		} else {
			view = LayoutInflater.from(context).inflate(
					R.layout.liaotian_content_chuangkou, null);
		}
		// 初始化
		left_layout = (LinearLayout) view.findViewById(R.id.left_layout);
		right_Layout = (LinearLayout) view.findViewById(R.id.right_Layout);
		left_msg = (TextView) view.findViewById(R.id.left_msg);
		right_msg = (TextView) view.findViewById(R.id.right_msg);
		left_image_msg = (ImageView) view.findViewById(R.id.left_image_msg);
		right_image_msg = (ImageView) view.findViewById(R.id.right_image_msg);
		left_video_msg = (ImageView) view.findViewById(R.id.left_video_msg);
		right_video_msg = (ImageView) view.findViewById(R.id.right_video_msg);
		left_amr_msg = (ImageView) view.findViewById(R.id.left_amr_msg);
		right_amr_msg = (ImageView) view.findViewById(R.id.right_amr_msg);
		// 获取内容
		Chat chat = list.get(arg0);
		if (chat.getChat_type() == Integer.parseInt(Tools.userid)) {
			left_layout.setVisibility(View.GONE);
			right_Layout.setVisibility(View.VISIBLE);
			if (chat.getChat_content().contains(".jpg")) {
				right_image_msg.setVisibility(View.VISIBLE);
				right_image_msg
						.setImageBitmap(MediaUtils.getImageThumbnail(
								Tools.urlimg + chat.getChat_content(), context,
								60, 60));
				// 添加点击事件
				right_image_msg.setOnClickListener(new imageOnClickListener(
						chat.getChat_content()));
				right_video_msg.setVisibility(View.GONE);
				right_amr_msg.setVisibility(View.GONE);
			} else if (chat.getChat_content().contains(".mp4")) {
				right_video_msg.setVisibility(View.VISIBLE);
				right_video_msg.setImageBitmap(MediaUtils.getVideoThumbnail(
						Tools.urlvideo + chat.getChat_content(), 60, 60));
				// 添加点击事件
				right_video_msg.setOnClickListener(new videoOnclickListener(
						chat.getChat_content()));
				right_image_msg.setVisibility(View.GONE);
				right_amr_msg.setVisibility(View.GONE);
			} else if (chat.getChat_content().contains(".amr")) {
				right_msg.setText("语音");
				right_amr_msg.setVisibility(View.VISIBLE);
				right_amr_msg.setImageDrawable(context.getResources()
						.getDrawable(R.drawable.play_voice));
				// 添加点击事件
				right_amr_msg.setOnClickListener(new amrOnclickListener(chat
						.getChat_content()));
				right_image_msg.setVisibility(View.GONE);
				right_video_msg.setVisibility(View.GONE);
			} else {
				right_msg.setText(chat.getChat_content());
				right_amr_msg.setVisibility(View.GONE);
				right_image_msg.setVisibility(View.GONE);
				right_video_msg.setVisibility(View.GONE);
			}
		} else if (chat.getChat_type() == Integer
				.parseInt(Tools.chatted_userid)) {
			right_Layout.setVisibility(View.GONE);
			left_layout.setVisibility(View.VISIBLE);
			if (chat.getChat_content().contains(".jpg")) {
				left_image_msg.setVisibility(View.VISIBLE);
				left_image_msg
						.setImageBitmap(MediaUtils.getImageThumbnail(
								Tools.urlimg + chat.getChat_content(), context,
								60, 60));
				// 添加点击事件
				left_image_msg.setOnClickListener(new imageOnClickListener(chat
						.getChat_content()));
				left_video_msg.setVisibility(View.GONE);
				left_amr_msg.setVisibility(View.GONE);
			} else if (chat.getChat_content().contains(".mp4")) {
				left_video_msg.setVisibility(View.VISIBLE);
				left_video_msg.setImageBitmap(MediaUtils.getVideoThumbnail(
						Tools.urlvideo + chat.getChat_content(), 60, 60));
				// 添加点击事件
				left_video_msg.setOnClickListener(new videoOnclickListener(chat
						.getChat_content()));
				left_image_msg.setVisibility(View.GONE);
				left_amr_msg.setVisibility(View.GONE);
			} else if (chat.getChat_content().contains(".amr")) {
				left_amr_msg.setVisibility(View.VISIBLE);
				left_amr_msg.setImageDrawable(context.getResources()
						.getDrawable(R.drawable.play_voice));
				// 添加点击事件
				left_amr_msg.setOnClickListener(new amrOnclickListener(chat
						.getChat_content()));
				left_image_msg.setVisibility(View.GONE);
				left_video_msg.setVisibility(View.GONE);
			} else {
				left_msg.setText(chat.getChat_content());
				left_amr_msg.setVisibility(View.GONE);
				left_image_msg.setVisibility(View.GONE);
				left_video_msg.setVisibility(View.GONE);
			}
		}
		return view;
	}

	// 为获取音频添加点击事件
	class amrOnclickListener implements OnClickListener {
		public String amr;

		public amrOnclickListener(String amr) {
			super();
			this.amr = amr;
		}

		@Override
		public void onClick(View arg0) {
			try {
				URL url = new URL(Tools.urlamr + amr);
				URLConnection conection = url.openConnection();
				conection.connect();
				// download the file
				InputStream input = new BufferedInputStream(url.openStream(),
						8192);// 1024*8
				File f = new File(MediaUtils.getDiskCacheDir(context)
						+ "/stareyou_amrs");
				if (!f.isDirectory()) {
					f.mkdirs();
				}
				// Output stream
				OutputStream output = new FileOutputStream(
						MediaUtils.getDiskCacheDir(context) + "/stareyou_amrs"
								+ "/" + amr);
				byte data[] = new byte[1024];
				int count = 0;
				while ((count = input.read(data)) != -1) {
					// writing data to file
					output.write(data, 0, count);
					output.flush();
				}
				// closing streams
				output.close();
				input.close();
				// 下载完毕最终播放的录音文件
				File f2 = new File(MediaUtils.getDiskCacheDir(context)
						+ "/stareyou_amrs", amr);
				MediaPlayer mediaPlayer = new MediaPlayer();
				if (mediaPlayer.isPlaying()) {
					mediaPlayer.reset();// 重置为初始状态
				}
				mediaPlayer.setDataSource(f2.getPath());
				mediaPlayer.prepare();// 缓冲
				mediaPlayer.start();// 开始或恢复播放

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	// 为获取图片添加点击事件
	class imageOnClickListener implements OnClickListener {

		public String img;

		public imageOnClickListener(String img) {
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

	// 为获取视频添加点击事件
	class videoOnclickListener implements OnClickListener {
		public String video;

		public videoOnclickListener(String video) {
			super();
			this.video = video;
		}

		public void onClick(View arg0) {
			context.startActivity(new Intent(context, PlayMovieActivity.class)
					.putExtra("video_url", Tools.urlvideo + video));
		}

	}

}

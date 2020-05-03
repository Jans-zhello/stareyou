package com.example.Stareyou.Media;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.jar.Attributes.Name;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Stareyou.R;
import com.example.Stareyou.SendHelpActivity;
import com.example.Stareyou.SendShareActivity;
import com.example.Stareyou.util.Tools;

public class UpLoadMediaService extends Service {
	// 传递过来的媒体文件路径
	private String resourcepath = "";
	// 上传控件
	public ProgressBar mPgBar;
	public TextView mTvProgress;
	public AlertDialog alertdialog;
	public WindowManager mWindowManager;
	public LayoutInflater mLayoutInflater;
	// 返回到activity的资源名字
	private Context context;
	private String name = "";

	public IBinder onBind(Intent intent) {
		Bundle bundle = intent.getExtras();
		resourcepath = bundle.getString("resourcepath");
		return new MyBinner();
	}

	public void startUpLoadService() {
		init();
		new MyTask(context).execute(resourcepath);
	}

	public class MyBinner extends Binder {

		public void startUpLoad(Context context) {
			UpLoadMediaService.this.context = context;
			startUpLoadService();
		}
	}

	/**
	 * 初始化上传进度控件
	 */
	public void init() {
		mWindowManager = (WindowManager) getApplicationContext()
				.getSystemService(Context.WINDOW_SERVICE);
		mLayoutInflater = LayoutInflater.from(this);
		View upView = mLayoutInflater.inflate(R.layout.filebrowser_uploading,
				null);
		mPgBar = (ProgressBar) upView
				.findViewById(R.id.pb_filebrowser_uploading);
		mTvProgress = (TextView) upView
				.findViewById(R.id.tv_filebrowser_uploading);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("上传进度");
		builder.setView(upView);
		alertdialog = builder.create();
		alertdialog.getWindow().setType(
				WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		alertdialog.show();
	}

	/**
	 * 开启异步任务上传图片或视频
	 * 
	 * @author Administrator
	 * 
	 */
	class MyTask extends AsyncTask<String, Integer, String> {
		private Context context;
		public static final long MAX_VIDEO_SIZE = 50 * 1024 * 1024;
		public static final long MAX_PICTURE_SIZE = 10 * 1024 * 1024;

		public MyTask(Context context) {
			this.context = context;
		}

		public Context getContext() {
			return context;
		}

		public void setContext(Context context) {
			this.context = context;
		}

		@Override
		protected void onPostExecute(String result) {
			if (result != null && result.equals("图片文件不能超过10M")) {
				Toast.makeText(getContext(), "图片文件不能超过10M", 0).show();
			} else if (result != null && result.equals("视频文件不能超过50M")) {
				Toast.makeText(getContext(), "视频文件不能超过50M", 0).show();
			}
			ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
			RunningTaskInfo info = manager.getRunningTasks(1).get(0);
			String classname = info.topActivity.getShortClassName(); // 类名
			Message msg = Message.obtain();
			msg.obj = result;
			if (classname.contains("SendHelpActivity")) {
				SendHelpActivity.handler.sendMessage(msg);
			}

			if (classname.contains("SendShareActivity")) {
				SendShareActivity.handler.sendMessage(msg);
			}

			if (alertdialog.isShowing()) {
				alertdialog.dismiss();
			}

		}

		@Override
		protected void onPreExecute() {
			mTvProgress.setText("loading...");
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			mPgBar.setProgress(values[0]);
			mTvProgress.setText("loading..." + values[0] + "%");
		}

		@Override
		protected String doInBackground(String... params) {
			try {
				// 获取path内容
				String path = params[0];
				// 上传图片
				if (path.endsWith(".jpg") || path.endsWith(".jpeg")
						|| path.endsWith(".png")) {
					File f = new File(path);
					// 判断文件大小
					if (f.length() > MAX_PICTURE_SIZE) {
						return "图片文件不能超过10M";
					}
					Socket socket = new Socket(Tools.IP, Tools.PORT_3);
					InputStream in = socket.getInputStream();
					OutputStream out = socket.getOutputStream();
					// 客户端:就这种类型的文件,我要上传
					out.write(("upload,image," + f.length()).getBytes());
					out.flush();
					// 客户端:收到你发的名字了
					byte[] b = new byte[1024 * 10];
					in.read(b);
					String serverInfo = new String(b).trim();
					name = serverInfo.split(",")[1];
					// 客户端:我要往你服务器那发了
					FileInputStream fin = new FileInputStream(f);
					long total = fin.available();
					int len = 0;
					int num = 0;
					while ((len = fin.read(b)) != -1) {
						out.write(b, 0, len);
						num += len;
						publishProgress((int) ((num / (float) total) * 100));
						out.flush();
					}
					fin.close();
					out.close();
					in.close();
					return name;
				} else if (path.endsWith(".mp4") || path.endsWith(".3pg")) {
					File f = new File(path);
					// 判断文件大小
					if (f.length() > MAX_VIDEO_SIZE) {
						return "视频文件不能超过50M";
					}
					Socket socket = new Socket(Tools.IP, Tools.PORT_3);
					InputStream in = socket.getInputStream();
					OutputStream out = socket.getOutputStream();
					// 客户端:就这种类型的文件,我要上传
					out.write(("upload,video," + f.length()).getBytes());
					out.flush();
					// 客户端:收到你发的名字了
					byte[] b = new byte[1024 * 10];
					in.read(b);
					String serverInfo = new String(b).trim();
					name = serverInfo.split(",")[1];
					// 客户端:我要往你服务器那发了
					FileInputStream fin = new FileInputStream(f);
					long total = fin.available();
					int len = 0;
					int num = 0;
					while ((len = fin.read(b)) != -1) {
						out.write(b, 0, len);
						num += len;
						publishProgress((int) ((num / (float) total) * 100));
						out.flush();
					}
					fin.close();
					out.close();
					in.close();
					return name;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;

		}
	}
}

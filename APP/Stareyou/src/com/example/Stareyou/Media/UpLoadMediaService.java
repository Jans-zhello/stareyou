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
	// ���ݹ�����ý���ļ�·��
	private String resourcepath = "";
	// �ϴ��ؼ�
	public ProgressBar mPgBar;
	public TextView mTvProgress;
	public AlertDialog alertdialog;
	public WindowManager mWindowManager;
	public LayoutInflater mLayoutInflater;
	// ���ص�activity����Դ����
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
	 * ��ʼ���ϴ����ȿؼ�
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
		builder.setTitle("�ϴ�����");
		builder.setView(upView);
		alertdialog = builder.create();
		alertdialog.getWindow().setType(
				WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		alertdialog.show();
	}

	/**
	 * �����첽�����ϴ�ͼƬ����Ƶ
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
			if (result != null && result.equals("ͼƬ�ļ����ܳ���10M")) {
				Toast.makeText(getContext(), "ͼƬ�ļ����ܳ���10M", 0).show();
			} else if (result != null && result.equals("��Ƶ�ļ����ܳ���50M")) {
				Toast.makeText(getContext(), "��Ƶ�ļ����ܳ���50M", 0).show();
			}
			ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
			RunningTaskInfo info = manager.getRunningTasks(1).get(0);
			String classname = info.topActivity.getShortClassName(); // ����
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
				// ��ȡpath����
				String path = params[0];
				// �ϴ�ͼƬ
				if (path.endsWith(".jpg") || path.endsWith(".jpeg")
						|| path.endsWith(".png")) {
					File f = new File(path);
					// �ж��ļ���С
					if (f.length() > MAX_PICTURE_SIZE) {
						return "ͼƬ�ļ����ܳ���10M";
					}
					Socket socket = new Socket(Tools.IP, Tools.PORT_3);
					InputStream in = socket.getInputStream();
					OutputStream out = socket.getOutputStream();
					// �ͻ���:���������͵��ļ�,��Ҫ�ϴ�
					out.write(("upload,image," + f.length()).getBytes());
					out.flush();
					// �ͻ���:�յ��㷢��������
					byte[] b = new byte[1024 * 10];
					in.read(b);
					String serverInfo = new String(b).trim();
					name = serverInfo.split(",")[1];
					// �ͻ���:��Ҫ����������Ƿ���
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
					// �ж��ļ���С
					if (f.length() > MAX_VIDEO_SIZE) {
						return "��Ƶ�ļ����ܳ���50M";
					}
					Socket socket = new Socket(Tools.IP, Tools.PORT_3);
					InputStream in = socket.getInputStream();
					OutputStream out = socket.getOutputStream();
					// �ͻ���:���������͵��ļ�,��Ҫ�ϴ�
					out.write(("upload,video," + f.length()).getBytes());
					out.flush();
					// �ͻ���:�յ��㷢��������
					byte[] b = new byte[1024 * 10];
					in.read(b);
					String serverInfo = new String(b).trim();
					name = serverInfo.split(",")[1];
					// �ͻ���:��Ҫ����������Ƿ���
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

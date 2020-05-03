package com.example.Stareyou.Media;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.app.AlertDialog;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.Stareyou.R;

public class PlayMovieActivity extends Activity {
	private VideoView videoView;
	private ProgressBar mPgBar;
	private TextView mTvProgress;
	private AlertDialog alertdialog;
	private String videourl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.movie);
		initWidget();
	}

	public void initWidget() {
		videoView = (VideoView) findViewById(R.id.play_video);
		videourl = this.getIntent().getStringExtra("video_url");
		File file = new File(MediaUtils.getDiskCacheDir(PlayMovieActivity.this)
				+ "/stareyou_video/"
				+ videourl.substring(videourl.lastIndexOf("/") + 1));
		if (file.exists()) {
			playvideo();
		} else {
			init();
			new MyTask().execute(videourl);
		}
	}

	/**
	 * 初始化下载进度控件
	 */
	public void init() {
		View upView = LayoutInflater.from(this).inflate(
				R.layout.filebrowser_downloading, null);
		mPgBar = (ProgressBar) upView
				.findViewById(R.id.pb_filebrowser_downloading);
		mTvProgress = (TextView) upView
				.findViewById(R.id.tv_filebrowser_downloading);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setView(upView);
		alertdialog = builder.create();
		alertdialog.show();
	}

	// 开启任务执行下载视频
	class MyTask extends AsyncTask<String, Integer, String> {

		@Override
		protected void onPostExecute(String result) {
			if (result == null) {
				alertdialog.dismiss();
				// 播放视频
				playvideo();
			} else {
				alertdialog.dismiss();
				Toast.makeText(PlayMovieActivity.this, "缓冲失败,请重新缓冲",
						Toast.LENGTH_LONG);
			}
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			mTvProgress.setText("loading...");
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			mPgBar.setProgress(values[0]);
			mTvProgress.setText("正在缓冲..." + values[0] + "%");
		}

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			try {
				URL url = new URL(arg0[0]);
				URLConnection conection = url.openConnection();
				conection.connect();
				int lenghtOfFile = conection.getContentLength();

				// download the file
				InputStream input = new BufferedInputStream(url.openStream(),
						8192);// 1024*8
				File f = new File(
						MediaUtils.getDiskCacheDir(PlayMovieActivity.this),
						"/stareyou_video");
				if (!f.isDirectory()) {
					f.mkdirs();
				}
				// Output stream
				OutputStream output = new FileOutputStream(
						MediaUtils.getDiskCacheDir(PlayMovieActivity.this)
								+ "/stareyou_video/"
								+ videourl.substring(videourl.lastIndexOf("/") + 1));
				byte data[] = new byte[1024];
				int count = 0;
				int num = 0;
				while ((count = input.read(data)) != -1) {
					// writing data to file
					output.write(data, 0, count);
					num += count;
					publishProgress((int) ((num / (float) lenghtOfFile) * 100));
					output.flush();
				}
				// closing streams
				output.close();
				input.close();

			} catch (Exception e) {
				Log.e("Error: ", e.getMessage());
				return "获取失败,请重新获取";
			}

			return null;
		}

	}

	// 播放视频
	public void playvideo() {
		MediaController mc = new MediaController(PlayMovieActivity.this);
		File videoFile = new File(
				MediaUtils.getDiskCacheDir(PlayMovieActivity.this)
						+ "/stareyou_video/"
						+ videourl.substring(videourl.lastIndexOf("/") + 1));
		videoView.setVideoPath(videoFile.getPath());
		videoView.setMediaController(mc);
		mc.setMediaPlayer(videoView);
		videoView.requestFocus();
		videoView.start();
		videoView
				.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
					@Override
					public void onCompletion(MediaPlayer mp) {
						finish();
					}
				});
		videoView.setOnClickListener(new OnClickListener() {
			public void onClick(View paramView) {
				finish();
			}
		});
	}
}

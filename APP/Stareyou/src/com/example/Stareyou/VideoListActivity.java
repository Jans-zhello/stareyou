package com.example.Stareyou;

import java.util.Vector;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.VideoView;
import android.widget.AdapterView.OnItemLongClickListener;

import com.example.Stareyou.Adapter.VIAdapter;
import com.example.Stareyou.Exit.BaseActivity;
import com.example.Stareyou.model.Collection;
import com.example.Stareyou.model.Send;
import com.example.Stareyou.server.MainClientService;
import com.example.Stareyou.util.Tools;

public class VideoListActivity extends BaseActivity {
	private View empty_text_tip_vlist;
	private ImageView videolist_back;
	private ListView list;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.videolist);
		initWidget();
		// 如下两行代码解决socket连接不上的问题
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		getData();
	}

	public void initWidget() {
		videolist_back = (ImageView) this.findViewById(R.id.videolist_back);
		list = (ListView) this.findViewById(R.id.lv_videolist);
		empty_text_tip_vlist = this.findViewById(R.id.empty_text_tip_vlist);
		videolist_back
				.setOnClickListener(new android.view.View.OnClickListener() {

					@Override
					public void onClick(View arg0) {
						finish();

					}
				});
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		finish();
	}

	public void getData() {
		final Vector<Send> v = MainClientService.getSendData(Tools.userid);
		if (v != null) {
			// view.setVisibility(View.GONE);
		}
		final VIAdapter sla = new VIAdapter(v, VideoListActivity.this);
		list.setAdapter(sla);
		list.setEmptyView(empty_text_tip_vlist);
		list.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					final int arg2, final long arg3) {
				AlertDialog.Builder builder = new Builder(
						VideoListActivity.this);
				builder.setMessage("确定删除?");
				builder.setTitle("提示");

				// 添加AlertDialog.Builder对象的setPositiveButton()方法
				builder.setPositiveButton("确定", new OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						v.remove(arg2);
						String msg = MainClientService.deleteSend(arg3 + "");
						Toast.makeText(VideoListActivity.this, "删除成功",
								Toast.LENGTH_LONG).show();
						sla.notifyDataSetChanged();
					}
				});

				// 添加AlertDialog.Builder对象的setNegativeButton()方法
				builder.setNegativeButton("取消", new OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {

					}
				});

				builder.create().show();

				return false;
			}

		});
	}

}

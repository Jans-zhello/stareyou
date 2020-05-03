package com.example.Stareyou;

import java.util.Vector;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.Stareyou.Adapter.IVAdapter;
import com.example.Stareyou.Adapter.VIAdapter;
import com.example.Stareyou.Exit.BaseActivity;
import com.example.Stareyou.model.Send;
import com.example.Stareyou.server.MainClientService;
import com.example.Stareyou.util.Tools;

public class PhotoListActivity extends BaseActivity {
	private View empty_text_tip_zlist;
	private ImageView photolist_back;
	private ListView list;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photolist);
		initWidget();
		// 如下两行代码解决socket连接不上的问题
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		getData();
	}

	public void initWidget() {
		photolist_back = (ImageView) this.findViewById(R.id.photolist_back);
		list = (ListView) this.findViewById(R.id.lv_photolist);
		empty_text_tip_zlist = findViewById(R.id.empty_text_tip_zlist);
		photolist_back
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
		final IVAdapter sla = new IVAdapter(v, PhotoListActivity.this);
		list.setAdapter(sla);
		list.setEmptyView(empty_text_tip_zlist);
		list.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					final int arg2, final long arg3) {
				AlertDialog.Builder builder = new Builder(
						PhotoListActivity.this);
				builder.setMessage("确定删除?");
				builder.setTitle("提示");

				// 添加AlertDialog.Builder对象的setPositiveButton()方法
				builder.setPositiveButton("确定", new OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						v.remove(arg2);
						String msg = MainClientService.deleteSend(arg3 + "");
						Toast.makeText(PhotoListActivity.this, "删除成功",
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

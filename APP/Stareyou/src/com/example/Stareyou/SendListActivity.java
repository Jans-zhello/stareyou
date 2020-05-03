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
import android.view.*;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.Stareyou.Adapter.SClistAdapter;
import com.example.Stareyou.Exit.BaseActivity;
import com.example.Stareyou.model.Send;
import com.example.Stareyou.server.MainClientService;
import com.example.Stareyou.util.Tools;

public class SendListActivity extends BaseActivity {
	private ImageView sendlist_back;
	private ListView list;
	private View empty_text_tip;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sendlist);
		initWidget();
		// �������д�����socket���Ӳ��ϵ�����
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		getData();
	}

	public void initWidget() {
		sendlist_back = (ImageView) this.findViewById(R.id.sendlist_back);
		list = (ListView) this.findViewById(R.id.lv_sendlist);
		sendlist_back.setOnClickListener(new SendBackOnclickListener());
		empty_text_tip = findViewById(R.id.empty_text_tip);
	}

	//
	class SendBackOnclickListener implements android.view.View.OnClickListener {

		@Override
		public void onClick(View arg0) {
			finish();
		}

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
			/* view.setVisibility(View.GONE); */
		}
		final SClistAdapter sla = new SClistAdapter(v, SendListActivity.this);
		list.setAdapter(sla);
		list.setEmptyView(empty_text_tip);
		list.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					final int arg2, final long arg3) {
				AlertDialog.Builder builder = new Builder(SendListActivity.this);
				builder.setMessage("ȷ��ɾ��?");
				builder.setTitle("��ʾ");

				// ���AlertDialog.Builder�����setPositiveButton()����
				builder.setPositiveButton("ȷ��", new OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						v.remove(arg2);
						String msg = MainClientService.deleteSend(arg3 + "");
						Toast.makeText(SendListActivity.this, "ɾ���ɹ�",
								Toast.LENGTH_LONG).show();
						sla.notifyDataSetChanged();
					}
				});

				// ���AlertDialog.Builder�����setNegativeButton()����
				builder.setNegativeButton("ȡ��", new OnClickListener() {

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

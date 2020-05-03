package com.example.Stareyou.Adapter;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Vector;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Stareyou.R;
import com.example.Stareyou.model.Help;
import com.example.Stareyou.model.Order;
import com.example.Stareyou.model.Users;
import com.example.Stareyou.server.MainClientService;
import com.example.Stareyou.util.ShowTime;
import com.example.Stareyou.util.Tools;
import com.example.countdowmtimer.CountDownTimeInfo;
import com.example.countdowmtimer.CountDownTimerUtil;

public class HelpMsgAdapter extends BaseAdapter {
	// 获取内容
	private Vector<Order> list;
	private Context context;
	// 倒计时
	private List<CountDownTimeInfo> mList;

	public HelpMsgAdapter(Vector<Order> list, Context context,
			List<CountDownTimeInfo> mList) {
		super();
		this.list = list;
		this.context = context;
		this.mList = mList;
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
		return list.get(arg0).getOrderid();
	}

	public void UpdateUi(Vector<Order> ve) {
		list = ve;
		this.notifyDataSetChanged();
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		Log.i("getdata", "getView方法执行");
		if (arg1 == null) {
			holder = new ViewHolder();
			arg1 = LayoutInflater.from(context).inflate(
					R.layout.helpmsg_content, null);
			holder.help_user_1 = (TextView) arg1.findViewById(R.id.help_user_1);
			holder.help_user_2 = (TextView) arg1.findViewById(R.id.help_user_2);
			holder.title_order = (TextView) arg1.findViewById(R.id.title_order);
			holder.order_time = (TextView) arg1.findViewById(R.id.order_time);
			holder.num_day = (TextView) arg1.findViewById(R.id.num_day);
			holder.helped_btn = (Button) arg1.findViewById(R.id.helped_btn);
			arg1.setTag(holder);
		} else {
			holder = (ViewHolder) arg1.getTag();
		}
		// 获取内容
		Order order = list.get(arg0);
		CountDownTimeInfo info = mList.get(arg0);
		holder.timer = new CountDownTimerUtil(holder.num_day, 0, 1, context,
				order.getHelpid() + "", order.getOrder_title(),
				order.getOrderid() + "");
		holder.helped_btn.setOnClickListener(new TestOnclickListener(order
				.getHelpid() + "", order.getOrder_title(), order.getOrderid()
				+ ""));
		// 显示帮助人用户名
		if (order.getUserid() + "" != null) {
			Users u = MainClientService.viewUsers(order.getUserid() + "");
			holder.help_user_1.setText(u.getUsername());
		}
		// 显示被帮助人用户名
		if (order.getHelped_userid() + "" != null) {
			Users u = MainClientService
					.viewUsers(order.getHelped_userid() + "");
			holder.help_user_2.setText(u.getUsername());
		}
		// 显示帮助标题
		if (order.getOrder_title() != null) {
			holder.title_order.setText(order.getOrder_title());
		}
		// 显示时间
		if (order.getOrder_date() != null) {
			holder.order_time.setText(ShowTime.getInterval(ShowTime
					.conversion(order.getOrder_date())));
		}

		// 倒计时
		long maxTime = info.time - (System.currentTimeMillis() - info.system)
				/ 1000;
		if (maxTime <= 0) {
			/*
			 * Intent i = new Intent(context, AssessActivity.class);
			 * i.putExtra("order_title", order.getOrder_title());
			 * i.putExtra("order_helpid", order.getHelpid() + "");
			 * context.startActivity(i);
			 */
			Toast.makeText(context, "帮助倒计时时间到了，请您给出评价吧", Toast.LENGTH_LONG);
		} else {
			holder.timer.resetCountDownTimer(holder.num_day, maxTime, 1);
		}

		return arg1;
	}

	class ViewHolder {
		// 主要内容控件
		TextView help_user_1;
		TextView help_user_2;
		TextView title_order;
		TextView order_time;
		TextView num_day;
		Button helped_btn;
		CountDownTimerUtil timer;
	}

	class TestOnclickListener implements OnClickListener {
		public String helpid;
		public String title;
		public String orderid;
		private RatingBar rating;
		private double num;

		public TestOnclickListener(String helpid, String title, String orderid) {
			super();
			this.helpid = helpid;
			this.title = title;
			this.orderid = orderid;
		}

		@SuppressLint("NewApi")
		@Override
		public void onClick(View arg0) {
			try {
				final AlertDialog.Builder builder = new AlertDialog.Builder(
						context, R.style.AlertDialog);
				LayoutInflater factory = LayoutInflater.from(context);
				final View v = factory.inflate(R.layout.assess, null);
				rating = (RatingBar) v.findViewById(R.id.rating);
				rating.setOnRatingBarChangeListener(new RatListener());
				builder.setIcon(R.drawable.ic_launcher);
				builder.setTitle("星级感受");
				builder.setView(v);
				builder.setPositiveButton("小评一下",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								HandlerData();
								dialog.dismiss();
							}
						});
				builder.setNegativeButton("有缘再评",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								HandlerData();
								dialog.dismiss();
							}
						});
				AlertDialog dialog = builder.create();
				dialog.show();
				// 改变按钮颜色
				Button button = dialog
						.getButton(DialogInterface.BUTTON_POSITIVE);
				button.setTextColor(Color.parseColor("#00c8aa"));
				// 改变Title大小
				Field mAlert = AlertDialog.class.getDeclaredField("mAlert");
				mAlert.setAccessible(true);
				Object alertController = mAlert.get(dialog);

				Field mTitleView = alertController.getClass().getDeclaredField(
						"mTitleView");
				mTitleView.setAccessible(true);

				TextView title = (TextView) mTitleView.get(alertController);
				title.setTextSize(18);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// 处理数据 对接服务器端
		public void HandlerData() {
			if (num <= 0) {
				num = 0;
			}
			String message = MainClientService.assess(Tools.userid, helpid,
					num / 5);
			Toast.makeText(context, message, Toast.LENGTH_LONG).show();
			MainClientService.deleteOrder(orderid);
		}

		// 添加rating监听事件
		class RatListener implements OnRatingBarChangeListener {

			@Override
			public void onRatingChanged(RatingBar arg0, float arg1, boolean arg2) {
				num = (double) arg1;
			}

		}
	}

}
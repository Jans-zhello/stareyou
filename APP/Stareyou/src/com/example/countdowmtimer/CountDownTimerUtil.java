package com.example.countdowmtimer;

import java.lang.reflect.Field;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Stareyou.R;
import com.example.Stareyou.server.MainClientService;
import com.example.Stareyou.util.Tools;

public class CountDownTimerUtil {
	// 倒计时timer
	private CountDownTimer countDownTimer;
	private Context context;
	public String helpid;
	public String title;
	public String orderid;
	private RatingBar rating;
	private TextView rat_tip;
	private double num;

	// 单位计时文本设置回调接口
	// private OnSetTextListener listener;
	// private TextView timeView;

	/**
	 * @param button需要显示倒计时的Button
	 * @param defaultString默认显示的字符串
	 * @param max需要进行倒计时的最大值
	 *            ,单位是秒
	 * @param interval倒计时的间隔
	 *            ，单位是秒
	 */
	public CountDownTimerUtil(final TextView view, long max, long interval,
			String DateF, Context context, String helpid, String title,
			String orderid) {
		this.context = context;
		this.helpid = helpid;
		this.title = title;
		this.orderid = orderid;
		// 由于CountDownTimer并不是准确计时，在onTick方法调用的时候，time会有1-10ms左右的误差，这会导致最后一秒不会调用onTick()
		// 因此，设置间隔的时候，默认减去了10ms，从而减去误差。
		// 经过以上的微调，最后一秒的显示时间会由于10ms延迟的积累，导致显示时间比1s长max*10ms的时间，其他时间的显示正常,总时间正常
		countDownTimer = new CountDownTimer(max * 1000, interval * 1000 - 10) {

			@Override
			public void onTick(long time) {
				view.setText(DateUtil.getHour3(time / 1000));
			}

			@Override
			public void onFinish() {
			}
		};
	}

	public void resetCountDownTimer(final TextView view, long max, long interval) {

		if (countDownTimer != null) {
			countDownTimer.cancel();
		}
		if (max <= 0) {
			return;
		}
		countDownTimer = new CountDownTimer(max * 1000, interval * 1000 - 10) {

			@Override
			public void onTick(long time) {
				view.setText(DateUtil.getHour3(time / 1000));
			}

			@SuppressLint("NewApi")
			@Override
			public void onFinish() {
				try {
					final AlertDialog.Builder builder = new AlertDialog.Builder(
							context, R.style.AlertDialog);
					LayoutInflater factory = LayoutInflater.from(context);
					final View v = factory.inflate(R.layout.assess, null);
					rating = (RatingBar) v.findViewById(R.id.rating);
					rat_tip = (TextView) v.findViewById(R.id.rat_tip);
					rat_tip.setTextColor(Color.BLACK);
					rating.setOnRatingBarChangeListener(new RatListener());
					builder.setIcon(R.drawable.ic_launcher);
					builder.setTitle(title + "订单过期la");
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
					button.setTextColor(Color.parseColor("#FFFF00"));
					// 改变Title大小
					Field mAlert = AlertDialog.class.getDeclaredField("mAlert");
					mAlert.setAccessible(true);
					Object alertController = mAlert.get(dialog);

					Field mTitleView = alertController.getClass()
							.getDeclaredField("mTitleView");
					mTitleView.setAccessible(true);

					TextView title = (TextView) mTitleView.get(alertController);
					title.setTextSize(18);
					title.setTextColor(Color.YELLOW);
				} catch (Exception e) {
					// TODO: handle exception
				}

				/*
				 * Intent i = new Intent(context, AssessActivity.class);
				 * i.putExtra("order_title", title); i.putExtra("order_helpid",
				 * helpid); context.startActivity(i);
				 */
				cancel();
			}
		};
		countDownTimer.start();
	}

	public CountDownTimerUtil(final TextView view, long max, long interval,
			Context context, String helpid, String title, String orderid) {
		this(view, max, interval, "", context, helpid, title, orderid);
	}

	/**
	 * 开始倒计时
	 */
	public void start() {
		countDownTimer.start();
	}

	/**
	 * 结束
	 */
	public void cancel() {
		countDownTimer.cancel();
	}

	// 处理数据 对接服务器端
	public void HandlerData() {
		if (num <= 0) {
			num = 0;
		}
		String message = MainClientService
				.assess(Tools.userid, helpid, num / 5);
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

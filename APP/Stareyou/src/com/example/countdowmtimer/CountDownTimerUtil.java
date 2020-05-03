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
	// ����ʱtimer
	private CountDownTimer countDownTimer;
	private Context context;
	public String helpid;
	public String title;
	public String orderid;
	private RatingBar rating;
	private TextView rat_tip;
	private double num;

	// ��λ��ʱ�ı����ûص��ӿ�
	// private OnSetTextListener listener;
	// private TextView timeView;

	/**
	 * @param button��Ҫ��ʾ����ʱ��Button
	 * @param defaultStringĬ����ʾ���ַ���
	 * @param max��Ҫ���е���ʱ�����ֵ
	 *            ,��λ����
	 * @param interval����ʱ�ļ��
	 *            ����λ����
	 */
	public CountDownTimerUtil(final TextView view, long max, long interval,
			String DateF, Context context, String helpid, String title,
			String orderid) {
		this.context = context;
		this.helpid = helpid;
		this.title = title;
		this.orderid = orderid;
		// ����CountDownTimer������׼ȷ��ʱ����onTick�������õ�ʱ��time����1-10ms���ҵ�����ᵼ�����һ�벻�����onTick()
		// ��ˣ����ü����ʱ��Ĭ�ϼ�ȥ��10ms���Ӷ���ȥ��
		// �������ϵ�΢�������һ�����ʾʱ�������10ms�ӳٵĻ��ۣ�������ʾʱ���1s��max*10ms��ʱ�䣬����ʱ�����ʾ����,��ʱ������
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
					builder.setTitle(title + "��������la");
					builder.setView(v);
					builder.setPositiveButton("С��һ��",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									HandlerData();
									dialog.dismiss();
								}
							});
					builder.setNegativeButton("��Ե����",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									HandlerData();
									dialog.dismiss();
								}
							});
					AlertDialog dialog = builder.create();
					dialog.show();
					// �ı䰴ť��ɫ
					Button button = dialog
							.getButton(DialogInterface.BUTTON_POSITIVE);
					button.setTextColor(Color.parseColor("#FFFF00"));
					// �ı�Title��С
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
	 * ��ʼ����ʱ
	 */
	public void start() {
		countDownTimer.start();
	}

	/**
	 * ����
	 */
	public void cancel() {
		countDownTimer.cancel();
	}

	// �������� �Խӷ�������
	public void HandlerData() {
		if (num <= 0) {
			num = 0;
		}
		String message = MainClientService
				.assess(Tools.userid, helpid, num / 5);
		Toast.makeText(context, message, Toast.LENGTH_LONG).show();
		MainClientService.deleteOrder(orderid);
	}

	// ���rating�����¼�
	class RatListener implements OnRatingBarChangeListener {

		@Override
		public void onRatingChanged(RatingBar arg0, float arg1, boolean arg2) {
			num = (double) arg1;
		}

	}
}

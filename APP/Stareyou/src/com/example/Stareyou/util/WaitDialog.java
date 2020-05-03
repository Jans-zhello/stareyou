package com.example.Stareyou.util;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.Stareyou.R;

/**
 * 自定义等待对话框
 */
public class WaitDialog extends Dialog {
	private Animation animation = null;
	private TextView hintTv;
	private boolean isCanceled;

	private Handler handler = new Handler() {
	};
	public static int dismissDelay = 19; // 设置几秒关闭，自动刷新

	private UpdateTime updateTime = new UpdateTime();

	/**
	 * 声明为静态方法外部可直接类名.方法名调用
	 * 
	 * @param context
	 * @param message
	 * @return
	 */
	// 启动并显示dialog,点击对话框以外的区域不关闭对话框.
	public static WaitDialog showProgressDialog(Context context, String message) {
		WaitDialog dialog = new WaitDialog(context, message);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setCancelable(false);
		dialog.refreshTime();
		dialog.show();
		return dialog;
	}

	// 启动并显示dialog,点击对话框以外的区域直接关闭对话框.
	public static WaitDialog showProgressDialog(Context context,
			String message, boolean canceledOnTouchOutside, boolean cancelable) {
		WaitDialog dialog = new WaitDialog(context, message);
		dialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
		dialog.setCancelable(cancelable);
		dialog.show();
		return dialog;
	}

	// 设置提示用户信息
	public void setHint(String message) {
		hintTv.setText(message);
	}

	// 构造方法
	public WaitDialog(Context context, String message) {
		super(context, R.style.waiting_dialog_style);
		initView(context, message);
	}

	/**
	 * 动态刷新 还剩几秒关闭时间
	 */
	private void refreshTime() {
		handler.postDelayed(updateTime, 1000);
	}

	class UpdateTime implements Runnable {
		public void run() {
			setHint("正在等待对方进入聊天窗口,若无应答," + (dismissDelay--) + "秒自动关闭");
			if (dismissDelay == -1) {
				dismiss();
				dismissDelay = 19;
			} else {
				handler.postDelayed(updateTime, 1000);
			}

		}
	}

	// 初始化控件
	private void initView(Context context, String message) {
		View view = LayoutInflater.from(context).inflate(
				R.layout.dailog_waiting, null);

		if (!TextUtils.isEmpty(message)) {
			hintTv = (TextView) view.findViewById(R.id.progress_message);
			hintTv.setText(message);
		}

		ImageView image = (ImageView) view.findViewById(R.id.progress_view);
		animation = AnimationUtils.loadAnimation(context,
				R.anim.loading_animation);
		image.startAnimation(animation);

		setContentView(view);
	}

	// 关闭dialog
	public void dismiss() {
		if (isShowing()) {
			super.dismiss();
			animation.cancel();
		}
		if (handler != null) {
			handler.removeCallbacksAndMessages(null);
			handler = null;
		}
		isCanceled = true;
	}

}

package com.example.Stareyou;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.example.Stareyou.Exit.BaseActivity;
import com.example.Stareyou.homelayout.AccountFragment;
import com.example.Stareyou.homelayout.BaseFragmentPagerAdapter;
import com.example.Stareyou.homelayout.ContactsFragment;
import com.example.Stareyou.homelayout.HelpFragment;
import com.example.Stareyou.homelayout.LiaotianFragment;
import com.example.Stareyou.homelayout.ShareFragment;
import com.example.Stareyou.model.Amaze;
import com.example.Stareyou.server.InformClient;
import com.example.Stareyou.server.MainClientService;
import com.example.Stareyou.util.Tools;

public class MainActivity extends BaseActivity {
	private static ViewPager viewPager;
	private RadioGroup rGroup;
	private RadioButton[] rb = new RadioButton[5];
	private Drawable[] drawables = new Drawable[5];
	private static MyAdapter myAdapter;
	private FragmentManager fragmentManager;
	private List<Fragment> list;
	private static final int NO_1 = 0x1;

	private Handler handler = new Handler();

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.main);
		// 如下两行代码解决socket连接不上的问题
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		initWidget();
		handler.postDelayed(r, 1000);
	}

	Runnable r = new Runnable() {

		@Override
		public void run() {
			if (showHelpToMe() != null) {
				showTip(showHelpToMe());
			} else {
				handler.postDelayed(r, 1000);
			}
		}
	};

	/**
	 * 检查是否有人帮助我
	 */
	private Amaze showHelpToMe() {
		if (!Tools.userid.equals("")) {
			Amaze amaze = MainClientService.viewAmaze(Tools.userid);
			if (amaze != null) {
				return amaze;
			}
		}
		return null;
	}

	// 通知栏
	private void showTip(Amaze amaze) {
		NotificationCompat.Builder builder = new NotificationCompat.Builder(
				this);
		builder.setSmallIcon(R.drawable.ic_launcher);
		builder.setContentTitle("您有一条消息,点击打开");
		builder.setContentText("有人愿意帮助您了");
		// 设置Notification.Default_ALL(默认启用全部服务(呼吸灯，铃声等)
		builder.setDefaults(Notification.DEFAULT_ALL);
		// 调用NotificationCompat.Builder的setContentIntent()来添加PendingIntent

		Intent intent = new Intent(this, LiaoTianWindowActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra("amazeid", amaze.getAmazeid() + "");
		intent.putExtra("helpid", amaze.getHelpid() + "");
		intent.putExtra("username", amaze.getAmaze_username());
		intent.putExtra("amaze_userid", amaze.getAmaze_userid() + "");
		PendingIntent pi = PendingIntent.getActivity(this, 0, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);

		builder.setContentIntent(pi);
		// 获取Notification
		Notification n = builder.build();
		n.flags |= Notification.FLAG_AUTO_CANCEL; // 点击一次后自动消除
		// 通过NotificationCompat.Builder.build()来获得notification对象自己
		NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		// 然后调用NotificationManager.notify()向系统转交
		manager.notify(NO_1, n);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		handler.removeCallbacks(r);
	}

	/**
	 * 广播处理activity返回来的数据
	 */
	public static class MyReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			Bundle bundle = intent.getExtras();
			String s = bundle.getString("jumpid");
			if (s.equals("0")) {
				Log.i("setdata", "收到广播");
				viewPager.setCurrentItem(0);
				myAdapter.notifyFragmentByPosition(0);
			} else if (s.equals("1")) {
				Log.i("setdata", "收到广播");
				viewPager.setCurrentItem(1);
				myAdapter.notifyFragmentByPosition(1);
			} else if (s.equals("2")) {
				myAdapter.notifyFragmentByPosition(2);
			} else if (s.equals("3")) {
				myAdapter.notifyFragmentByPosition(3);
			} else if (s.equals("4")) {
				viewPager.setCurrentItem(0);
				myAdapter.notifyDataSetChanged();
			}
		}

	}

	private void initWidget() {
		viewPager = (ViewPager) findViewById(R.id.main_viewpager);
		// 将RadioButton装进数组中
		rGroup = (RadioGroup) findViewById(R.id.main_radio);
		rb[0] = (RadioButton) findViewById(R.id.main_radio_huzhu);
		rb[1] = (RadioButton) findViewById(R.id.main_radio_fenxiang);
		rb[2] = (RadioButton) findViewById(R.id.main_radio_lianxiren);
		rb[3] = (RadioButton) findViewById(R.id.main_radio_liaotian);
		rb[4] = (RadioButton) findViewById(R.id.main_radio_zhanghao);
		// for循环对每一个RadioButton图片进行缩放
		for (int i = 0; i < 5; i++) {
			// 挨着给每个RadioButton加入drawable限制边距以控制显示大小
			drawables = rb[i].getCompoundDrawables();
			// 获取drawables，2/5表示图片要缩小的比例
			Rect r = new Rect(0, 0, drawables[1].getMinimumWidth() * 2 / 5,
					drawables[1].getMinimumHeight() * 2 / 5);
			// 定义一个Rect边界
			drawables[1].setBounds(r);
			// 给每一个RadioButton设置图片大小
			rb[i].setCompoundDrawables(null, drawables[1], null, null);
		}
		list = getlistfragments();
		fragmentManager = getSupportFragmentManager();
		myAdapter = new MyAdapter(fragmentManager);
		viewPager.setAdapter(myAdapter);
		viewPager.setCurrentItem(0);
		rGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.main_radio_huzhu:
					viewPager.setCurrentItem(0);
					break;
				case R.id.main_radio_fenxiang:
					viewPager.setCurrentItem(1);
					break;
				case R.id.main_radio_lianxiren:
					viewPager.setCurrentItem(2);
					break;
				case R.id.main_radio_liaotian:
					viewPager.setCurrentItem(3);
					break;
				case R.id.main_radio_zhanghao:
					viewPager.setCurrentItem(4);
					break;
				default:
					break;
				}
			}
		});
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
				RadioButton button = (RadioButton) rGroup.getChildAt(arg0);
				button.setChecked(true);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	public List<Fragment> getlistfragments() {
		List<Fragment> list = new ArrayList<Fragment>();
		list.add(HelpFragment.newInstance());
		list.add(ShareFragment.newInstance());
		list.add(ContactsFragment.newInstance());
		list.add(LiaotianFragment.newInstance());
		list.add(AccountFragment.newInstance());
		return list;
	}

	class MyAdapter extends BaseFragmentPagerAdapter {
		public MyAdapter(FragmentManager fm) {
			super(fm, list);
		}
	}

	/**
	 * 再按一次返回到桌面实现
	 */
	int count = 0;

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			if (event.getAction() == KeyEvent.ACTION_DOWN
					&& event.getRepeatCount() == 0) {
				// 判断间隔时间
				if (count == 1) {
					try {
						InformClient.close();
						MainClientService.logout(Tools.userid);
						Intent intent = new Intent();
						intent.setAction(BaseActivity.EXIT_APP_ACTION);
						sendBroadcast(intent);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					String msg = "再按一次退出程序";
					Toast.makeText(MainActivity.this, msg, 0).show();
					count++;
				}

			}
			return true;
		}
		return super.dispatchKeyEvent(event);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		count = 0;
	}
}

package com.example.Stareyou.homelayout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.example.Stareyou.CollectionActivity;
import com.example.Stareyou.LoginActivity;
import com.example.Stareyou.PhotoListActivity;
import com.example.Stareyou.R;
import com.example.Stareyou.RegisterActivity;
import com.example.Stareyou.SendListActivity;
import com.example.Stareyou.SuggestionActivity;
import com.example.Stareyou.VideoListActivity;
import com.example.Stareyou.ViewUserInfoActivity;
import com.example.Stareyou.Exit.BaseActivity;
import com.example.Stareyou.model.Users;
import com.example.Stareyou.server.InformClient;
import com.example.Stareyou.server.MainClientService;
import com.example.Stareyou.util.SaveLoginUserUtils;
import com.example.Stareyou.util.Tools;

public class AccountFragment extends Fragment {

	/**
	 * 中间部分图片切换
	 */
	private ViewPager viewPager; // android-support-v4中的滑动组件
	private List<ImageView> imageViews; // 滑动的图片集合
	private String[] titles; // 图片标题
	private int[] imageResId; // 图片ID
	private List<View> dots; // 图片标题正文的那些点
	private TextView tv_title;
	private int currentItem = 0; // 当前图片的索引号
	private ScheduledExecutorService scheduledExecutorService;

	private TextView name;
	private ImageView img;
	private ImageView detail;
	private List<Users> users;
	// 重要控件
	private View view = null;
	private TextView grade;// 幸福度
	private TextView fabiao_number;// 发表数量
	private TextView shoucang_number;
	private TextView guanzhu_number;
	private TextView fensi_number;
	private TextView bangzhu_number;
	private TextView fabiao_list;
	private TextView shoucang_list;
	private TextView video_list;
	private TextView photo_list;
	private TextView fankui_list;
	private TextView tuichu_list;

	private static final AccountFragment accountfragment = new AccountFragment();

	public static AccountFragment newInstance() {
		return accountfragment;
	}

	@SuppressLint("NewApi")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.account, container, false);
		detail = (ImageView) view.findViewById(R.id.detail);
		name = (TextView) view.findViewById(R.id.name);
		img = (ImageView) view.findViewById(R.id.user_head);
		grade = (TextView) view.findViewById(R.id.grade);
		fabiao_number = (TextView) view.findViewById(R.id.fabiao_number);
		shoucang_number = (TextView) view.findViewById(R.id.shoucang_number);
		guanzhu_number = (TextView) view.findViewById(R.id.guanzhu_number);
		fensi_number = (TextView) view.findViewById(R.id.fensi_number);
		bangzhu_number = (TextView) view.findViewById(R.id.bangzhu_number);
		fabiao_list = (TextView) view.findViewById(R.id.fabiao_list);
		shoucang_list = (TextView) view.findViewById(R.id.shoucang_list);
		video_list = (TextView) view.findViewById(R.id.video_list);
		photo_list = (TextView) view.findViewById(R.id.photo_list);
		fankui_list = (TextView) view.findViewById(R.id.fankui_list);
		tuichu_list = (TextView) view.findViewById(R.id.tuichu_list);
		users = new SaveLoginUserUtils(getActivity()).getAllUsers();
		if (Tools.usericon != null && !Tools.flag) {
			img.setImageBitmap(Tools.usericon);
		}
		Log.i("username", Tools.username);
		if (!Tools.username.equals("") && !Tools.flag) {
			name.setText(Tools.username);
		}
		name.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (users == null) {
					Intent intent = new Intent(getActivity(),
							RegisterActivity.class);
					startActivity(intent);
				} else if (users != null && Tools.flag) {
					Intent intent = new Intent(getActivity(),
							LoginActivity.class);
					startActivity(intent);
				} else {
					Intent intent = new Intent(getActivity(),
							ViewUserInfoActivity.class);
					startActivity(intent);
				}

			}
		});
		detail.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (users == null) {
					Intent intent = new Intent(getActivity(),
							RegisterActivity.class);
					startActivity(intent);

				} else if (users != null && Tools.flag) {
					Intent intent = new Intent(getActivity(),
							LoginActivity.class);
					startActivity(intent);
				} else {
					Intent intent = new Intent(getActivity(),
							ViewUserInfoActivity.class);
					startActivity(intent);

				}
			}
		});
		fabiao_list.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(getActivity(), SendListActivity.class));

			}
		});
		shoucang_list.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(getActivity(),
						CollectionActivity.class));

			}
		});
		video_list.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(getActivity(), VideoListActivity.class));

			}
		});
		photo_list.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(getActivity(), PhotoListActivity.class));

			}
		});
		fankui_list.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(getActivity(),
						SuggestionActivity.class));

			}
		});
		tuichu_list.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				new AlertDialog.Builder(getActivity())
						.setTitle("提示")
						.setMessage("确认退出吗？")
						.setIcon(R.drawable.ic_launcher)
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										try {
											InformClient.close();
											MainClientService
													.logout(Tools.userid);
											Intent intent = new Intent();
											intent.setAction(BaseActivity.EXIT_APP_ACTION);
											getActivity().sendBroadcast(intent);
										} catch (Exception e) {
											e.printStackTrace();
										}
									}
								})
						.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {

									}
								}).show();
			}
		});

		// 如下两行代码解决socket连接不上的问题
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		initNumber();
		initPictureExchange(view);
		return view;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		// 当Activity显示出来后，每两秒钟切换一次图片显示
		scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 2,
				TimeUnit.SECONDS);
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		// 当Activity不可见的时候停止切换
		scheduledExecutorService.shutdown();
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		// 当Activity不可见的时候停止切换
		scheduledExecutorService.shutdown();
	}

	// 更新各种数量
	public void initNumber() {
		if (!Tools.userid.equals("")) {
			Users u = MainClientService.viewUsers(Tools.userid);
			grade.setText(u.getHelp_number() * 10 + 100 + "%");
			fabiao_number.setText(u.getSend_number() + "");
			shoucang_number.setText(u.getCollection_number() + "");
			guanzhu_number.setText(u.getConcern_number() + "");
			fensi_number.setText(u.getFans_number() + "");
			bangzhu_number.setText(u.getHelp_number() + "");
		}

	}

	// 切换当前显示的图片
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			viewPager.setCurrentItem(currentItem);// 切换当前显示的图片
		};
	};

	public void initPictureExchange(View view) {
		imageResId = new int[] { R.drawable.a, R.drawable.b, R.drawable.c,
				R.drawable.d, R.drawable.e };
		titles = new String[imageResId.length];
		titles[0] = "stareyou只为遇见更好的自己";
		titles[1] = "stareyou只为遇见更好的自己";
		titles[2] = "stareyou只为遇见更好的自己";
		titles[3] = "stareyou只为遇见更好的自己";
		titles[4] = "stareyou只为遇见更好的自己";

		imageViews = new ArrayList<ImageView>();

		// 初始化图片资源
		for (int i = 0; i < imageResId.length; i++) {
			ImageView imageView = new ImageView(getActivity());
			imageView.setImageResource(imageResId[i]);
			imageView.setScaleType(ScaleType.CENTER_CROP);
			imageViews.add(imageView);
		}

		dots = new ArrayList<View>();
		dots.add(view.findViewById(R.id.v_dot0));
		dots.add(view.findViewById(R.id.v_dot1));
		dots.add(view.findViewById(R.id.v_dot2));
		dots.add(view.findViewById(R.id.v_dot3));
		dots.add(view.findViewById(R.id.v_dot4));

		tv_title = (TextView) view.findViewById(R.id.tv_title);
		tv_title.setText(titles[0]);
		viewPager = (ViewPager) view.findViewById(R.id.vp);
		viewPager.setAdapter(new MyAdapter());// 设置填充ViewPager页面的适配器
		// 设置一个监听器，当ViewPager中的页面改变时调用
		viewPager.setOnPageChangeListener(new MyPageChangeListener());

	}

	/**
	 * 换行切换任务
	 * 
	 * @author Administrator
	 * 
	 */
	private class ScrollTask implements Runnable {

		public void run() {
			synchronized (viewPager) {
				System.out.println("currentItem: " + currentItem);
				currentItem = (currentItem + 1) % imageViews.size();
				handler.obtainMessage().sendToTarget(); // 通过Handler切换图片
			}
		}

	}

	/**
	 * 当ViewPager中页面的状态发生改变时调用
	 * 
	 * @author Administrator
	 * 
	 */
	private class MyPageChangeListener implements OnPageChangeListener {
		private int oldPosition = 0;

		/**
		 * This method will be invoked when a new page becomes selected.
		 * position: Position index of the new selected page.
		 */
		public void onPageSelected(int position) {
			currentItem = position;
			tv_title.setText(titles[position]);
			dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
			dots.get(position).setBackgroundResource(R.drawable.dot_focused);
			oldPosition = position;
		}

		public void onPageScrollStateChanged(int arg0) {

		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}
	}

	/**
	 * 填充ViewPager页面的适配器
	 * 
	 * @author Administrator
	 * 
	 */
	private class MyAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return imageResId.length;
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(imageViews.get(arg1));
			return imageViews.get(arg1);
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView((View) arg2);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {

		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {

		}

		@Override
		public void finishUpdate(View arg0) {

		}
	}
}

package com.example.Stareyou.homelayout;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.Stareyou.CustomIndicator;
import com.example.Stareyou.R;
import com.example.Stareyou.SendShareActivity;
import com.example.Stareyou.util.Tools;

public class ShareFragment extends Fragment {
	private ViewPager viewpager;
	private MyAdapter myAdapter;
	private List<Fragment> list;
	private CustomIndicator mCustomIndicator;
	private TextView haorenquan;
	private TextView xiaoxi;

	private View v;
	private ImageView title_head_img1;
	private ImageView title_sendshare_img;

	private static final ShareFragment sharefragment = new ShareFragment();

	public static ShareFragment newInstance() {
		return sharefragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.share, container, false);
		mCustomIndicator = (CustomIndicator) view
				.findViewById(R.id.share_custom_nav);
		haorenquan = (TextView) view.findViewById(R.id.haorenquan);
		xiaoxi = (TextView) view.findViewById(R.id.xiaoxi);
		v = view.findViewById(R.id.layout_title_share);
		title_head_img1 = (ImageView) v.findViewById(R.id.title_head_img1);
		title_sendshare_img = (ImageView) v
				.findViewById(R.id.title_sendshare_img);
		if (Tools.usericon != null && !Tools.flag) {
			title_head_img1.setImageBitmap(Tools.usericon);
		}
		title_sendshare_img.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(), SendShareActivity.class));
			}
		});
		// 获取数据源
		list = getListFragment();
		viewpager = (ViewPager) view.findViewById(R.id.share_viewpager);
		myAdapter = new MyAdapter(getChildFragmentManager());
		viewpager.setAdapter(myAdapter);
		viewpager.setCurrentItem(0);
		viewpager.setOffscreenPageLimit(2);
		haorenquan.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				viewpager.setCurrentItem(0);
			}
		});
		xiaoxi.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				viewpager.setCurrentItem(1);
			}
		});
		viewpager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				mCustomIndicator.getViewNewInstance().drawArguments(arg0, arg1);
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
		return view;
	}

	private List<Fragment> getListFragment() {
		List<Fragment> list = new ArrayList<Fragment>();
		list.add(GoodPersonFragment.newInstance());
		list.add(ShareMsgFragment.newInstance());
		return list;
	}

	class MyAdapter extends FragmentPagerAdapter {

		public MyAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			return list == null ? null : list.get(arg0);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list == null ? 0 : list.size();
		}

	}
}

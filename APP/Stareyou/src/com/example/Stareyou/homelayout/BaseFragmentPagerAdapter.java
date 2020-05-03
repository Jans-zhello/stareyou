package com.example.Stareyou.homelayout;

import java.util.List;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

public class BaseFragmentPagerAdapter extends FragmentPagerAdapter {
	private FragmentManager fm;
	private List<Fragment> list;

	// 保存每个Fragment的Tag，刷新页面的依据
	protected SparseArray<String> tags = new SparseArray<String>();

	public BaseFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
		super(fm);
		this.fm = fm;
		this.list = list;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// 得到缓存的fragment
		Fragment fragment = (Fragment) super.instantiateItem(container,
				position);
		String tag = fragment.getTag();
		// 保存每个Fragment的Tag
		tags.put(position, tag);
		return fragment;
	}

	// 拿到指定位置的Fragment
	public Fragment getFragmentByPosition(int position) {
		return fm.findFragmentByTag(tags.get(position));
	}

	public List<Fragment> getFragments() {
		return fm.getFragments();
	}

	// 刷新指定位置的Fragment
	@SuppressLint("NewApi")
	public void notifyFragmentByPosition(int position) {
		tags.removeAt(position);
		notifyDataSetChanged();
	}

	@Override
	public int getItemPosition(Object object) {
		Fragment fragment = (Fragment) object;
		// 如果Item对应的Tag存在，则不进行刷新
		if (tags.indexOfValue(fragment.getTag()) > -1) {
			return super.getItemPosition(object);
		}
		return POSITION_NONE;
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
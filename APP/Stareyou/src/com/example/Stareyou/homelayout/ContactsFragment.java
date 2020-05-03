package com.example.Stareyou.homelayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.Stareyou.R;
import com.example.Stareyou.Adapter.ExpandableListViewAdapter;
import com.example.Stareyou.Adapter.ExpandableListViewAdapter.ListRow;
import com.example.Stareyou.Adapter.ExpandableListViewAdapter.RowContent;
import com.example.Stareyou.model.Concern;
import com.example.Stareyou.model.Order;
import com.example.Stareyou.server.MainClientService;
import com.example.Stareyou.util.Constants;
import com.example.Stareyou.util.Tools;

public class ContactsFragment extends Fragment {
	private ExpandableListView list;
	private ExpandableListViewAdapter adapter;

	private List<ListRow> groupList = new ArrayList<ListRow>();
	private List<List<ListRow>> childList = new ArrayList<List<ListRow>>();

	private View view = null;

	private static final ContactsFragment contactsfragment = new ContactsFragment();

	public static ContactsFragment newInstance() {
		return contactsfragment;
	}

	@SuppressLint("NewApi")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.contacts, container, false);
		fillInListContent(view);
		// 如下两行代码解决socket连接不上的问题
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		return view;
	}

	public void fillInListContent(View view) {
		list = (ExpandableListView) view.findViewById(R.id.listView);
		getData();
		if (adapter == null) {
			adapter = new ExpandableListViewAdapter(getActivity(), groupList,
					childList);
			list.setAdapter(adapter);
			list.setGroupIndicator(null);
			list.setDivider(null);
		} else {
			adapter.UpdateUI(groupList, childList);
			list.setAdapter(adapter);
			list.setGroupIndicator(null);
			list.setDivider(null);
		}

	}
	public void getData() {
		groupList = new ArrayList<ListRow>();
		childList = new ArrayList<List<ListRow>>();
		for (int i = 0; i < 4; i++) {
			ListRow itemRow = new ListRow();
			itemRow.setRowType(Constants.ROW_IN_LIST);
			itemRow.setLayout_id(R.layout.contacts_group_row);
			itemRow.setSelectable(true);

			ArrayList<RowContent> rowContents = new ArrayList<RowContent>();
			RowContent content = new RowContent();
			content.setLayout_id(R.id.groupText);
			content.setType(Constants.TEXT);
			if (i == 0) {
				content.setText(getResources().getString(R.string.my_fensi));
				content.setColor(R.string.contacts_color);
			} else if (i == 1) {
				content.setText(getResources().getString(R.string.my_concern));
				content.setColor(R.string.contacts_color);
			} else if (i == 2) {
				content.setText(getResources().getString(R.string.my_xiangzhu));
				content.setColor(R.string.contacts_color);
			} else {
				content.setText(getResources().getString(R.string.my_shouzhu));
				content.setColor(R.string.contacts_color);
			}
			rowContents.add(content);

			content = new RowContent();
			content.setLayout_id(R.id.expandButtonImage);
			content.setType(Constants.EXPAND_IMAGE);
			rowContents.add(content);

			itemRow.setRowContents(rowContents);
			groupList.add(itemRow);
			List<ListRow> rows = new ArrayList<ListRow>();
			// 获取聊天人员
			if (i == 0) {
				// 获取粉丝列表
				Vector<Concern> v = MainClientService.getFansData(Tools.userid);
				if (v != null && v.size() > 0) {
					for (Concern concern : v) {
						itemRow = new ListRow();
						itemRow.setRowType(Constants.ROW_IN_LIST);
						itemRow.setLayout_id(R.layout.contacts_child_row);
						itemRow.setSelectable(true);

						rowContents = new ArrayList<RowContent>();
						content = new RowContent();
						content.setLayout_id(R.id.contact_icon);
						content.setType(Constants.CONTACT_ICON);
						content.setUserid(concern.getUserid() + "");
						rowContents.add(content);

						content = new RowContent();
						content.setLayout_id(R.id.childText);
						content.setType(Constants.TEXT);
						content.setUserid(concern.getUserid() + "");
						content.setText(concern.getUsername());
						content.setColor(getResources().getColor(
								R.color.contacts_list_color));
						rowContents.add(content);

						content = new RowContent();
						content.setLayout_id(R.id.delete);
						content.setConcern_id(concern.getConcernid() + "");
						content.setType(Constants.BUTTON);
						rowContents.add(content);

						itemRow.setRowContents(rowContents);
						rows.add(itemRow);

					}
				} else {
					itemRow = new ListRow();
					itemRow.setRowType(Constants.ROW_IN_LIST);
					itemRow.setLayout_id(R.layout.contacts_child_row);
					itemRow.setSelectable(true);

					rowContents = new ArrayList<RowContent>();

					content = new RowContent();
					content.setLayout_id(R.id.childText);
					content.setType(Constants.TEXT);
					content.setText("暂无相关好友");
					content.setColor(getResources().getColor(
							R.color.contacts_list_color));
					rowContents.add(content);

					itemRow.setRowContents(rowContents);
					rows.add(itemRow);
				}
			} else if (i == 1) {
				// 获取关注列表
				Vector<Concern> v = MainClientService
						.getConcernData(Tools.userid);
				if (v != null && v.size() > 0) {
					for (Concern concern : v) {
						itemRow = new ListRow();
						itemRow.setRowType(Constants.ROW_IN_LIST);
						itemRow.setLayout_id(R.layout.contacts_child_row);
						itemRow.setSelectable(true);

						rowContents = new ArrayList<RowContent>();
						content = new RowContent();
						content.setLayout_id(R.id.contact_icon);
						content.setType(Constants.CONTACT_ICON);
						content.setUserid(concern.getUserid() + "");
						rowContents.add(content);

						content = new RowContent();
						content.setLayout_id(R.id.childText);
						content.setType(Constants.TEXT);
						content.setUserid(concern.getUserid() + "");
						content.setText(concern.getUsername());
						content.setColor(getResources().getColor(
								R.color.contacts_list_color));
						rowContents.add(content);

						content = new RowContent();
						content.setLayout_id(R.id.delete);
						content.setConcern_id(concern.getConcernid() + "");
						content.setType(Constants.BUTTON);
						rowContents.add(content);

						itemRow.setRowContents(rowContents);
						rows.add(itemRow);
					}
				} else {
					itemRow = new ListRow();
					itemRow.setRowType(Constants.ROW_IN_LIST);
					itemRow.setLayout_id(R.layout.contacts_child_row);
					itemRow.setSelectable(true);

					rowContents = new ArrayList<RowContent>();

					content = new RowContent();
					content.setLayout_id(R.id.childText);
					content.setType(Constants.TEXT);
					content.setText("暂无相关好友");
					content.setColor(getResources().getColor(
							R.color.contacts_list_color));
					rowContents.add(content);

					itemRow.setRowContents(rowContents);
					rows.add(itemRow);
				}

			} else if (i == 2) {
				// 获取相助列表
				Vector<Order> v = MainClientService.getHelpData(Tools.userid);
				if (v != null && v.size() > 0) {
					for (Order order : v) {
						itemRow = new ListRow();
						itemRow.setRowType(Constants.ROW_IN_LIST);
						itemRow.setLayout_id(R.layout.contacts_child_row);
						itemRow.setSelectable(true);

						rowContents = new ArrayList<RowContent>();
						content = new RowContent();
						content.setLayout_id(R.id.contact_icon);
						content.setType(Constants.CONTACT_ICON);
						content.setUserid(order.getUserid() + "");
						rowContents.add(content);

						content = new RowContent();
						content.setLayout_id(R.id.childText);
						content.setType(Constants.TEXT);
						content.setUserid(order.getUserid() + "");
						content.setText(order.getUsername());
						content.setColor(getResources().getColor(
								R.color.contacts_list_color));
						rowContents.add(content);

						itemRow.setRowContents(rowContents);
						rows.add(itemRow);
					}
				} else {
					itemRow = new ListRow();
					itemRow.setRowType(Constants.ROW_IN_LIST);
					itemRow.setLayout_id(R.layout.contacts_child_row);
					itemRow.setSelectable(true);

					rowContents = new ArrayList<RowContent>();

					content = new RowContent();
					content.setLayout_id(R.id.childText);
					content.setType(Constants.TEXT);
					content.setText("暂无相关好友");
					content.setColor(getResources().getColor(
							R.color.contacts_list_color));
					rowContents.add(content);

					itemRow.setRowContents(rowContents);
					rows.add(itemRow);
				}

			} else {
				// 获取受助列表
				Vector<Order> v = MainClientService.getHelpMeData(Tools.userid);
				if (v != null && v.size() > 0) {
					for (Order order : v) {
						itemRow = new ListRow();
						itemRow.setRowType(Constants.ROW_IN_LIST);
						itemRow.setLayout_id(R.layout.contacts_child_row);
						itemRow.setSelectable(true);

						rowContents = new ArrayList<RowContent>();
						content = new RowContent();
						content.setLayout_id(R.id.contact_icon);
						content.setUserid(order.getUserid() + "");
						content.setType(Constants.CONTACT_ICON);

						rowContents.add(content);

						content = new RowContent();
						content.setLayout_id(R.id.childText);
						content.setType(Constants.TEXT);
						content.setUserid(order.getUserid() + "");
						content.setText(order.getUsername());
						content.setColor(getResources().getColor(
								R.color.contacts_list_color));
						rowContents.add(content);

						itemRow.setRowContents(rowContents);
						rows.add(itemRow);
					}
				} else {
					itemRow = new ListRow();
					itemRow.setRowType(Constants.ROW_IN_LIST);
					itemRow.setLayout_id(R.layout.contacts_child_row);
					itemRow.setSelectable(true);

					rowContents = new ArrayList<RowContent>();

					content = new RowContent();
					content.setLayout_id(R.id.childText);
					content.setType(Constants.TEXT);
					content.setText("暂无相关好友");
					content.setColor(getResources().getColor(
							R.color.contacts_list_color));
					rowContents.add(content);

					itemRow.setRowContents(rowContents);
					rows.add(itemRow);
				}

			}
			childList.add(rows);
		}

	}

}

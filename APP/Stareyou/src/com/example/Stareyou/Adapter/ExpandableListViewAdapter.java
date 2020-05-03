package com.example.Stareyou.Adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Stareyou.LiaoTianWindowActivity;
import com.example.Stareyou.R;
import com.example.Stareyou.util.Constants;

public class ExpandableListViewAdapter extends BaseExpandableListAdapter {

	private Context context;
	private List<ListRow> groupList = new ArrayList<ListRow>();
	private List<List<ListRow>> childList = new ArrayList<List<ListRow>>();

	public ExpandableListViewAdapter(Context context, List<ListRow> groupList,
			List<List<ListRow>> childList) {
		this.context = context;
		this.groupList = groupList;
		this.childList = childList;
	}

	public void UpdateUI(List<ListRow> gList, List<List<ListRow>> cList) {
		this.groupList = gList;
		this.childList = cList;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return null;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return 0;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		ListRow row = (ListRow) childList.get(groupPosition).get(childPosition);
		ListRowView view = new ListRowView(context, row, groupPosition,
				childPosition);
		return view;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return childList.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return null;
	}

	@Override
	public int getGroupCount() {
		return groupList.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return 0;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {

		ListRow row = (ListRow) groupList.get(groupPosition);
		List<RowContent> viewContents = row.getRowContents();
		for (int i = 0; i < viewContents.size(); i++) {
			RowContent content = viewContents.get(i);
			if (content.getType() == Constants.EXPAND_IMAGE) {
				if (isExpanded) {
					content.setImage_id(R.drawable.btn_expand_on);
				} else {
					content.setImage_id(R.drawable.btn_expand_off);
				}
			}
		}
		ListRowView view = new ListRowView(context, row, groupPosition, 0);
		return view;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}

	public static class ListRow {

		private int rowType;
		private boolean selectable;
		private int layout_id;
		private boolean isChecked;

		private List<RowContent> rowContents = new ArrayList<RowContent>();
		private int index;

		public int getRowType() {
			return rowType;
		}

		public void setRowType(int rowType) {
			this.rowType = rowType;
		}

		public boolean isSelectable() {
			return selectable;
		}

		public void setSelectable(boolean selectable) {
			this.selectable = selectable;
		}

		public int getLayout_id() {
			return layout_id;
		}

		public void setLayout_id(int layoutId) {
			layout_id = layoutId;
		}

		public List<RowContent> getRowContents() {
			return rowContents;
		}

		public void setRowContents(List<RowContent> rowContents) {
			this.rowContents = rowContents;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		public boolean isChecked() {
			return isChecked;
		}

		public void setChecked(boolean isChecked) {
			this.isChecked = isChecked;
		}

	}

	public static class RowContent {

		private int type;
		private int layout_id;
		private String userid;
		private String text;
		private int image_id = -1;
		private String concern_id;

		public String getConcern_id() {
			return concern_id;
		}

		public void setConcern_id(String concern_id) {
			this.concern_id = concern_id;
		}

		public String getUserid() {
			return userid;
		}

		public void setUserid(String userid) {
			this.userid = userid;
		}

		private int color = Color.WHITE;

		public int getImage_id() {
			return image_id;
		}

		public void setImage_id(int imageId) {
			image_id = imageId;
		}

		public int getType() {
			return type;
		}

		public void setType(int type) {
			this.type = type;
		}

		public int getLayout_id() {
			return layout_id;
		}

		public void setLayout_id(int layoutId) {
			layout_id = layoutId;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		public int getColor() {
			return color;
		}

		public void setColor(int color) {
			this.color = color;
		}

	}

	public class ListRowView extends LinearLayout {

		public ListRow row;
		private int groupIndex;
		private int childIndex;
		private Context context;

		public ListRowView(Context context, ListRow row, int groupIndex,
				int childIndex) {
			super(context);
			this.context = context;
			this.row = row;
			this.groupIndex = groupIndex;
			this.childIndex = childIndex;
			addView(inflate(context, row.getLayout_id(), null));
			setData(row);
		}

		public ListRow getRow() {
			return row;
		}

		public void setData(ListRow row) {

			List<RowContent> viewContents = row.getRowContents();
			if ((viewContents != null) && (viewContents.size() > 0)) {

				for (final RowContent viewContent : viewContents) {
					int type = viewContent.getType();
					int layout_id = viewContent.getLayout_id();

					switch (type) {
					case Constants.TEXT:
						TextView textView = (TextView) this
								.findViewById(layout_id);
						if (textView != null) {
							textView.setText(viewContent.getText());
							if (viewContent.getColor() != Color.WHITE)
								textView.setTextColor(viewContent.getColor());
							if (!textView.getText().toString().equals("暂无相关好友")
									&& !textView.getText().toString()
											.equals("我相助于")
									&& !textView.getText().toString()
											.equals("我受助于")
									&& !textView.getText().toString()
											.equals("我的粉丝")
									&& !textView.getText().toString()
											.equals("我的关注")) {
								textView.setOnClickListener(new OnClickListener() {
									@Override
									public void onClick(View arg0) {
										Intent i = new Intent(context,
												LiaoTianWindowActivity.class);
										i.putExtra("userid",
												viewContent.getUserid());
										context.startActivity(i);
									}
								});

							}

						}
						break;
					case Constants.EXPAND_IMAGE:
					case Constants.IMAGE:
						int icon_id = viewContent.getImage_id();
						if (icon_id != -1)
							((ImageView) this.findViewById(layout_id))
									.setBackgroundResource(icon_id);
						break;
					case Constants.CONTACT_ICON:
						ImageView img = (ImageView) this
								.findViewById(layout_id);
						img.setImageDrawable(context.getResources()
								.getDrawable(R.drawable.ic_launcher));
						img.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View arg0) {
								Intent i = new Intent(context,
										LiaoTianWindowActivity.class);
								i.putExtra("userid", viewContent.getUserid());
								context.startActivity(i);

							}
						});
						break;
					case Constants.BUTTON:
						Button delete = (Button) this.findViewById(layout_id);
						delete.setVisibility(View.VISIBLE);
						delete.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View arg0) {
								Dialog dialog = null;
								AlertDialog.Builder builder = new AlertDialog.Builder(
										context);
								builder.setIcon(
										android.R.drawable.ic_dialog_alert)
										.setTitle("您确定要刪除ta")
										.setPositiveButton(
												"确定",
												new DialogInterface.OnClickListener() {

													public void onClick(
															DialogInterface dialog,
															int whichButton) {
														// 取消关注或粉丝关系
														Toast.makeText(
																context,
																"茫茫人海，相遇不易，建议不删除",
																Toast.LENGTH_LONG)
																.show();
													}
												})
										.setNegativeButton(
												"取消",
												new DialogInterface.OnClickListener() {
													public void onClick(
															DialogInterface dialog,
															int whichButton) {

													}
												});
								dialog = builder.create();
								dialog.show();

							}
						});
						break;
					default:
						break;
					}
				}
			}
		}
	}

}

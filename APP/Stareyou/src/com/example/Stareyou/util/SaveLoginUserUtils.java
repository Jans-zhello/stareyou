package com.example.Stareyou.util;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import com.example.Stareyou.Media.MediaUtils;
import com.example.Stareyou.db.DBHelper;
import com.example.Stareyou.db.DBInfo;
import com.example.Stareyou.model.Users;

public class SaveLoginUserUtils {
	private DBHelper dbHelper;
	private String[] columns = { "userid", "username", "password", "phone",
			"usericon" };

	public SaveLoginUserUtils(Context context) {
		dbHelper = new DBHelper(context);

	}

	/**
	 * 添加用戶信息
	 * 
	 * @param userInfo
	 */
	public void inserUser(String userid, String username, String password,
			String phone, Bitmap usericon) {

		SQLiteDatabase database = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues(2);
		byte[] iconbyte = MediaUtils.BitmapToByte(usericon);
		values.put("userid", userid);
		values.put("username", username);
		values.put("password", password);
		values.put("phone", phone);
		values.put("usericon", iconbyte);
		database.insert(DBInfo.Table.USER_INFO_TABLE_NAME, null, values);
		database.close();
	}

	/**
	 * 根据userid获取用户信息
	 */
	public Users getUserInfoByUserId(String userid) {
		SQLiteDatabase database = dbHelper.getReadableDatabase();
		Users user = null;
		Cursor cursor = database.query(DBInfo.Table.USER_INFO_TABLE_NAME,
				columns, "userid" + "=?", new String[] { userid }, null, null,
				null);
		if (cursor.moveToFirst() == false) {
			return null;
		}
		if (cursor.getCount() > 0 && cursor != null) {
			String uid = cursor.getString(cursor.getColumnIndex("userid"));
			String username = cursor.getString(cursor
					.getColumnIndex("username"));
			String password = cursor.getString(cursor
					.getColumnIndex("password"));
			String phone = cursor.getString(cursor.getColumnIndex("phone"));
			byte[] byteIcon = cursor.getBlob(cursor.getColumnIndex("usericon"));
			user = new Users(Integer.parseInt(uid), username, password, phone,
					byteIcon);
		}
		cursor.close();
		database.close();
		return user;
	}

	/**
	 * 获取所有用户信息
	 * 
	 * 
	 */
	public List<Users> getAllUsers() {
		SQLiteDatabase database = dbHelper.getReadableDatabase();
		List<Users> users = null;
		Cursor cursor = database.query(DBInfo.Table.USER_INFO_TABLE_NAME,
				columns, null, null, null, null, null);
		if (cursor.getCount() > 0 && cursor != null) {
			users = new ArrayList<Users>(cursor.getCount());
			Users userInfo = null;
			while (cursor.moveToNext()) {
				String uid = cursor.getString(cursor.getColumnIndex("userid"));
				String username = cursor.getString(cursor
						.getColumnIndex("username"));
				String password = cursor.getString(cursor
						.getColumnIndex("password"));
				String phone = cursor.getString(cursor.getColumnIndex("phone"));
				byte[] byteIcon = cursor.getBlob(cursor
						.getColumnIndex("usericon"));
				userInfo = new Users(Integer.parseInt(uid), username, password,
						phone, byteIcon);
				users.add(userInfo);
			}
		} else {
			return null;
		}
		cursor.close();
		database.close();
		return users;
	}
}

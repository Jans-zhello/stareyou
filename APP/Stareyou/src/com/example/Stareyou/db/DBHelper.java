package com.example.Stareyou.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	public DBHelper(Context context) {

		super(context, DBInfo.DB.DB_NAME, null, DBInfo.DB.VERSION);
	}

	public void onCreate(SQLiteDatabase db) {

		db.execSQL(DBInfo.Table.USER_INFO_CREATE);
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}

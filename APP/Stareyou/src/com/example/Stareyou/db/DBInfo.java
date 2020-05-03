package com.example.Stareyou.db;

/**
 * 
 * 数据库信息类
 * 
 * @author Administrator
 * 
 */
public class DBInfo {

	public static class DB {
		// 数据库名称
		public static final String DB_NAME = "zzz";
		// 版本号
		public static final int VERSION = 1;

	}

	public static class Table {
		public static final String USER_INFO_TABLE_NAME = "login_user";
		public static final String USER_INFO_CREATE = "CREATE TABLE IF NOT EXISTS "
				+ USER_INFO_TABLE_NAME
				+ "( _id INTEGER PRIMARY KEY,userid TEXT,username TEXT,password TEXT,phone TEXT,usericon BLOB)";
		public static final String USER_INFO_DROP = "DROP TABLE"
				+ USER_INFO_TABLE_NAME;
	}
}

package com.example.Stareyou.db;

/**
 * 
 * ���ݿ���Ϣ��
 * 
 * @author Administrator
 * 
 */
public class DBInfo {

	public static class DB {
		// ���ݿ�����
		public static final String DB_NAME = "zzz";
		// �汾��
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
package com.example.Stareyou.db;

import java.sql.Connection;
import java.sql.DriverManager;

import com.example.Stareyou.util.ServerConfig;

//单例模式实现java连接数据库
public class DBManager {
	private DBManager() {

	}

	private static DBManager db = new DBManager();

	public static DBManager getDBManager() {
		return db;
	}

	public Connection getConnection() throws Exception {
		Class.forName(ServerConfig.getValue("database.driverclass"));
		return DriverManager.getConnection(
				ServerConfig.getValue("database.url"),
				ServerConfig.getValue("database.username"),
				ServerConfig.getValue("database.password"));
	}
	
	
	/**
	 * java测试是否连接上数据库
	 */
	public static void main(String[] args) throws Exception {
		System.out.println(getDBManager().getConnection());
	}
}

package com.example.Stareyou.util;

import java.io.FileInputStream;
import java.util.Properties;
/**
 * 操作配置文件类
 * @author Tiny
 *
 */
public class ServerConfig {
  
	private static Properties p = new Properties();
	static{
	   try {
		p.load(new FileInputStream("./src/server.ini"));
	} catch (Exception e) {
	    e.printStackTrace();
	    System.out.println("服务器加载配置文件出错");
	}
  }
	
	public static String getValue(String key){
           return p.getProperty(key);		
	}
	
	
	
	
}

package com.example.Stareyou.util;

import java.io.FileInputStream;
import java.util.Properties;
/**
 * ���������ļ���
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
	    System.out.println("���������������ļ�����");
	}
  }
	
	public static String getValue(String key){
           return p.getProperty(key);		
	}
	
	
	
	
}

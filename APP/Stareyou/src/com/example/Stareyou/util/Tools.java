package com.example.Stareyou.util;

import java.io.File;

import android.R.bool;
import android.graphics.Bitmap;
import android.os.Environment;

public class Tools {
	public static String IP = "192.168.23.1";
	public static int PORT_1 = 8111;
	public static int PORT_2 = 8112;
	public static int PORT_3 = 8113;

	public static boolean flag = true;// 判断是否点击登录按钮过去到AccountActivity页面

	public static String username = "";
	public static String password = "";
	public static String phone = "";
	public static String userid = "";
	public static Bitmap usericon = null;
	public static String upload_usericon = Environment
			.getExternalStorageDirectory()
			+ File.separator
			+ "temp_head_image.jpg";
	public static String urlimg = "http://192.168.23.1:8080/stareyou/image/";
	public static String urlvideo = "http://192.168.23.1:8080/stareyou/video/";
	public static String urlamr = "http://192.168.23.1:8080/stareyou/amr/";
	public static String chatted_userid = "";
}

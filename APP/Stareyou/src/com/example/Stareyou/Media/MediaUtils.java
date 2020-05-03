package com.example.Stareyou.Media;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

import com.example.Stareyou.R;
import com.example.Stareyou.util.Tools;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MediaUtils {

	/**
	 * imgPath转bitmap
	 * 
	 * @param url
	 * @return
	 */
	public static Bitmap getLoacalBitmap(String imgpath) {
		try {
			FileInputStream fis = new FileInputStream(imgpath);
			return BitmapFactory.decodeStream(fis); // /把流转化为Bitmap图片
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * bitmap转换成byte[]
	 * 
	 * @param
	 * @return
	 */
	public static byte[] BitmapToByte(Bitmap bitmap) {
		if (bitmap == null) {
			return null;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 70, baos);
		return baos.toByteArray();
	}

	/**
	 * byte[]转bitmap
	 * 
	 * @param imgByte
	 * @return
	 */

	public static Bitmap Bytes2Bimap(byte[] b) {
		if (b.length != 0) {
			return BitmapFactory.decodeByteArray(b, 0, b.length);
		} else {
			return null;
		}
	}

	/**
	 * bitmap转file
	 * 
	 * @param bitmap
	 */
	public static void saveBitmapFile(Bitmap bitmap) {
		File file = new File(Environment.getExternalStorageDirectory()
				+ File.separator + "temp_head_image.jpg");
		if (!file.exists()) {
			try {
				BufferedOutputStream bos = new BufferedOutputStream(
						new FileOutputStream(file));
				bitmap.compress(Bitmap.CompressFormat.JPEG, 70, bos);
				bos.flush();
				bos.close();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("broken");
			}
		}
	}

	/**
	 * 获取视频缩略图
	 * 
	 * @param videoPath
	 * @param width
	 * @param height
	 * @param kind
	 * @return
	 */
	@SuppressLint("NewApi")
	public static Bitmap getVideoThumbnail(String url, int width, int height) {
		Bitmap bitmap = null;
		MediaMetadataRetriever retriever = new MediaMetadataRetriever();
		int kind = MediaStore.Video.Thumbnails.MINI_KIND;
		try {
			if (Build.VERSION.SDK_INT >= 14) {
				retriever.setDataSource(url, new HashMap<String, String>());
			} else {
				retriever.setDataSource(url);
			}
			bitmap = retriever.getFrameAtTime();
		} catch (IllegalArgumentException ex) {
			// Assume this is a corrupt video file
		} catch (RuntimeException ex) {
			// Assume this is a corrupt video file.
		} finally {
			try {
				retriever.release();
			} catch (RuntimeException ex) {
				// Ignore failures while cleaning up.
			}
		}
		if (kind == Images.Thumbnails.MICRO_KIND && bitmap != null) {
			bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
					ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		}
		System.out.println("*********" + bitmap.toString());
		return bitmap;
	}

	/**
	 * 获取本地图片缩略图
	 */
	public static Bitmap getLocalImageThumbnail(String imagePath,
			Context context, int width, int height) {
		Bitmap bitmap = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		// 获取这个图片的宽和高，注意此处的bitmap为null
		bitmap = BitmapFactory.decodeFile(imagePath, options);
		options.inJustDecodeBounds = false; // 设为 false
		// 计算缩放比
		int h = options.outHeight;
		int w = options.outWidth;
		int beWidth = w / width;
		int beHeight = h / height;
		int be = 1;
		if (beWidth < beHeight) {
			be = beWidth;
		} else {
			be = beHeight;
		}
		if (be <= 0) {
			be = 1;
		}
		options.inSampleSize = be;
		// 重新读入图片，读取缩放后的bitmap，注意这次要把options.inJustDecodeBounds 设为 false
		bitmap = BitmapFactory.decodeFile(imagePath, options);
		// 利用ThumbnailUtils来创建缩略图，这里要指定要缩放哪个Bitmap对象
		bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
				ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		return bitmap;
	}

	/**
	 * 获取网络图片缩略图
	 * 
	 * @param imagePath
	 * @param width
	 * @param height
	 * @return
	 */
	public static Bitmap getImageThumbnail(String imageurl, Context context,
			int width, int height) {
		CacheImgToLocal(imageurl, context);
		Bitmap bitmap = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		// 获取这个图片的宽和高，注意此处的bitmap为null
		bitmap = BitmapFactory.decodeFile(
				MediaUtils.getDiskCacheDir(context) + "/stareyou_img/"
						+ imageurl.substring(imageurl.lastIndexOf("/") + 1),
				options);
		options.inJustDecodeBounds = false; // 设为 false
		// 计算缩放比
		int h = options.outHeight;
		int w = options.outWidth;
		int beWidth = w / width;
		int beHeight = h / height;
		int be = 1;
		if (beWidth < beHeight) {
			be = beWidth;
		} else {
			be = beHeight;
		}
		if (be <= 0) {
			be = 1;
		}
		options.inSampleSize = be;
		// 重新读入图片，读取缩放后的bitmap，注意这次要把options.inJustDecodeBounds 设为 false
		bitmap = BitmapFactory.decodeFile(
				MediaUtils.getDiskCacheDir(context) + "/stareyou_img/"
						+ imageurl.substring(imageurl.lastIndexOf("/") + 1),
				options);
		// 利用ThumbnailUtils来创建缩略图，这里要指定要缩放哪个Bitmap对象
		bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
				ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		return bitmap;
	}

	/**
	 * 缓存图片到本地
	 */
	public static void CacheImgToLocal(String imgurl, Context context) {
		Log.i("getdata", "开始写入本地");
		File f = new File(MediaUtils.getDiskCacheDir(context), "/stareyou_img");
		if (!f.isDirectory()) {
			f.mkdirs();
		}
		File file = new File(MediaUtils.getDiskCacheDir(context)
				+ "/stareyou_img/"
				+ imgurl.substring(imgurl.lastIndexOf("/") + 1));
		Log.i("getdata", String.valueOf(file.exists()));
		if (imgurl != null && !"".equals(imgurl) && !file.exists()) {
			try {
				Log.i("getdata", "开始读写");
				Log.i("getdata", imgurl);
				URL url = new URL(imgurl);
				URLConnection conection = url.openConnection();
				conection.connect();
				// download the file
				InputStream input = new BufferedInputStream(url.openStream(),
						8192);// 1024*8
				// Output stream
				OutputStream output = new FileOutputStream(
						MediaUtils.getDiskCacheDir(context) + "/stareyou_img/"
								+ imgurl.substring(imgurl.lastIndexOf("/") + 1));
				byte data[] = new byte[1024];
				int count = 0;
				while ((count = input.read(data)) != -1) {
					// writing data to file
					output.write(data, 0, count);
					output.flush();
				}
				// closing streams
				output.close();
				input.close();
				Log.i("getdata", "写入本地完成");
			} catch (Exception e) {
				e.printStackTrace();
				Log.i("gedata",e.toString());
			}
		}
		
	}

	/**
	 * 获取缓存目录
	 * 
	 * @param context
	 * @return
	 */
	@SuppressLint("NewApi")
	public static String getDiskCacheDir(Context context) {
		String cachePath = null;
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())
				|| !Environment.isExternalStorageRemovable()) {
			cachePath = context.getExternalCacheDir().getPath();
		} else {
			cachePath = context.getCacheDir().getPath();
		}
		return cachePath;
	}

	/**
	 * 上传头像到服务器
	 */
	public static String UpLoadUserIcno() {

		try {
			File f = new File(Tools.upload_usericon);
			Socket socket = new Socket(Tools.IP, Tools.PORT_3);
			InputStream in = socket.getInputStream();
			OutputStream out = socket.getOutputStream();
			// 客户端:就这种类型的文件,我要上传
			out.write(("upload,image," + f.length()).getBytes());
			out.flush();
			// 客户端:收到你发的名字了
			byte[] b = new byte[1024 * 10];
			in.read(b);
			String usericon = new String(b).trim().split(",")[1];
			// 客户端:我要往你服务器那发了
			FileInputStream fin = new FileInputStream(f);
			int len = 0;
			while ((len = fin.read(b)) != -1) {
				out.write(b, 0, len);
				out.flush();
			}
			fin.close();
			out.close();
			in.close();
			return usericon;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("上传头像出错!!!!");
		}
		return "";
	}
}

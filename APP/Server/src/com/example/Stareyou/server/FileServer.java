package com.example.Stareyou.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import com.example.Stareyou.util.ServerConfig;

/**
 * 文件服务器 约定命名格式: download,[image,amr,video],xixihaha.jpg
 * upload,[image,amr,video],50
 * 
 * @author Tiny
 * 
 */
public class FileServer extends Thread {
	private Socket socket = null;

	public FileServer(Socket socket) {
		this.socket = socket;
	}

	// 开启服务器
	private static ServerSocket server = null;
	private static boolean flag = true;

	public static void OpenServer() throws Exception {
		try {
			server = new ServerSocket(Integer.parseInt(ServerConfig
					.getValue("file_server_port")));
			while (flag) {
				new FileServer(server.accept()).start();
			}

		} catch (Exception e) {
			e.printStackTrace();
			try {
				server.close();
			} catch (Exception e1) {
			}
			throw e;
		}
	}

	// 关闭服务器
	public static void closeServer() throws Exception {
		try {
			flag = false;
			server.close();
		} catch (Exception e) {
			throw e;
		}
	}

	public void run() {

		try {
			InputStream in = socket.getInputStream();
			OutputStream out = socket.getOutputStream();
			File outputFile = null;
			// 服务器:收到你发的文件类型，先别急我得先准备一下
			byte[] b = new byte[1000];
			in.read(b);
			String cmd = new String(b).trim();
			if (cmd.startsWith("download")) {
				String[] ss = cmd.split(",");
				String filename = ss[2];
				if (ss[1].equalsIgnoreCase("image")) {
					outputFile = new File(
							ServerConfig.getValue("output_image_path"),
							filename);

				} else if (ss[1].equalsIgnoreCase("amr")) {
					outputFile = new File(
							ServerConfig.getValue("output_amr_path"), filename);
				} else if (ss[1].equalsIgnoreCase("video")) {
					outputFile = new File(
							ServerConfig.getValue("output_video_path"),
							filename);
				}
				// 服务器:准备好了,这个是下载文件后的字节数
				out.write((outputFile.length() + "").getBytes());
				out.flush();
				in.read(b);
				// 服务器:知道了,下载到客户端
				FileInputStream fin = new FileInputStream(outputFile);
				int len = 0;
				byte[] b2 = new byte[1024];
				while ((len = fin.read(b2)) != -1) {
					out.write(b2, 0, len);
					out.flush();
				}
				fin.close();
			} else if (cmd.startsWith("upload")) {
				String[] ss = cmd.split(",");
				long length = Long.parseLong(ss[2]);
				String filename = "";
				if (ss[1].equalsIgnoreCase("image")) {

					filename = new Date().getTime() + "R"
							+ (int) (Math.random() * 1000) + "R"
							+ (int) (Math.random() * 1000) + ".jpg";

					outputFile = new File(
							ServerConfig.getValue("output_image_path"),
							filename);

				} else if (ss[1].equalsIgnoreCase("amr")) {
					filename = new Date().getTime() + "R"
							+ (int) (Math.random() * 1000) + "R"
							+ (int) (Math.random() * 1000) + ".amr";
					outputFile = new File(
							ServerConfig.getValue("output_amr_path"), filename);
				} else if (ss[1].equalsIgnoreCase("video")) {
					filename = new Date().getTime() + "R"
							+ (int) (Math.random() * 1000) + "R"
							+ (int) (Math.random() * 1000) + ".mp4";
					outputFile = new File(
							ServerConfig.getValue("output_video_path"),
							filename);
				}
				// 服务器:准备好了,这个是你上传之后的文件名,你堆吧
				out.write(("ok," + filename).getBytes());
				out.flush();
				// 服务器:知道了,上传到磁盘
				FileOutputStream outs = new FileOutputStream(outputFile);
				int len = 0;
				long size = 0;
				byte[] b2 = new byte[1024];
				while ((len = in.read(b2)) != -1) {
					if (size >= length) {
						break;
					}
					outs.write(b2, 0, len);
					size += len;
				}
				outs.flush();
				outs.close();

			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {

			try {
				socket.close();
			} catch (IOException e) {

			}
		}

	}
}
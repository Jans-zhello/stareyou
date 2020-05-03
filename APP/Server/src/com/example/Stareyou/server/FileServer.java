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
 * �ļ������� Լ��������ʽ: download,[image,amr,video],xixihaha.jpg
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

	// ����������
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

	// �رշ�����
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
			// ������:�յ��㷢���ļ����ͣ��ȱ��ҵ���׼��һ��
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
				// ������:׼������,����������ļ�����ֽ���
				out.write((outputFile.length() + "").getBytes());
				out.flush();
				in.read(b);
				// ������:֪����,���ص��ͻ���
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
				// ������:׼������,��������ϴ�֮����ļ���,��Ѱ�
				out.write(("ok," + filename).getBytes());
				out.flush();
				// ������:֪����,�ϴ�������
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
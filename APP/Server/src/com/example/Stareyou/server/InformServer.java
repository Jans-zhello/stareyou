package com.example.Stareyou.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Set;

import com.example.Stareyou.util.ServerConfig;

/**
 * 聊天室人员登记服务器(暂只支持两人)
 * 
 * @author Tiny
 * 
 */
public class InformServer extends Thread {
	private Socket socket = null;

	public InformServer(Socket socket) {
		this.socket = socket;
	}

	// 开启服务器
	private static ServerSocket server = null;
	private static boolean flag = true;

	public static void OpenServer() throws Exception {
		try {
			server = new ServerSocket(Integer.parseInt(ServerConfig
					.getValue("inform_server_port")));
			while (flag) {
				new InformServer(server.accept()).start();
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

	// chatuserpool登记聊天用户
	public static HashMap<String, ObjectOutputStream> chatuserpool = new HashMap<String, ObjectOutputStream>();

	public void run() {
		try {
			ObjectInputStream oin = new ObjectInputStream(
					socket.getInputStream());
			ObjectOutputStream out = new ObjectOutputStream(
					socket.getOutputStream());
			while (true) {
				MessageHandle m = (MessageHandle) oin.readObject();
				String[] userids = m.getValue().get("userid").toString()
						.split("###");
				for (String string : userids) {
					chatuserpool.put(string, out);
				}
			}

		} catch (Exception e) {
			try {
				socket.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}

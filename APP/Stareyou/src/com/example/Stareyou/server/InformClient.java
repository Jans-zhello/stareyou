package com.example.Stareyou.server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Hashtable;
import java.util.Vector;

import android.util.Log;

import com.example.Stareyou.LiaoTianWindowActivity;
import com.example.Stareyou.model.Chat;
import com.example.Stareyou.util.Tools;

/**
 * �Խ�Server��InformServer������
 * 
 * @author Administrator
 * 
 */
public class InformClient extends Thread {
	static Socket socket = null;
	public static LiaoTianWindowActivity chat = null;

	@Override
	public void run() {
		try {
			socket = new Socket(Tools.IP, Tools.PORT_2);
			ObjectOutputStream oout = new ObjectOutputStream(
					socket.getOutputStream());
			ObjectInputStream oin = new ObjectInputStream(
					socket.getInputStream());
			MessageHandle m = new MessageHandle();
			Hashtable table = new Hashtable();
			table.put("userid", Tools.chatted_userid);
			m.setValue(table);
			oout.writeObject(m);
			oout.flush();
			while (true) {
				Log.i("chat", "�Ѿ�������...");
				MessageHandle m2 = (MessageHandle) oin.readObject();
				Log.i("chat", m2.toString());
				if (m2.getReturnvalue().get("message").toString()
						.equals("Inform")) {
					// ��ȡ��������
					Vector<Chat> vector = MainClientService.chatData();
					if (vector != null) {
						chat.vc = vector;
						android.os.Message msg1 = new android.os.Message();
						chat.hand.sendMessage(msg1);
					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// �ر�
	public static void close() throws Exception {
		try {
			if (socket != null) {
				socket.close();	
			}
		} catch (Exception e) {
			throw e;
		}
	}
}

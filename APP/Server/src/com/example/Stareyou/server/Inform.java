package com.example.Stareyou.server;

import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Set;
//��ȡ֪ͨ��������userpool��ObjectOutputStream,�����ݸ���׼��
public class Inform extends Thread {
	MessageHandle m = null;

	public Inform(MessageHandle messageHandle) {
		this.m = messageHandle;
	}

	public void run() {
		HashMap<String, ObjectOutputStream> hm = (HashMap<String, ObjectOutputStream>) InformServer.chatuserpool
				.clone();
		Set<String> keys = hm.keySet();
		for (String key : keys) {
			try {
				System.out.println(key);
				ObjectOutputStream out = hm.get(key);
				synchronized (out) {
					out.writeObject(m);
					out.flush();
				}
			} catch (Exception e) {
				
				
				
				
			}
		}
	}
}

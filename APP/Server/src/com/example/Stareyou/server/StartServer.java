package com.example.Stareyou.server;

public class StartServer {

	public static void main(String[] args) {

		new Thread(){
			public void run() {
				try {
					MainServer.OpenServer();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
			
		}.start();
		new Thread() {
			public void run() {
				try {
					InformServer.OpenServer();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
		}.start();
		new Thread(){
			public void run() {
				
				try {
					FileServer.OpenServer();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
			
		}.start();
		
	}

}

package com.example.Stareyou.model;

import java.io.Serializable;
/**
 *   username:ת���˵��û���
	 number;//ת�˷�������
 * @author Administrator
 *
 */
public class SendRedirect implements Serializable {
	private String username;//ת���˵��û���
	private int number;//ת�˷�������

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
	

}

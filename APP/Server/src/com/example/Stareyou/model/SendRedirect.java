package com.example.Stareyou.model;

import java.io.Serializable;
/**
 *   username:转发人的用户名
	 number;//转人发的数量
 * @author Administrator
 *
 */
public class SendRedirect implements Serializable {
	private String username;//转发人的用户名
	private int number;//转人发的数量

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

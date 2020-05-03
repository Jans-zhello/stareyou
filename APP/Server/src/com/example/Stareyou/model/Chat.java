package com.example.Stareyou.model;

import java.io.Serializable;

/**
 * chatid:聊天ID userid:聊天用户1 ID chatted_userid:聊天用户2 ID helpid:帮助详情ID
 * chat_content:聊天内容 chat_type:聊天内容类型 0为文字 1为图片 2为视频 3为录音 chat_date:及时聊天时间记录
 * 
 * @author Administrator
 * 
 */
public class Chat implements Serializable {
	private int chatid;// 聊天ID
	private int userid;// 聊天用户1 ID
	private int chatted_userid;// 聊天用户2 ID
	private String username1;// 聊天用户1名
	private String username2;// 聊天用户2名
	private int helpid;// 帮助详情ID
	private String chat_content;// 聊天内容
	private int chat_type;// 聊天内容类型 0为文字 1为图片 2为视频 3为录音
	private String chat_date;// 及时聊天时间记录

	public String getUsername1() {
		return username1;
	}

	public void setUsername1(String username1) {
		this.username1 = username1;
	}

	public String getUsername2() {
		return username2;
	}

	public void setUsername2(String username2) {
		this.username2 = username2;
	}

	public int getChat_type() {
		return chat_type;
	}

	public void setChat_type(int chat_type) {
		this.chat_type = chat_type;
	}

	public int getChatid() {
		return chatid;
	}

	public void setChatid(int chatid) {
		this.chatid = chatid;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public int getChatted_userid() {
		return chatted_userid;
	}

	public void setChatted_userid(int chatted_userid) {
		this.chatted_userid = chatted_userid;
	}

	public int getHelpid() {
		return helpid;
	}

	public void setHelpid(int helpid) {
		this.helpid = helpid;
	}

	public String getChat_content() {
		return chat_content;
	}

	public void setChat_content(String chat_content) {
		this.chat_content = chat_content;
	}

	public String getChat_date() {
		return chat_date;
	}

	public void setChat_date(String chat_date) {
		this.chat_date = chat_date;
	}

}
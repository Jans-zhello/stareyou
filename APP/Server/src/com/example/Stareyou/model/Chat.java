package com.example.Stareyou.model;

import java.io.Serializable;

/**
 * chatid:����ID userid:�����û�1 ID chatted_userid:�����û�2 ID helpid:��������ID
 * chat_content:�������� chat_type:������������ 0Ϊ���� 1ΪͼƬ 2Ϊ��Ƶ 3Ϊ¼�� chat_date:��ʱ����ʱ���¼
 * 
 * @author Administrator
 * 
 */
public class Chat implements Serializable {
	private int chatid;// ����ID
	private int userid;// �����û�1 ID
	private int chatted_userid;// �����û�2 ID
	private String username1;// �����û�1��
	private String username2;// �����û�2��
	private int helpid;// ��������ID
	private String chat_content;// ��������
	private int chat_type;// ������������ 0Ϊ���� 1ΪͼƬ 2Ϊ��Ƶ 3Ϊ¼��
	private String chat_date;// ��ʱ����ʱ���¼

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
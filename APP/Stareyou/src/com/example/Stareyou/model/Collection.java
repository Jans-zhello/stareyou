package com.example.Stareyou.model;

import java.io.Serializable;

/**
 * collectionid:�ղ�ID userid:�ղ��û�ID collectioned_userid:���ղ��û���ID sendid:���ղص�����
 * collection_date:�ղ����� username:�ղ��˵��û��� number:�ղ�������
 * 
 * @author Administrator
 * 
 */
public class Collection implements Serializable {

	private int collectionid;// �ղ�ID
	private int userid;// �ղ��û�ID
	private int collectioned_userid;// ���ղ��û���ID
	private int sendid;// ���ղص�����
	private String send_content;// ���ղص���������
	private int send_type;// ���ղص���������
	private String collection_date;// �ղ�����
	private String username;// �ղ��˵��û���
	private int number;// �ղ�������
	private String icon;

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getSend_content() {
		return send_content;
	}

	public void setSend_content(String send_content) {
		this.send_content = send_content;
	}

	public int getSend_type() {
		return send_type;
	}

	public void setSend_type(int send_type) {
		this.send_type = send_type;
	}

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

	public int getCollectionid() {
		return collectionid;
	}

	public void setCollectionid(int collectionid) {
		this.collectionid = collectionid;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public int getCollectioned_userid() {
		return collectioned_userid;
	}

	public void setCollectioned_userid(int collectioned_userid) {
		this.collectioned_userid = collectioned_userid;
	}

	public int getSendid() {
		return sendid;
	}

	public void setSendid(int sendid) {
		this.sendid = sendid;
	}

	public String getCollection_date() {
		return collection_date;
	}

	public void setCollection_date(String collection_date) {
		this.collection_date = collection_date;
	}

}
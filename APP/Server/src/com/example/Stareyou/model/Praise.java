package com.example.Stareyou.model;

import java.io.Serializable;
/**
 *  praiseid:����ID
	userid:������ID
	praised_userid:�����޵���ID
	sendid:�����޵�����ID
	praise_date:��������
	username:�����˵��û���
	number:��¼��������
 * @author Administrator
 *
 */
public class Praise implements Serializable{

	private int praiseid;//����ID
	private int userid;//������ID
	private int praised_userid;//�����޵���ID
	private int sendid;//�����޵�����ID
	private String praise_date;//��������
	private String username;//�����˵��û���
	private int number;//��¼��������
	
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getPraiseid() {
		return praiseid;
	}
	public void setPraiseid(int praiseid) {
		this.praiseid = praiseid;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public int getPraised_userid() {
		return praised_userid;
	}
	public void setPraised_userid(int praised_userid) {
		this.praised_userid = praised_userid;
	}
	public int getSendid() {
		return sendid;
	}
	public void setSendid(int sendid) {
		this.sendid = sendid;
	}
	public String getPraise_date() {
		return praise_date;
	}
	public void setPraise_date(String praise_date) {
		this.praise_date = praise_date;
	}
    
	
}
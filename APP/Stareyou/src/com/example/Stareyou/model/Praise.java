package com.example.Stareyou.model;

import java.io.Serializable;
/**
 *  praiseid:点赞ID
	userid:点赞人ID
	praised_userid:被点赞的人ID
	sendid:被点赞的文章ID
	praise_date:点赞日期
	username:点赞人的用户名
	number:记录点赞人数
 * @author Administrator
 *
 */
public class Praise implements Serializable{

	private int praiseid;//点赞ID
	private int userid;//点赞人ID
	private int praised_userid;//被点赞的人ID
	private int sendid;//被点赞的文章ID
	private String praise_date;//点赞日期
	private String username;//点赞人的用户名
	private int number;//记录点赞人数
	
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
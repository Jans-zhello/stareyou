package com.example.Stareyou.model;

import java.io.Serializable;
/**
 *  concernid:��עID
	userid:��ע�û�ID
	concerned_userid:����ע��ID
	concern_date:��ע����
	username:��ע�˵��û���
    number:��ע����
 * @author Administrator
 *
 */
public class Concern implements Serializable{
	private int concernid;//��עID
	private int userid;//��ע�û�ID
	private int concerned_userid;//����ע��ID
	private String concern_date;//��ע����
    private String username;//��ע�˵��û���
    private int number;//��ע����
    
    
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

	public int getConcernid() {
		return concernid;
	}

	public void setConcernid(int concernid) {
		this.concernid = concernid;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public int getConcerned_userid() {
		return concerned_userid;
	}

	public void setConcerned_userid(int concerned_userid) {
		this.concerned_userid = concerned_userid;
	}

	public String getConcern_date() {
		return concern_date;
	}

	public void setConcern_date(String concern_date) {
		this.concern_date = concern_date;
	}

}
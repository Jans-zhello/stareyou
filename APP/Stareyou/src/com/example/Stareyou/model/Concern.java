package com.example.Stareyou.model;

import java.io.Serializable;
/**
 *  concernid:关注ID
	userid:关注用户ID
	concerned_userid:被关注的ID
	concern_date:关注日期
	username:关注人的用户名
    number:关注数量
 * @author Administrator
 *
 */
public class Concern implements Serializable{
	private int concernid;//关注ID
	private int userid;//关注用户ID
	private int concerned_userid;//被关注的ID
	private String concern_date;//关注日期
    private String username;//关注人的用户名
    private int number;//关注数量
    
    
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
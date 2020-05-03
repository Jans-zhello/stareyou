package com.example.Stareyou.model;

import java.io.Serializable;
import java.util.Vector;

/**
 * private Vector<Assess> assesses = new Vector<Assess>();//每条help信息对应多个评价信息
 * helpid:帮助详情ID userid:需要帮助人的ID help_title:帮助标题 help_content:帮助内容
 * help_type:帮助内容类型 0为文字 1为图片 2为视频 3为录音 help_date:请求帮助的日期
 * 
 * @author Administrator
 * 
 */
public class Help implements Serializable {

	private Vector<Assess> assesses = new Vector<Assess>();// 每条help信息对应多个评价信息

	private int helpid;// 帮助详情ID
	private int userid;// 需要帮助的人的ID
	private String username;// 需要帮助的人的用户名
	private String help_title;// 帮助标题
	private String help_content;// 帮助内容
	private int help_type;// 帮助内容类型 0为文字 1为图片 2为视频 3为录音
	private String help_icon;// 帮助人头像
	private String help_location;// 帮助地址
	private String help_date;// 请求帮助的日期

	public String getHelp_icon() {
		return help_icon;
	}

	public void setHelp_icon(String help_icon) {
		this.help_icon = help_icon;
	}

	public Vector<Assess> getAssesses() {
		return assesses;
	}

	public void setAssesses(Vector<Assess> assesses) {
		this.assesses = assesses;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getHelpid() {
		return helpid;
	}

	public void setHelpid(int helpid) {
		this.helpid = helpid;
	}

	public int getHelp_type() {
		return help_type;
	}

	public void setHelp_type(int help_type) {
		this.help_type = help_type;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getHelp_title() {
		return help_title;
	}

	public void setHelp_title(String help_title) {
		this.help_title = help_title;
	}

	public String getHelp_content() {
		return help_content;
	}

	public void setHelp_content(String help_content) {
		this.help_content = help_content;
	}

	public String getHelp_date() {
		return help_date;
	}

	public void setHelp_date(String help_date) {
		this.help_date = help_date;
	}

	public String getHelp_location() {
		return help_location;
	}

	public void setHelp_location(String help_location) {
		this.help_location = help_location;
	}

}
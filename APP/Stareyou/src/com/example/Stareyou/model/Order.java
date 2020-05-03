package com.example.Stareyou.model;

import java.io.Serializable;

/**
 * orderid:订单ID userid:帮助人ID helped_userid:被帮助人ID private int username;//用户名
 * helpid:帮助详情ID与Help的helpid对应 order_title:订单标题 order_date:订单日期
 * 
 * @author Administrator
 * 
 */
public class Order implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7058735683298624871L;
	/**
	 * 
	 */
	private int orderid;// 订单ID
	private int userid;// 帮助人ID
	private String username;// 用户名
	private int helped_userid;// 被帮助人ID
	private int helpid;// 帮助详情ID与Help的helpid对应
	private String order_title;// 订单标题
	private String order_date;// 订单日期

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getOrderid() {
		return orderid;
	}

	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public int getHelped_userid() {
		return helped_userid;
	}

	public void setHelped_userid(int helped_userid) {
		this.helped_userid = helped_userid;
	}

	public int getHelpid() {
		return helpid;
	}

	public void setHelpid(int helpid) {
		this.helpid = helpid;
	}

	public String getOrder_title() {
		return order_title;
	}

	public void setOrder_title(String order_title) {
		this.order_title = order_title;
	}

	public String getOrder_date() {
		return order_date;
	}

	public void setOrder_date(String order_date) {
		this.order_date = order_date;
	}

}
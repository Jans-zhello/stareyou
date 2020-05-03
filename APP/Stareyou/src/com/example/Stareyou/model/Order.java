package com.example.Stareyou.model;

import java.io.Serializable;

/**
 * orderid:����ID userid:������ID helped_userid:��������ID private int username;//�û���
 * helpid:��������ID��Help��helpid��Ӧ order_title:�������� order_date:��������
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
	private int orderid;// ����ID
	private int userid;// ������ID
	private String username;// �û���
	private int helped_userid;// ��������ID
	private int helpid;// ��������ID��Help��helpid��Ӧ
	private String order_title;// ��������
	private String order_date;// ��������

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
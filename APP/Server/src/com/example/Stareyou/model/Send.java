package com.example.Stareyou.model;

import java.io.Serializable;
import java.util.Vector;

/**
 * 
 private Vector<Review> rw = new Vector<Review>();//与评论表构成一对多关系
 * 
 * private Vector<Praise> pe = new Vector<Praise>();//与点赞表构成一对多关系
 * 
 * private Vector<Collection> cl = new Vector<Collection>();//与收藏表构成一对多关系
 * 
 * private Vector<Concern> co = new Vector<Concern>();//与关注表构成一对多关系
 * 
 * private Vector<SendRedirect> sr = new Vector<SendRedirect>();//与转发表构成一对多关系
 * 
 * sendid:发送文章ID
 * 
 * userid:发送人ID
 * 
 * send_content:发送文章内容
 * 
 * send_type:发送类型 0为文字 1为图片 2为视频 3为录音
 * 
 * send_date:发送时间
 * 
 * username:发送人用户名
 * 
 * @author Administrator
 * 
 */
public class Send implements Serializable {

	private Vector<Review> rw = new Vector<Review>();// 与评论表构成一对多关系

	private Vector<Praise> pe = new Vector<Praise>();// 与点赞表构成一对多关系

	private Vector<Collection> cl = new Vector<Collection>();// 与收藏表构成一对多关系

	private Vector<Concern> co = new Vector<Concern>();// 与关注表构成一对多关系

	private Vector<SendRedirect> sr = new Vector<SendRedirect>();// 与转发表构成一对多关系

	private int sendid;// 发送文章ID

	private int userid;// 发送人ID

	private String send_content;// 发送文章内容

	private int send_type;// 发送类型 0为文字 1为图片 2为视频 3为录音

	private String send_date;// 发送时间

	private String username;// 发送人用户名

	private String send_icon;// 发送人头像

	private String send_location;// 发送人地址

	public String getSend_icon() {
		return send_icon;
	}

	public void setSend_icon(String send_icon) {
		this.send_icon = send_icon;
	}

	public Vector<Praise> getPe() {
		return pe;
	}

	public void setPe(Vector<Praise> pe) {
		this.pe = pe;
	}

	public Vector<Collection> getCl() {
		return cl;
	}

	public void setCl(Vector<Collection> cl) {
		this.cl = cl;
	}

	public Vector<Concern> getCo() {
		return co;
	}

	public void setCo(Vector<Concern> co) {
		this.co = co;
	}

	public Vector<SendRedirect> getSr() {
		return sr;
	}

	public void setSr(Vector<SendRedirect> sr) {
		this.sr = sr;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Vector<Review> getRw() {
		return rw;
	}

	public void setRw(Vector<Review> rw) {
		this.rw = rw;
	}

	public int getSendid() {
		return sendid;
	}

	public void setSendid(int sendid) {
		this.sendid = sendid;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
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

	public String getSend_date() {
		return send_date;
	}

	public void setSend_date(String send_date) {
		this.send_date = send_date;
	}

	public String getSend_location() {
		return send_location;
	}

	public void setSend_location(String send_location) {
		this.send_location = send_location;
	}

}
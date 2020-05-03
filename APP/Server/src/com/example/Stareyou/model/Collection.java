package com.example.Stareyou.model;

import java.io.Serializable;

/**
 * collectionid:收藏ID userid:收藏用户ID collectioned_userid:被收藏用户的ID sendid:被收藏的文章
 * collection_date:收藏日期 username:收藏人的用户名 number:收藏人数量
 * 
 * @author Administrator
 * 
 */
public class Collection implements Serializable {

	private int collectionid;// 收藏ID
	private int userid;// 收藏用户ID
	private int collectioned_userid;// 被收藏用户的ID
	private int sendid;// 被收藏的文章
	private String send_content;// 被收藏的文章内容
	private int send_type;// 被收藏的文章类型
	private String collection_date;// 收藏日期
	private String username;// 收藏人的用户名
	private int number;// 收藏人数量
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
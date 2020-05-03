package com.example.Stareyou.model;

import java.io.Serializable;

/**
 * userid:用户ID password:用户密码 send_number:发表文章数量 concern_number:关注用户数量
 * fans_number:粉丝数量 collection_number:收藏文章数量 help_number:帮助人数量
 * ukey:ukey码唯一标识(由时间和随机数生成)
 * 
 * @author Administrator
 * 
 */
public class Users implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2415544958303040534L;
	private int userid;// 用户ID
	private String username;// 用户名
	private String password;// 用户密码
	private String sex;// 用户性别
	private String phone;// 用户手机号
	private byte[] usericon;// 用户头像
    private String date;
    
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public byte[] getUsericon() {
		return usericon;
	}

	public void setUsericon(byte[] usericon) {
		this.usericon = usericon;
	}

	private int send_number;// 发表文章数量
	private int concern_number;// 关注用户数量
	private int fans_number;// 粉丝数量
	private int collection_number;// 收藏文章数量
	private int help_number;// 帮助人数量
	private String ukey;// ukey码唯一标识(由时间和随机数生成)

	public Users(int userid, String username, String password, String phone,
			byte[] usericon) {
		super();
		this.userid = userid;
		this.username = username;
		this.password = password;
		this.phone = phone;
		this.usericon = usericon;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getSend_number() {
		return send_number;
	}

	public void setSend_number(int send_number) {
		this.send_number = send_number;
	}

	public int getConcern_number() {
		return concern_number;
	}

	public void setConcern_number(int concern_number) {
		this.concern_number = concern_number;
	}

	public int getFans_number() {
		return fans_number;
	}

	public void setFans_number(int fans_number) {
		this.fans_number = fans_number;
	}

	public int getCollection_number() {
		return collection_number;
	}

	public void setCollection_number(int collection_number) {
		this.collection_number = collection_number;
	}

	public int getHelp_number() {
		return help_number;
	}

	public void setHelp_number(int help_number) {
		this.help_number = help_number;
	}

	public String getUkey() {
		return ukey;
	}

	public void setUkey(String ukey) {
		this.ukey = ukey;
	}

}
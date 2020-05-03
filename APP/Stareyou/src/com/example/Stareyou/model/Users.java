package com.example.Stareyou.model;

import java.io.Serializable;

/**
 * userid:�û�ID password:�û����� send_number:������������ concern_number:��ע�û�����
 * fans_number:��˿���� collection_number:�ղ��������� help_number:����������
 * ukey:ukey��Ψһ��ʶ(��ʱ������������)
 * 
 * @author Administrator
 * 
 */
public class Users implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2415544958303040534L;
	private int userid;// �û�ID
	private String username;// �û���
	private String password;// �û�����
	private String sex;// �û��Ա�
	private String phone;// �û��ֻ���
	private byte[] usericon;// �û�ͷ��
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

	private int send_number;// ������������
	private int concern_number;// ��ע�û�����
	private int fans_number;// ��˿����
	private int collection_number;// �ղ���������
	private int help_number;// ����������
	private String ukey;// ukey��Ψһ��ʶ(��ʱ������������)

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
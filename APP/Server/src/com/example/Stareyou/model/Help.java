package com.example.Stareyou.model;

import java.io.Serializable;
import java.util.Vector;

/**
 * private Vector<Assess> assesses = new Vector<Assess>();//ÿ��help��Ϣ��Ӧ���������Ϣ
 * helpid:��������ID userid:��Ҫ�����˵�ID help_title:�������� help_content:��������
 * help_type:������������ 0Ϊ���� 1ΪͼƬ 2Ϊ��Ƶ 3Ϊ¼�� help_date:�������������
 * 
 * @author Administrator
 * 
 */
public class Help implements Serializable {

	private Vector<Assess> assesses = new Vector<Assess>();// ÿ��help��Ϣ��Ӧ���������Ϣ

	private int helpid;// ��������ID
	private int userid;// ��Ҫ�������˵�ID
	private String username;// ��Ҫ�������˵��û���
	private String help_title;// ��������
	private String help_content;// ��������
	private int help_type;// ������������ 0Ϊ���� 1ΪͼƬ 2Ϊ��Ƶ 3Ϊ¼��
	private String help_icon;// ������ͷ��
	private String help_location;// ������ַ
	private String help_date;// �������������

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
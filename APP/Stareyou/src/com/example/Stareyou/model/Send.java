package com.example.Stareyou.model;

import java.io.Serializable;
import java.util.Vector;

/**
 * 
 private Vector<Review> rw = new Vector<Review>();//�����۱���һ�Զ��ϵ
 * 
 * private Vector<Praise> pe = new Vector<Praise>();//����ޱ���һ�Զ��ϵ
 * 
 * private Vector<Collection> cl = new Vector<Collection>();//���ղر���һ�Զ��ϵ
 * 
 * private Vector<Concern> co = new Vector<Concern>();//���ע����һ�Զ��ϵ
 * 
 * private Vector<SendRedirect> sr = new Vector<SendRedirect>();//��ת������һ�Զ��ϵ
 * 
 * sendid:��������ID
 * 
 * userid:������ID
 * 
 * send_content:������������
 * 
 * send_type:�������� 0Ϊ���� 1ΪͼƬ 2Ϊ��Ƶ 3Ϊ¼��
 * 
 * send_date:����ʱ��
 * 
 * username:�������û���
 * 
 * @author Administrator
 * 
 */
public class Send implements Serializable {

	private Vector<Review> rw = new Vector<Review>();// �����۱���һ�Զ��ϵ

	private Vector<Praise> pe = new Vector<Praise>();// ����ޱ���һ�Զ��ϵ

	private Vector<Collection> cl = new Vector<Collection>();// ���ղر���һ�Զ��ϵ

	private Vector<Concern> co = new Vector<Concern>();// ���ע����һ�Զ��ϵ

	private Vector<SendRedirect> sr = new Vector<SendRedirect>();// ��ת������һ�Զ��ϵ

	private int sendid;// ��������ID

	private int userid;// ������ID

	private String send_content;// ������������

	private int send_type;// �������� 0Ϊ���� 1ΪͼƬ 2Ϊ��Ƶ 3Ϊ¼��

	private String send_date;// ����ʱ��

	private String username;// �������û���

	private String send_icon;// ������ͷ��

	private String send_location;// �����˵�ַ

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
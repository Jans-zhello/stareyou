package com.example.Stareyou.model;

import java.io.Serializable;
/**
 * 	reviewid:���۱�ID
	userid:������ID
	reviewed_userid:�����۵���ID
	sendid:�����۵�����ID
	review_content:��������
	review_type:�������� 0Ϊ���� 1ΪͼƬ 2Ϊ��Ƶ  3Ϊ¼��
	review_date:����ʱ��
	username:�������û���
 * @author Administrator
 *
 */
public class Review implements Serializable{

	private int reviewid;//���۱�ID
	private int userid;//������ID
	private int reviewed_userid;//�����۵���ID
	private int sendid;//�����۵�����ID
	private String review_content;//��������
	private int review_type;//�������� 0Ϊ���� 1ΪͼƬ 2Ϊ��Ƶ  3Ϊ¼��
	private String review_date;//����ʱ��
	private String username;//�������û���
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getReviewid() {
		return reviewid;
	}
	public void setReviewid(int reviewid) {
		this.reviewid = reviewid;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public int getReviewed_userid() {
		return reviewed_userid;
	}
	public void setReviewed_userid(int reviewed_userid) {
		this.reviewed_userid = reviewed_userid;
	}
	public int getSendid() {
		return sendid;
	}
	public void setSendid(int sendid) {
		this.sendid = sendid;
	}
	public String getReview_content() {
		return review_content;
	}
	public void setReview_content(String review_content) {
		this.review_content = review_content;
	}
	public int getReview_type() {
		return review_type;
	}
	public void setReview_type(int review_type) {
		this.review_type = review_type;
	}
	public String getReview_date() {
		return review_date;
	}
	public void setReview_date(String review_date) {
		this.review_date = review_date;
	}
    
}
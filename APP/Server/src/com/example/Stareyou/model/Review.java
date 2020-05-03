package com.example.Stareyou.model;

import java.io.Serializable;
/**
 * 	reviewid:评论表ID
	userid:评论人ID
	reviewed_userid:被评论的人ID
	sendid:被评论的文章ID
	review_content:评论内容
	review_type:评论类型 0为文字 1为图片 2为视频  3为录音
	review_date:评论时间
	username:评论人用户名
 * @author Administrator
 *
 */
public class Review implements Serializable{

	private int reviewid;//评论表ID
	private int userid;//评论人ID
	private int reviewed_userid;//被评论的人ID
	private int sendid;//被评论的文章ID
	private String review_content;//评论内容
	private int review_type;//评论类型 0为文字 1为图片 2为视频  3为录音
	private String review_date;//评论时间
	private String username;//评论人用户名
	
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
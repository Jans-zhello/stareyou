package com.example.Stareyou.model;

import java.io.Serializable;

/**
 * assessid:评价ID userid:被帮助人ID helper_number:帮助者数量 satisfied:满意度
 * assess_date:评价时间
 * 
 * @author Administrator
 * 
 */
public class Assess implements Serializable {
	private int assessid;// 评价ID
	private int userid;// 被帮助人ID
	private int helpid;// 帮助id
	private int helper_number;// 帮助者数量
	private double satisfied;// 满意度
	private String assess_date;// 评价时间

	public int getHelpid() {
		return helpid;
	}

	public void setHelpid(int helpid) {
		this.helpid = helpid;
	}

	public int getAssessid() {
		return assessid;
	}

	public void setAssessid(int assessid) {
		this.assessid = assessid;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public int getHelper_number() {
		return helper_number;
	}

	public void setHelper_number(int helper_number) {
		this.helper_number = helper_number;
	}

	public double getSatisfied() {
		return satisfied;
	}

	public void setSatisfied(double satisfied) {
		this.satisfied = satisfied;
	}

	public String getAssess_date() {
		return assess_date;
	}

	public void setAssess_date(String assess_date) {
		this.assess_date = assess_date;
	}

}
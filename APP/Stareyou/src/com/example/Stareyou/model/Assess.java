package com.example.Stareyou.model;

import java.io.Serializable;

/**
 * assessid:����ID userid:��������ID helper_number:���������� satisfied:�����
 * assess_date:����ʱ��
 * 
 * @author Administrator
 * 
 */
public class Assess implements Serializable {
	private int assessid;// ����ID
	private int userid;// ��������ID
	private int helpid;// ����id
	private int helper_number;// ����������
	private double satisfied;// �����
	private String assess_date;// ����ʱ��

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
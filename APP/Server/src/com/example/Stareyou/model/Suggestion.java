package com.example.Stareyou.model;

import java.io.Serializable;
/**
 * 	suggestionid:�����id
	userid:������id
	suggestion_content:��������
	suggestion_date:����ʱ��
 * @author Administrator
 *
 */
public class Suggestion implements Serializable{
	private int suggestionid;//�����id
	private int userid;//������id
	private String suggestion_content;//��������
	private String suggestion_date;//����ʱ��

	public int getSuggestionid() {
		return suggestionid;
	}

	public void setSuggestionid(int suggestionid) {
		this.suggestionid = suggestionid;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getSuggestion_content() {
		return suggestion_content;
	}

	public void setSuggestion_content(String suggestion_content) {
		this.suggestion_content = suggestion_content;
	}

	public String getSuggestion_date() {
		return suggestion_date;
	}

	public void setSuggestion_date(String suggestion_date) {
		this.suggestion_date = suggestion_date;
	}

}
package com.example.Stareyou.model;

import java.io.Serializable;
/**
 * 	suggestionid:建议表id
	userid:建议人id
	suggestion_content:建议内容
	suggestion_date:建议时间
 * @author Administrator
 *
 */
public class Suggestion implements Serializable{
	private int suggestionid;//建议表id
	private int userid;//建议人id
	private String suggestion_content;//建议内容
	private String suggestion_date;//建议时间

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
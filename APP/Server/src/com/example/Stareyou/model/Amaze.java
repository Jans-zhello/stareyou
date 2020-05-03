package com.example.Stareyou.model;

import java.io.Serializable;

public class Amaze implements Serializable {
	private int amazeid;
	private int helpid;
	private int amaze_userid;
	private String amaze_username;
	private int amazed_userid;
	private String amazed_username;
	private String amaze_date;

	public int getHelpid() {
		return helpid;
	}

	public void setHelpid(int helpid) {
		this.helpid = helpid;
	}

	public int getAmazeid() {
		return amazeid;
	}

	public void setAmazeid(int amazeid) {
		this.amazeid = amazeid;
	}

	public int getAmaze_userid() {
		return amaze_userid;
	}

	public void setAmaze_userid(int amaze_userid) {
		this.amaze_userid = amaze_userid;
	}

	public String getAmaze_username() {
		return amaze_username;
	}

	public void setAmaze_username(String amaze_username) {
		this.amaze_username = amaze_username;
	}

	public int getAmazed_userid() {
		return amazed_userid;
	}

	public void setAmazed_userid(int amazed_userid) {
		this.amazed_userid = amazed_userid;
	}

	public String getAmazed_username() {
		return amazed_username;
	}

	public void setAmazed_username(String amazed_username) {
		this.amazed_username = amazed_username;
	}

	public String getAmaze_date() {
		return amaze_date;
	}

	public void setAmaze_date(String amaze_date) {
		this.amaze_date = amaze_date;
	}

}

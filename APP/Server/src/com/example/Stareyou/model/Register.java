package com.example.Stareyou.model;

import java.io.Serializable;
/**
 *       userid:注册用户ID与Users的userid对应
         sex:注册用户性别
         date:注册日期
         username:用户名
         phone:手机号
 * @author Administrator
 *
 */
public class Register implements Serializable{

private  int  userid;//注册用户ID与Users的userid对应
private  String  sex;//注册用户性别
private  String  date;//注册日期
private  String  username;//用户名
private  String  phone;//手机号
public int getUserid() {
	return userid;
}
public void setUserid(int userid) {
	this.userid = userid;
}
public String getSex() {
	return sex;
}
public void setSex(String sex) {
	this.sex = sex;
}
public String getDate() {
	return date;
}
public void setDate(String date) {
	this.date = date;
}
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public String getPhone() {
	return phone;
}
public void setPhone(String phone) {
	this.phone = phone;
}


}
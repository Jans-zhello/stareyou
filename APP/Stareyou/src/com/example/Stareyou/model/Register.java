package com.example.Stareyou.model;

import java.io.Serializable;
/**
 *       userid:ע���û�ID��Users��userid��Ӧ
         sex:ע���û��Ա�
         date:ע������
         username:�û���
         phone:�ֻ���
 * @author Administrator
 *
 */
public class Register implements Serializable{

private  int  userid;//ע���û�ID��Users��userid��Ӧ
private  String  sex;//ע���û��Ա�
private  String  date;//ע������
private  String  username;//�û���
private  String  phone;//�ֻ���
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
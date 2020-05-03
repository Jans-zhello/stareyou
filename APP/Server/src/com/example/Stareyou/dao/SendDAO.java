package com.example.Stareyou.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import com.example.Stareyou.model.Send;

public class SendDAO {
	private Connection conn = null;

	public SendDAO(Connection conn) {
		this.conn = conn;
	}

	public void add(Send obj) throws Exception {
		PreparedStatement st = conn
				.prepareStatement("insert into send(userid,username,send_content,send_type,send_icon,send_location) values(?,?,?,?,?,?)");
		st.setInt(1, obj.getUserid());
		st.setString(2, obj.getUsername());
		st.setString(3, obj.getSend_content());
		st.setInt(4, obj.getSend_type());
		st.setString(5, obj.getSend_icon());
		st.setString(6, obj.getSend_location());
		if (st.executeUpdate() <= 0) {
			throw new Exception();
		}
	}

	public void set(Send obj) throws Exception {
		PreparedStatement st = conn
				.prepareStatement("update send set  userid =?,username =?,send_content =?,send_type =?,send_icon=?,send_location=? where sendid=?");
		st.setInt(1, obj.getUserid());
		st.setString(2, obj.getUsername());
		st.setString(3, obj.getSend_content());
		st.setInt(4, obj.getSend_type());
		st.setString(5, obj.getSend_icon());
		st.setString(6, obj.getSend_location());
		st.setInt(7, obj.getSendid());
		if (st.executeUpdate() <= 0) {
			throw new Exception();
		}
	}

	public void delete(int obj) throws Exception {
		PreparedStatement st = conn
				.prepareStatement("delete from send where sendid=?");
		st.setObject(1, obj);

		if (st.executeUpdate() <= 0) {
			throw new Exception();
		}
	}

	public Vector<Send> findKey(int key) throws Exception {
		return findColumnName("sendid", key);
	}

	public Vector<Send> findColumnName(String cname, Object value)
			throws Exception {
		PreparedStatement pst = conn
				.prepareStatement("SELECT * FROM send WHERE " + cname + "=?");
		pst.setObject(1, value);
		ResultSet re = pst.executeQuery();
		Vector<Send> list = new Vector<Send>();
		while (re.next()) {
			Send obj1 = new Send();
			obj1.setSendid(re.getInt("sendid"));
			obj1.setUserid(re.getInt("userid"));
			obj1.setUsername(re.getString("username"));
			obj1.setSend_content(re.getString("send_content"));
			obj1.setSend_type(re.getInt("send_type"));
			obj1.setSend_icon(re.getString("send_icon"));
			obj1.setSend_location(re.getString("send_location"));
			obj1.setSend_date(re.getString("send_date"));
			list.add(obj1);
		}
		return list;
	}

	public Vector<Send> findAll() throws Exception {
		PreparedStatement pst = conn.prepareStatement("SELECT * FROM send");
		ResultSet re = pst.executeQuery();
		Vector<Send> list = new Vector<Send>();
		while (re.next()) {
			Send obj1 = new Send();
			obj1.setSendid(re.getInt("sendid"));
			obj1.setUserid(re.getInt("userid"));
			obj1.setUsername(re.getString("username"));
			obj1.setSend_content(re.getString("send_content"));
			obj1.setSend_type(re.getInt("send_type"));
			obj1.setSend_icon(re.getString("send_icon"));
			obj1.setSend_location(re.getString("send_location"));
			obj1.setSend_date(re.getString("send_date"));
			list.add(obj1);
		}
		return list;
	}

}
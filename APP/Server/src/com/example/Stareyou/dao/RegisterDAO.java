package com.example.Stareyou.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import com.example.Stareyou.model.Register;

public class RegisterDAO {
	private Connection conn = null;

	public RegisterDAO(Connection conn) {
		this.conn = conn;
	}

	public void add(Register obj) throws Exception {
		PreparedStatement st = conn
				.prepareStatement("insert into register(userid,username,sex,phone) values(?,?,?,?)");
		st.setInt(1, obj.getUserid());
		st.setString(2, obj.getUsername());
		st.setString(3, obj.getSex());
		st.setString(4, obj.getPhone());
		if (st.executeUpdate() <= 0) {
			throw new Exception();
		}
	}

	public void set(Register obj) throws Exception {
		PreparedStatement st = conn
				.prepareStatement("update register set  username =?,sex =?,phone =? where userid=?");
		st.setString(1, obj.getUsername());
		st.setString(2, obj.getSex());
		st.setString(3, obj.getPhone());
		st.setInt(4, obj.getUserid());
		if (st.executeUpdate() <= 0) {
			throw new Exception();
		}
	}

	public void delete(int obj) throws Exception {
		PreparedStatement st = conn
				.prepareStatement("delete from register where userid=?");
		st.setObject(1, obj);

		if (st.executeUpdate() <= 0) {
			throw new Exception();
		}
	}

	public Vector<Register> findKey(int key) throws Exception {
		return findColumnName("userid", key);
	}
	public Vector<Register> findColumnName(String cname, Object value)
			throws Exception {
		PreparedStatement pst = conn
				.prepareStatement("SELECT * FROM register WHERE " + cname
						+ "=?");
		pst.setObject(1, value);
		ResultSet re = pst.executeQuery();
		Vector<Register> list = new Vector<Register>();
		while (re.next()) {
			Register obj1 = new Register();
			obj1.setUserid(re.getInt("userid"));
			obj1.setUsername(re.getString("username"));
			obj1.setSex(re.getString("sex"));
			obj1.setPhone(re.getString("phone"));
			obj1.setDate(re.getString("date"));
			list.add(obj1);
		}
		return list;
	}

	public Vector<Register> findAll() throws Exception {
		PreparedStatement pst = conn
				.prepareStatement("SELECT * FROM register");
		ResultSet re = pst.executeQuery();
		Vector<Register> list = new Vector<Register>();
		while (re.next()) {
			Register obj1 = new Register();
			obj1.setUserid(re.getInt("userid"));
			obj1.setUsername(re.getString("username"));
			obj1.setSex(re.getString("sex"));
			obj1.setPhone(re.getString("phone"));
			obj1.setDate(re.getString("date"));
			list.add(obj1);
		}
		return list;
	}

}
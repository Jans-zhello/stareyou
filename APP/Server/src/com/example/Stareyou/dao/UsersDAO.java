package com.example.Stareyou.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import com.example.Stareyou.model.Users;

public class UsersDAO {
	private Connection conn = null;

	public UsersDAO(Connection conn) {
		this.conn = conn;
	}

	public void add(Users obj) throws Exception {
		PreparedStatement st = conn
				.prepareStatement("insert into users(password,send_number,concern_number,fans_number,collection_number,help_number,ukey) values(?,?,?,?,?,?,?)");

		st.setString(1, obj.getPassword());
		st.setInt(2, obj.getSend_number());
		st.setInt(3, obj.getConcern_number());
		st.setInt(4, obj.getFans_number());
		st.setInt(5, obj.getCollection_number());
		st.setInt(6, obj.getHelp_number());
		st.setString(7, obj.getUkey());
		if (st.executeUpdate() <= 0) {
			throw new Exception();
		}
	}

	public void set(Users obj) throws Exception {
		PreparedStatement st = conn
				.prepareStatement("update users set  password =?,send_number=?,concern_number=?,fans_number=?,collection_number=?,help_number=?,ukey =? where userid=?");
		st.setString(1, obj.getPassword());
		st.setInt(2, obj.getSend_number());
		st.setInt(3, obj.getConcern_number());
		st.setInt(4, obj.getFans_number());
		st.setInt(5, obj.getCollection_number());
		st.setInt(6, obj.getHelp_number());
		st.setString(7, obj.getUkey());
		st.setInt(8, obj.getUserid());
		if (st.executeUpdate() <= 0) {
			throw new Exception();
		}
	}

	public void delete(int obj) throws Exception {
		PreparedStatement st = conn
				.prepareStatement("delete from users where userid=?");
		st.setObject(1, obj);

		if (st.executeUpdate() <= 0) {
			throw new Exception();
		}
	}

	public Vector<Users> findKey(int key) throws Exception {
		return findColumnName("userid", key);
	}

	public Vector<Users> findColumnName(String cname, Object value)
			throws Exception {
		PreparedStatement pst = conn
				.prepareStatement("SELECT * FROM users WHERE " + cname + "=?");
		pst.setObject(1, value);
		ResultSet re = pst.executeQuery();
		Vector<Users> list = new Vector<Users>();
		while (re.next()) {
			Users obj1 = new Users();
			obj1.setUserid(re.getInt("userid"));
			obj1.setPassword(re.getString("password"));
			obj1.setSend_number(re.getInt("send_number"));
			obj1.setConcern_number(re.getInt("concern_number"));
			obj1.setFans_number(re.getInt("fans_number"));
			obj1.setCollection_number(re.getInt("collection_number"));
			obj1.setHelp_number(re.getInt("help_number"));
			obj1.setUkey(re.getString("ukey"));
			list.add(obj1);
		}
		return list;
	}

	public Vector<Users> findAll() throws Exception {
		PreparedStatement pst = conn.prepareStatement("SELECT * FROM users");
		ResultSet re = pst.executeQuery();
		Vector<Users> list = new Vector<Users>();
		while (re.next()) {
			Users obj1 = new Users();
			obj1.setUserid(re.getInt("userid"));
			obj1.setPassword(re.getString("password"));
			obj1.setSend_number(re.getInt("send_number"));
			obj1.setConcern_number(re.getInt("concern_number"));
			obj1.setFans_number(re.getInt("fans_number"));
			obj1.setCollection_number(re.getInt("collection_number"));
			obj1.setHelp_number(re.getInt("help_number"));
			obj1.setUkey(re.getString("ukey"));
			list.add(obj1);
		}
		return list;
	}

}
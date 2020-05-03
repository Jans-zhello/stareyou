package com.example.Stareyou.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import com.example.Stareyou.model.Help;

public class HelpDAO {
	private Connection conn = null;

	public HelpDAO(Connection conn) {
		this.conn = conn;
	}

	public void add(Help obj) throws Exception {
		PreparedStatement st = conn
				.prepareStatement("insert into help(userid,username,help_title,help_content,help_type,help_icon,help_location) values(?,?,?,?,?,?,?)");
		st.setInt(1, obj.getUserid());
		st.setString(2, obj.getUsername());
		st.setString(3, obj.getHelp_title());
		st.setString(4, obj.getHelp_content());
		st.setInt(5, obj.getHelp_type());
		st.setString(6, obj.getHelp_icon());
		st.setString(7, obj.getHelp_location());
		if (st.executeUpdate() <= 0) {
			throw new Exception();
		}
	}

	public void set(Help obj) throws Exception {
		PreparedStatement st = conn
				.prepareStatement("update help set  userid =?,username=?,help_title =?,help_content =?,help_type=?,help_icon=?,help_location=? where helpid=?");
		st.setInt(1, obj.getUserid());
		st.setString(2, obj.getUsername());
		st.setString(3, obj.getHelp_title());
		st.setString(4, obj.getHelp_content());
		st.setInt(5, obj.getHelp_type());
		st.setString(6, obj.getHelp_icon());
		st.setString(7, obj.getHelp_location());
		st.setInt(8, obj.getHelpid());
		if (st.executeUpdate() <= 0) {
			throw new Exception();
		}
	}

	public void delete(int obj) throws Exception {
		PreparedStatement st = conn
				.prepareStatement("delete from help where helpid=?");
		st.setObject(1, obj);

		if (st.executeUpdate() <= 0) {
			throw new Exception();
		}
	}

	public Vector<Help> findKey(int key) throws Exception {
		return findColumnName("helpid", key);
	}

	public Vector<Help> findColumnName(String cname, Object value)
			throws Exception {
		PreparedStatement pst = conn
				.prepareStatement("SELECT * FROM help WHERE " + cname + "=?");
		pst.setObject(1, value);
		ResultSet re = pst.executeQuery();
		Vector<Help> list = new Vector<Help>();
		while (re.next()) {
			Help obj1 = new Help();
			obj1.setHelpid(re.getInt("helpid"));
			obj1.setUserid(re.getInt("userid"));
			obj1.setUsername(re.getString("username"));
			obj1.setHelp_title(re.getString("help_title"));
			obj1.setHelp_content(re.getString("help_content"));
			obj1.setHelp_type(re.getInt("help_type"));
			obj1.setHelp_icon(re.getString("help_icon"));
			obj1.setHelp_location(re.getString("help_location"));
			obj1.setHelp_date(re.getString("help_date"));
			list.add(obj1);
		}
		return list;
	}

	public Vector<Help> findAll() throws Exception {
		PreparedStatement pst = conn.prepareStatement("SELECT * FROM help");
		ResultSet re = pst.executeQuery();
		Vector<Help> list = new Vector<Help>();
		while (re.next()) {
			Help obj1 = new Help();
			obj1.setHelpid(re.getInt("helpid"));
			obj1.setUserid(re.getInt("userid"));
			obj1.setUsername(re.getString("username"));
			obj1.setHelp_title(re.getString("help_title"));
			obj1.setHelp_content(re.getString("help_content"));
			obj1.setHelp_type(re.getInt("help_type"));
			obj1.setHelp_icon(re.getString("help_icon"));
			obj1.setHelp_location(re.getString("help_location"));
			obj1.setHelp_date(re.getString("help_date"));
			list.add(obj1);
		}
		return list;
	}

}
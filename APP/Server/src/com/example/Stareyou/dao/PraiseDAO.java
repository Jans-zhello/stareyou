package com.example.Stareyou.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import com.example.Stareyou.model.Praise;

public class PraiseDAO {
	private Connection conn = null;

	public PraiseDAO(Connection conn) {
		this.conn = conn;
	}

	public void add(Praise obj) throws Exception {
		PreparedStatement st = conn
				.prepareStatement("insert into praise(userid,praised_userid,sendid) values(?,?,?)");
		st.setInt(1, obj.getUserid());
		st.setInt(2, obj.getPraised_userid());
		st.setInt(3, obj.getSendid());
		if (st.executeUpdate() <= 0) {
			throw new Exception();
		}
	}

	public void set(Praise obj) throws Exception {
		PreparedStatement st = conn
				.prepareStatement("update praise set  userid =?,praised_userid =?,sendid =? where praiseid=?");
		st.setInt(1, obj.getUserid());
		st.setInt(2, obj.getPraised_userid());
		st.setInt(3, obj.getSendid());
		st.setInt(4,obj.getPraiseid());
		if (st.executeUpdate() <= 0) {
			throw new Exception();
		}
	}

	public void delete(int obj) throws Exception {
		PreparedStatement st = conn
				.prepareStatement("delete from praise where praiseid=?");
		st.setObject(1, obj);

		if (st.executeUpdate() <= 0) {
			throw new Exception();
		}
	}

	public Vector<Praise> findKey(int key) throws Exception {
		return findColumnName("praiseid", key);
	}
	public Vector<Praise> findColumnName(String cname, Object value)
			throws Exception {
		PreparedStatement pst = conn
				.prepareStatement("SELECT * FROM praise WHERE " + cname
						+ "=?");
		pst.setObject(1, value);
		ResultSet re = pst.executeQuery();
		Vector<Praise> list = new Vector<Praise>();
		while (re.next()) {
			Praise obj1 = new Praise();
			obj1.setPraiseid(re.getInt("praiseid"));
			obj1.setUserid(re.getInt("userid"));
			obj1.setPraised_userid(re.getInt("praised_userid"));
			obj1.setSendid(re.getInt("sendid"));
			obj1.setPraise_date(re.getString("praise_date"));
			list.add(obj1);
		}
		return list;
	}

	public Vector<Praise> findAll() throws Exception {
		PreparedStatement pst = conn
				.prepareStatement("SELECT * FROM praise");
		ResultSet re = pst.executeQuery();
		Vector<Praise> list = new Vector<Praise>();
		while (re.next()) {
			Praise obj1 = new Praise();
			obj1.setPraiseid(re.getInt("praiseid"));
			obj1.setUserid(re.getInt("userid"));
			obj1.setPraised_userid(re.getInt("praised_userid"));
			obj1.setSendid(re.getInt("sendid"));
			obj1.setPraise_date(re.getString("praise_date"));
			list.add(obj1);
		}
		return list;
	}

}
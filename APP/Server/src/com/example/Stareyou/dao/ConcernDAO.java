package com.example.Stareyou.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import com.example.Stareyou.model.Concern;

public class ConcernDAO {
	private Connection conn = null;

	public ConcernDAO(Connection conn) {
		this.conn = conn;
	}

	public void add(Concern obj) throws Exception {
		PreparedStatement st = conn
				.prepareStatement("insert into concern(userid,concerned_userid) values(?,?)");
		st.setInt(1, obj.getUserid());
		st.setInt(2, obj.getConcerned_userid());
		if (st.executeUpdate() <= 0) {
			throw new Exception();
		}
	}

	public void set(Concern obj) throws Exception {
		PreparedStatement st = conn
				.prepareStatement("update concern set  userid =?,concerned_userid =? where concernid=?");
		st.setInt(1, obj.getUserid());
		st.setInt(2, obj.getConcerned_userid());
		st.setInt(3, obj.getConcernid());
		if (st.executeUpdate() <= 0) {
			throw new Exception();
		}
	}

	public void delete(int obj) throws Exception {
		PreparedStatement st = conn
				.prepareStatement("delete from concern where concernid=?");
		st.setObject(1, obj);

		if (st.executeUpdate() <= 0) {
			throw new Exception();
		}
	}

	public Vector<Concern> findKey(int key) throws Exception {
		return findColumnName("concernid", key);
	}
	public Vector<Concern> findColumnName(String cname, Object value)
			throws Exception {
		PreparedStatement pst = conn
				.prepareStatement("SELECT * FROM concern WHERE " + cname
						+ "=?");
		pst.setObject(1, value);
		ResultSet re = pst.executeQuery();
		Vector<Concern> list = new Vector<Concern>();
		while (re.next()) {
			Concern obj1 = new Concern();
			obj1.setConcernid(re.getInt("concernid"));
			obj1.setUserid(re.getInt("userid"));
			obj1.setConcerned_userid(re.getInt("concerned_userid"));
			obj1.setConcern_date(re.getString("concern_date"));
			list.add(obj1);
		}
		return list;
	}

	public Vector<Concern> findAll() throws Exception {
		PreparedStatement pst = conn
				.prepareStatement("SELECT * FROM concern");
		ResultSet re = pst.executeQuery();
		Vector<Concern> list = new Vector<Concern>();
		while (re.next()) {
			Concern obj1 = new Concern();
			obj1.setConcernid(re.getInt("concernid"));
			obj1.setUserid(re.getInt("userid"));
			obj1.setConcerned_userid(re.getInt("concerned_userid"));
			obj1.setConcern_date(re.getString("concern_date"));
			list.add(obj1);
		}
		return list;
	}

}
package com.example.Stareyou.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import com.example.Stareyou.model.Assess;

public class AssessDAO {
	private Connection conn = null;

	public AssessDAO(Connection conn) {
		this.conn = conn;
	}

	public void add(Assess obj) throws Exception {
		PreparedStatement st = conn
				.prepareStatement("insert into assess(userid,helpid,helper_number,satisfied) values(?,?,?,?)");
		st.setInt(1, obj.getUserid());
		st.setInt(2, obj.getHelpid());
		st.setInt(3, obj.getHelper_number());
		st.setDouble(4, obj.getSatisfied());
		if (st.executeUpdate() <= 0) {
			throw new Exception();
		}
	}

	public void set(Assess obj) throws Exception {
		PreparedStatement st = conn
				.prepareStatement("update assess set  userid =?,helpid =?,helper_number=?,satisfied =? where assessid=?");
		st.setInt(1, obj.getUserid());
		st.setInt(2, obj.getHelpid());
		st.setInt(3, obj.getHelper_number());
		st.setDouble(4, obj.getSatisfied());
		st.setInt(5, obj.getAssessid());
		if (st.executeUpdate() <= 0) {
			throw new Exception();
		}
	}

	public void delete(int obj) throws Exception {
		PreparedStatement st = conn
				.prepareStatement("delete from assess where assessid=?");
		st.setObject(1, obj);

		if (st.executeUpdate() <= 0) {
			throw new Exception();
		}
	}

	public Vector<Assess> findKey(int key) throws Exception {
		return findColumnName("assessid", key);
	}

	public Vector<Assess> findColumnName(String cname, Object value)
			throws Exception {
		PreparedStatement pst = conn
				.prepareStatement("SELECT * FROM assess WHERE " + cname + "=?");
		pst.setObject(1, value);
		ResultSet re = pst.executeQuery();
		Vector<Assess> list = new Vector<Assess>();
		while (re.next()) {
			Assess assess = new Assess();
			assess.setAssessid(re.getInt("assessid"));
			assess.setUserid(re.getInt("userid"));
			assess.setHelpid(re.getInt("helpid"));
			assess.setHelper_number(re.getInt("helper_number"));
			assess.setSatisfied(re.getDouble("satisfied"));
			assess.setAssess_date(re.getString("assess_date"));
			list.add(assess);
		}
		return list;
	}

	public Vector<Assess> findAll() throws Exception {
		PreparedStatement pst = conn.prepareStatement("SELECT * FROM assess");
		ResultSet re = pst.executeQuery();
		Vector<Assess> list = new Vector<Assess>();
		while (re.next()) {
			Assess assess = new Assess();
			assess.setAssessid(re.getInt("assessid"));
			assess.setUserid(re.getInt("userid"));
			assess.setHelpid(re.getInt("helpid"));
			assess.setHelper_number(re.getInt("helper_number"));
			assess.setSatisfied(re.getDouble("satisfied"));
			assess.setAssess_date(re.getString("assess_date"));
			list.add(assess);
		}
		return list;
	}

}
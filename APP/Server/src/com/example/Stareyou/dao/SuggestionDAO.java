package com.example.Stareyou.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import com.example.Stareyou.model.Suggestion;

public class SuggestionDAO {
	private Connection conn = null;

	public SuggestionDAO(Connection conn) {
		this.conn = conn;
	}

	public void add(Suggestion obj) throws Exception {
		PreparedStatement st = conn
				.prepareStatement("insert into suggestion(userid,suggestion_content) values(?,?)");
		st.setInt(1, obj.getUserid());
		st.setString(2, obj.getSuggestion_content());
		if (st.executeUpdate() <= 0) {
			throw new Exception();
		}
	}

	public void set(Suggestion obj) throws Exception {
		PreparedStatement st = conn
				.prepareStatement("update suggestion set  userid =?,suggestion_content =? where suggestionid=?");
		st.setInt(1, obj.getUserid());
		st.setString(2, obj.getSuggestion_content());
		st.setInt(3, obj.getSuggestionid());
		if (st.executeUpdate() <= 0) {
			throw new Exception();
		}
	}

	public void delete(int obj) throws Exception {
		PreparedStatement st = conn
				.prepareStatement("delete from suggestion where suggestionid=?");
		st.setObject(1, obj);

		if (st.executeUpdate() <= 0) {
			throw new Exception();
		}
	}

	public Vector<Suggestion> findKey(int key) throws Exception {
		return findColumnName("suggestionid", key);
	}
	public Vector<Suggestion> findColumnName(String cname, Object value)
			throws Exception {
		PreparedStatement pst = conn
				.prepareStatement("SELECT * FROM suggestion WHERE " + cname
						+ "=?");
		pst.setObject(1, value);
		ResultSet re = pst.executeQuery();
		Vector<Suggestion> list = new Vector<Suggestion>();
		while (re.next()) {
			Suggestion obj1 = new Suggestion();
			obj1.setSuggestionid(re.getInt("suggestionid"));
			obj1.setUserid(re.getInt("userid"));
			obj1.setSuggestion_content(re.getString("suggestion_content"));
			obj1.setSuggestion_date(re.getString("suggestion_date"));
			list.add(obj1);
		}
		return list;
	}

	public Vector<Suggestion> findAll() throws Exception {
		PreparedStatement pst = conn
				.prepareStatement("SELECT * FROM suggestion");
		ResultSet re = pst.executeQuery();
		Vector<Suggestion> list = new Vector<Suggestion>();
		while (re.next()) {
			Suggestion obj1 = new Suggestion();
			obj1.setSuggestionid(re.getInt("suggestionid"));
			obj1.setUserid(re.getInt("userid"));
			obj1.setSuggestion_content(re.getString("suggestion_content"));
			obj1.setSuggestion_date(re.getString("suggestion_date"));
			list.add(obj1);
		}
		return list;
	}

}
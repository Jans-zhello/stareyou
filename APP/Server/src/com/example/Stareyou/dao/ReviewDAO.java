package com.example.Stareyou.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import com.example.Stareyou.model.Review;

public class ReviewDAO {
	private Connection conn = null;

	public ReviewDAO(Connection conn) {
		this.conn = conn;
	}

	public void add(Review obj) throws Exception {
		PreparedStatement st = conn
				.prepareStatement("insert into review(userid,reviewed_userid,sendid,review_content,review_type) values(?,?,?,?,?)");

		st.setInt(1, obj.getUserid());
		st.setInt(2, obj.getReviewed_userid());
		st.setInt(3, obj.getSendid());
		st.setString(4, obj.getReview_content());
		st.setInt(5, obj.getReview_type());
		if (st.executeUpdate() <= 0) {
			throw new Exception();
		}
	}

	public void set(Review obj) throws Exception {
		PreparedStatement st = conn
				.prepareStatement("update review set  userid =?,reviewed_userid =?,sendid =?,review_content =?,review_type =? where reviewid=?");
		st.setInt(1, obj.getUserid());
		st.setInt(2, obj.getReviewed_userid());
		st.setInt(3, obj.getSendid());
		st.setString(4, obj.getReview_content());
		st.setInt(5, obj.getReview_type());
		st.setInt(6, obj.getReviewid());
		if (st.executeUpdate() <= 0) {
			throw new Exception();
		}
	}

	public void delete(int obj) throws Exception {
		PreparedStatement st = conn
				.prepareStatement("delete from review where reviewid=?");
		st.setObject(1, obj);

		if (st.executeUpdate() <= 0) {
			throw new Exception();
		}
	}

	public Vector<Review> findKey(int key) throws Exception {
		return findColumnName("reviewid", key);
	}

	public Vector<Review> findColumnName(String cname, Object value)
			throws Exception {
		PreparedStatement pst = conn
				.prepareStatement("SELECT * FROM review WHERE " + cname + "=?");
		pst.setObject(1, value);
		ResultSet re = pst.executeQuery();
		Vector<Review> list = new Vector<Review>();
		while (re.next()) {
			Review obj1 = new Review();
			obj1.setReviewid(re.getInt("reviewid"));
			obj1.setUserid(re.getInt("userid"));
			obj1.setReviewed_userid(re.getInt("reviewed_userid"));
			obj1.setSendid(re.getInt("sendid"));
			obj1.setReview_content(re.getString("review_content"));
			obj1.setReview_type(re.getInt("review_type"));
			obj1.setReview_date(re.getString("review_date"));
			list.add(obj1);
		}
		return list;
	}

	public Vector<Review> findAll() throws Exception {
		PreparedStatement pst = conn.prepareStatement("SELECT * FROM review");
		ResultSet re = pst.executeQuery();
		Vector<Review> list = new Vector<Review>();
		while (re.next()) {
			Review obj1 = new Review();
			obj1.setReviewid(re.getInt("reviewid"));
			obj1.setUserid(re.getInt("userid"));
			obj1.setReviewed_userid(re.getInt("reviewed_userid"));
			obj1.setSendid(re.getInt("sendid"));
			obj1.setReview_content(re.getString("review_content"));
			obj1.setReview_type(re.getInt("review_type"));
			obj1.setReview_date(re.getString("review_date"));
			list.add(obj1);
		}
		return list;
	}

}
package com.example.Stareyou.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import com.example.Stareyou.model.Amaze;
import com.example.Stareyou.model.Assess;

public class AmazeDAO {
	private Connection conn = null;

	public AmazeDAO(Connection conn) {
		this.conn = conn;
	}

	public void add(Amaze obj) throws Exception {
		PreparedStatement st = conn
				.prepareStatement("insert into amaze(helpid,amaze_userid,amaze_username,amazed_userid,amazed_username) values(?,?,?,?,?)");
		st.setInt(1, obj.getHelpid());
		st.setInt(2, obj.getAmaze_userid());
		st.setString(3, obj.getAmaze_username());
		st.setInt(4, obj.getAmazed_userid());
		st.setString(5, obj.getAmazed_username());
		if (st.executeUpdate() <= 0) {
			throw new Exception();
		}
	}

}

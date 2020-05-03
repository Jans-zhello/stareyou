package com.example.Stareyou.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import com.example.Stareyou.model.Chat;

public class ChatDAO {
	private Connection conn = null;

	public ChatDAO(Connection conn) {
		this.conn = conn;
	}

	public void add(Chat obj) throws Exception {
		PreparedStatement st = conn
				.prepareStatement("insert into chat(userid,chatted_userid,helpid,chat_content,chat_type) values(?,?,?,?,?)");
		st.setInt(1, obj.getUserid());
		st.setInt(2, obj.getChatted_userid());
		st.setInt(3, obj.getHelpid());
		st.setString(4, obj.getChat_content());
		st.setInt(5, obj.getChat_type());
		if (st.executeUpdate() <= 0) {
			throw new Exception();
		}
	}

	public void set(Chat obj) throws Exception {
		PreparedStatement st = conn
				.prepareStatement("update chat set  userid =?,chatted_userid =?,helpid =?,chat_content=?,chat_type=? where chatid=?");
		st.setInt(1, obj.getUserid());
		st.setInt(2, obj.getChatted_userid());
		st.setInt(3, obj.getHelpid());
		st.setString(4, obj.getChat_content());
		st.setInt(5, obj.getChat_type());
		st.setInt(6, obj.getChatid());
		if (st.executeUpdate() <= 0) {
			throw new Exception();
		}
	}

	public void delete(int obj) throws Exception {
		PreparedStatement st = conn
				.prepareStatement("delete from chat where chatid=?");
		st.setObject(1, obj);

		if (st.executeUpdate() <= 0) {
			throw new Exception();
		}
	}

	public Vector<Chat> findKey(int key) throws Exception {
		return findColumnName("chatid", key);
	}

	public Vector<Chat> findColumnName(String cname, Object value)
			throws Exception {
		PreparedStatement pst = conn
				.prepareStatement("SELECT * FROM chat WHERE " + cname + "=?");
		pst.setObject(1, value);
		ResultSet re = pst.executeQuery();
		Vector<Chat> list = new Vector<Chat>();
		while (re.next()) {
			Chat obj1 = new Chat();
			obj1.setChatid(re.getInt("chatid"));
			obj1.setUserid(re.getInt("userid"));
			obj1.setChatted_userid(re.getInt("chatted_userid"));
			obj1.setHelpid(re.getInt("helpid"));
			obj1.setChat_content(re.getString("chat_content"));
			obj1.setChat_type(re.getInt("chat_type"));
			obj1.setChat_date(re.getString("chat_date"));
			list.add(obj1);
		}
		return list;
	}

	public Vector<Chat> findAll() throws Exception {
		PreparedStatement pst = conn.prepareStatement("SELECT * FROM chat");
		ResultSet re = pst.executeQuery();
		Vector<Chat> list = new Vector<Chat>();
		while (re.next()) {
			Chat obj1 = new Chat();
			obj1.setChatid(re.getInt("chatid"));
			obj1.setUserid(re.getInt("userid"));
			obj1.setChatted_userid(re.getInt("chatted_userid"));
			obj1.setHelpid(re.getInt("helpid"));
			obj1.setChat_content(re.getString("chat_content"));
			obj1.setChat_type(re.getInt("chat_type"));
			obj1.setChat_date(re.getString("chat_date"));
			list.add(obj1);
		}
		return list;
	}

}
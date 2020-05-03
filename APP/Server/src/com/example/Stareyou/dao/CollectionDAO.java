package com.example.Stareyou.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import com.example.Stareyou.model.Collection;

public class CollectionDAO {
	private Connection conn = null;

	public CollectionDAO(Connection conn) {
		this.conn = conn;
	}

	public void add(Collection obj) throws Exception {
		PreparedStatement st = conn
				.prepareStatement("insert into collection(userid,collectioned_userid,sendid) values(?,?,?)");
		st.setInt(1, obj.getUserid());
		st.setInt(2, obj.getCollectioned_userid());
		st.setInt(3, obj.getSendid());
		if (st.executeUpdate() <= 0) {
			throw new Exception();
		}
	}

	public void set(Collection obj) throws Exception {
		PreparedStatement st = conn
				.prepareStatement("update collection set  userid =?,collectioned_userid =?,sendid =? where collectionid=?");
		st.setInt(1, obj.getUserid());
		st.setInt(2, obj.getCollectioned_userid());
		st.setInt(3, obj.getSendid());
		st.setInt(4, obj.getCollectionid());
		if (st.executeUpdate() <= 0) {
			throw new Exception();
		}
	}

	public void delete(int obj) throws Exception {
		PreparedStatement st = conn
				.prepareStatement("delete from collection where collectionid=?");
		st.setObject(1, obj);

		if (st.executeUpdate() <= 0) {
			throw new Exception();
		}
	}

	public Vector<Collection> findKey(int key) throws Exception {
		return findColumnName("collectionid", key);
	}
	public Vector<Collection> findColumnName(String cname, Object value)
			throws Exception {
		PreparedStatement pst = conn
				.prepareStatement("SELECT * FROM collection WHERE " + cname
						+ "=?");
		pst.setObject(1, value);
		ResultSet re = pst.executeQuery();
		Vector<Collection> list = new Vector<Collection>();
		while (re.next()) {
			Collection obj1 = new Collection();
			obj1.setCollectionid(re.getInt("collectionid"));
			obj1.setUserid(re.getInt("userid"));
			obj1.setCollectioned_userid(re.getInt("collectioned_userid"));
			obj1.setSendid(re.getInt("sendid"));
			obj1.setCollection_date(re.getString("collection_date"));
			list.add(obj1);
		}
		return list;
	}

	public Vector<Collection> findAll() throws Exception {
		PreparedStatement pst = conn
				.prepareStatement("SELECT * FROM collection");
		ResultSet re = pst.executeQuery();
		Vector<Collection> list = new Vector<Collection>();
		while (re.next()) {
			Collection obj1 = new Collection();
			obj1.setCollectionid(re.getInt("collectionid"));
			obj1.setUserid(re.getInt("userid"));
			obj1.setCollectioned_userid(re.getInt("collectioned_userid"));
			obj1.setSendid(re.getInt("sendid"));
			obj1.setCollection_date(re.getString("collection_date"));
			list.add(obj1);
		}
		return list;
	}

}
package com.example.Stareyou.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import com.example.Stareyou.model.Order;

public class OrderDAO {
	private Connection conn = null;

	public OrderDAO(Connection conn) {
		this.conn = conn;
	}

	public void add(Order obj) throws Exception {
		PreparedStatement st = conn
				.prepareStatement("insert into stareyou_order(userid,helped_userid,helpid,order_title) values(?,?,?,?)");
		st.setInt(1, obj.getUserid());
		st.setInt(2, obj.getHelped_userid());
		st.setInt(3, obj.getHelpid());
		st.setString(4, obj.getOrder_title());
		if (st.executeUpdate() <= 0) {
			throw new Exception();
		}
	}

	public void set(Order obj) throws Exception {
		PreparedStatement st = conn
				.prepareStatement("update order set  userid =?,helped_userid =?,helpid =?,order_title=? where orderid=?");
		st.setInt(1, obj.getUserid());
		st.setInt(2,obj.getHelped_userid());
		st.setInt(3, obj.getHelpid());
		st.setString(4, obj.getOrder_title());
		st.setInt(5,obj.getOrderid());
		if (st.executeUpdate() <= 0) {
			throw new Exception();
		}
	}

	public void delete(int obj) throws Exception {
		PreparedStatement st = conn
				.prepareStatement("delete from order where orderid=?");
		st.setObject(1, obj);

		if (st.executeUpdate() <= 0) {
			throw new Exception();
		}
	}

	public Vector<Order> findKey(int key) throws Exception {
		return findColumnName("orderid", key);
	}
	public Vector<Order> findColumnName(String cname, Object value)
			throws Exception {
		PreparedStatement pst = conn
				.prepareStatement("SELECT * FROM order WHERE " + cname
						+ "=?");
		pst.setObject(1, value);
		ResultSet re = pst.executeQuery();
		Vector<Order> list = new Vector<Order>();
		while (re.next()) {
			Order obj1 = new Order();
			obj1.setOrderid(re.getInt("orderid"));
			obj1.setUserid(re.getInt("userid"));
			obj1.setHelped_userid(re.getInt("helped_userid"));
			obj1.setHelpid(re.getInt("helpid"));
			obj1.setOrder_title(re.getString("order_title"));
			obj1.setOrder_date(re.getString("order_date"));
			list.add(obj1);
		}
		return list;
	}

	public Vector<Order> findAll() throws Exception {
		PreparedStatement pst = conn
				.prepareStatement("SELECT * FROM order");
		ResultSet re = pst.executeQuery();
		Vector<Order> list = new Vector<Order>();
		while (re.next()) {
			Order obj1 = new Order();
			obj1.setOrderid(re.getInt("orderid"));
			obj1.setUserid(re.getInt("userid"));
			obj1.setHelped_userid(re.getInt("helped_userid"));
			obj1.setHelpid(re.getInt("helpid"));
			obj1.setOrder_title(re.getString("order_title"));
			obj1.setOrder_date(re.getString("order_date"));
			list.add(obj1);
		}
		return list;
	}

}
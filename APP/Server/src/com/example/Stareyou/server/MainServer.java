package com.example.Stareyou.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import com.example.Stareyou.dao.AmazeDAO;
import com.example.Stareyou.dao.AssessDAO;
import com.example.Stareyou.dao.ChatDAO;
import com.example.Stareyou.dao.CollectionDAO;
import com.example.Stareyou.dao.ConcernDAO;
import com.example.Stareyou.dao.HelpDAO;
import com.example.Stareyou.dao.OrderDAO;
import com.example.Stareyou.dao.PraiseDAO;
import com.example.Stareyou.dao.RegisterDAO;
import com.example.Stareyou.dao.ReviewDAO;
import com.example.Stareyou.dao.SendDAO;
import com.example.Stareyou.dao.SuggestionDAO;
import com.example.Stareyou.dao.UsersDAO;
import com.example.Stareyou.db.DBManager;
import com.example.Stareyou.model.Action;
import com.example.Stareyou.model.Amaze;
import com.example.Stareyou.model.Assess;
import com.example.Stareyou.model.Chat;
import com.example.Stareyou.model.Collection;
import com.example.Stareyou.model.Concern;
import com.example.Stareyou.model.Help;
import com.example.Stareyou.model.Order;
import com.example.Stareyou.model.Praise;
import com.example.Stareyou.model.Register;
import com.example.Stareyou.model.Review;
import com.example.Stareyou.model.Send;
import com.example.Stareyou.model.SendRedirect;
import com.example.Stareyou.model.Suggestion;
import com.example.Stareyou.model.Users;
import com.example.Stareyou.util.Constants;
import com.example.Stareyou.util.ServerConfig;

/**
 * ��������(�ճ���Ϣ���������)
 * 
 * ����ͻ������� ����: һ̨���������ͬʱ�ṩ������� ��Щ��ͬ�ķ���֮��ͨ���˿ں������� ��ͬ�Ķ˿ں����ṩ��ͬ�ķ���:
 * ����ʹ��ͬһ��������,��ͬһwifi��,���˿ںŲ�һ��һ��
 * 
 * @author Tiny
 * 
 */
public class MainServer extends Thread {
	// �������˺Ϳͻ��˵�socket
	private Socket socket = null;
	private static ServerSocket server = null;
	// userpool�Ǽ��û�
	private static HashMap<Integer, ObjectOutputStream> userpool = new HashMap<Integer, ObjectOutputStream>();
	// �Ǽ��û���userid
	int userid = 0;
	// ������������boolean����
	private static boolean flag = true;
	// ���������ϢHashTable
	private static Hashtable<Integer, Integer> news = new Hashtable<Integer, Integer>();

	public MainServer(Socket socket) {
		this.socket = socket;
	}

	// ����������
	public static void OpenServer() throws Exception {
		try {
			server = new ServerSocket(Integer.parseInt(ServerConfig
					.getValue("server_port")));
			while (flag) {
				new MainServer(server.accept()).start();
			}

		} catch (Exception e) {
			e.printStackTrace();
			try {
				closeServer();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			throw e;
		}
	}

	// �رշ�����
	public static void closeServer() throws Exception {
		try {
			flag = false;
			server.close();
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * �����̷߳���
	 */
	public void run() {
		try {
			ObjectOutputStream out = new ObjectOutputStream(
					socket.getOutputStream());
			ObjectInputStream oin = new ObjectInputStream(
					socket.getInputStream());
			while (true) {
				MessageHandle msHandle = (MessageHandle) oin.readObject();
				System.out.println("�������õ���Ϣ��:" + msHandle.getType());
				/**
				 * ��¼��̨���
				 * 
				 * @param msHandle
				 * @param out
				 */
				if (msHandle.getType().equalsIgnoreCase(Constants.LOGIN)) {
					int userid = Integer.parseInt(msHandle.getValue()
							.get("userid").toString());
					String passsword = msHandle.getValue().get("password")
							.toString();
					this.userid = userid;
					Connection conn = com.example.Stareyou.db.DBManager
							.getDBManager().getConnection();
					UsersDAO db = new UsersDAO(conn);
					Vector<Users> v = db.findKey(userid);
					MessageHandle m1 = new MessageHandle();// ���ͻ��˻�����Ϣ
					if (v.size() >= 1) {
						if (v.get(0).getPassword().equalsIgnoreCase(passsword)) {
							Hashtable table = new Hashtable();
							Integer nInteger = news.get(this.userid);
							if (nInteger == null || nInteger == 0) {
								nInteger = 0;
							}
							table.put("news", nInteger);
							table.put("userid", userid);
							table.put("message", "��¼�ɹ�");
							m1.setReturnvalue(table);
							// ����������⣬��֮ǰ����,�Ƴ���ȥ
							ObjectOutputStream out2 = userpool.get(userid);
							if (out2 != null) {
								try {
									out2.close();
									userpool.remove(userid);
								} catch (Exception e) {
								}
							}
							if (nInteger != 0) {
								news.remove(userid);
							}
							// ����¼�ɹ����û���ӵ�HashMap����ȥ
							userpool.put(userid, out);
							news.put(userid, nInteger);
						} else {
							Hashtable table = new Hashtable();
							table.put("message", "�������");
							m1.setReturnvalue(table);
						}
					} else {
						Hashtable table = new Hashtable();
						table.put("message", "����δע��");
						m1.setReturnvalue(table);
					}
					out.writeObject(m1);
					out.flush();
					conn.close();
					// ��¼ʧ�ܴ��������׳��쳣�ر�socket
					if (!m1.getReturnvalue().get("message").equals("��¼�ɹ�")) {
						throw new Exception();
					}
				}
				/**
				 * ע�����˳���̨���
				 */
				else if (msHandle.getType().equalsIgnoreCase(Constants.LOGOUT)
						|| msHandle.getType().equalsIgnoreCase(Constants.EXIT)) {

					Integer removeuserid = Integer.parseInt(msHandle.getValue()
							.get("removeuserid").toString());
					if (userpool.containsKey(removeuserid)) {
						userpool.remove(removeuserid);
					}
					throw new Exception();
				}
				/**
				 * ע���̨���
				 */
				else if (msHandle.getType()
						.equalsIgnoreCase(Constants.REGISTER)) {
					Connection conn = com.example.Stareyou.db.DBManager
							.getDBManager().getConnection();
					MessageHandle m2 = new MessageHandle();// ���ͻ��˻�����Ϣ
					try {
						conn.setAutoCommit(false);// �ֶ��ύ����
						UsersDAO db = new UsersDAO(conn);
						RegisterDAO db2 = new RegisterDAO(conn);
						// ����Ƿ�����
						Vector<Register> v0 = db2.findAll();
						for (Register userinfos : v0) {
							if (msHandle.getValue().get("username").toString()
									.equals(userinfos.getUsername())) {
								Hashtable table = new Hashtable();
								table.put("message", "���û�����ע��");
								m2.setReturnvalue(table);
								out.writeObject(m2);
								out.flush();
								return;
							}
						}
						// ����users��
						Users u1 = new Users();
						u1.setPassword(msHandle.getValue().get("password")
								.toString());
						u1.setCollection_number(0);
						u1.setConcern_number(0);
						u1.setFans_number(0);
						u1.setHelp_number(0);
						u1.setSend_number(0);
						/**
						 * ͨ��ukey�ֶε�Ψһ�Ի�ȡ�ող����userid(��˼·)
						 */
						String key = new Date().getTime() + "R"
								+ (Math.random() * 100) + "R"
								+ (Math.random() * 1000);
						u1.setUkey(key);
						db.add(u1);
						/**
						 * ��ȡ�ող����userid
						 */
						Vector<Users> v2 = db.findColumnName("ukey", key);
						u1 = v2.get(0);
						// ����register��
						Register info = new Register();
						info.setUserid(u1.getUserid());
						info.setPhone(msHandle.getValue().get("phone")
								.toString());
						info.setSex(msHandle.getValue().get("sex").toString());
						info.setUsername(msHandle.getValue().get("username")
								.toString());
						db2.add(info);
						conn.commit();
						// ���ͻ��˷�����Ϣ
						Hashtable<String, String> table = new Hashtable<String, String>();
						table.put("message", "ע��ɹ�");
						table.put("userid", u1.getUserid() + "");
						m2.setReturnvalue(table);
					} catch (Exception e) {
						e.printStackTrace();
						conn.rollback();// ����������ع�������֮ǰ�Ĳ���
						Hashtable table = new Hashtable();
						table.put("message", "����������,ע��ʧ��");
						m2.setReturnvalue(table);
					} finally {
						conn.close();
					}
					out.writeObject(m2);
					out.flush();
				}

				/**
				 * �����̨���
				 */
				else if (msHandle.getType().equalsIgnoreCase(Constants.SEND)) {
					Connection conn = com.example.Stareyou.db.DBManager
							.getDBManager().getConnection();
					MessageHandle m4 = new MessageHandle();// ���ͻ��˻�����Ϣ
					try {
						Send send = new Send();
						send.setUserid(Integer.parseInt(msHandle.getValue()
								.get("userid").toString()));
						send.setUsername(msHandle.getValue().get("username")
								.toString());
						send.setSend_content(msHandle.getValue()
								.get("send_content").toString());
						send.setSend_type(Integer.parseInt(msHandle.getValue()
								.get("send_type").toString()));
						send.setSend_icon(msHandle.getValue().get("send_icon")
								.toString());
						if (msHandle.getValue().get("send_location") != null) {
							send.setSend_location(msHandle.getValue()
									.get("send_location").toString());
						}
						new SendDAO(conn).add(send);

						// ��uses��������send_number����
						PreparedStatement ps = conn
								.prepareStatement("update users set send_number = send_number+1 where userid="
										+ Integer.parseInt(msHandle.getValue()
												.get("userid").toString()));
						if (ps.executeUpdate() <= 0) {
							throw new Exception();
						}

						Hashtable table = new Hashtable();
						table.put("message", "����ɹ�");
						m4.setReturnvalue(table);
					} catch (Exception e) {
						Hashtable table = new Hashtable();
						table.put("message", "����ʧ��");
						m4.setReturnvalue(table);

					} finally {
						conn.close();
					}
					out.writeObject(m4);
					out.flush();
				}
				/**
				 * �������ۺ�̨���
				 */
				else if (msHandle.getType().equalsIgnoreCase(Constants.REVIEW)) {
					Connection conn = com.example.Stareyou.db.DBManager
							.getDBManager().getConnection();
					MessageHandle m4 = new MessageHandle();// ���ͻ��˻�����Ϣ
					try {
						Review r = new Review();
						r.setSendid(Integer.parseInt(msHandle.getValue()
								.get("sendid").toString()));
						r.setUserid(Integer.parseInt(msHandle.getValue()
								.get("userid").toString()));
						r.setReviewed_userid(Integer.parseInt(msHandle
								.getValue().get("reviewed_userid").toString()));
						r.setReview_content(msHandle.getValue()
								.get("review_content").toString());
						r.setReview_type(Integer.parseInt(msHandle.getValue()
								.get("review_type").toString()));
						new ReviewDAO(conn).add(r);
						// ����һ�����������Ϣ
						if (news.containsKey(Integer.parseInt(msHandle
								.getValue().get("userid").toString()))) {
							news.put(
									Integer.parseInt(msHandle.getValue()
											.get("userid").toString()),
									news.get(Integer.parseInt(msHandle
											.getValue().get("userid")
											.toString())) + 1);
						} else {
							news.put(
									Integer.parseInt(msHandle.getValue()
											.get("userid").toString()), 1);
						}
						Hashtable table = new Hashtable();
						table.put("message", "���۳ɹ�");
						m4.setReturnvalue(table);
					} catch (Exception e) {
						e.printStackTrace();
						Hashtable table = new Hashtable();
						table.put("message", "����ʧ��");
						m4.setReturnvalue(table);

					} finally {
						conn.close();
					}
					out.writeObject(m4);
					out.flush();
				}
				/**
				 * ���޺�̨���
				 */
				else if (msHandle.getType().equalsIgnoreCase(Constants.PRAISE)) {
					Connection conn = com.example.Stareyou.db.DBManager
							.getDBManager().getConnection();
					MessageHandle m4 = new MessageHandle();// ���ͻ��˻�����Ϣ
					try {
						Praise praise = new Praise();
						praise.setUserid(Integer.parseInt(msHandle.getValue()
								.get("userid").toString()));
						praise.setPraised_userid(Integer.parseInt(msHandle
								.getValue().get("praised_userid").toString()));
						praise.setSendid(Integer.parseInt(msHandle.getValue()
								.get("sendid").toString()));
						new PraiseDAO(conn).add(praise);
						// ����һ�����������Ϣ
						if (news.containsKey(Integer.parseInt(msHandle
								.getValue().get("userid").toString()))) {
							news.put(
									Integer.parseInt(msHandle.getValue()
											.get("userid").toString()),
									news.get(Integer.parseInt(msHandle
											.getValue().get("userid")
											.toString())) + 1);
						} else {
							news.put(
									Integer.parseInt(msHandle.getValue()
											.get("userid").toString()), 1);
						}
						Hashtable table = new Hashtable();
						table.put("message", "����");
						m4.setReturnvalue(table);
					} catch (Exception e) {
						e.printStackTrace();
						Hashtable table = new Hashtable();
						table.put("message", "����ʧ��");
						m4.setReturnvalue(table);

					} finally {
						conn.close();
					}
					out.writeObject(m4);
					out.flush();
				}
				/**
				 * �ղغ�̨���
				 */
				else if (msHandle.getType().equalsIgnoreCase(
						Constants.COLLECTION)) {
					Connection conn = com.example.Stareyou.db.DBManager
							.getDBManager().getConnection();
					MessageHandle m4 = new MessageHandle();// ���ͻ��˻�����Ϣ
					try {
						Collection collection = new Collection();
						collection.setUserid(Integer.parseInt(msHandle
								.getValue().get("userid").toString()));
						collection
								.setCollectioned_userid(Integer
										.parseInt(msHandle.getValue()
												.get("collectioned_userid")
												.toString()));
						collection.setSendid(Integer.parseInt(msHandle
								.getValue().get("sendid").toString()));
						new CollectionDAO(conn).add(collection);
						// ����һ�����������Ϣ
						if (news.containsKey(Integer.parseInt(msHandle
								.getValue().get("userid").toString()))) {
							news.put(
									Integer.parseInt(msHandle.getValue()
											.get("userid").toString()),
									news.get(Integer.parseInt(msHandle
											.getValue().get("userid")
											.toString())) + 1);
						} else {
							news.put(
									Integer.parseInt(msHandle.getValue()
											.get("userid").toString()), 1);
						}
						// ��uses��������collection_number����
						PreparedStatement ps = conn
								.prepareStatement("update users set collection_number = collection_number+1 where userid="
										+ Integer.parseInt(msHandle.getValue()
												.get("userid").toString()));
						if (ps.executeUpdate() <= 0) {
							throw new Exception();
						}

						Hashtable table = new Hashtable();
						table.put("message", "���ղ�");
						m4.setReturnvalue(table);
					} catch (Exception e) {
						Hashtable table = new Hashtable();
						table.put("message", "�ղ�ʧ��");
						m4.setReturnvalue(table);

					} finally {
						conn.close();
					}
					out.writeObject(m4);
					out.flush();
				}
				/**
				 * ��ע��̨���
				 */
				else if (msHandle.getType().equalsIgnoreCase(Constants.CONCERN)) {
					Connection conn = com.example.Stareyou.db.DBManager
							.getDBManager().getConnection();
					MessageHandle m4 = new MessageHandle();// ���ͻ��˻�����Ϣ
					try {
						Concern concern = new Concern();
						concern.setUserid(Integer.parseInt(msHandle.getValue()
								.get("userid").toString()));
						concern.setConcerned_userid(Integer.parseInt(msHandle
								.getValue().get("concerned_userid").toString()));
						new ConcernDAO(conn).add(concern);

						// ����һ�����������Ϣ
						if (news.containsKey(Integer.parseInt(msHandle
								.getValue().get("userid").toString()))) {
							news.put(
									Integer.parseInt(msHandle.getValue()
											.get("userid").toString()),
									news.get(Integer.parseInt(msHandle
											.getValue().get("userid")
											.toString())) + 1);
						} else {
							news.put(
									Integer.parseInt(msHandle.getValue()
											.get("userid").toString()), 1);
						}
						// ��uses��������concern_number����
						PreparedStatement ps = conn
								.prepareStatement("update users set concern_number = concern_number+1 where userid="
										+ Integer.parseInt(msHandle.getValue()
												.get("userid").toString()));
						if (ps.executeUpdate() <= 0) {
							throw new Exception();
						}
						// ��uses��������fans_number����
						PreparedStatement pst = conn
								.prepareStatement("update users set fans_number = fans_number+1 where userid="
										+ Integer.parseInt(msHandle.getValue()
												.get("concerned_userid")
												.toString()));
						if (pst.executeUpdate() <= 0) {
							throw new Exception();
						}

						Hashtable table = new Hashtable();
						table.put("message", "�ѹ�ע");
						m4.setReturnvalue(table);
					} catch (Exception e) {
						e.printStackTrace();
						Hashtable table = new Hashtable();
						table.put("message", "��עʧ��");
						m4.setReturnvalue(table);
					} finally {
						conn.close();
					}
					out.writeObject(m4);
					out.flush();
				}
				/**
				 * ת����̨���
				 */
				else if (msHandle.getType().equalsIgnoreCase(
						Constants.SEND_REDIRECT)) {
					Connection conn = com.example.Stareyou.db.DBManager
							.getDBManager().getConnection();
					MessageHandle m4 = new MessageHandle();// ���ͻ��˻�����Ϣ
					try {
						Send send = new Send();
						send.setUserid(Integer.parseInt(msHandle.getValue()
								.get("userid").toString()));
						send.setUsername(msHandle.getValue().get("username")
								.toString());
						send.setSend_content("#"
								+ msHandle.getValue().get("sendid").toString()
								+ "#"
								+ msHandle.getValue().get("send_content")
										.toString());
						send.setSend_type(Integer.parseInt(msHandle.getValue()
								.get("send_type").toString()));
						send.setSend_icon(msHandle.getValue().get("send_icon")
								.toString());
						if (msHandle.getValue().get("send_location") != null) {
							send.setSend_location(msHandle.getValue()
									.get("send_location").toString());
						}

						new SendDAO(conn).add(send);
						// ����һ�����������Ϣ
						if (news.containsKey(Integer.parseInt(msHandle
								.getValue().get("userid").toString()))) {
							news.put(
									Integer.parseInt(msHandle.getValue()
											.get("userid").toString()),
									news.get(Integer.parseInt(msHandle
											.getValue().get("userid")
											.toString())) + 1);
						} else {
							news.put(
									Integer.parseInt(msHandle.getValue()
											.get("userid").toString()), 1);
						}
						// ��uses��������send_number����
						PreparedStatement ps = conn
								.prepareStatement("update users set send_number = send_number+1 where userid="
										+ Integer.parseInt(msHandle.getValue()
												.get("userid").toString()));
						if (ps.executeUpdate() <= 0) {
							throw new Exception();
						}

						Hashtable table = new Hashtable();
						table.put("message", "��ת��");
						m4.setReturnvalue(table);
					} catch (Exception e) {
						e.printStackTrace();
						Hashtable table = new Hashtable();
						table.put("message", "ת��ʧ��");
						m4.setReturnvalue(table);

					} finally {
						conn.close();
					}
					out.writeObject(m4);
					out.flush();
				}
				/**
				 * ������̨���
				 */
				else if (msHandle.getType().equalsIgnoreCase(Constants.HELP)) {
					Connection conn = com.example.Stareyou.db.DBManager
							.getDBManager().getConnection();
					MessageHandle m4 = new MessageHandle();// ���ͻ��˻�����Ϣ
					try {
						Help help = new Help();
						help.setUserid(Integer.parseInt(msHandle.getValue()
								.get("userid").toString()));
						help.setUsername(msHandle.getValue().get("username")
								.toString());
						help.setHelp_title(msHandle.getValue()
								.get("help_title").toString());
						help.setHelp_content(msHandle.getValue()
								.get("help_content").toString());
						help.setHelp_type(Integer.parseInt(msHandle.getValue()
								.get("help_type").toString()));
						help.setHelp_icon(msHandle.getValue().get("help_icon")
								.toString());
						if (msHandle.getValue().get("help_location") != null) {
							help.setHelp_location(msHandle.getValue()
									.get("help_location").toString());
						}
						new HelpDAO(conn).add(help);
						Hashtable table = new Hashtable();
						table.put("message", "����ɹ�");
						m4.setReturnvalue(table);
					} catch (Exception e) {
						e.printStackTrace();
						Hashtable table = new Hashtable();
						table.put("message", "����ʧ��");
						m4.setReturnvalue(table);
					} finally {
						conn.close();
					}
					out.writeObject(m4);
					out.flush();
				}
				/**
				 * ȥ���ʺ�̨���
				 */
				else if (msHandle.getType().equalsIgnoreCase(Constants.AMAZE)) {
					Connection conn = com.example.Stareyou.db.DBManager
							.getDBManager().getConnection();
					MessageHandle m4 = new MessageHandle();// ���ͻ��˻�����Ϣ
					try {
						Amaze amaze = new Amaze();
						amaze.setHelpid(Integer.parseInt(msHandle.getValue()
								.get("helpid").toString()));
						amaze.setAmaze_userid(Integer.parseInt(msHandle
								.getValue().get("amaze_userid").toString()));
						amaze.setAmaze_username(msHandle.getValue()
								.get("amaze_username").toString());
						amaze.setAmazed_userid(Integer.parseInt(msHandle
								.getValue().get("amazed_userid").toString()));
						amaze.setAmazed_username(msHandle.getValue()
								.get("amazed_username").toString());
						new AmazeDAO(conn).add(amaze);
						Hashtable table = new Hashtable();
						table.put("message", "���˳ɹ�");
						m4.setReturnvalue(table);
					} catch (Exception e) {
						e.printStackTrace();
						Hashtable table = new Hashtable();
						table.put("message", "����ʧ��");
						m4.setReturnvalue(table);
					} finally {
						conn.close();
					}
					out.writeObject(m4);
					out.flush();
				}

				/**
				 * ������̨���
				 */
				else if (msHandle.getType().equalsIgnoreCase(Constants.ORDER)) {
					Connection conn = com.example.Stareyou.db.DBManager
							.getDBManager().getConnection();
					MessageHandle m4 = new MessageHandle();// ���ͻ��˻�����Ϣ
					try {
						Order order = new Order();
						order.setUserid(Integer.parseInt(msHandle.getValue()
								.get("userid").toString()));
						order.setHelped_userid(Integer.parseInt(msHandle
								.getValue().get("helped_userid").toString()));
						order.setHelpid(Integer.parseInt(msHandle.getValue()
								.get("helpid").toString()));
						order.setOrder_title(msHandle.getValue()
								.get("order_title").toString());
						new OrderDAO(conn).add(order);
						// ����helplist��
						PreparedStatement st = conn
								.prepareStatement("insert into helplist(userid,helped_userid) values(?,?)");
						st.setInt(
								1,
								Integer.parseInt(msHandle.getValue()
										.get("userid").toString()));
						st.setInt(
								2,
								Integer.parseInt(msHandle.getValue()
										.get("helped_userid").toString()));
						if (st.executeUpdate() <= 0) {
							throw new Exception();
						}

						// ��uses��������help_number����
						PreparedStatement ps = conn
								.prepareStatement("update users set help_number = help_number+1 where userid="
										+ Integer.parseInt(msHandle.getValue()
												.get("userid").toString()));
						if (ps.executeUpdate() <= 0) {
							throw new Exception();
						}

						Hashtable table = new Hashtable();
						table.put("message", "�����ɶ���");
						m4.setReturnvalue(table);
					} catch (Exception e) {
						e.printStackTrace();
						Hashtable table = new Hashtable();
						table.put("message", "���ɶ���ʧ��");
						m4.setReturnvalue(table);
					} finally {
						conn.close();
					}
					out.writeObject(m4);
					out.flush();
				}
				/**
				 * ����helpid���Ұ�������
				 */
				else if (msHandle.getType().equalsIgnoreCase(
						Constants.FIND_ORDER)) {
					Connection conn = DBManager.getDBManager().getConnection();
					MessageHandle m = new MessageHandle();
					Help help = new Help();
					try {
						ResultSet rs = conn.createStatement().executeQuery(
								"select * from help where helpid="
										+ Integer.parseInt(msHandle.getValue()
												.get("helpid").toString()));
						while (rs.next()) {
							help.setHelp_title(rs.getString("help_title"));
						}
						Hashtable ht = new Hashtable();
						ht.put("help_by", help);
						ht.put("message", "����helpid�ɹ�");
						m.setReturnvalue(ht);
					} catch (Exception e) {
						e.printStackTrace();
						Hashtable t = new Hashtable();
						t.put("message", "����helpidʧ��");
						m.setReturnvalue(t);
					} finally {
						conn.close();
					}
					out.writeObject(m);
					out.flush();

				}

				/**
				 * �����̨���
				 */
				else if (msHandle.getType().equalsIgnoreCase(Constants.CHAT)) {
					Connection conn = com.example.Stareyou.db.DBManager
							.getDBManager().getConnection();
					MessageHandle m4 = new MessageHandle();// ���ͻ��˻�����Ϣ
					try {
						Chat chat = new Chat();
						chat.setUserid(Integer.parseInt(msHandle.getValue()
								.get("userid").toString()));
						chat.setChatted_userid(Integer.parseInt(msHandle
								.getValue().get("chatted_userid").toString()));
						chat.setHelpid(Integer.parseInt(msHandle.getValue()
								.get("helpid").toString()));
						chat.setChat_content(msHandle.getValue()
								.get("chat_content").toString());
						chat.setChat_type(Integer.parseInt(msHandle.getValue()
								.get("chat_type").toString()));
						new ChatDAO(conn).add(chat);

						Hashtable table = new Hashtable();
						table.put("message", "���ͳɹ�");
						m4.setReturnvalue(table);
						// ֪ͨ����������˸����ҷ������ݣ��������ݿ�����������
						MessageHandle m = new MessageHandle();
						Hashtable table2 = new Hashtable();
						table2.put("message", "Inform");
						m.setReturnvalue(table2);
						new Inform(m).start();
					} catch (Exception e) {
						e.printStackTrace();
						Hashtable table = new Hashtable();
						table.put("message", "����������ʧ��,�����µ��");
						m4.setReturnvalue(table);
					} finally {
						conn.close();
					}
					out.writeObject(m4);
					out.flush();
				}
				/**
				 * ���ۺ�̨���
				 */
				else if (msHandle.getType().equalsIgnoreCase(Constants.ASSESS)) {
					Connection conn = com.example.Stareyou.db.DBManager
							.getDBManager().getConnection();
					MessageHandle m4 = new MessageHandle();// ���ͻ��˻�����Ϣ
					try {
						Assess assess = new Assess();
						assess.setUserid(Integer.parseInt(msHandle.getValue()
								.get("userid").toString()));
						assess.setHelpid(Integer.parseInt(msHandle.getValue()
								.get("helpid").toString()));
						ResultSet rsResultSet = conn.createStatement()
								.executeQuery(
										"select * from assess where userid = '"
												+ assess.getUserid() + "'"
												+ "and helpid='"
												+ assess.getHelpid() + "'");
						if (rsResultSet.next() == false) {
							assess.setHelper_number(1);
							assess.setSatisfied(Double.parseDouble(msHandle
									.getValue().get("satisfied").toString()));
							new AssessDAO(conn).add(assess);
						} else {
							rsResultSet.previous();
							while (rsResultSet.next()) {
								assess.setAssessid(rsResultSet
										.getInt("assessid"));
								System.out.println(rsResultSet
										.getInt("helper_number"));
								assess.setHelper_number(rsResultSet
										.getInt("helper_number") + 1);
								System.out.println(assess.getHelper_number());
								assess.setSatisfied((rsResultSet
										.getDouble("satisfied") + Double
										.parseDouble(msHandle.getValue()
												.get("satisfied").toString()))
										/ assess.getHelper_number());
								new AssessDAO(conn).set(assess);
							}
						}
						Hashtable table = new Hashtable();
						table.put("message", "��л����");
						m4.setReturnvalue(table);
					} catch (Exception e) {
						e.printStackTrace();
						Hashtable table = new Hashtable();
						table.put("message", "����ʧ��");
						m4.setReturnvalue(table);
					} finally {
						conn.close();
					}
					out.writeObject(m4);
					out.flush();
				}
				/**
				 * ���������̨���
				 */
				else if (msHandle.getType().equalsIgnoreCase(
						Constants.SUGGESTION)) {
					Connection conn = com.example.Stareyou.db.DBManager
							.getDBManager().getConnection();
					MessageHandle m4 = new MessageHandle();// ���ͻ��˻�����Ϣ
					try {
						Suggestion suggestion = new Suggestion();
						suggestion.setUserid(Integer.parseInt(msHandle
								.getValue().get("userid").toString()));
						suggestion.setSuggestion_content(msHandle.getValue()
								.get("suggestion_content").toString());
						new SuggestionDAO(conn).add(suggestion);
						Hashtable table = new Hashtable();
						table.put("message", "лл���Ľ���");
						m4.setReturnvalue(table);
					} catch (Exception e) {
						Hashtable table = new Hashtable();
						table.put("message", "����ʧ��");
						m4.setReturnvalue(table);
					} finally {
						conn.close();
					}
					out.writeObject(m4);
					out.flush();
				}
				/**
				 * ����û���Ϣ
				 */
				else if (msHandle.getType().equalsIgnoreCase(
						Constants.DOWNLOAD_USER)) {
					Connection conn = DBManager.getDBManager().getConnection();
					MessageHandle m = new MessageHandle();// ���ͻ��˻�����Ϣ
					try {
						ResultSet rSet = conn
								.createStatement()
								.executeQuery(
										"select * from users,register where users.userid=register.userid and users.userid="
												+ Integer.parseInt(msHandle
														.getValue()
														.get("userid")
														.toString()));
						Users users = new Users();
						while (rSet.next()) {
							users.setUserid(rSet.getInt("userid"));
							users.setUsername(rSet.getString("username"));
							users.setSex(rSet.getString("sex"));
							users.setPhone(rSet.getString("phone"));
							users.setDate(rSet.getString("date"));
							users.setCollection_number(rSet
									.getInt("collection_number"));
							users.setConcern_number(rSet
									.getInt("concern_number"));
							users.setFans_number(rSet.getInt("fans_number"));
							users.setSend_number(rSet.getInt("send_number"));
							users.setHelp_number(rSet.getInt("help_number"));
						}
						Hashtable table = new Hashtable();
						table.put("message", "����û���Ϣ");
						table.put("view", users);
						m.setReturnvalue(table);
					} catch (Exception e) {
						e.printStackTrace();
						Hashtable table = new Hashtable();
						table.put("message", "����û���Ϣʧ��");
						m.setReturnvalue(table);
					} finally {
						conn.close();
					}
					out.writeObject(m);
					out.flush();
				}
				/**
				 * ������涯̬ˢ������
				 */
				else if (msHandle.getType().equalsIgnoreCase(
						Constants.DOWNLOAD_DYNAMIC)) {
					Connection conn = com.example.Stareyou.db.DBManager
							.getDBManager().getConnection();
					MessageHandle m3 = new MessageHandle();// ���ͻ��˻�����Ϣ
					try {
						ResultSet rs = conn
								.createStatement()
								.executeQuery(
										"select  s.sendid,s.userid,r.username,s.send_content,s.send_icon,s.send_location,s.send_date from send s left join register r on s.userid=r.userid order by s.send_date desc limit 10");
						Vector<Send> v = new Vector<Send>();// �Ӵ����Ϣ�壬�����������ݡ��������ݡ��ղ���������ע������ת�������Լ���������
						while (rs.next()) {
							Send s = new Send();
							s.setSendid(rs.getInt("sendid"));
							s.setUserid(rs.getInt("userid"));
							s.setUsername(rs.getString("username"));
							s.setSend_icon(rs.getString("send_icon"));
							s.setSend_date(rs.getString("send_date"));
							s.setSend_location(rs.getString("send_location"));
							s.setSend_content(rs.getString("send_content"));
							v.add(s);
							/**
							 * ÿһ��sendid��Ӧ�����������
							 */
							ResultSet rs1 = conn
									.createStatement()
									.executeQuery(
											"select r1.sendid"
													+ ",r1.reviewid,r1.review_content,r1.review_type,r1.review_date,r1.userid,r2.username from review r1 inner join register r2 "
													+ "on r1.userid=r2.userid and"
													+ " r1.sendid="
													+ s.getSendid()
													+ " order by r1.review_date desc");
							while (rs1.next()) {
								Review r = new Review();
								r.setUserid(rs1.getInt("userid"));
								r.setReview_date(rs1.getString("review_date"));
								r.setSendid(rs1.getInt("sendid"));
								r.setReview_content(rs1
										.getString("review_content"));
								r.setReview_type(rs1.getInt("review_type"));
								r.setUsername(rs1.getString("username"));
								r.setReviewid(rs1.getInt("reviewid"));
								s.getRw().add(r);
							}
							/**
							 * ÿ��sendid��Ӧ�����������
							 */
							ResultSet rs2 = conn
									.createStatement()
									.executeQuery(
											"select username from register,praise where register.userid = praise.userid and praise.sendid ="
													+ s.getSendid());
							ResultSet count1 = conn
									.createStatement()
									.executeQuery(
											"select count(*) from register,praise where register.userid = praise.userid and praise.sendid ="
													+ s.getSendid());
							int count_praise = 0;
							while (count1.next()) {
								count_praise = count1.getInt("count(*)");
							}
							while (rs2.next()) {
								Praise praise = new Praise();
								praise.setUsername(rs2.getString("username"));
								praise.setNumber(count_praise);
								s.getPe().add(praise);
							}
							/**
							 * ÿ��sendid��Ӧ����ղ�����
							 */
							ResultSet rs3 = conn
									.createStatement()
									.executeQuery(
											"select username from register,collection where register.userid = collection.userid and collection.sendid ="
													+ s.getSendid());
							ResultSet count2 = conn
									.createStatement()
									.executeQuery(
											"select count(*) from register,collection where register.userid = collection.userid and collection.sendid ="
													+ s.getSendid());
							int count_collection = 0;
							while (count2.next()) {
								count_collection = count2.getInt("count(*)");
							}
							while (rs3.next()) {
								Collection collection = new Collection();
								collection.setUsername(rs3
										.getString("username"));
								collection.setNumber(count_collection);
								s.getCl().add(collection);
							}
							/**
							 * ÿ��sendid��Ӧ�����ע����
							 */
							ResultSet rs4 = conn
									.createStatement()
									.executeQuery(
											"select username from register,concern where register.userid = concern.userid and concern.concerned_userid ="
													+ s.getUserid());
							ResultSet count3 = conn
									.createStatement()
									.executeQuery(
											"select count(*) from register,concern where register.userid = concern.userid and concern.concerned_userid ="
													+ s.getUserid());
							int count_concern = 0;
							while (count3.next()) {
								count_concern = count3.getInt("count(*)");
							}
							while (rs4.next()) {
								Concern concern = new Concern();
								concern.setUsername(rs4.getString("username"));
								concern.setNumber(count_concern);
								s.getCo().add(concern);
							}
							/**
							 * ÿ��sendid��Ӧ���ת������
							 */
							ResultSet rs5 = conn
									.createStatement()
									.executeQuery(
											"select send.username from register,send where register.userid = send.userid and send.send_content like "
													+ "'#"
													+ s.getSendid()
													+ "#%'");
							ResultSet count4 = conn
									.createStatement()
									.executeQuery(
											"select count(*) from register,send where register.userid = send.userid and send.send_content like "
													+ "'#"
													+ s.getSendid()
													+ "#%'");
							int count_sendredirect = 0;
							while (count4.next()) {
								count_sendredirect = count4.getInt("count(*)");
							}
							while (rs5.next()) {
								SendRedirect sendRedirect = new SendRedirect();
								sendRedirect.setUsername(rs5
										.getString("username"));
								sendRedirect.setNumber(count_sendredirect);
								s.getSr().add(sendRedirect);
							}
						}
						Hashtable table = new Hashtable();
						table.put("message", "ˢ�³ɹ�");
						table.put("share", v);
						m3.setReturnvalue(table);
					} catch (Exception e) {
						e.printStackTrace();
						Hashtable table = new Hashtable();
						table.put("message", "ˢ��ʧ��");
						m3.setReturnvalue(table);
					} finally {
						conn.close();
					}
					out.writeObject(m3);
					out.flush();
				}
				/**
				 * ���ȥ����
				 */
				else if (msHandle.getType().equalsIgnoreCase(
						Constants.DOWNLOAD_AMAZE)) {
					Connection conn = DBManager.getDBManager().getConnection();
					MessageHandle messageHandle = new MessageHandle();
					try {
						ResultSet rsResultSet = conn
								.createStatement()
								.executeQuery(
										"select * from amaze where amazeid = (select max(amazeid) from amaze) and amazed_userid = "
												+ Integer.parseInt(msHandle
														.getValue()
														.get("userid")
														.toString()));
						if (rsResultSet.next() == false) {
							Hashtable table = new Hashtable();
							table.put("message", "����������");
							messageHandle.setReturnvalue(table);
						} else {
							rsResultSet.previous();
							Amaze amaze = new Amaze();
							while (rsResultSet.next()) {
								amaze.setAmazeid(rsResultSet.getInt("amazeid"));
								amaze.setHelpid(rsResultSet.getInt("helpid"));
								amaze.setAmaze_userid(rsResultSet
										.getInt("amaze_userid"));
								amaze.setAmaze_username(rsResultSet
										.getString("amaze_username"));
								amaze.setAmazed_userid(rsResultSet
										.getInt("amazed_userid"));
								amaze.setAmazed_username(rsResultSet
										.getString("amazed_username"));
								amaze.setAmaze_date(rsResultSet
										.getString("amaze_date"));
							}
							Hashtable table = new Hashtable();
							table.put("message", "���amaze��Ϣ�ɹ�");
							table.put("amaze", amaze);
							messageHandle.setReturnvalue(table);
						}

					} catch (Exception e) {
						e.printStackTrace();
						Hashtable table = new Hashtable();
						table.put("message", "���amaze��Ϣ��Ϣʧ��");
						messageHandle.setReturnvalue(table);
					} finally {
						conn.close();
					}
					out.writeObject(messageHandle);
					out.flush();
				}

				/**
				 * ���������Ϣ
				 */
				else if (msHandle.getType().equalsIgnoreCase(
						Constants.DOWNLOAD_ORDER)) {
					Connection conn = DBManager.getDBManager().getConnection();
					MessageHandle messageHandle = new MessageHandle();
					try {
						ResultSet rsResultSet = conn
								.createStatement()
								.executeQuery(
										"select * from stareyou_order,help where help.help_title = stareyou_order.order_title and stareyou_order.helped_userid = "
												+ Integer.parseInt(msHandle
														.getValue()
														.get("userid")
														.toString()));
						Vector<Order> v = new Vector<Order>();
						while (rsResultSet.next()) {
							Order order = new Order();
							order.setOrderid(rsResultSet.getInt("orderid"));
							order.setHelpid(rsResultSet.getInt("helpid"));
							order.setUserid(rsResultSet.getInt("userid"));
							order.setHelped_userid(rsResultSet
									.getInt("helped_userid"));
							order.setOrder_title(rsResultSet
									.getString("order_title"));
							order.setOrder_date(rsResultSet
									.getString("order_date"));
							v.add(order);
						}
						Hashtable table = new Hashtable();
						table.put("message", "���������Ϣ�ɹ�");
						table.put("order", v);
						messageHandle.setReturnvalue(table);
					} catch (Exception e) {
						e.printStackTrace();
						Hashtable table = new Hashtable();
						table.put("message", "���������Ϣʧ��");
						messageHandle.setReturnvalue(table);
					} finally {
						conn.close();
					}
					out.writeObject(messageHandle);
					out.flush();
				}
				/**
				 * ���������Ϣ
				 */
				else if (msHandle.getType().equalsIgnoreCase(
						Constants.DOWNLOAD_HELP)) {
					Connection conn = DBManager.getDBManager().getConnection();
					MessageHandle mh = new MessageHandle();
					try {
						ResultSet rSet = conn
								.createStatement()
								.executeQuery(
										"select * from help,register where help.userid = register.userid ");
						Vector<Help> v = new Vector<Help>();
						while (rSet.next()) {
							Help help = new Help();
							help.setHelpid(rSet.getInt("helpid"));
							help.setUserid(rSet.getInt("userid"));
							help.setUsername(rSet.getString("username"));
							help.setHelp_title(rSet.getString("help_title"));
							help.setHelp_content(rSet.getString("help_content"));
							help.setHelp_type(rSet.getInt("help_type"));
							help.setHelp_icon(rSet.getString("help_icon"));
							if (rSet.getString("help_location") != null) {
								help.setHelp_location(rSet
										.getString("help_location"));
							}
							help.setHelp_date(rSet.getString("help_date"));
							v.add(help);
							// ÿ��helpid��Ӧ���������Ϣ
							ResultSet rsResultSet = conn.createStatement()
									.executeQuery(
											"select * from assess where assess.helpid="
													+ help.getHelpid());
							if (rsResultSet.next() == false) {
								Assess assess = new Assess();
								assess.setHelper_number(0);
								assess.setSatisfied(0);
								help.getAssesses().add(assess);
							} else {
								rsResultSet.previous();
								while (rsResultSet.next()) {
									Assess assess = new Assess();
									assess.setHelper_number(rsResultSet
											.getInt("helper_number"));
									assess.setSatisfied(rsResultSet
											.getDouble("satisfied"));
									help.getAssesses().add(assess);
								}
							}
						}
						Hashtable table = new Hashtable();
						table.put("message", "���������Ϣ�ɹ�");
						table.put("help", v);
						mh.setReturnvalue(table);
					} catch (Exception e) {
						e.printStackTrace();
						Hashtable table = new Hashtable();
						table.put("message", "���������Ϣʧ��");
						mh.setReturnvalue(table);
					} finally {
						conn.close();
					}
					out.writeObject(mh);
					out.flush();
				}
				// �������������Ϣ����
				else if (msHandle.getType().equalsIgnoreCase(
						Constants.DOWNLOAD_NEWS)) {
					MessageHandle msg = new MessageHandle();
					Hashtable table = new Hashtable();
					table.put(
							"news",
							news.get(Integer.parseInt(msHandle.getValue()
									.get("userid").toString())));
					table.put("message", "�������������Ϣ����");
					msg.setReturnvalue(table);
					out.writeObject(msg);
					out.flush();
				}
				// ��0���������Ϣ
				else if (msHandle.getType().equalsIgnoreCase(
						Constants.DELETE_NEWS)) {
					MessageHandle msg = new MessageHandle();
					Hashtable table = new Hashtable();
					if (news.containsKey(Integer.parseInt(msHandle.getValue()
							.get("userid").toString()))) {
						news.put(
								Integer.parseInt(msHandle.getValue()
										.get("userid").toString()), 0);
					} else {
						news.put(
								Integer.parseInt(msHandle.getValue()
										.get("userid").toString()), 0);
					}
					table.put("message", 0 + "");
					msg.setReturnvalue(table);
					out.writeObject(msg);
					out.flush();
				}
				// �������������ϸ����
				else if (msHandle.getType().equalsIgnoreCase(
						Constants.DOWNLOAD_NEWS_INFO)) {
					Connection conn = DBManager.getDBManager().getConnection();
					List<Action> list = new ArrayList<Action>();
					MessageHandle ms = new MessageHandle();
					try {
						// ��ȡ������ϸ��Ϣ
						ResultSet rsSet = conn
								.createStatement()
								.executeQuery(
										"select * from register,review,send  where review.userid = register.userid and review.sendid = send.sendid and review.reviewed_userid = "
												+ Integer.parseInt(msHandle
														.getValue()
														.get("userid")
														.toString()));
						while (rsSet.next()) {
							Action a = new Action();
							a.setUsername(rsSet.getString("username"));
							a.setArticle_content(rsSet
									.getString("send_content"));
							a.setReview_content(rsSet
									.getString("review_content"));
							a.setDate(rsSet.getString("review_date"));
							a.setAction("��������");
							list.add(a);
						}
						// ��ȡ������ϸ��Ϣ
						ResultSet rsSet2 = conn
								.createStatement()
								.executeQuery(
										"select * from register,praise,send where praise.userid = register.userid and send.sendid = praise.sendid and praise.praised_userid = "
												+ Integer.parseInt(msHandle
														.getValue()
														.get("userid")
														.toString()));
						while (rsSet2.next()) {
							Action a = new Action();
							a.setUsername(rsSet2.getString("username"));
							a.setArticle_content(rsSet2
									.getString("send_content"));
							a.setDate(rsSet2.getString("praise_date"));
							a.setAction("ŭ������");
							list.add(a);
						}
						// ��ȡ�ղ���ϸ��Ϣ
						ResultSet rsSet3 = conn
								.createStatement()
								.executeQuery(
										"select * from register,collection,send where collection.userid = register.userid and collection.sendid = send.sendid and collection.collectioned_userid = "
												+ Integer.parseInt(msHandle
														.getValue()
														.get("userid")
														.toString()));
						while (rsSet3.next()) {
							Action a = new Action();
							a.setUsername(rsSet3.getString("username"));
							a.setArticle_content(rsSet3
									.getString("send_content"));
							a.setDate(rsSet3.getString("collection_date"));
							a.setAction("�ղ����������");
							list.add(a);
						}
						// ��ȡ��ע��ϸ��Ϣ
						ResultSet rsSet4 = conn
								.createStatement()
								.executeQuery(
										"select * from register,concern where concern.userid = register.userid and concern.concerned_userid = "
												+ Integer.parseInt(msHandle
														.getValue()
														.get("userid")
														.toString()));
						while (rsSet4.next()) {
							Action a = new Action();
							a.setUsername(rsSet4.getString("username"));
							a.setDate(rsSet4.getString("concern_date"));
							a.setAction("��ע����");
							list.add(a);
						}
						// ��ȡת����ϸ��Ϣ
						ResultSet rsSet5 = conn.createStatement().executeQuery(
								"select sendid from send where send.userid = "
										+ Integer.parseInt(msHandle.getValue()
												.get("userid").toString()));
						while (rsSet5.next()) {
							ResultSet rsSet6 = conn
									.createStatement()
									.executeQuery(
											"select * from register,send where register.userid = send.userid and send.send_content like"
													+ "'#"
													+ rsSet5.getInt("sendid")
													+ "#%'");
							while (rsSet6.next()) {
								Action a = new Action();
								a.setUsername(rsSet6.getString("username"));
								a.setArticle_content(rsSet6
										.getString("send_content"));
								a.setDate(rsSet6.getString("send_date"));
								a.setAction("ת�����������");
								list.add(a);
							}
						}
						Hashtable table = new Hashtable();
						table.put("message", "�������������ϸ���ݳɹ�");
						table.put("news_info", list);
						ms.setReturnvalue(table);
					} catch (Exception e) {
						e.printStackTrace();
						Hashtable t = new Hashtable();
						t.put("message", "�������������ϸ����ʧ��");
						ms.setReturnvalue(t);

					} finally {
						conn.close();
					}
					out.writeObject(ms);
					out.flush();
				}
				// �����ҵķ������Ʒ�б�
				else if (msHandle.getType().equalsIgnoreCase(
						Constants.DOWNLOAD_SEND)) {
					Connection conn = DBManager.getDBManager().getConnection();
					MessageHandle m = new MessageHandle();
					Vector<Send> vector = new Vector<Send>();
					try {
						ResultSet rs = conn.createStatement().executeQuery(
								"select * from send where send.userid="
										+ Integer.parseInt(msHandle.getValue()
												.get("userid").toString()));
						while (rs.next()) {
							Send send = new Send();
							send.setSendid(rs.getInt("sendid"));
							send.setUsername(rs.getString("username"));
							send.setSend_content(rs.getString("send_content"));
							send.setSend_type(rs.getInt("send_type"));
							send.setSend_icon(rs.getString("send_icon"));
							send.setSend_date(rs.getString("send_date"));
							if (rs.getString("send_location") != null) {
								send.setSend_location(rs
										.getString("send_location"));
							}
							vector.add(send);
						}
						Hashtable ht = new Hashtable();
						ht.put("send", vector);
						ht.put("message", "�����ҵķ������Ʒ�б�ɹ�");
						m.setReturnvalue(ht);
					} catch (Exception e) {
						e.printStackTrace();
						Hashtable t = new Hashtable();
						t.put("message", "�����ҵķ������Ʒ�б�ʧ��!");
						m.setReturnvalue(t);
					} finally {
						conn.close();
					}
					out.writeObject(m);
					out.flush();
				}
				// �����ҵ��ղ���Ʒ�б�
				else if (msHandle.getType().equalsIgnoreCase(
						Constants.DOWNLOAD_COLLECTION)) {
					Connection conn = DBManager.getDBManager().getConnection();
					MessageHandle m = new MessageHandle();
					Vector<Collection> vector = new Vector<Collection>();
					try {
						ResultSet rs = conn
								.createStatement()
								.executeQuery(
										"select * from collection,send where collection.sendid = send.sendid and collection.userid ="
												+ Integer.parseInt(msHandle
														.getValue()
														.get("userid")
														.toString()));
						while (rs.next()) {
							Collection c = new Collection();
							c.setCollectionid(rs.getInt("collectionid"));
							c.setUsername(rs.getString("username"));
							c.setSend_content(rs.getString("send_content"));
							c.setIcon(rs.getString("send_icon"));
							c.setSend_type(rs.getInt("send_type"));
							c.setCollection_date(rs
									.getString("collection_date"));
							vector.add(c);
						}
						Hashtable ht = new Hashtable();
						ht.put("collection", vector);
						ht.put("message", "�����ҵ��ղ���Ʒ�б�ɹ�");
						m.setReturnvalue(ht);
					} catch (Exception e) {
						e.printStackTrace();
						Hashtable t = new Hashtable();
						t.put("message", "�����ҵ��ղ���Ʒ�б�ʧ��!");
						m.setReturnvalue(t);
					} finally {
						conn.close();
					}
					out.writeObject(m);
					out.flush();
				}
				// �����ҵĹ�ע�б�(����QQ)
				else if (msHandle.getType().equalsIgnoreCase(
						Constants.DOWNLOAD_CONCERN)) {
					Connection conn = DBManager.getDBManager().getConnection();
					MessageHandle msg = new MessageHandle();
					Vector<Concern> vector = new Vector<Concern>();
					try {
						ResultSet rs = conn
								.createStatement()
								.executeQuery(
										"select * from register,concern where register.userid = concern.concerned_userid and concern.userid="
												+ Integer.parseInt(msHandle
														.getValue()
														.get("userid")
														.toString()));
						while (rs.next()) {
							Concern concern = new Concern();
							concern.setConcernid(rs.getInt("concernid"));
							concern.setUserid(rs.getInt("userid"));
							concern.setUsername(rs.getString("username"));
							vector.add(concern);
						}
						Hashtable table = new Hashtable();
						table.put("concern_list", vector);
						table.put("message", "�����ҵĹ�ע�б�ɹ�");
						msg.setReturnvalue(table);
					} catch (Exception e) {
						e.printStackTrace();
						Hashtable table = new Hashtable();
						table.put("message", "�����ҵĹ�ע�б�ʧ��");
						msg.setReturnvalue(table);
					} finally {
						conn.close();
					}
					out.writeObject(msg);
					out.flush();
				}
				// �����ҵķ�˿�б�(����QQ)
				else if (msHandle.getType().equalsIgnoreCase(
						Constants.DOWNLOAD_FANS)) {
					Connection conn = DBManager.getDBManager().getConnection();
					MessageHandle msg = new MessageHandle();
					Vector<Concern> vector = new Vector<Concern>();
					try {
						ResultSet rs = conn
								.createStatement()
								.executeQuery(
										"select * from register,concern where register.userid = concern.userid and concern.concerned_userid="
												+ Integer.parseInt(msHandle
														.getValue()
														.get("userid")
														.toString()));
						while (rs.next()) {
							Concern concern = new Concern();
							concern.setConcernid(rs.getInt("concernid"));
							concern.setUserid(rs.getInt("userid"));
							concern.setUsername(rs.getString("username"));
							vector.add(concern);
						}
						Hashtable table = new Hashtable();
						table.put("concerned_list", vector);
						table.put("message", "�����ҵķ�˿�б�ɹ�");
						msg.setReturnvalue(table);
					} catch (Exception e) {
						e.printStackTrace();
						Hashtable table = new Hashtable();
						table.put("message", "�����ҵķ�˿�б�ʧ��");
						msg.setReturnvalue(table);
					} finally {
						conn.close();
					}
					out.writeObject(msg);
					out.flush();

				}
				// �����Ұ��������˵��б�
				else if (msHandle.getType().equalsIgnoreCase(
						Constants.DOWNLOAD_HELPER)) {
					Connection conn = DBManager.getDBManager().getConnection();
					MessageHandle msg = new MessageHandle();
					Vector<Order> vector = new Vector<Order>();
					try {
						ResultSet rs = conn
								.createStatement()
								.executeQuery(
										"select * from register,helplist where register.userid = helplist.helped_userid and helplist.userid="
												+ Integer.parseInt(msHandle
														.getValue()
														.get("userid")
														.toString()));
						while (rs.next()) {
							Order order = new Order();
							order.setUserid(rs.getInt("userid"));
							order.setUsername(rs.getString("username"));
							vector.add(order);
						}
						Hashtable table = new Hashtable();
						table.put("helper_list", vector);
						table.put("message", "�����Ұ��������˵��б�ɹ�");
						msg.setReturnvalue(table);
					} catch (Exception e) {
						e.printStackTrace();
						Hashtable table = new Hashtable();
						table.put("message", "�����Ұ��������˵��б�ʧ��");
						msg.setReturnvalue(table);
					} finally {
						conn.close();
					}
					out.writeObject(msg);
					out.flush();
				}
				// ���ذ������ҵ��˵��б�
				else if (msHandle.getType().equalsIgnoreCase(
						Constants.DOWNLOAD_HELPED)) {
					Connection conn = DBManager.getDBManager().getConnection();
					MessageHandle msg = new MessageHandle();
					Vector<Order> vector = new Vector<Order>();
					try {
						ResultSet rs = conn
								.createStatement()
								.executeQuery(
										"select * from register,helplist where register.userid = helplist.userid and helplist.helped_userid="
												+ Integer.parseInt(msHandle
														.getValue()
														.get("userid")
														.toString()));
						while (rs.next()) {
							Order order = new Order();
							order.setUserid(rs.getInt("userid"));
							order.setUsername(rs.getString("username"));
							vector.add(order);
						}
						Hashtable table = new Hashtable();
						table.put("helped_list", vector);
						table.put("message", "���ذ������ҵ��˵��б�ɹ�");
						msg.setReturnvalue(table);
					} catch (Exception e) {
						e.printStackTrace();
						Hashtable table = new Hashtable();
						table.put("message", "���ذ������ҵ��˵��б�ʧ��");
						msg.setReturnvalue(table);
					} finally {
						conn.close();
					}
					out.writeObject(msg);
					out.flush();
				}
				// ����chat�б�����(QQ����)
				else if (msHandle.getType().equalsIgnoreCase(
						Constants.DOWNLOAD_CHAT)) {
					Connection conn = DBManager.getDBManager().getConnection();
					MessageHandle msg = new MessageHandle();
					Vector<Chat> vector = new Vector<Chat>();
					try {
						ResultSet rs = conn.createStatement().executeQuery(
								"SELECT * FROM chat where (userid= '"
										+ Integer.parseInt(msHandle.getValue()
												.get("userid").toString())
										+ "'"
										+ " AND chatted_userid = '"
										+ Integer.parseInt(msHandle.getValue()
												.get("chatted_userid")
												.toString())
										+ "')"
										+ " OR (userid= '"
										+ Integer.parseInt(msHandle.getValue()
												.get("chatted_userid")
												.toString())
										+ "' AND chatted_userid = '"
										+ Integer.parseInt(msHandle.getValue()
												.get("userid").toString())
										+ "')");
						while (rs.next()) {
							Chat chat = new Chat();
							chat.setChat_content(rs.getString("chat_content"));
							chat.setChat_type(rs.getInt("chat_type"));
							chat.setChat_date(rs.getString("chat_date"));
							vector.add(chat);
						}
						Hashtable table = new Hashtable();
						table.put("download_chat", vector);
						table.put("message", "����chat�б����ݳɹ�");
						msg.setReturnvalue(table);
					} catch (Exception e) {
						e.printStackTrace();
						Hashtable table = new Hashtable();
						table.put("message", "����chat�б�����ʧ��");
					} finally {
						conn.close();
					}
					out.writeObject(msg);
					out.flush();
				}
				// ����liaotian_chat�б�����
				else if (msHandle.getType().equalsIgnoreCase(
						Constants.DOWNLOAD_LIAOTIAN_CHAT)) {
					Connection conn = DBManager.getDBManager().getConnection();
					MessageHandle msg = new MessageHandle();
					Vector<Chat> vector = new Vector<Chat>();
					try {
						ResultSet rs = conn
								.createStatement()
								.executeQuery(
										"select * from chat as c where not exists(select 1 from chat where c.userid = userid AND c.chatted_userid = chatted_userid  AND c.chat_date < chat_date) AND (userid = "
												+ "'"
												+ Integer.parseInt(msHandle
														.getValue()
														.get("userid")
														.toString())
												+ "'"
												+ "OR chatted_userid = '"
												+ Integer.parseInt(msHandle
														.getValue()
														.get("userid")
														.toString())
												+ "'"
												+ ")"
												+ " AND (not exists(select 1 from chat where c.userid = chatted_userid AND c.chatted_userid = userid  AND c.chat_date < chat_date))");
						while (rs.next()) {
							Chat chat = new Chat();
							chat.setChatid(rs.getInt("chatid"));
							chat.setUserid(rs.getInt("userid"));
							chat.setChatted_userid(rs.getInt("chatted_userid"));
							chat.setChat_content(rs.getString("chat_content"));
							chat.setChat_date(rs.getString("chat_date"));
							vector.add(chat);
						}
						Hashtable table = new Hashtable();
						table.put("download_liaotian_chat", vector);
						table.put("message", "����liaotian_chat�б����ݳɹ�");
						msg.setReturnvalue(table);
					} catch (Exception e) {
						e.printStackTrace();
						Hashtable table = new Hashtable();
						table.put("message", "����liaotian_chat�б�����ʧ��");
					} finally {
						conn.close();
					}
					out.writeObject(msg);
					out.flush();
				}
				// ����chat_id��������
				else if (msHandle.getType().equalsIgnoreCase(
						Constants.DOWNLOAD_CHAT_BY_ID)) {
					Connection conn = DBManager.getDBManager().getConnection();
					MessageHandle msg = new MessageHandle();
					Chat c = new Chat();
					try {
						ResultSet rs = conn.createStatement().executeQuery(
								"select * from chat where chatid = "
										+ Integer.parseInt(msHandle.getValue()
												.get("id").toString()));
						while (rs.next()) {
							c.setUserid(rs.getInt("userid"));
							c.setChatted_userid(rs.getInt("chatted_userid"));
						}
						Hashtable table = new Hashtable();
						table.put("download_chat_by_id", c);
						table.put("message", "���سɹ�");
						msg.setReturnvalue(table);
					} catch (Exception e) {
						e.printStackTrace();
						Hashtable table = new Hashtable();
						table.put("message", "����liaotian_chat�б�����ʧ��");
					} finally {
						conn.close();
					}
					out.writeObject(msg);
					out.flush();
				}

				// ɾ���ҵķ������Ʒ
				else if (msHandle.getType().equalsIgnoreCase(
						Constants.DELETE_SEND)) {
					Connection conn = DBManager.getDBManager().getConnection();
					MessageHandle msg = new MessageHandle();
					Hashtable table = new Hashtable();
					conn.prepareStatement("SET FOREIGN_KEY_CHECKS=0").execute();
					conn.prepareStatement(
							"delete from send where sendid="
									+ Integer.parseInt(msHandle.getValue()
											.get("sendid").toString()))
							.execute();
					conn.prepareStatement("SET FOREIGN_KEY_CHECKS=1").execute();
					table.put("message", "ɾ���ҵķ���ɹ�");
					msg.setReturnvalue(table);
					out.writeObject(msg);
					out.flush();
				}
				// ɾ���ҵ��ղص���Ʒ
				else if (msHandle.getType().equalsIgnoreCase(
						Constants.DELETE_COLLECTION)) {
					Connection conn = DBManager.getDBManager().getConnection();
					MessageHandle msg = new MessageHandle();
					Hashtable table = new Hashtable();
					conn.prepareStatement("SET FOREIGN_KEY_CHECKS=0").execute();
					conn.prepareStatement(
							"delete from collection where collectionid="
									+ Integer.parseInt(msHandle.getValue()
											.get("collectionid").toString()))
							.execute();
					conn.prepareStatement("SET FOREIGN_KEY_CHECKS=1").execute();
					table.put("message", "ɾ���ҵ��ղسɹ�");
					msg.setReturnvalue(table);
					out.writeObject(msg);
					out.flush();
				}
				// ȡ����ע
				else if (msHandle.getType().equalsIgnoreCase(
						Constants.DELETE_CONCERN)) {
					Connection conn = DBManager.getDBManager().getConnection();
					MessageHandle msg = new MessageHandle();
					Hashtable table = new Hashtable();
					conn.prepareStatement("SET FOREIGN_KEY_CHECKS=0").execute();
					conn.prepareStatement(
							"delete from concern where concernid = "
									+ Integer.parseInt(msHandle.getValue()
											.get("concernid").toString()))
							.execute();
					conn.prepareStatement("SET FOREIGN_KEY_CHECKS=1").execute();
					table.put("message", "ȡ����ע�ɹ�");
					msg.setReturnvalue(table);
					out.writeObject(msg);
					out.flush();
				}
				// ɾ��amaze
				else if (msHandle.getType().equalsIgnoreCase(
						Constants.DELETE_AMAZE)) {
					Connection conn = DBManager.getDBManager().getConnection();
					MessageHandle msg = new MessageHandle();
					Hashtable table = new Hashtable();
					conn.prepareStatement("SET FOREIGN_KEY_CHECKS=0").execute();
					conn.prepareStatement(
							"delete from amaze where amazeid = "
									+ Integer.parseInt(msHandle.getValue()
											.get("amazeid").toString()))
							.execute();
					conn.prepareStatement("SET FOREIGN_KEY_CHECKS=1").execute();
					table.put("message", "ɾ��amaze�ɹ�");
					msg.setReturnvalue(table);
					out.writeObject(msg);
					out.flush();
				}
				// ɾ������
				else if (msHandle.getType().equalsIgnoreCase(
						Constants.DELETE_ORDER)) {
					Connection conn = DBManager.getDBManager().getConnection();
					MessageHandle msg = new MessageHandle();
					Hashtable table = new Hashtable();
					conn.prepareStatement("SET FOREIGN_KEY_CHECKS=0").execute();
					conn.prepareStatement(
							"delete from stareyou_order where stareyou_order.orderid="
									+ Integer.parseInt(msHandle.getValue()
											.get("orderid").toString()))
							.execute();
					conn.prepareStatement("SET FOREIGN_KEY_CHECKS=1").execute();
					table.put("message", "ɾ�������ɹ�");
					msg.setReturnvalue(table);
					out.writeObject(msg);
					out.flush();
				}

				// ɾ������
				else if (msHandle.getType().equalsIgnoreCase(
						Constants.DELETE_HELP)) {
					Connection conn = DBManager.getDBManager().getConnection();
					MessageHandle msg = new MessageHandle();
					Hashtable table = new Hashtable();
					conn.prepareStatement("SET FOREIGN_KEY_CHECKS=0").execute();
					conn.prepareStatement(
							"delete from help where helpid="
									+ Integer.parseInt(msHandle.getValue()
											.get("helpid").toString()))
							.execute();
					conn.prepareStatement("SET FOREIGN_KEY_CHECKS=1").execute();
					table.put("message", "ɾ�������ɹ�");
					msg.setReturnvalue(table);
					out.writeObject(msg);
					out.flush();
				}
				// ��������
				else {
					Connection conn = DBManager.getDBManager().getConnection();
					MessageHandle m = new MessageHandle();// ���ͻ��˻�����Ϣ
					try {
						ResultSet rSet = conn.createStatement().executeQuery(
								"select password from users where users.userid="
										+ Integer.parseInt(msHandle.getValue()
												.get("userid").toString()));
						String password = rSet.getString("password");
						Hashtable t = new Hashtable();
						t.put("message", "���ҳɹ�");
						t.put("password", password);
						m.setReturnvalue(t);
					} catch (Exception e) {
						e.printStackTrace();
						Hashtable table = new Hashtable();
						table.put("message", "����ʧ��,����������");
						m.setReturnvalue(table);
					} finally {
						conn.close();
					}
					out.writeObject(m);
					out.flush();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				socket.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}

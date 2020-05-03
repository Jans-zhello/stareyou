package com.example.Stareyou.server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import android.util.Log;

import com.example.Stareyou.model.Action;
import com.example.Stareyou.model.Amaze;
import com.example.Stareyou.model.Chat;
import com.example.Stareyou.model.Collection;
import com.example.Stareyou.model.Concern;
import com.example.Stareyou.model.Help;
import com.example.Stareyou.model.Order;
import com.example.Stareyou.model.Send;
import com.example.Stareyou.model.Users;
import com.example.Stareyou.util.Constants;
import com.example.Stareyou.util.Tools;

/**
 * 对接server端MainService
 */

public class MainClientService {
	private static ObjectInputStream oin = null;
	private static ObjectOutputStream oout = null;

	// 连接server端 端口、ip地址
	private static void conn() {
		try {
			Socket socket = new Socket(Tools.IP, Tools.PORT_1);
			oout = new ObjectOutputStream(socket.getOutputStream());
			oin = new ObjectInputStream(socket.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 对接server端注册程序
	public static String register(String username, String password,
			String phone, String sex) {
		try {
			conn();
			// 向server端发送注册内容
			MessageHandle m = new MessageHandle();
			Hashtable ht = new Hashtable();
			ht.put("username", username);
			ht.put("password", password);
			ht.put("phone", phone);
			ht.put("sex", sex);
			m.setValue(ht);
			m.setType(Constants.REGISTER);
			oout.writeObject(m);
			oout.flush();
			// 从server端读取反馈
			MessageHandle m2 = (MessageHandle) oin.readObject();
			String returnmessage = m2.getReturnvalue().get("message")
					.toString();
			String returnuserid = null;
			if (m2.getReturnvalue().get("userid") != null) {
				returnuserid = m2.getReturnvalue().get("userid").toString();
			}
			if (!returnmessage.equals("注册成功")) {
				oout.close();
				oin.close();
			}
			return returnmessage + "*" + returnuserid;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 对接Serve端忘记密码
	public static String findPassword(String userid) {
		try {
			conn();
			// 向Server端发送登录内容
			MessageHandle m = new MessageHandle();
			Hashtable ht = new Hashtable();
			ht.put("userid", userid);
			m.setValue(ht);
			m.setType(Constants.FIND_PASSWORD);
			oout.writeObject(m);
			oout.flush();
			// 从server端读取反馈
			MessageHandle m2 = (MessageHandle) oin.readObject();
			String message = m2.getReturnvalue().get("message").toString();
			String password = m2.getReturnvalue().get("password").toString();
			if (!message.equals("查找成功")) {
				oout.close();
				oin.close();
			}
			return message + "*" + password;
		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;

	}

	// 对接server端登录程序
	public static String login(String userid, String password) {
		try {
			conn();
			// 向Server端发送登录内容
			MessageHandle m = new MessageHandle();
			Hashtable ht = new Hashtable();
			ht.put("userid", userid);
			ht.put("password", password);
			m.setValue(ht);
			m.setType(Constants.LOGIN);
			oout.writeObject(m);
			oout.flush();
			// 从server端读取反馈
			MessageHandle m2 = (MessageHandle) oin.readObject();
			String message = m2.getReturnvalue().get("message").toString();
			if (!message.equals("登录成功")) {
				oout.close();
				oin.close();
			}
			return message;
		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;
	}

	// 对接server端发送程序
	public static String send(String userid, String username,
			String send_content, Integer send_type, String send_icon,
			String send_location) {
		try {
			conn();
			// 向server端发送发送内容
			MessageHandle m = new MessageHandle();
			Hashtable ht = new Hashtable();
			ht.put("userid", userid);
			ht.put("username", username);
			ht.put("send_content", send_content);
			ht.put("send_type", send_type);
			ht.put("send_icon", send_icon);
			if (send_location != null) {
				ht.put("send_location", send_location);
			}
			m.setValue(ht);
			m.setType(Constants.SEND);
			oout.writeObject(m);
			oout.flush();
			// 从server端读取反馈
			MessageHandle m2 = (MessageHandle) oin.readObject();
			String message = m2.getReturnvalue().get("message").toString();
			if (!message.equals("分享成功")) {
				oout.close();
				oin.close();
			}
			return message;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 对接server端评论程序
	public static String review(String sendid, String userid,
			String reviewed_userid, String review_content, Integer review_type) {
		try {
			conn();
			// 向server端发送评论内容
			MessageHandle m = new MessageHandle();
			Hashtable ht = new Hashtable();
			ht.put("sendid", sendid);
			ht.put("userid", userid);
			ht.put("reviewed_userid", reviewed_userid);
			ht.put("review_content", review_content);
			ht.put("review_type", review_type);
			m.setValue(ht);
			m.setType(Constants.REVIEW);
			oout.writeObject(m);
			oout.flush();
			// 从server端读取反馈
			MessageHandle m2 = (MessageHandle) oin.readObject();
			String message = m2.getReturnvalue().get("message").toString();
			if (!message.equals("评论成功")) {
				oout.close();
				oin.close();
			}
			return message;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 对接server端点赞程序
	public static String praise(String userid, String praised_userid,
			String sendid) {
		try {
			conn();
			// 向server端发送点赞内容
			MessageHandle m = new MessageHandle();
			Hashtable ht = new Hashtable();
			ht.put("userid", userid);
			ht.put("praised_userid", praised_userid);
			ht.put("sendid", sendid);
			m.setValue(ht);
			m.setType(Constants.PRAISE);
			oout.writeObject(m);
			oout.flush();
			// 从server端读取反馈
			MessageHandle m2 = (MessageHandle) oin.readObject();
			String message = m2.getReturnvalue().get("message").toString();
			if (!message.equals("已赞")) {
				oout.close();
				oin.close();
			}
			return message;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 对接server端收藏程序
	public static String collection(String userid, String collectioned_userid,
			String sendid) {
		try {
			conn();
			// 向server端发送收藏内容
			MessageHandle m = new MessageHandle();
			Hashtable ht = new Hashtable();
			ht.put("userid", userid);
			ht.put("collectioned_userid", collectioned_userid);
			ht.put("sendid", sendid);
			m.setValue(ht);
			m.setType(Constants.COLLECTION);
			oout.writeObject(m);
			oout.flush();
			// 从server端读取反馈
			MessageHandle m2 = (MessageHandle) oin.readObject();
			String message = m2.getReturnvalue().get("message").toString();
			if (!message.equals("已收藏")) {
				oout.close();
				oin.close();
			}
			return message;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 对接server端关注程序
	public static String concern(String userid, String concerned_userid) {
		try {
			conn();
			// 向server端发送关注内容
			MessageHandle m = new MessageHandle();
			Hashtable ht = new Hashtable();
			ht.put("userid", userid);
			ht.put("concerned_userid", concerned_userid);
			m.setValue(ht);
			m.setType(Constants.CONCERN);
			oout.writeObject(m);
			oout.flush();
			// 从server端读取反馈
			MessageHandle m2 = (MessageHandle) oin.readObject();
			String message = m2.getReturnvalue().get("message").toString();
			if (!message.equals("已关注")) {
				oout.close();
				oin.close();
			}
			return message;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 对接server端转发程序
	public static String redirect(String userid, String username,
			String sendid, String send_content, Integer send_type,
			String send_icon, String send_location) {
		try {
			conn();
			// 向server端发送转发内容
			MessageHandle m = new MessageHandle();
			Hashtable ht = new Hashtable();
			ht.put("userid", userid);
			ht.put("username", username);
			ht.put("sendid", sendid);
			ht.put("send_content", send_content);
			ht.put("send_type", send_type);
			ht.put("send_icon", send_icon);
			if (send_location != null) {
				ht.put("send_location", send_location);
			}
			m.setValue(ht);
			m.setType(Constants.SEND_REDIRECT);
			oout.writeObject(m);
			oout.flush();
			// 从server端读取反馈
			MessageHandle m2 = (MessageHandle) oin.readObject();
			String message = m2.getReturnvalue().get("message").toString();
			if (!message.equals("已转发")) {
				oout.close();
				oin.close();
			}
			return message;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 对接server端帮助程序
	public static String help(String userid, String username,
			String help_title, String help_content, Integer help_type,
			String help_icon, String help_location) {
		try {
			conn();
			// 向server端发送帮助内容
			MessageHandle m = new MessageHandle();
			Hashtable ht = new Hashtable();
			ht.put("userid", userid);
			ht.put("username", username);
			ht.put("help_title", help_title);
			ht.put("help_content", help_content);
			ht.put("help_type", help_type);
			ht.put("help_icon", help_icon);
			if (help_location != null) {
				ht.put("help_location", help_location);
			}
			m.setValue(ht);
			m.setType(Constants.HELP);
			oout.writeObject(m);
			oout.flush();
			// 从server端读取反馈
			MessageHandle m2 = (MessageHandle) oin.readObject();
			String message = m2.getReturnvalue().get("message").toString();
			if (!message.equals("发表成功")) {
				oout.close();
				oin.close();
			}
			return message;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 对接server端amaze程序
	public static String amaze(String helpid, String amaze_userid,
			String amaze_username, String amazed_userid, String amazed_username) {
		try {
			conn();
			// 向server端发送amaze内容
			MessageHandle m = new MessageHandle();
			Hashtable ht = new Hashtable();
			ht.put("helpid", helpid);
			ht.put("amaze_userid", amaze_userid);
			ht.put("amaze_username", amaze_username);
			ht.put("amazed_userid", amazed_userid);
			ht.put("amazed_username", amazed_username);
			m.setValue(ht);
			m.setType(Constants.AMAZE);
			oout.writeObject(m);
			oout.flush();
			// 从server端读取反馈
			MessageHandle m2 = (MessageHandle) oin.readObject();
			String message = m2.getReturnvalue().get("message").toString();
			if (!message.equals("聊人成功")) {
				oout.close();
				oin.close();
			}
			return message;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 对接server端订单程序
	public static String order(String userid, String helped_userid,
			String helpid, String order_title) {
		try {
			conn();
			// 向server端发送订单内容
			MessageHandle m = new MessageHandle();
			Hashtable ht = new Hashtable();
			ht.put("userid", userid);
			ht.put("helped_userid", helped_userid);
			ht.put("helpid", helpid);
			Log.i("title", order_title);
			ht.put("order_title", order_title);
			m.setValue(ht);
			m.setType(Constants.ORDER);
			oout.writeObject(m);
			oout.flush();
			// 从server端读取反馈
			MessageHandle m2 = (MessageHandle) oin.readObject();
			String message = m2.getReturnvalue().get("message").toString();
			if (!message.equals("已生成订单")) {
				oout.close();
				oin.close();
			}
			return message;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 对接server端根据helpid查找帮助程序
	public static Help find_order(String helpid) {
		try {
			conn();
			// 向server端发送订单内容
			MessageHandle m = new MessageHandle();
			Hashtable ht = new Hashtable();
			ht.put("helpid", helpid);
			m.setValue(ht);
			m.setType(Constants.FIND_ORDER);
			oout.writeObject(m);
			oout.flush();
			// 从server端读取反馈
			MessageHandle m2 = (MessageHandle) oin.readObject();
			String message = m2.getReturnvalue().get("message").toString();
			if (!message.equals("查找helpid成功")) {
				oout.close();
				oin.close();
			}
			return (Help) m2.getReturnvalue().get("help_by");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 对接server端聊天程序
	public static void chat(final String userid, final String chatted_userid,
			final String helpid, final String chat_content,
			final Integer chat_type) {
		new Thread() {
			public void run() {
				synchronized (MainClientService.class) {
					try {
						Log.i("sare", "跑到这了");
						conn();
						// 向server端发送聊天内容
						MessageHandle m = new MessageHandle();
						Hashtable ht = new Hashtable();
						ht.put("userid", userid);
						ht.put("chatted_userid", chatted_userid);
						ht.put("helpid", helpid);
						ht.put("chat_content", chat_content);
						ht.put("chat_type", chat_type);
						m.setValue(ht);
						m.setType(Constants.CHAT);
						oout.writeObject(m);
						oout.flush();
						// 从server端读取反馈
						MessageHandle m2 = (MessageHandle) oin.readObject();
						String message = m2.getReturnvalue().get("message")
								.toString();
						if (!message.equals("发送成功")) {
							oout.close();
							oin.close();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			};
		}.start();

	}

	// 下载chat聊天内容
	public static Vector<Chat> chatData() {
		conn();
		MessageHandle msg = new MessageHandle();
		Hashtable ht = new Hashtable();
		ht.put("userid", Tools.userid);
		ht.put("chatted_userid", Tools.chatted_userid);
		msg.setValue(ht);
		msg.setType(Constants.DOWNLOAD_CHAT);
		synchronized (MainClientService.class) {
			try {
				oout.writeObject(msg);
				oout.flush();
				msg = (MessageHandle) oin.readObject();
				System.out.println("msgGetValue:"
						+ msg.getReturnvalue().get("message").toString());
				if (msg.getReturnvalue().get("message").toString()
						.equals("下载chat列表内容成功")) {
					return (Vector<Chat>) msg.getReturnvalue().get(
							"download_chat");
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return null;
	}

	// 根据聊天ID获取内容
	public static Chat viewchatbyid(String id) {
		conn();
		MessageHandle msg = new MessageHandle();
		Hashtable ht = new Hashtable();
		ht.put("id", id);
		msg.setValue(ht);
		msg.setType(Constants.DOWNLOAD_CHAT_BY_ID);
		synchronized (MainClientService.class) {
			try {
				oout.writeObject(msg);
				oout.flush();
				msg = (MessageHandle) oin.readObject();
				System.out.println("msgGetValue:"
						+ msg.getReturnvalue().get("message").toString());
				if (msg.getReturnvalue().get("message").toString()
						.equals("下载成功")) {
					return (Chat) msg.getReturnvalue().get(
							"download_chat_by_id");
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return null;
	}

	// 下载聊天窗口内容
	public static Vector<Chat> chatWindowData(String userid) {
		conn();
		MessageHandle msg = new MessageHandle();
		Hashtable ht = new Hashtable();
		ht.put("userid", userid);
		msg.setValue(ht);
		msg.setType(Constants.DOWNLOAD_LIAOTIAN_CHAT);
		synchronized (MainClientService.class) {
			try {
				oout.writeObject(msg);
				oout.flush();
				msg = (MessageHandle) oin.readObject();
				System.out.println("msgGetValue:"
						+ msg.getReturnvalue().get("message").toString());
				if (msg.getReturnvalue().get("message").toString()
						.equals("下载liaotian_chat列表内容成功")) {
					return (Vector<Chat>) msg.getReturnvalue().get(
							"download_liaotian_chat");
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return null;
	}

	// 对接server端评价程序
	public static String assess(String userid, String helpid, Double satisfied) {
		try {
			conn();
			// 向server端发送订单内容
			MessageHandle m = new MessageHandle();
			Hashtable ht = new Hashtable();
			ht.put("userid", userid);
			ht.put("helpid", helpid);
			ht.put("satisfied", satisfied);
			m.setValue(ht);
			m.setType(Constants.ASSESS);
			oout.writeObject(m);
			oout.flush();
			// 从server端读取反馈
			MessageHandle m2 = (MessageHandle) oin.readObject();
			String message = m2.getReturnvalue().get("message").toString();
			if (!message.equals("感谢评价")) {
				oout.close();
				oin.close();
			}
			return message;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 对接server端建议程序
	public static String suggestion(String userid, String suggestion_content) {
		try {
			conn();
			// 向server端发送订单内容
			MessageHandle m = new MessageHandle();
			Hashtable ht = new Hashtable();
			ht.put("userid", userid);
			ht.put("suggestion_content", suggestion_content);
			m.setValue(ht);
			m.setType(Constants.SUGGESTION);
			oout.writeObject(m);
			oout.flush();
			// 从server端读取反馈
			MessageHandle m2 = (MessageHandle) oin.readObject();
			String message = m2.getReturnvalue().get("message").toString();
			if (!message.equals("谢谢您的建议")) {
				oout.close();
				oin.close();
			}
			return message;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 对接server端浏览用户信息
	public static Users viewUsers(String userid) {
		conn();
		MessageHandle msg = new MessageHandle();
		Hashtable ht = new Hashtable();
		ht.put("userid", userid);
		msg.setValue(ht);
		msg.setType(Constants.DOWNLOAD_USER);
		synchronized (MainClientService.class) {
			try {
				oout.writeObject(msg);
				oout.flush();
				msg = (MessageHandle) oin.readObject();
				if (msg.getReturnvalue().get("message").toString()
						.equals("浏览用户信息")) {
					return (Users) msg.getReturnvalue().get("view");
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return null;
	}

	// 对接server端获取分享内容
	public static Vector<Send> getShare() {
		conn();
		MessageHandle msg = new MessageHandle();
		msg.setType(Constants.DOWNLOAD_DYNAMIC);
		synchronized (MainClientService.class) {
			try {
				oout.writeObject(msg);
				oout.flush();
				msg = (MessageHandle) oin.readObject();
				System.out.println("msgGetValue:"
						+ msg.getReturnvalue().get("message").toString());
				if (msg.getReturnvalue().get("message").toString()
						.equals("刷新成功")) {
					return (Vector<Send>) msg.getReturnvalue().get("share");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return null;
	}

	// 对接server端浏览订单信息
	public static Vector<Order> viewOrder() {
		conn();
		MessageHandle msg = new MessageHandle();
		Hashtable ht = new Hashtable();
		ht.put("userid", Tools.userid);
		msg.setValue(ht);
		msg.setType(Constants.DOWNLOAD_ORDER);
		synchronized (MainClientService.class) {
			try {
				oout.writeObject(msg);
				oout.flush();
				msg = (MessageHandle) oin.readObject();
				System.out.println("msgGetValue:"
						+ msg.getReturnvalue().get("message").toString());
				if (msg.getReturnvalue().get("message").toString()
						.equals("浏览订单信息成功")) {
					return (Vector<Order>) msg.getReturnvalue().get("order");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return null;
	}

	// 对接server端浏览帮助信息
	public static Vector<Help> viewHelp() {
		conn();
		MessageHandle msg = new MessageHandle();
		msg.setType(Constants.DOWNLOAD_HELP);
		synchronized (MainClientService.class) {
			try {
				oout.writeObject(msg);
				oout.flush();
				msg = (MessageHandle) oin.readObject();
				System.out.println("msgGetValue:"
						+ msg.getReturnvalue().get("message").toString());
				if (msg.getReturnvalue().get("message").toString()
						.equals("浏览帮助信息成功")) {
					return (Vector<Help>) msg.getReturnvalue().get("help");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return null;
	}

	// 对接server端浏览amaze信息
	public static Amaze viewAmaze(String userid) {
		conn();
		MessageHandle msg = new MessageHandle();
		Hashtable t = new Hashtable();
		t.put("userid", userid);
		msg.setValue(t);
		msg.setType(Constants.DOWNLOAD_AMAZE);
		synchronized (MainClientService.class) {
			try {
				oout.writeObject(msg);
				oout.flush();
				msg = (MessageHandle) oin.readObject();
				System.out.println("msgGetValue:"
						+ msg.getReturnvalue().get("message").toString());
				if (msg.getReturnvalue().get("message").toString()
						.equals("浏览amaze信息成功")) {
					return (Amaze) msg.getReturnvalue().get("amaze");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return null;
	}

	// 对接server端下载与我相关数量
	public static Integer downNews(String userid) {
		conn();
		MessageHandle msg = new MessageHandle();
		Hashtable ht = new Hashtable();
		ht.put("userid", userid);
		msg.setType(Constants.DOWNLOAD_NEWS);
		msg.setValue(ht);
		synchronized (MainClientService.class) {
			try {
				oout.writeObject(msg);
				oout.flush();
				msg = (MessageHandle) oin.readObject();
				System.out.println("msgGetValue:"
						+ msg.getReturnvalue().get("message").toString());
				if (msg.getReturnvalue().get("message").toString()
						.equals("下载与我相关消息数量")) {
					return (Integer) msg.getReturnvalue().get("news");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return null;
	}

	// 对接Server端清0与我相关
	public static String cancelNews(String userid) {
		conn();
		MessageHandle msg = new MessageHandle();
		Hashtable ht = new Hashtable();
		ht.put("userid", userid);
		msg.setType(Constants.DELETE_NEWS);
		msg.setValue(ht);
		synchronized (MainClientService.class) {
			try {
				oout.writeObject(msg);
				oout.flush();
				msg = (MessageHandle) oin.readObject();
				if (msg.getReturnvalue().get("message").toString().equals("0")) {
					return "清0完毕";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

	}

	// 对接server端与我相关详细内容
	public static List<Action> getNewsdata(String userid) {
		conn();
		MessageHandle msg = new MessageHandle();
		Hashtable ht = new Hashtable();
		ht.put("userid", userid);
		msg.setType(Constants.DOWNLOAD_NEWS_INFO);
		msg.setValue(ht);
		synchronized (MainClientService.class) {
			try {
				oout.writeObject(msg);
				oout.flush();
				msg = (MessageHandle) oin.readObject();
				System.out.println("msgGetValue:"
						+ msg.getReturnvalue().get("message").toString());
				if (msg.getReturnvalue().get("message").toString()
						.equals("下载与我相关详细内容成功")) {
					return (List<Action>) msg.getReturnvalue().get("news_info");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return null;
	}

	// 对接server端发表的作品列表
	public static Vector<Send> getSendData(String userid) {
		conn();
		MessageHandle msg = new MessageHandle();
		Hashtable ht = new Hashtable();
		ht.put("userid", userid);
		msg.setType(Constants.DOWNLOAD_SEND);
		msg.setValue(ht);
		synchronized (MainClientService.class) {
			try {
				oout.writeObject(msg);
				oout.flush();
				msg = (MessageHandle) oin.readObject();
				System.out.println("msgGetValue:"
						+ msg.getReturnvalue().get("message").toString());
				if (msg.getReturnvalue().get("message").toString()
						.equals("下载我的发表的作品列表成功")) {
					return (Vector<Send>) msg.getReturnvalue().get("send");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return null;
	}

	// 对接server端收藏的作品列表
	public static Vector<Collection> getCollectionData(String userid) {
		conn();
		MessageHandle msg = new MessageHandle();
		Hashtable ht = new Hashtable();
		ht.put("userid", userid);
		msg.setType(Constants.DOWNLOAD_COLLECTION);
		msg.setValue(ht);
		synchronized (MainClientService.class) {
			try {
				oout.writeObject(msg);
				oout.flush();
				msg = (MessageHandle) oin.readObject();
				System.out.println("msgGetValue:"
						+ msg.getReturnvalue().get("message").toString());
				if (msg.getReturnvalue().get("message").toString()
						.equals("下载我的收藏作品列表成功")) {
					return (Vector<Collection>) msg.getReturnvalue().get(
							"collection");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return null;
	}

	// 对接Server端下载关注列表
	public static Vector<Concern> getConcernData(String userid) {
		conn();
		MessageHandle msg = new MessageHandle();
		Hashtable ht = new Hashtable();
		ht.put("userid", userid);
		msg.setType(Constants.DOWNLOAD_CONCERN);
		msg.setValue(ht);
		synchronized (MainClientService.class) {
			try {
				oout.writeObject(msg);
				oout.flush();
				msg = (MessageHandle) oin.readObject();
				System.out.println("msgGetValue:"
						+ msg.getReturnvalue().get("message").toString());
				if (msg.getReturnvalue().get("message").toString()
						.equals("下载我的关注列表成功")) {
					return (Vector<Concern>) msg.getReturnvalue().get(
							"concern_list");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return null;
	}

	// 对接Server端下载粉丝列表
	public static Vector<Concern> getFansData(String userid) {
		conn();
		MessageHandle msg = new MessageHandle();
		Hashtable ht = new Hashtable();
		ht.put("userid", userid);
		msg.setType(Constants.DOWNLOAD_FANS);
		msg.setValue(ht);
		synchronized (MainClientService.class) {
			try {
				oout.writeObject(msg);
				oout.flush();
				msg = (MessageHandle) oin.readObject();
				System.out.println("msgGetValue:"
						+ msg.getReturnvalue().get("message").toString());
				if (msg.getReturnvalue().get("message").toString()
						.equals("下载我的粉丝列表成功")) {
					return (Vector<Concern>) msg.getReturnvalue().get(
							"concerned_list");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return null;
	}

	// 对接Server端下载我帮助过的人列表
	public static Vector<Order> getHelpData(String userid) {
		conn();
		MessageHandle msg = new MessageHandle();
		Hashtable ht = new Hashtable();
		ht.put("userid", userid);
		msg.setType(Constants.DOWNLOAD_HELPER);
		msg.setValue(ht);
		synchronized (MainClientService.class) {
			try {
				oout.writeObject(msg);
				oout.flush();
				msg = (MessageHandle) oin.readObject();
				System.out.println("msgGetValue:"
						+ msg.getReturnvalue().get("message").toString());
				if (msg.getReturnvalue().get("message").toString()
						.equals("下载我帮助过的人的列表成功")) {
					return (Vector<Order>) msg.getReturnvalue().get(
							"helper_list");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return null;
	}

	// 对接Server端下载帮助过我的人列表
	public static Vector<Order> getHelpMeData(String userid) {
		conn();
		MessageHandle msg = new MessageHandle();
		Hashtable ht = new Hashtable();
		ht.put("userid", userid);
		msg.setType(Constants.DOWNLOAD_HELPED);
		msg.setValue(ht);
		synchronized (MainClientService.class) {
			try {
				oout.writeObject(msg);
				oout.flush();
				msg = (MessageHandle) oin.readObject();
				System.out.println("msgGetValue:"
						+ msg.getReturnvalue().get("message").toString());
				if (msg.getReturnvalue().get("message").toString()
						.equals("下载帮助过我的人的列表成功")) {
					return (Vector<Order>) msg.getReturnvalue().get(
							"helped_list");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return null;
	}

	// 对接Server端删除我的作品
	public static String deleteSend(String sendid) {
		try {
			conn();
			MessageHandle m = new MessageHandle();
			Hashtable ht = new Hashtable();
			ht.put("sendid", sendid);
			m.setType(Constants.DELETE_SEND);
			m.setValue(ht);
			oout.writeObject(m);
			oout.flush();
			MessageHandle m2 = (MessageHandle) oin.readObject();
			String message = m2.getReturnvalue().get("message").toString();
			if (!message.equals("删除我的发表成功")) {
				oout.close();
				oin.close();
			}
			return message;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 删除amaze
	public static String deleteAmaze(String amazeid) {
		try {
			conn();
			MessageHandle m = new MessageHandle();
			Hashtable ht = new Hashtable();
			ht.put("amazeid", amazeid);
			m.setType(Constants.DELETE_AMAZE);
			m.setValue(ht);
			oout.writeObject(m);
			oout.flush();
			MessageHandle m2 = (MessageHandle) oin.readObject();
			String message = m2.getReturnvalue().get("message").toString();
			if (!message.equals("删除amaze成功")) {
				oout.close();
				oin.close();
			}
			return message;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 对接Server端删除我的收藏
	public static String deleteCollection(String collectionid) {
		try {
			conn();
			MessageHandle m = new MessageHandle();
			Hashtable ht = new Hashtable();
			ht.put("collectionid", collectionid);
			m.setType(Constants.DELETE_COLLECTION);
			m.setValue(ht);
			oout.writeObject(m);
			oout.flush();
			MessageHandle m2 = (MessageHandle) oin.readObject();
			String message = m2.getReturnvalue().get("message").toString();
			if (!message.equals("删除我的收藏成功")) {
				oout.close();
				oin.close();
			}
			return message;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 对接Server端取消关注
	public static String deleteConcern(String concernid) {
		try {
			conn();
			MessageHandle m = new MessageHandle();
			Hashtable ht = new Hashtable();
			ht.put("concernid", concernid);
			m.setType(Constants.DELETE_CONCERN);
			m.setValue(ht);
			oout.writeObject(m);
			oout.flush();
			MessageHandle m2 = (MessageHandle) oin.readObject();
			String message = m2.getReturnvalue().get("message").toString();
			if (!message.equals("取消关注成功")) {
				oout.close();
				oin.close();
			}
			return message;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 对接Server端删除订单
	public static String deleteOrder(String orderid) {
		try {
			conn();
			MessageHandle m = new MessageHandle();
			Hashtable ht = new Hashtable();
			ht.put("orderid", orderid);
			m.setType(Constants.DELETE_ORDER);
			m.setValue(ht);
			oout.writeObject(m);
			oout.flush();
			MessageHandle m2 = (MessageHandle) oin.readObject();
			String message = m2.getReturnvalue().get("message").toString();
			if (!message.equals("删除订单成功")) {
				oout.close();
				oin.close();
			}
			return message;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 对接Server端删除帮助
	public static String deleteHelp(String helpid) {
		try {
			conn();
			MessageHandle m = new MessageHandle();
			Hashtable ht = new Hashtable();
			ht.put("helpid", helpid);
			m.setType(Constants.DELETE_HELP);
			m.setValue(ht);
			oout.writeObject(m);
			oout.flush();
			MessageHandle m2 = (MessageHandle) oin.readObject();
			String message = m2.getReturnvalue().get("message").toString();
			if (!message.equals("删除帮助成功")) {
				oout.close();
				oin.close();
			}
			return message;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 注销
	public static void logout(String userid) {
		try {
			conn();
			MessageHandle m = new MessageHandle();
			Hashtable ht = new Hashtable();
			ht.put("removeuserid", userid);
			m.setType(Constants.LOGOUT);
			m.setValue(ht);
			oout.writeObject(m);
			oout.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

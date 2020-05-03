package com.example.Stareyou.server;

/**
 * 信息处理类(连接服务器和客户端的桥梁)
 */
import java.io.Serializable;
import java.util.Hashtable;

public class MessageHandle implements Serializable {

	public Hashtable value = null;
	public Hashtable returnvalue = null;
	public String type = "";

	public Hashtable getValue() {
		return value;
	}

	public void setValue(Hashtable value) {
		this.value = value;
	}

	public Hashtable getReturnvalue() {
		return returnvalue;
	}

	public void setReturnvalue(Hashtable returnvalue) {
		this.returnvalue = returnvalue;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}

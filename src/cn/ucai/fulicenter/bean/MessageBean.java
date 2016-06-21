package cn.ucai.fulicenter.bean;

import java.io.Serializable;

/**
 * 获取服务端返回的响应实体类
 * @author yao
 *
 */
public class MessageBean implements Serializable {
	/** 响应是否成功,true:成功，false：失败*/
	private boolean success;
	/** 返回的字符串*/
	private String msg;
	public MessageBean(boolean success, String msg) {
		super();
		this.success = success;
		this.msg = msg;
	}
	
	public MessageBean() {
		// TODO Auto-generated constructor stub
	}
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	@Override
	public String toString() {
		return "MessageBean [success=" + success + ", msg=" + msg + "]";
	}
	
}

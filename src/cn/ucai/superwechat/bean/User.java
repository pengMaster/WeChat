package cn.ucai.superwechat.bean;


/**
 * User entity. @author MyEclipse Persistence Tools
 */
public class User extends Location implements java.io.Serializable {
	private static final long serialVersionUID = 6848921231724157394L;

	// Fields

	/**
	 * 
	 */
	private Integer muserId;
	private String muserName;
	private String muserPassword;
	private String muserNick;
	private Integer muserUnreadMsgCount;

	// Constructors

	/** default constructor */
	public User() {
	}
	
	public User(boolean result, int msg) {
		this.setResult(result);
		this.setMsg(msg);
	}

	/** minimal constructor */
	public User(Integer MUserId, String MUserName, String MUserPassword, String MUserNick) {
		this.muserId = MUserId;
		this.muserName = MUserName;
		this.muserPassword = MUserPassword;
		this.muserNick = MUserNick;
	}

	/** full constructor */
	public User(Integer MUserId, String MUserName, String MUserPassword, String MUserNick,
			Integer MUserUnreadMsgCount) {
		this(MUserId, MUserName, MUserPassword, MUserNick);
		this.muserUnreadMsgCount = MUserUnreadMsgCount;
	}

	// Property accessors
	public Integer getMUserId() {
		return this.muserId;
	}

	public void setMUserId(Integer MUserId) {
		this.muserId = MUserId;
	}

	public String getMUserName() {
		return this.muserName;
	}

	public void setMUserName(String MUserName) {
		this.muserName = MUserName;
	}

	public String getMUserPassword() {
		return this.muserPassword;
	}

	public void setMUserPassword(String MUserPassword) {
		this.muserPassword = MUserPassword;
	}

	public String getMUserNick() {
		return this.muserNick;
	}

	public void setMUserNick(String MUserNick) {
		this.muserNick = MUserNick;
	}

	public Integer getMUserUnreadMsgCount() {
		return this.muserUnreadMsgCount;
	}

	public void setMUserUnreadMsgCount(Integer MUserUnreadMsgCount) {
		this.muserUnreadMsgCount = MUserUnreadMsgCount;
	}

	@Override
	public String toString() {
		return "User [MUserId=" + muserId + ", MUserName=" + muserName
				+ ", MUserPassword=" + muserPassword + ", MUserNick="
				+ muserNick + ", MUserUnreadMsgCount=" + muserUnreadMsgCount
				+ "]";
	}
	

}
package cn.ucai.superwechat.bean;

/**
 * Contact entity. @author MyEclipse Persistence Tools
 */
public class Contact extends User implements java.io.Serializable {
	private static final long serialVersionUID = -2183229871248294573L;

	/**
	 * 
	 */
	// Fields

	private Integer mcontactId;
	private Integer mcontactUserId;
	private String mcontactUserName;
	private Integer mcontactCid;
	private String mcontactCname;

	// Constructors

	/** default constructor */
	public Contact() {
	}

	/** full constructor */
	public Contact(Integer MContactId, Integer MContactUserId, String MContactUserName,
			Integer MContactCid, String MContactCname) {
		this.mcontactId = MContactId;
		this.mcontactUserId = MContactUserId;
		this.mcontactUserName = MContactUserName;
		this.mcontactCid = MContactCid;
		this.mcontactCname = MContactCname;
	}

	// Property accessors
	public Integer getMContactId() {
		return this.mcontactId;
	}

	public void setMContactId(Integer MContactId) {
		this.mcontactId = MContactId;
	}

	public Integer getMContactUserId() {
		return this.mcontactUserId;
	}

	public void setMContactUserId(Integer MContactUserId) {
		this.mcontactUserId = MContactUserId;
	}

	public String getMContactUserName() {
		return this.mcontactUserName;
	}

	public void setMContactUserName(String MContactUserName) {
		this.mcontactUserName = MContactUserName;
	}

	public Integer getMContactCid() {
		return this.mcontactCid;
	}

	public void setMContactCid(Integer MContactCid) {
		this.mcontactCid = MContactCid;
	}

	public String getMContactCname() {
		return this.mcontactCname;
	}

	public void setMContactCname(String MContactCname) {
		this.mcontactCname = MContactCname;
	}

	@Override
	public String toString() {
		return "Contact [MContactId=" + mcontactId + ", MContactUserId="
				+ mcontactUserId + ", MContactUserName=" + mcontactUserName
				+ ", MContactCid=" + mcontactCid + ", MContactCname="
				+ mcontactCname + "]";
	}

	
}
package cn.ucai.superwechat.bean;


/**
 * Member entity. @author MyEclipse Persistence Tools
 */
public class Member extends User implements java.io.Serializable {
	private static final long serialVersionUID = 6913484375413577556L;

	// Fields

	/**
	 * 
	 */
	private Integer mmemberId;
	private Integer mmemberUserId;
	private String mmemberUserName;
	private Integer mmemberGroupId;
	private String mmemberGroupHxid;
	private Integer mmemberPermission;

	// Constructors

	/** default constructor */
	public Member() {
	}

	/** full constructor */
	public Member(Integer MMemberUserId, String MMemberUserName,
			Integer MMemberGroupId, String MMemberGroupHxid,
			Integer MMemberPermission) {
		this.mmemberUserId = MMemberUserId;
		this.mmemberUserName = MMemberUserName;
		this.mmemberGroupId = MMemberGroupId;
		this.mmemberGroupHxid = MMemberGroupHxid;
		this.mmemberPermission = MMemberPermission;
	}

	// Property accessors
	public Integer getMMemberId() {
		return this.mmemberId;
	}

	public void setMMemberId(Integer MMemberId) {
		this.mmemberId = MMemberId;
	}

	public Integer getMMemberUserId() {
		return this.mmemberUserId;
	}

	public void setMMemberUserId(Integer MMemberUserId) {
		this.mmemberUserId = MMemberUserId;
	}

	public String getMMemberUserName() {
		return this.mmemberUserName;
	}

	public void setMMemberUserName(String MMemberUserName) {
		this.mmemberUserName = MMemberUserName;
	}

	public Integer getMMemberGroupId() {
		return this.mmemberGroupId;
	}

	public void setMMemberGroupId(Integer MMemberGroupId) {
		this.mmemberGroupId = MMemberGroupId;
	}

	public String getMMemberGroupHxid() {
		return this.mmemberGroupHxid;
	}

	public void setMMemberGroupHxid(String MMemberGroupHxid) {
		this.mmemberGroupHxid = MMemberGroupHxid;
	}

	public Integer getMMemberPermission() {
		return this.mmemberPermission;
	}

	public void setMMemberPermission(Integer MMemberPermission) {
		this.mmemberPermission = MMemberPermission;
	}

	@Override
	public String toString() {
		return "Member [MMemberId=" + mmemberId + ", MMemberUserId="
				+ mmemberUserId + ", MMemberUserName=" + mmemberUserName
				+ ", MMemberGroupId=" + mmemberGroupId + ", MMemberGroupHxid="
				+ mmemberGroupHxid + ", MMemberPermission=" + mmemberPermission
				+ "]";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Member)) return false;

		Member member = (Member) o;

		if (!mmemberUserId.equals(member.mmemberUserId)) return false;
		if (!mmemberUserName.equals(member.mmemberUserName)) return false;
		if (!mmemberGroupId.equals(member.mmemberGroupId)) return false;
		return mmemberGroupHxid.equals(member.mmemberGroupHxid);

	}

	@Override
	public int hashCode() {
		int result = mmemberUserId.hashCode();
		result = 31 * result + mmemberUserName.hashCode();
		result = 31 * result + mmemberGroupId.hashCode();
		result = 31 * result + mmemberGroupHxid.hashCode();
		return result;
	}
}
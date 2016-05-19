package cn.ucai.superwechat.bean;


/**
 * Group entity. @author MyEclipse Persistence Tools
 */
public class Group extends Location implements java.io.Serializable {
	private static final long serialVersionUID = -2853820541320732366L;

	// Fields

	/**
	 * 
	 */
	private Integer mgroupId;
	private String mgroupHxid;
	private String mgroupName;
	private String mgroupDescription;
	private String mgroupOwner;
	private String mgroupLastModifiedTime;
	private Integer mgroupMaxUsers;
	private Integer mgroupAffiliationsCount;
	private Boolean mgroupIsPublic;
	private Boolean mgroupAllowInvites;

	// Constructors

	/** default constructor */
	public Group() {
	}

	/** minimal constructor */
	public Group(String MGroupOwner, String MGroupLastModifiedTime,
			Integer MGroupAffiliationsCount, Boolean MGroupIsPublic,
			Boolean MGroupAllowInvites) {
		this.mgroupOwner = MGroupOwner;
		this.mgroupLastModifiedTime = MGroupLastModifiedTime;
		this.mgroupAffiliationsCount = MGroupAffiliationsCount;
		this.mgroupIsPublic = MGroupIsPublic;
		this.mgroupAllowInvites = MGroupAllowInvites;
	}

	/** full constructor */
	public Group(String MGroupHxid, String MGroupName,
			String MGroupDescription, String MGroupOwner,
			String MGroupLastModifiedTime, Integer MGroupMaxUsers,
			Integer MGroupAffiliationsCount, Boolean MGroupIsPublic,
			Boolean MGroupAllowInvites) {
		this(MGroupOwner, MGroupLastModifiedTime, MGroupAffiliationsCount,
				MGroupIsPublic, MGroupAllowInvites);
		this.mgroupHxid = MGroupHxid;
		this.mgroupName = MGroupName;
		this.mgroupDescription = MGroupDescription;
		this.mgroupMaxUsers = MGroupMaxUsers;
	}

	public Group(boolean result, int msg) {
		this.setResult(result);
		this.setMsg(msg);
	}

	// Property accessors
	public Integer getMGroupId() {
		return this.mgroupId;
	}

	public void setMGroupId(Integer MGroupId) {
		this.mgroupId = MGroupId;
	}

	public String getMGroupHxid() {
		return this.mgroupHxid;
	}

	public void setMGroupHxid(String MGroupHxid) {
		this.mgroupHxid = MGroupHxid;
	}

	public String getMGroupName() {
		return this.mgroupName;
	}

	public void setMGroupName(String MGroupName) {
		this.mgroupName = MGroupName;
	}

	public String getMGroupDescription() {
		return this.mgroupDescription;
	}

	public void setMGroupDescription(String MGroupDescription) {
		this.mgroupDescription = MGroupDescription;
	}

	public String getMGroupOwner() {
		return this.mgroupOwner;
	}

	public void setMGroupOwner(String MGroupOwner) {
		this.mgroupOwner = MGroupOwner;
	}

	public String getMGroupLastModifiedTime() {
		return this.mgroupLastModifiedTime;
	}

	public void setMGroupLastModifiedTime(String MGroupLastModifiedTime) {
		this.mgroupLastModifiedTime = MGroupLastModifiedTime;
	}

	public Integer getMGroupMaxUsers() {
		return this.mgroupMaxUsers;
	}

	public void setMGroupMaxUsers(Integer MGroupMaxUsers) {
		this.mgroupMaxUsers = MGroupMaxUsers;
	}

	public Integer getMGroupAffiliationsCount() {
		return this.mgroupAffiliationsCount;
	}

	public void setMGroupAffiliationsCount(Integer MGroupAffiliationsCount) {
		this.mgroupAffiliationsCount = MGroupAffiliationsCount;
	}

	public Boolean getMGroupIsPublic() {
		return this.mgroupIsPublic;
	}

	public void setMGroupIsPublic(Boolean MGroupIsPublic) {
		this.mgroupIsPublic = MGroupIsPublic;
	}

	public Boolean getMGroupAllowInvites() {
		return this.mgroupAllowInvites;
	}

	public void setMGroupAllowInvites(Boolean MGroupAllowInvites) {
		this.mgroupAllowInvites = MGroupAllowInvites;
	}

	@Override
	public String toString() {
		return "Group [MGroupId=" + mgroupId + ", MGroupHxid=" + mgroupHxid
				+ ", MGroupName=" + mgroupName + ", MGroupDescription="
				+ mgroupDescription + ", MGroupOwner=" + mgroupOwner
				+ ", MGroupLastModifiedTime=" + mgroupLastModifiedTime
				+ ", MGroupMaxUsers=" + mgroupMaxUsers
				+ ", MGroupAffiliationsCount=" + mgroupAffiliationsCount
				+ ", MGroupIsPublic=" + mgroupIsPublic
				+ ", MGroupAllowInvites=" + mgroupAllowInvites + "]";
	}
	

}
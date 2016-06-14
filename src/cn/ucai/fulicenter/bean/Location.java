package cn.ucai.fulicenter.bean;


/**
 * Location entity. @author MyEclipse Persistence Tools
 */
public class Location extends Avatar implements java.io.Serializable {
	private static final long serialVersionUID = 8197872390536640826L;

	// Fields

	/**
	 * 
	 */
	private Integer mlocationId;
	private Integer mlocationUserId;
	private String mlocationUserName;
	private Double mlocationLatitude;
	private Double mlocationLongitude;
	private Boolean mlocationIsSearched;
	private String mlocationLastUpdateTime;

	// Constructors

	/** default constructor */
	public Location() {
	}

	/** minimal constructor */
	public Location(Integer MLocationUserId, String MLocationUserName,
			Boolean MLocationIsSearched) {
		this.mlocationUserId = MLocationUserId;
		this.mlocationUserName = MLocationUserName;
		this.mlocationIsSearched = MLocationIsSearched;
	}

	/** full constructor */
	public Location(Integer MLocationUserId, String MLocationUserName,
			Double MLocationLatitude, Double MLocationLongitude,
			Boolean MLocationIsSearched, String MLocationLastUpdateTime) {
		this.mlocationUserId = MLocationUserId;
		this.mlocationUserName = MLocationUserName;
		this.mlocationLatitude = MLocationLatitude;
		this.mlocationLongitude = MLocationLongitude;
		this.mlocationIsSearched = MLocationIsSearched;
		this.mlocationLastUpdateTime = MLocationLastUpdateTime;
	}

	// Property accessors
	public Integer getMLocationId() {
		return this.mlocationId;
	}

	public void setMLocationId(Integer MLocationId) {
		this.mlocationId = MLocationId;
	}

	public Integer getMLocationUserId() {
		return this.mlocationUserId;
	}

	public void setMLocationUserId(Integer MLocationUserId) {
		this.mlocationUserId = MLocationUserId;
	}

	public String getMLocationUserName() {
		return this.mlocationUserName;
	}

	public void setMLocationUserName(String MLocationUserName) {
		this.mlocationUserName = MLocationUserName;
	}

	public Double getMLocationLatitude() {
		return this.mlocationLatitude;
	}

	public void setMLocationLatitude(Double MLocationLatitude) {
		this.mlocationLatitude = MLocationLatitude;
	}

	public Double getMLocationLongitude() {
		return this.mlocationLongitude;
	}

	public void setMLocationLongitude(Double MLocationLongitude) {
		this.mlocationLongitude = MLocationLongitude;
	}

	public Boolean getMLocationIsSearched() {
		return this.mlocationIsSearched;
	}

	public void setMLocationIsSearched(Boolean MLocationIsSearched) {
		this.mlocationIsSearched = MLocationIsSearched;
	}

	public String getMLocationLastUpdateTime() {
		return this.mlocationLastUpdateTime;
	}

	public void setMLocationLastUpdateTime(String MLocationLastUpdateTime) {
		this.mlocationLastUpdateTime = MLocationLastUpdateTime;
	}

	@Override
	public String toString() {
		return "Location [MLocationId=" + mlocationId + ", MLocationUserId="
				+ mlocationUserId + ", MLocationUserName=" + mlocationUserName
				+ ", MLocationLatitude=" + mlocationLatitude
				+ ", MLocationLongitude=" + mlocationLongitude
				+ ", MLocationIsSearched=" + mlocationIsSearched
				+ ", MLocationLastUpdateTime=" + mlocationLastUpdateTime + "]";
	}
	

}
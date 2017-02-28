package research.dlsu.model;

public class OnSiteUser {
	
	public static final String TABLE = "onsiteuser";
	public static final String ID = "idonsiteuser";
	public static final String SERIALNUMBER = "serialnumber";
	
	private int id;
	private String serialNumber;
	
	public OnSiteUser(){}
		
	public OnSiteUser(int id, String serialNumber) {
		super();
		this.id = id;
		this.serialNumber = serialNumber;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getSerialNumber() {
		return serialNumber;
	}
	
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
}

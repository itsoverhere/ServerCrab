package research.dlsu.model;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Created by courtneyngo on 4/22/16.
 */
public class Crab {
	
	public static final String TABLE = "crab";
	
	public static final String IDCRAB = "idcrab";
//	public static final String IDCRABUPDATE = "idcrab_update";
	public static final String PHONEIDCRAB = "phoneidcrab";
//	public static final String PHONEIDCRABUPDATE = "phoneidcrab_update";
	
	public static final String WEIGHT = "weight";
	public static final String LATITUDE = "latitude";
	public static final String LONGITUDE = "longitude";
	public static final String STATUS = "status";
	public static final String TAG = "tag";
	public static final String RESULT = "result";
	public static final String FARM = "farm";
	public static final String CITY = "city";
	
	public static final String IDONSITEUSER = "idonsiteuser";
	public static final String IDOFFSITEUSER = "idoffsiteuser";
	
//	public static final String HAS_RESULT = "has_result";

    private int id;
    private int phoneidcrab;
    private int idonsiteuser;
    private int idoffsiteuser;
    
    private double weight;
    private double latitude;
    private double longitude;
    
    private String status;
    private String tag;
    private String result;
    
    private String farm;
    private String city;

    private Date lastUpdate;
    private int numOfUpdates;
    
    private boolean hasResult;
    
    private ArrayList<CrabUpdate> listCrabUpdates;

    public Crab(){}

    public Crab(int id, String status, String tag, Date lastUpdate, int numOfUpdates) {
        this.id = id;
        this.status = status;
        this.tag = tag;
        this.lastUpdate = lastUpdate;
        this.numOfUpdates = numOfUpdates;
    }
    
    public Crab(int id, int phoneidcrab, int idonsiteuser, int idoffsiteuser,
			double weight, double latitude, double longitude, String status,
			String tag, String result, String farm, String city,
			Date lastUpdate, int numOfUpdates) {
		super();
		this.id = id;
		this.phoneidcrab = phoneidcrab;
		this.idonsiteuser = idonsiteuser;
		this.idoffsiteuser = idoffsiteuser;
		this.weight = weight;
		this.latitude = latitude;
		this.longitude = longitude;
		this.status = status;
		this.tag = tag;
		this.result = result;
		this.farm = farm;
		this.city = city;
		this.lastUpdate = lastUpdate;
		this.numOfUpdates = numOfUpdates;
	}

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public int getNumOfUpdates() {
        return numOfUpdates;
    }

    public void setNumOfUpdates(int numOfUpdates) {
        this.numOfUpdates = numOfUpdates;
    }

	public int getPhoneidcrab() {
		return phoneidcrab;
	}

	public void setPhoneidcrab(int phoneidcrab) {
		this.phoneidcrab = phoneidcrab;
	}

	public int getIdonsiteuser() {
		return idonsiteuser;
	}

	public void setIdonsiteuser(int idonsiteuser) {
		this.idonsiteuser = idonsiteuser;
	}

	public int getIdoffsiteuser() {
		return idoffsiteuser;
	}

	public void setIdoffsiteuser(int idoffsiteuser) {
		this.idoffsiteuser = idoffsiteuser;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getFarm() {
		return farm;
	}

	public void setFarm(String farm) {
		this.farm = farm;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public ArrayList<CrabUpdate> getListCrabUpdates() {
		return listCrabUpdates;
	}

	public void setListCrabUpdates(ArrayList<CrabUpdate> listCrabUpdates) {
		this.listCrabUpdates = listCrabUpdates;
	}

	public boolean isHasResult() {
		return hasResult;
	}

	public void setHasResult(boolean hasResult) {
		this.hasResult = hasResult;
	}

	@Override
	public String toString() {
		return "Crab [id=" + id + ", phoneidcrab=" + phoneidcrab
				+ ", idonsiteuser=" + idonsiteuser + ", idoffsiteuser="
				+ idoffsiteuser + ", weight=" + weight + ", latitude="
				+ latitude + ", longitude=" + longitude + ", status=" + status
				+ ", tag=" + tag + ", result=" + result + ", farm=" + farm
				+ ", city=" + city + ", lastUpdate=" + lastUpdate
				+ ", numOfUpdates=" + numOfUpdates + ", hasResult=" + hasResult
				+ ", listCrabUpdates=" + listCrabUpdates + "]";
	}
	
}
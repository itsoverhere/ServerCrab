package research.dlsu.model;

import java.sql.Date;

/**
 * Created by courtneyngo on 4/26/16.
 */
public class CrabUpdate {

	public static final String TABLE = "crab_update";
	
	public static final String IDCRAB = "idcrab";
	public static final String IDCRABUPDATE = "idcrab_update";
	public static final String PHONEIDCRAB = "phoneidcrab";
	public static final String PHONEIDCRABUPDATE = "phoneidcrab_update";
	
	public static final String PARAMETER_PHONEIDCRABUPDATE = "idcrabupdate";
	public static final String PARAMETER_PHONEIDDCRAB = "idcrab";
	public static final String PARAMETER_SERVERIDCRAB = "serveridcrab";

	public static final String IDCRABSTRING = "idcrabstring";
	
	public static final String PATH = "path";
	public static final String TYPE = "crabtype";
	public static final String DATE = "date";
	public static final String RESULT = "result";

	public static final String IDONSITEUSER = "idonsiteuser";
	public static final String IDOFFSITEUSER = "idoffsiteuser";
	
    private long id; // idcrabupdate
    private long idcrab;
    private long phoneidcrabupdate;
    private long phoneidcrab;
    private String type;
    private String path;
    private Date date;
    private String result;
    
    private int idonsiteuser;
    private int idoffsiteuser;

    public CrabUpdate(){}

    public CrabUpdate(long id, String path, Date date) {
        this.id = id;
        this.path = path;
        this.date = date;
    }

    public CrabUpdate(long id, long idcrab, long phoneidcrabupdate,
    		long phoneidcrab, String path, Date date) {
		super();
		this.id = id;
		this.idcrab = idcrab;
		this.phoneidcrabupdate = phoneidcrabupdate;
		this.phoneidcrab = phoneidcrab;
		this.path = path;
		this.date = date;
	}

	public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

	public long getIdcrab() {
		return idcrab;
	}

	public void setIdcrab(long idcrab) {
		this.idcrab = idcrab;
	}

	public long getPhoneidcrabupdate() {
		return phoneidcrabupdate;
	}

	public void setPhoneidcrabupdate(long phoneidcrabupdate) {
		this.phoneidcrabupdate = phoneidcrabupdate;
	}

	public long getPhoneidcrab() {
		return phoneidcrab;
	}

	public void setPhoneidcrab(long phoneidcrab) {
		this.phoneidcrab = phoneidcrab;
	}
	
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	@Override
	public String toString() {
		return "CrabUpdate [id=" + id + ", idcrab=" + idcrab
				+ ", phoneidcrabupdate=" + phoneidcrabupdate + ", phoneidcrab="
				+ phoneidcrab + ", path=" + path + ", date=" + date + "]";
	}
	
	
}


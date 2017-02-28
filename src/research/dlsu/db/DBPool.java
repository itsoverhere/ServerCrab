package research.dlsu.db;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

public class DBPool {
	private static DBPool singleton;
	private static BasicDataSource ds;
	final String username = "root";
	final String password = "password";
	final String url = "jdbc:mysql://localhost:3306/schema_crab";
	
	private DBPool(){
		ds = new BasicDataSource();
		ds.setUrl(url);
		ds.setUsername(username);
		ds.setPassword(password);
		ds.setDriverClassName("com.mysql.jdbc.Driver");
	}
	
	public static DBPool getInstance(){
		if(singleton==null)
			singleton = new DBPool();
		return singleton;
	}
	
	public static Connection getConnection(){
		try {
			return ds.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}

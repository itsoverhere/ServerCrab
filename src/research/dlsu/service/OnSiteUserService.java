package research.dlsu.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.dbutils.DbUtils;

import research.dlsu.db.DBPool;
import research.dlsu.model.OnSiteUser;

public class OnSiteUserService {

	public int getIdFromIMEI(String sn){
		Connection conn = DBPool.getInstance().getConnection();
		int idonsiteuser = -1; 
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(
					"SELECT " + OnSiteUser.ID + " FROM " + OnSiteUser.TABLE
					+ " WHERE " + OnSiteUser.SERIALNUMBER + "=?;"
					);
			pstmt.setString(1, sn);
			
			rs = pstmt.executeQuery();
			if(rs.next()){
				idonsiteuser = rs.getInt(OnSiteUser.ID);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DbUtils.closeQuietly(rs);
			DbUtils.closeQuietly(pstmt);
			DbUtils.closeQuietly(conn);
		}
		
		return idonsiteuser;
	}
	
	public int insertOnSiteUser(String serialNumber){
		Connection conn = DBPool.getInstance().getConnection();
		PreparedStatement pstmt = null;
		int i = -1;
		
		try {
			pstmt = conn.prepareStatement(
					"INSERT INTO " + OnSiteUser.TABLE + " ("
					+ OnSiteUser.SERIALNUMBER
					+ ")"
					+ " VALUES (?)",
					Statement.RETURN_GENERATED_KEYS
					);
			
			pstmt.setString(1, serialNumber);
			
			i = pstmt.executeUpdate();
			
			if (i == 0) {
	            throw new SQLException("Creating user failed, no rows affected.");
	        }

	        try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                i = generatedKeys.getInt(1);
	            }
	            else {
	                throw new SQLException("Creating user failed, no ID obtained.");
	            }
	        }
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DbUtils.closeQuietly(pstmt);
			DbUtils.closeQuietly(conn);
		}
		
		return i;
	}
	
	public int insertOnSiteUser(OnSiteUser onsiteuser){
		Connection conn = DBPool.getInstance().getConnection();
		PreparedStatement pstmt = null;
		int i = -1;
		
		try {
			pstmt = conn.prepareStatement(
					"INSERT INTO " + OnSiteUser.TABLE + " ("
					+ OnSiteUser.SERIALNUMBER
					+ ")"
					+ " VALUES (?)",
					Statement.RETURN_GENERATED_KEYS
					);
			pstmt.setString(1, onsiteuser.getSerialNumber());
			
			i = pstmt.executeUpdate();
			
			if (i == 0) {
	            throw new SQLException("Creating user failed, no rows affected.");
	        }

	        try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                i = generatedKeys.getInt(1);
	            }
	            else {
	                throw new SQLException("Creating user failed, no ID obtained.");
	            }
	        }
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DbUtils.closeQuietly(pstmt);
			DbUtils.closeQuietly(conn);
		}
		
		return i;
	}
	
}

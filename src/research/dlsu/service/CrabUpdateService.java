package research.dlsu.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.commons.dbutils.DbUtils;
import org.apache.tomcat.dbcp.dbcp.DbcpException;

import research.dlsu.db.DBPool;
import research.dlsu.model.Crab;
import research.dlsu.model.CrabUpdate;

public class CrabUpdateService {

	public long insertCrabUpdate(CrabUpdate cu){
		Connection conn = DBPool.getInstance().getConnection();
		PreparedStatement pstmt = null;
		long id = -1;
		
		try {
			pstmt = conn.prepareStatement(
					"INSERT INTO " + CrabUpdate.TABLE + "("
					//+ CrabUpdate.IDCRAB + ", " 
					//+ CrabUpdate.PHONEIDCRAB + ", "
					+ CrabUpdate.PHONEIDCRABUPDATE + ", "
					+ CrabUpdate.PATH + ", "
					+ CrabUpdate.TYPE + ", "
					+ CrabUpdate.DATE + ","
					+ CrabUpdate.IDONSITEUSER + ","
					+ CrabUpdate.RESULT
					+ ") "
					+ " VALUES (?, ?, ?, ?, ?, ?);",
					Statement.RETURN_GENERATED_KEYS
					);
			
			//pstmt.setLong(1, cu.getIdcrab());
			//pstmt.setLong(2, cu.getPhoneidcrab());
			pstmt.setLong(1, cu.getPhoneidcrabupdate());
			pstmt.setString(2, cu.getPath());
			pstmt.setString(3, cu.getType());
			pstmt.setDate(4, cu.getDate());
			pstmt.setInt(5, cu.getIdonsiteuser());
			pstmt.setString(6, cu.getResult());
			
			id = pstmt.executeUpdate();
			
			if (id == 0) {
	            throw new SQLException("Creating user failed, no rows affected.");
	        }

	        try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                id = generatedKeys.getLong(1);
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
		
		return id;
	}
	
	
	// done FIX THIS
	public ArrayList<CrabUpdate> getAvailableResultsFromSNAndListPhoneIdCrab(String serialNumber, String phoneidcrabupdateString){
		ArrayList<CrabUpdate> listCrabUpdateResults = new ArrayList<CrabUpdate>();
		
//		phoneidcrabString = "(" + phoneidcrabString + ")";
		// ^ this is already done in the mobile app 
		
		int idonsiteuser = new OnSiteUserService().getIdFromIMEI(serialNumber);
		// user supposedly already has an idonsiteuser when submitting a photo
		// idonsiteuser can never be -1, this will only happen if user refreshed (gets result) before submitting any photo
			// in the case above, the user will get a prompt saying "All crabs already have results" before connecting to the server
		
		Connection conn = DBPool.getInstance().getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(
					"SELECT " + CrabUpdate.PHONEIDCRABUPDATE + ", " + CrabUpdate.RESULT
					+ " FROM " + CrabUpdate.TABLE
					+ " WHERE " + CrabUpdate.IDONSITEUSER + " =? "
					   + " AND " + CrabUpdate.PHONEIDCRABUPDATE + " in " + phoneidcrabupdateString 
					);
			pstmt.setInt(1,  idonsiteuser);
			rs = pstmt.executeQuery();
			
			System.out.println(pstmt.toString());
			
			while(rs.next()){
				if(rs.getString(CrabUpdate.RESULT) != null){
					CrabUpdate c = new CrabUpdate();
					c.setPhoneidcrabupdate(rs.getInt(CrabUpdate.PHONEIDCRABUPDATE));
					c.setResult(rs.getString(CrabUpdate.RESULT));
					listCrabUpdateResults.add(c);
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DbUtils.closeQuietly(rs);
			DbUtils.closeQuietly(pstmt);
			DbUtils.closeQuietly(conn);
		}
		
		return listCrabUpdateResults;
	}
	
	public ArrayList<CrabUpdate> getAllCrabUpdatesOfCrab(int idcrab){
		ArrayList<CrabUpdate> listCrabUpdates = new ArrayList<CrabUpdate>();
		
		Connection conn = DBPool.getInstance().getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(
					"SELECT * FROM " + CrabUpdate.TABLE 
					+ " WHERE " + CrabUpdate.IDCRAB + " =?;");
			pstmt.setInt(1, idcrab);
			
			rs = pstmt.executeQuery();
			while(rs.next()){
				CrabUpdate cu = new CrabUpdate();
				cu.setId(rs.getInt(CrabUpdate.IDCRABUPDATE));
				cu.setIdcrab(idcrab);
				cu.setPhoneidcrab(rs.getInt(CrabUpdate.PHONEIDCRAB));
				cu.setPhoneidcrabupdate(rs.getInt(CrabUpdate.PHONEIDCRABUPDATE));
				cu.setPath(rs.getString(CrabUpdate.PATH));
				cu.setDate(rs.getDate(CrabUpdate.DATE));
				listCrabUpdates.add(cu);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DbUtils.closeQuietly(rs);
			DbUtils.closeQuietly(pstmt);
			DbUtils.closeQuietly(conn);
		}
		
		return listCrabUpdates;
	}
	
	public static CrabUpdate getCrabUpdate(int idcrabupdate){
		String sql = "SELECT * FROM " + CrabUpdate.TABLE
				+ " WHERE " + CrabUpdate.IDCRABUPDATE + " = ? ";
		
		Connection conn = DBPool.getInstance().getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		CrabUpdate cu = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idcrabupdate);
			
			rs = pstmt.executeQuery();
			if(rs.next()){
				cu = new CrabUpdate();
				cu.setId(rs.getInt(CrabUpdate.IDCRABUPDATE));
				cu.setPhoneidcrabupdate(rs.getInt(CrabUpdate.PHONEIDCRABUPDATE));
				cu.setPath(rs.getString(CrabUpdate.PATH));
				cu.setDate(rs.getDate(CrabUpdate.DATE));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DbUtils.closeQuietly(rs);
			DbUtils.closeQuietly(pstmt);
			DbUtils.closeQuietly(conn);
		}
		
		return cu;
		
	}
	
	public static int updateCrabUpdateResult(String result, int idcrabupdate){
		String sql = "UPDATE " + CrabUpdate.TABLE
				   + " SET " + CrabUpdate.RESULT + " = ? "
				   + " WHERE " + CrabUpdate.IDCRABUPDATE + " ?;";
		
		Connection conn = DBPool.getInstance().getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		CrabUpdate cu = null;
		int affectedRows = -1;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, result);
			pstmt.setInt(2, idcrabupdate);
			
			affectedRows = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DbUtils.closeQuietly(rs);
			DbUtils.closeQuietly(pstmt);
			DbUtils.closeQuietly(conn);
		}
		
		return affectedRows;
	}
	
}

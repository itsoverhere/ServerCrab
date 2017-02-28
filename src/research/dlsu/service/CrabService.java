package research.dlsu.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.commons.dbutils.DbUtils;

import research.dlsu.db.DBPool;
import research.dlsu.model.Crab;


public class CrabService {
	
	public long insertNewCrab(Crab crab){
		Connection conn = DBPool.getInstance().getConnection();
		PreparedStatement pstmt = null;
		long id = -1;
		try {
			pstmt = conn.prepareStatement(
					"INSERT INTO " + Crab.TABLE + "("
					+ Crab.CITY + ", " 
					+ Crab.FARM + ", "
					+ Crab.LATITUDE + ", "
					+ Crab.LONGITUDE + ", "
					+ Crab.PHONEIDCRAB + ", "
					+ Crab.RESULT + ", "
					+ Crab.STATUS + ", "
					+ Crab.TAG + ", "
					+ Crab.WEIGHT + ","
					+ Crab.IDONSITEUSER
					+ ") "
					+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);",
					Statement.RETURN_GENERATED_KEYS
					);
			
			pstmt.setString(1, crab.getCity());
			pstmt.setString(2, crab.getFarm());
			pstmt.setDouble(3, crab.getLatitude());
			pstmt.setDouble(4, crab.getLongitude());
			pstmt.setInt(5, crab.getPhoneidcrab());
			pstmt.setString(6, crab.getResult());
			pstmt.setString(7, crab.getStatus());
			pstmt.setString(8, crab.getTag());
			pstmt.setDouble(9, crab.getWeight());
			
			pstmt.setInt(10, crab.getIdonsiteuser());
			
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
	
	public ArrayList<Crab> getAllCrabs(){
		ArrayList<Crab> crabList = new ArrayList<Crab>();
		
		Connection conn = DBPool.getInstance().getConnection();
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement(
					"SELECT * FROM " + Crab.TABLE + ";"
					// + " WHERE " + Crab.IDCRAB + " = ?"
				);
			
			rs = pstmt.executeQuery();
			while(rs.next()){
				Crab c = new Crab();
				c.setId(rs.getInt(Crab.IDCRAB));
				c.setPhoneidcrab(rs.getInt(Crab.PHONEIDCRAB));
				c.setCity(rs.getString(Crab.CITY));
				c.setFarm(rs.getString(Crab.FARM));
				c.setLatitude(rs.getDouble(Crab.LATITUDE));
				c.setLongitude(rs.getDouble(Crab.LONGITUDE));
				c.setResult(rs.getString(Crab.RESULT));
				c.setStatus(rs.getString(Crab.STATUS));
				c.setTag(rs.getString(Crab.TAG));
				c.setWeight(rs.getDouble(Crab.WEIGHT));
				
				crabList.add(c);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DbUtils.closeQuietly(rs);
			DbUtils.closeQuietly(pstmt);
			DbUtils.closeQuietly(conn);
		}
		
		return crabList;
	}
	
	public boolean checkIfCrabHasResult(int idcrab){
		Connection conn = DBPool.getInstance().getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = null;
		
		try {
			pstmt = conn.prepareStatement(
					"SELECT " + Crab.RESULT + " FROM " + Crab.TABLE
					+ " WHERE " + Crab.RESULT + "=? "
					);
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				result = rs.getString(Crab.RESULT);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DbUtils.closeQuietly(rs);
			DbUtils.closeQuietly(pstmt);
			DbUtils.closeQuietly(conn);
		}
		
		if(result == null || result.trim().isEmpty()){
			return false;
		}else{
			return true;
		}
		
	}
	
	public boolean doesCrabExist(int idcrab){
		Connection conn = DBPool.getInstance().getConnection();
		int count = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
				
		try {
			pstmt = conn.prepareStatement(
					"SELECT COUNT(*) AS COUNT FROM " + Crab.TABLE + ";"
					+ " WHERE " + Crab.IDCRAB + " = ?"
				);
			pstmt.setInt(1, idcrab);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				count = rs.getInt("COUNT");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DbUtils.closeQuietly(rs);
			DbUtils.closeQuietly(pstmt);
			DbUtils.closeQuietly(conn);
		}
		
		if(count == 1){
			return true;
		}else if(count > 1){
			throw new UnknownError("There are more than one(1) entry for this id. There is something wrong in the database.");
		}else{
			return false;
		}
	}
	
	public Crab getCrabWithSNAndPhoneIdCrab(String serialNumber, int phoneIdCrab){
		Crab c = new Crab();
		Connection conn = DBPool.getInstance().getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int idonsiteuser = new OnSiteUserService().getIdFromIMEI(serialNumber);
		
		try {
			pstmt = conn.prepareStatement(
					"SELECT * FROM " + Crab.TABLE
					+ " WHERE " + Crab.IDONSITEUSER + " =?"
					+ " AND " + Crab.PHONEIDCRAB + "=? ;"
					);
			pstmt.setInt(1, idonsiteuser);
			pstmt.setInt(2, phoneIdCrab);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				// TODO
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DbUtils.closeQuietly(rs);
			DbUtils.closeQuietly(pstmt);
			DbUtils.closeQuietly(conn);
		}
		
		return c;
	}

	public int getIdWithSNAndPhoneIdCrab(String serialNumber, int phoneIdCrab){
		Connection conn = DBPool.getInstance().getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int idcrab = -1;
		
		int idonsiteuser = new OnSiteUserService().getIdFromIMEI(serialNumber);
		
		try {
			pstmt = conn.prepareStatement(
					"SELECT " + Crab.IDCRAB + " FROM " + Crab.TABLE
					+ " WHERE " + Crab.IDONSITEUSER + " =?"
					+ " AND " + Crab.PHONEIDCRAB + "=? ;"
					);
			pstmt.setInt(1, idonsiteuser);
			pstmt.setInt(2, phoneIdCrab);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				idcrab = rs.getInt(Crab.IDCRAB);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DbUtils.closeQuietly(rs);
			DbUtils.closeQuietly(pstmt);
			DbUtils.closeQuietly(conn);
		}
		
		return idcrab;
	}

	public String getResultForCrabWithSNAndPhoneIdCrab(String serialNumber, int phoneIdCrab){
		Connection conn = DBPool.getInstance().getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = null;
		
		int idonsiteuser = new OnSiteUserService().getIdFromIMEI(serialNumber);
		
		try {
			pstmt = conn.prepareStatement(
					"SELECT " + Crab.RESULT + " FROM " + Crab.TABLE
					+ " WHERE " + Crab.IDONSITEUSER + " =?"
					+ " AND " + Crab.PHONEIDCRAB + "=? ;"
					);
			pstmt.setInt(1, idonsiteuser);
			pstmt.setInt(2, phoneIdCrab);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				result = rs.getString(Crab.RESULT);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DbUtils.closeQuietly(rs);
			DbUtils.closeQuietly(pstmt);
			DbUtils.closeQuietly(conn);
		}
		
		return result;
	}
	
	public ArrayList<Crab> getResultsFromSNAndListPhoneIdCrab(String serialNumber, ArrayList<Integer> listPhoneIdCrab){
		ArrayList<Crab> listCrabResults = new ArrayList<Crab>();
		
		String phoneIdCrabs = "(" + listPhoneIdCrab.get(0);
		int ctr = 1;
		while(ctr < listPhoneIdCrab.size()){
			phoneIdCrabs += ", " + listPhoneIdCrab.get(ctr);
			ctr++;
		}
		phoneIdCrabs += ")";
		
		int idonsiteuser = new OnSiteUserService().getIdFromIMEI(serialNumber) ;
		
		Connection conn = DBPool.getInstance().getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(
					"SELECT " + Crab.PHONEIDCRAB + ", " + Crab.RESULT
					+ " FROM " + Crab.TABLE
					+ " WHERE " + Crab.IDONSITEUSER + " =? "
					   + " AND " + Crab.PHONEIDCRAB + " in " + phoneIdCrabs 
					);
			pstmt.setInt(1,  idonsiteuser);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				Crab c = new Crab();
				c.setPhoneidcrab(rs.getInt(Crab.PHONEIDCRAB));
				c.setResult(rs.getString(Crab.RESULT));
				listCrabResults.add(c);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DbUtils.closeQuietly(rs);
			DbUtils.closeQuietly(pstmt);
			DbUtils.closeQuietly(conn);
		}
		
		return listCrabResults;
	}
	
	public ArrayList<Crab> getAvailableResultsFromSNAndListPhoneIdCrab(String serialNumber, ArrayList<Integer> listPhoneIdCrab){
		ArrayList<Crab> listCrabResults = new ArrayList<Crab>();
		
		String phoneIdCrabs = "(" + listPhoneIdCrab.get(0);
		int ctr = 1;
		while(ctr < listPhoneIdCrab.size()){
			phoneIdCrabs += ", " + listPhoneIdCrab.get(ctr);
			ctr++;
		}
		phoneIdCrabs += ")";
		
		int idonsiteuser = new OnSiteUserService().getIdFromIMEI(serialNumber) ;
		
		Connection conn = DBPool.getInstance().getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(
					"SELECT " + Crab.PHONEIDCRAB + ", " + Crab.RESULT
					+ " FROM " + Crab.TABLE
					+ " WHERE " + Crab.IDONSITEUSER + " =? "
					   + " AND " + Crab.PHONEIDCRAB + " in " + phoneIdCrabs 
					);
			pstmt.setInt(1,  idonsiteuser);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				if(rs.getString(Crab.RESULT) != null){
					Crab c = new Crab();
					c.setPhoneidcrab(rs.getInt(Crab.PHONEIDCRAB));
					c.setResult(rs.getString(Crab.RESULT));
					listCrabResults.add(c);
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
		
		return listCrabResults;
	}
	
	public ArrayList<Crab> getAvailableResultsFromSNAndListPhoneIdCrab(String serialNumber, String phoneidcrabString){
		ArrayList<Crab> listCrabResults = new ArrayList<Crab>();
		
//		phoneidcrabString = "(" + phoneidcrabString + ")";
		
		int idonsiteuser = new OnSiteUserService().getIdFromIMEI(serialNumber) ;
		
		Connection conn = DBPool.getInstance().getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(
					"SELECT " + Crab.PHONEIDCRAB + ", " + Crab.RESULT
					+ " FROM " + Crab.TABLE
					+ " WHERE " + Crab.IDONSITEUSER + " =? "
					   + " AND " + Crab.PHONEIDCRAB + " in " + phoneidcrabString 
					);
			pstmt.setInt(1,  idonsiteuser);
			rs = pstmt.executeQuery();
			
			System.out.println(pstmt.toString());
			
			while(rs.next()){
				if(rs.getString(Crab.RESULT) != null){
					Crab c = new Crab();
					c.setPhoneidcrab(rs.getInt(Crab.PHONEIDCRAB));
					c.setResult(rs.getString(Crab.RESULT));
					listCrabResults.add(c);
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
		
		return listCrabResults;
	}
	
	public static void main(String[] args){
		ArrayList<Integer> listPhoneIdCrabs = new ArrayList<Integer>();
		listPhoneIdCrabs.add(1);
		listPhoneIdCrabs.add(3);
		String serialNumber="def";
		ArrayList<Crab> crabList = new CrabService().getAvailableResultsFromSNAndListPhoneIdCrab(serialNumber, listPhoneIdCrabs);
		
		for(Crab c: crabList){
			System.out.println(c.toString());
		}
	}
}

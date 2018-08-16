package org.unibl.etf.sni.api.mysql.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.unibl.etf.sni.api.mysql.dto.IdentityCardDto;
import org.unibl.etf.sni.api.util.ConnectionPool;



public class IdentityCardDao {
	private static final String SQL_SELECT_BY_DATE="SELECT * FROM identity_card WHERE valid_from=?";
	private static final String SQL_SELECT_BY_PID="SELECT i.* FROM identity_card i INNER JOIN user u ON i.user_id=u.id WHERE u.pid=?";
	private static final String SQL_INSERT="INSERT INTO identity_card VALUES (?,?,?,?,?,?)";
	private static final String SQL_SELECT_ALL="SELECT * FROM identity_card";
	
	public static List<IdentityCardDto> getAll() {
		PreparedStatement ps = null;
		Connection c = null;
		List<IdentityCardDto> result = new ArrayList<>();
		ResultSet rs = null;
		try {
			c = ConnectionPool.getInstance().checkOut();
			ps = c.prepareStatement(SQL_SELECT_ALL);
			rs = ps.executeQuery();
			while (rs.next()) {
				IdentityCardDto idc=new IdentityCardDto();
				idc.setId(rs.getInt("id"));
				idc.setSerial(rs.getString("serial"));
				idc.setStatus(rs.getBoolean("status"));
				idc.setUserId(rs.getInt("user_id"));
				idc.setValidFrom(rs.getDate("valid_from"));
				idc.setValidUntil(rs.getDate("valid_until"));
				result.add(idc);
			}
			ps.close();
		} catch (Exception exp) {
			exp.printStackTrace();
		} finally {
			ConnectionPool.close(ps);
			ConnectionPool.getInstance().checkIn(c);
		}
		return result;
	}
	
	
	public static boolean insert(IdentityCardDto idc) {
		PreparedStatement ps = null;
		Connection c = null;
		boolean inserted=false;
		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_INSERT;
			Object pom[] = { null,idc.getValidFrom(),idc.getValidUntil(),idc.getStatus(),idc.getUserId(),idc.getSerial()};
			ps = ConnectionPool.prepareStatement(c, query, true, pom);
			ps.executeUpdate();
			inserted=ps.getGeneratedKeys().next();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionPool.close(ps);
			ConnectionPool.getInstance().checkIn(c);
		}
		return inserted;
	}
	
	public static List<IdentityCardDto> getByDate(Date validFrom) {
		PreparedStatement ps = null;
		Connection c = null;
		List<IdentityCardDto> result = new ArrayList<>();
		ResultSet rs = null;
		try {
			c = ConnectionPool.getInstance().checkOut();
			Object pom[] = { validFrom };
			ps=ConnectionPool.prepareStatement(c, SQL_SELECT_BY_DATE, false, pom);
			rs = ps.executeQuery();
			while (rs.next()) {
				IdentityCardDto idc=new IdentityCardDto();
				idc.setId(rs.getInt("id"));
				idc.setSerial(rs.getString("serial"));
				idc.setStatus(rs.getBoolean("status"));
				idc.setUserId(rs.getInt("user_id"));
				idc.setValidFrom(rs.getDate("valid_from"));
				idc.setValidUntil(rs.getDate("valid_until"));
				result.add(idc);
			}
			ps.close();
		} catch (Exception exp) {
			exp.printStackTrace();
		} finally {
			ConnectionPool.close(ps);
			ConnectionPool.getInstance().checkIn(c);
		}
		return result;
	}
	public static List<IdentityCardDto> getByUid(String uid) {
		PreparedStatement ps = null;
		Connection c = null;
		List<IdentityCardDto> result = new ArrayList<>();
		ResultSet rs = null;
		try {
			c = ConnectionPool.getInstance().checkOut();
			Object pom[] = { uid};
			ps = ConnectionPool.prepareStatement(c,SQL_SELECT_BY_PID,false, pom);
			rs = ps.executeQuery();
			while (rs.next()) {
				IdentityCardDto idc=new IdentityCardDto();
				idc.setId(rs.getInt("id"));
				idc.setSerial(rs.getString("serial"));
				idc.setStatus(rs.getBoolean("status"));
				idc.setUserId(rs.getInt("user_id"));
				idc.setValidFrom(rs.getDate("valid_from"));
				idc.setValidUntil(rs.getDate("valid_until"));
				result.add(idc);
			}
			ps.close();
		} catch (Exception exp) {
			exp.printStackTrace();
		} finally {
			ConnectionPool.close(ps);
			ConnectionPool.getInstance().checkIn(c);
		}
		return result;
	}


}



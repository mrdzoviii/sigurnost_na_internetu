package org.unibl.etf.sni.adminapp.mysql.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.unibl.etf.sni.adminapp.mysql.dto.PassportDto;
import org.unibl.etf.sni.adminapp.util.ConnectionPool;

public class PassportDao {
	private static final String SQL_SELECT_BY_DATE="SELECT * FROM passport WHERE valid_from=? and status=1";
	private static final String SQL_SELECT_BY_UID="SELECT p.* FROM passport p INNER JOIN user u ON p.user_id=u.id WHERE u.pid=? and status=1";
	private static final String SQL_INSERT="INSERT INTO passport VALUES (?,?,?,?,?,?)";
private static final String SQL_UPDATE="UPDATE passport SET status=0 WHERE status=1 and user_id=?";
	
	
	public static boolean update(Integer userId) {
		PreparedStatement ps = null;
		Connection c = null;
		try {
			c = ConnectionPool.getInstance().checkOut();
			Object pom[] = {userId};
			ps = ConnectionPool.prepareStatement(c, SQL_UPDATE, false, pom);
			ps.executeUpdate();
			ps.close();
		} catch (Exception exp) {
			exp.printStackTrace();
		} finally {
			ConnectionPool.close(ps);
			ConnectionPool.getInstance().checkIn(c);
		}
		return true;
	}
	
	
	public static boolean insert(PassportDto idc) {
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
	
	public static List<PassportDto> getByDate(Date validFrom) {
		PreparedStatement ps = null;
		Connection c = null;
		List<PassportDto> result = new ArrayList<>();
		ResultSet rs = null;
		try {
			c = ConnectionPool.getInstance().checkOut();
			Object pom[] = { validFrom };
			ps=ConnectionPool.prepareStatement(c, SQL_SELECT_BY_DATE, false, pom);
			rs = ps.executeQuery();
			while (rs.next()) {
				PassportDto idc=new PassportDto();
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
	public static List<PassportDto> getByUid(String pid) {
		PreparedStatement ps = null;
		Connection c = null;
		List<PassportDto> result = new ArrayList<>();
		ResultSet rs = null;
		try {
			c = ConnectionPool.getInstance().checkOut();
			Object pom[] = { pid };
			ps = ConnectionPool.prepareStatement(c,SQL_SELECT_BY_UID,false, pom);
			rs = ps.executeQuery();
			while (rs.next()) {
				PassportDto idc=new PassportDto();
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



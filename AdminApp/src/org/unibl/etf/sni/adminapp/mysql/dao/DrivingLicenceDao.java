package org.unibl.etf.sni.adminapp.mysql.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.unibl.etf.sni.adminapp.mysql.dto.DrivingLicenceDto;
import org.unibl.etf.sni.adminapp.util.ConnectionPool;

public class DrivingLicenceDao {
	private static final String SQL_SELECT_BY_DATE="SELECT * FROM driving_licence WHERE valid_from=?";
	private static final String SQL_SELECT_BY_UID="SELECT i.* FROM driving_licence i INNER JOIN user u ON i.user_id=u.id WHERE u.pid=?";
	private static final String SQL_INSERT="INSERT INTO driving_licence VALUES (?,?,?,?,?,?)";
	
	
	
	public static boolean insert(DrivingLicenceDto idc) {
		PreparedStatement ps = null;
		Connection c = null;
		ResultSet rs=null;
		boolean inserted=false;
		try {
			c = ConnectionPool.getInstance().checkOut();
		
			Object pom[] = { null,idc.getValidFrom(),idc.getValidUntil(),idc.getStatus(),idc.getUserId(),idc.getSerial()};
			ps = ConnectionPool.prepareStatement(c, SQL_INSERT, true, pom);
			ps.executeUpdate();
			rs=ps.getGeneratedKeys();
			inserted=rs.next();
			if(inserted) {
				System.out.println(rs.getLong(1));
				DrivingLicenceCategoryDao.batchInsert(rs.getLong(1),idc.getCategories());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionPool.close(ps);
			ConnectionPool.getInstance().checkIn(c);
		}
		return inserted;
	}
	
	public static List<DrivingLicenceDto> getByDate(Date validFrom) {
		PreparedStatement ps = null;
		Connection c = null;
		List<DrivingLicenceDto> result = new ArrayList<>();
		ResultSet rs = null;
		try {
			c = ConnectionPool.getInstance().checkOut();
			Object pom[] = { validFrom };
			ps=ConnectionPool.prepareStatement(c, SQL_SELECT_BY_DATE, false, pom);
			rs = ps.executeQuery();
			while (rs.next()) {
				DrivingLicenceDto idc=new DrivingLicenceDto();
				idc.setId(rs.getInt("id"));
				idc.setSerial(rs.getString("serial"));
				idc.setStatus(rs.getBoolean("status"));
				idc.setUserId(rs.getInt("user_id"));
				idc.setValidFrom(rs.getDate("valid_from"));
				idc.setValidUntil(rs.getDate("valid_until"));
				idc.setCategories(DrivingLicenceCategoryDao.getByLicenceId(idc.getId()));
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
	public static List<DrivingLicenceDto> getByUid(String uid) {
		PreparedStatement ps = null;
		Connection c = null;
		List<DrivingLicenceDto> result = new ArrayList<>();
		ResultSet rs = null;
		try {
			c = ConnectionPool.getInstance().checkOut();
			Object pom[] = { uid };
			ps = ConnectionPool.prepareStatement(c,SQL_SELECT_BY_UID,false, pom);
			rs = ps.executeQuery();
			while (rs.next()) {
				DrivingLicenceDto idc=new DrivingLicenceDto();
				idc.setId(rs.getInt("id"));
				idc.setSerial(rs.getString("serial"));
				idc.setStatus(rs.getBoolean("status"));
				idc.setUserId(rs.getInt("user_id"));
				idc.setValidFrom(rs.getDate("valid_from"));
				idc.setValidUntil(rs.getDate("valid_until"));
				idc.setCategories(DrivingLicenceCategoryDao.getByLicenceId(idc.getId()));
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



package org.unibl.etf.sni.adminapp.mysql.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.unibl.etf.sni.adminapp.mysql.dto.DrivingLicenceCategoryDto;
import org.unibl.etf.sni.adminapp.util.ConnectionPool;

public class DrivingLicenceCategoryDao {
	private static final String SQL_SELECT_BY_DRIVING_LICENCE="SELECT * FROM driving_licence_has_category WHERE driving_licence_id=? and banned=0";
	private static final String SQL_INSERT="INSERT INTO driving_licence_has_category VALUES (?,?,?,?)";
	public static List<DrivingLicenceCategoryDto> getByLicenceId(Integer id) {
		PreparedStatement ps = null;
		Connection c = null;
		List<DrivingLicenceCategoryDto> result = new ArrayList<>();
		ResultSet rs = null;
		try {
			c = ConnectionPool.getInstance().checkOut();
			Object pom[] = { id };
			ps = ConnectionPool.prepareStatement(c,SQL_SELECT_BY_DRIVING_LICENCE,false, pom);
			rs = ps.executeQuery();
			while (rs.next()) {
				DrivingLicenceCategoryDto dl=new DrivingLicenceCategoryDto();
				dl.setBanned(rs.getBoolean("banned"));
				dl.setCategoryId(rs.getInt("category_id"));
				dl.setDrivingLicenceId(rs.getInt("driving_licence_id"));
				dl.setValidFrom(rs.getDate("valid_from"));
				result.add(dl);
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
	
	public static boolean batchInsert(Long id,List<DrivingLicenceCategoryDto> categories) {
		PreparedStatement ps = null;
		Connection c = null;
		boolean inserted=false;
		try {
			c = ConnectionPool.getInstance().checkOut();
			c.setAutoCommit(false);
			ps=c.prepareStatement(SQL_INSERT);
			for(DrivingLicenceCategoryDto dl:categories)
			{
				ps.setInt(1,id.intValue());
				ps.setInt(2, dl.getCategoryId());
				ps.setDate(3, new java.sql.Date(dl.getValidFrom().getTime()));
				ps.setBoolean(4, dl.getBanned());
				ps.addBatch();
			}
			int[] result=ps.executeBatch();
			inserted=result.length==categories.size();
			c.commit();
			System.out.println("DONEEEE ");
		} catch (SQLException e) {
			try {
				c.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			
		} finally {
			ConnectionPool.close(ps);
			ConnectionPool.getInstance().checkIn(c);
		}
		return inserted;
	}
		 
}

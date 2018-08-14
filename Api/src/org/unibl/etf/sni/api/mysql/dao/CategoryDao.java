package org.unibl.etf.sni.api.mysql.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.unibl.etf.sni.api.mysql.dto.CategoryDto;
import org.unibl.etf.sni.api.util.ConnectionPool;

public class CategoryDao {
	private static final String SQL_SELECT= "SELECT * FROM category";
	private static final String SQL_SELECT_BY_ID="SELECT * FROM category WHERE id=?";

	
	
	
	public static List<CategoryDto> getAll() {
		PreparedStatement ps = null;
		Connection c = null;
		List<CategoryDto> result = new ArrayList<>();
		ResultSet rs = null;
		try {
			c = ConnectionPool.getInstance().checkOut();
			ps = c.prepareStatement(SQL_SELECT);
			rs = ps.executeQuery();
			while (rs.next()) {
				CategoryDto category=new CategoryDto();
				category.setId(rs.getInt("id"));
				category.setCategory(rs.getString("category"));
				result.add(category);
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
	public static CategoryDto getById(Integer id) {
		PreparedStatement ps = null;
		Connection c = null;
		CategoryDto category = null;
		ResultSet rs = null;
		try {
			c = ConnectionPool.getInstance().checkOut();
			Object pom[] = { id };
			ps = ConnectionPool.prepareStatement(c,SQL_SELECT_BY_ID,false, pom);
			rs = ps.executeQuery();
			if (rs.next()) {
				category=new CategoryDto();
				category.setId(rs.getInt("id"));
				category.setCategory(rs.getString("category"));
			}
			ps.close();
		} catch (Exception exp) {
			exp.printStackTrace();
		} finally {
			ConnectionPool.close(ps);
			ConnectionPool.getInstance().checkIn(c);
		}
		return category;
	}
}

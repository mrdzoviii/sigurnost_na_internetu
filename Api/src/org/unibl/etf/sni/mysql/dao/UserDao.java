package org.unibl.etf.sni.mysql.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.unibl.etf.sni.mysql.dto.UserDto;
import org.unibl.etf.sni.util.ConnectionPool;

public class UserDao {
	private static final String SQL_SELECT = "SELECT * FROM user";
	private static final String SQL_SELECT_BY_USERNAME="SELECT * FROM user WHERE username=?";
	private static final String SQL_SELECT_BY_ID="SELECT * FROM user WHERE id=?";
	
	public static List<UserDto> getAll() {
		PreparedStatement ps = null;
		Connection c = null;
		List<UserDto> result = new ArrayList<>();
		ResultSet rs = null;
		try {
			c = ConnectionPool.getInstance().checkOut();
			ps = c.prepareStatement(SQL_SELECT);
			rs = ps.executeQuery();
			while (rs.next()) {
				UserDto user=new UserDto();
				user.setId(rs.getInt("id"));
				user.setPassword(rs.getString("password"));
				user.setUsername(rs.getString("username"));
				user.setEmail(rs.getString("email"));
				user.setDateOfBirth(rs.getDate("date_of_birth"));
				user.setPlaceOfBirth(rs.getString("place_of_birth"));
				user.setPid(rs.getString("pid"));
				user.setResidence(rs.getString("residence"));
				user.setSex(rs.getBoolean("sex"));
				user.setName(rs.getString("name"));
				user.setSurname(rs.getString("surname"));
				user.setAdmin(rs.getBoolean("admin"));
				result.add(user);
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
	public static UserDto getByUsername(String username) {
		PreparedStatement ps = null;
		Connection c = null;
		UserDto user = null;
		ResultSet rs = null;
		try {
			c = ConnectionPool.getInstance().checkOut();
			Object pom[] = { username };
			ps = ConnectionPool.prepareStatement(c,SQL_SELECT_BY_USERNAME,false, pom);
			rs = ps.executeQuery();
			if (rs.next()) {
				user=new UserDto();
				user.setId(rs.getInt("id"));
				user.setPassword(rs.getString("password"));
				user.setUsername(rs.getString("username"));
				user.setEmail(rs.getString("email"));
				user.setDateOfBirth(rs.getDate("date_of_birth"));
				user.setPlaceOfBirth(rs.getString("place_of_birth"));
				user.setPid(rs.getString("pid"));
				user.setResidence(rs.getString("residence"));
				user.setSex(rs.getBoolean("sex"));
				user.setName(rs.getString("name"));
				user.setSurname(rs.getString("surname"));
				user.setAdmin(rs.getBoolean("admin"));
			}
			ps.close();
		} catch (Exception exp) {
			exp.printStackTrace();
		} finally {
			ConnectionPool.close(ps);
			ConnectionPool.getInstance().checkIn(c);
		}
		return user;
	}
	
	public static UserDto getById(Integer id) {
		PreparedStatement ps = null;
		Connection c = null;
		UserDto user = null;
		ResultSet rs = null;
		try {
			c = ConnectionPool.getInstance().checkOut();
			Object pom[] = { id };
			ps = ConnectionPool.prepareStatement(c,SQL_SELECT_BY_ID,false, pom);
			rs = ps.executeQuery();
			if (rs.next()) {
				user=new UserDto();
				user.setId(rs.getInt("id"));
				user.setPassword(rs.getString("password"));
				user.setUsername(rs.getString("username"));
				user.setEmail(rs.getString("email"));
				user.setDateOfBirth(rs.getDate("date_of_birth"));
				user.setPlaceOfBirth(rs.getString("place_of_birth"));
				user.setPid(rs.getString("pid"));
				user.setResidence(rs.getString("residence"));
				user.setSex(rs.getBoolean("sex"));
				user.setName(rs.getString("name"));
				user.setSurname(rs.getString("surname"));
				user.setAdmin(rs.getBoolean("admin"));
			}
			ps.close();
		} catch (Exception exp) {
			exp.printStackTrace();
		} finally {
			ConnectionPool.close(ps);
			ConnectionPool.getInstance().checkIn(c);
		}
		return user;
	}
}

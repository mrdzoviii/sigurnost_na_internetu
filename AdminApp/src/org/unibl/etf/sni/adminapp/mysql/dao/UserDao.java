package org.unibl.etf.sni.adminapp.mysql.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.unibl.etf.sni.adminapp.mysql.dto.UserDto;
import org.unibl.etf.sni.adminapp.util.ConnectionPool;

public class UserDao {
	private static final String SQL_SELECT = "SELECT * FROM user";
	private static final String SQL_SELECT_BY_USERNAME="SELECT * FROM user WHERE username=?";
	private static final String SQL_SELECT_BY_ID="SELECT * FROM user WHERE id=?";
	private static final String SQL_SELECT_BY_PID="SELECT * FROM user WHERE pid=?";
	private static final String SQL_SELECT_PERSON_ID="SELECT pid from user where pid like ?";
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
	
	public static List<String> getAllPids(String query) {
		PreparedStatement ps = null;
		Connection c = null;
		String tmp="%";
		if(query.matches("^[0-9]+$")) {
			tmp=query+"%";
		}else {
			return null;
		}
		List<String> result = new ArrayList<>();
		ResultSet rs = null;
		try {
			c = ConnectionPool.getInstance().checkOut();
			Object pom[] = { tmp};
			ps = ConnectionPool.prepareStatement(c,SQL_SELECT_PERSON_ID,false, pom);
			rs = ps.executeQuery();
			while (rs.next()) {
				result.add(rs.getString("pid"));
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
	
	public static UserDto getByPid(String pid) {
		PreparedStatement ps = null;
		Connection c = null;
		UserDto user = null;
		ResultSet rs = null;
		try {
			c = ConnectionPool.getInstance().checkOut();
			Object pom[] = { pid };
			ps = ConnectionPool.prepareStatement(c,SQL_SELECT_BY_PID,false, pom);
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

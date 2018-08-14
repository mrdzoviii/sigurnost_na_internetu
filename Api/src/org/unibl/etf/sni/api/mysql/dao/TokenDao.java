package org.unibl.etf.sni.api.mysql.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.unibl.etf.sni.api.mysql.dto.TokenDto;
import org.unibl.etf.sni.api.util.ConnectionPool;

public class TokenDao {
	private static final String SQL_SELECT = "SELECT * FROM token";
	private static final String SQL_SELECT_BY_USER_ID="SELECT * FROM token WHERE user_id=?";
	private static final String SQL_UPDATE="UPDATE token SET token=?,valid_until=? WHERE user_id=?";
	private static final String SQL_INSERT="INSERT INTO token values (?,?,?,?)";
	
	public static boolean update(TokenDto token) {
		PreparedStatement ps = null;
		Connection c = null;
		boolean result=false;
		try {
			c = ConnectionPool.getInstance().checkOut();
			Object pom[] = {token.getToken(),token.getValidUntil(),token.getUserId() };
			ps = ConnectionPool.prepareStatement(c, SQL_UPDATE, false, pom);
			int rows=ps.executeUpdate();
			result=rows>0;
			ps.close();
		} catch (Exception exp) {
			exp.printStackTrace();
		} finally {
			ConnectionPool.close(ps);
			ConnectionPool.getInstance().checkIn(c);
		}
		return result;
	}

	public static boolean insert(TokenDto token) {
		PreparedStatement ps = null;
		Connection c = null;
		boolean inserted=false;
		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_INSERT;
			Object pom[] = { null,token.getToken(),token.getValidUntil(),token.getUserId()};
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
	
	public static List<TokenDto> getAll() {
		PreparedStatement ps = null;
		Connection c = null;
		List<TokenDto> result = new ArrayList<>();
		ResultSet rs = null;
		try {
			c = ConnectionPool.getInstance().checkOut();
			ps = c.prepareStatement(SQL_SELECT);
			rs = ps.executeQuery();
			while (rs.next()) {
				TokenDto token=new TokenDto();
				token.setId(rs.getInt("id"));
				token.setValidUntil(rs.getTimestamp("valid_until"));
				token.setUserId(rs.getInt("user_id"));
				token.setToken(rs.getString("token"));
				result.add(token);
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
	public static TokenDto getByUserId(Integer userId) {
		PreparedStatement ps = null;
		Connection c = null;
		TokenDto token = null;
		ResultSet rs = null;
		try {
			c = ConnectionPool.getInstance().checkOut();
			Object pom[] = { userId };
			ps = ConnectionPool.prepareStatement(c,SQL_SELECT_BY_USER_ID,false, pom);
			rs = ps.executeQuery();
			if (rs.next()) {
				token=new TokenDto();
				token.setId(rs.getInt("id"));
				token.setValidUntil(rs.getTimestamp("valid_until"));
				token.setUserId(rs.getInt("user_id"));
				token.setToken(rs.getString("token"));
			}
			ps.close();
		} catch (Exception exp) {
			exp.printStackTrace();
		} finally {
			ConnectionPool.close(ps);
			ConnectionPool.getInstance().checkIn(c);
		}
		return token;
	}
}

package jp.sljacademy.bbs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import jp.sljacademy.bbs.bean.ColorMasterBean;

public class ColorMasterDao {
	private DataSource source;
	
	// ()の中に何もなかったら引数はいらない。NamingExceptionを投げてくる可能性がある
	public ColorMasterDao() throws NamingException {
		InitialContext context = new InitialContext();
		source = (DataSource)
		context.lookup("java:comp/env/jdbc/datasource");
	}
	
	public List<ColorMasterBean> getAllColors() throws SQLException {
		Connection connection = source.getConnection();
		List<ColorMasterBean> colors = new ArrayList<>();
		String sql = "SELECT COLOR_ID, COLOR_CODE, COLOR_NAME FROM COLOR_MASTER";
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				ColorMasterBean color = new ColorMasterBean();
				color.setColorId(resultSet.getString("COLOR_ID"));
				color.setColorCode(resultSet.getString("COLOR_CODE"));
				color.setColorName(resultSet.getString("COLOR_NAME"));
				colors.add(color);
			}
			// ステートメントを閉じる
			statement.close();
		} catch (SQLException e) {
			// e=発生した例外をそのまま投げる
			throw e;
		} finally {
			// データベース接続をクローズ
			if (connection != null) {
				connection.close();
			}
		}
		return colors;
	}
	
	public String getColorCode(String colorId) throws SQLException {
		
		Connection connection = source.getConnection();
		String color = "";
		
		String sql = "SELECT COLOR_CODE FROM COLOR_MASTER WHERE COLOR_ID = ?;";
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setString(1, colorId);
			
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				color = resultSet.getString("COLOR_CODE");
			}
			statement.close();
		} catch (SQLException e) {
			throw e;
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
		return color;
	}
}
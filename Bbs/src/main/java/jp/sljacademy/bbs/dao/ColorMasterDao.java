package jp.sljacademy.bbs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import jp.sljacademy.bbs.bean.ColorMasterBean;

// ColorMasterテーブルを操作する

public class ColorMasterDao {
	
	private DataSource source;
	
	public ColorMasterDao() throws NamingException {
		InitialContext context = new InitialContext();
		source = (DataSource)
			context.lookup("java:comp/env/jdbc/datasource");
	}
	
	// テーブルから色情報を取得するメソッド
	public ColorMasterBean getColorInformation() throws SQLException {
		ColorMasterBean colorMasterBean = new ColorMasterBean();
		
		// データベースへの接続を確立します
		Connection connection = source.getConnection();
		
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM COLOR_MASTER");
			ResultSet resultSet = preparedStatement.executeQuery();
			
			
		} catch (SQLException e) {
				e.printStackTrace(); // エラーハンドリングは適切に行うべき
		}
		return colorMasterBean;
	}
}

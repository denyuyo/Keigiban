package jp.sljacademy.bbs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import jp.sljacademy.bbs.bean.AccountBean;

// Accountテーブルを操作する

public class AccountDao {
	
	private DataSource source;
	private static final String SELECT = "select * from ACCOUNT where USER_ID=? and USER_PASS=?";
	
	public AccountDao() throws NamingException {
		InitialContext context = new InitialContext();
		source = (DataSource)context.lookup("java:comp/env/jdbc/datasource");
	}
	
	public AccountBean getAccount(String id, String password) throws SQLException {
		// アカウント情報を格納するための変数を初期化します
		AccountBean account = null;
		
		// データベースへの接続を確立します
		Connection connection = source.getConnection();
		
		try {
			// SQLクエリの準備
			PreparedStatement statement = connection.prepareStatement(SELECT);
			
			statement.setString(1, id);
			statement.setString(2, password);
			
			// クエリを実行し、結果を取得します
			ResultSet result = statement.executeQuery();
			
			// 結果が存在する場合、アカウント情報を取得して変数に格納します
			while (result.next()) {
				// アカウントBeanのインスタンスを生成します
				account = new AccountBean();
				
				// データベースの結果から、USER_IDとUSER_PASSなどを取得し、アカウントBeanに設定してる
				account.setId(result.getString("USER_ID"));
				account.setPassword(result.getString("USER_PASS"));
				account.setName(result.getString("USER_NAME")); 
	            account.setEmail(result.getString("EMAIL"));
			}
			
			// ステートメントを閉じる
			statement.close();
			
		} catch (SQLException e) {
			// エラーが発生した場合、エラー内容を出す
			e.printStackTrace();
			
		} finally {
			// データベース接続をクローズ
			if (connection != null) {
				connection.close();
			}
		}
		// 最終的に取得したアカウント情報を返す
		return account;
	}
}
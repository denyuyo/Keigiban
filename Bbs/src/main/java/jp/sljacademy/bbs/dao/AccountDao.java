package jp.sljacademy.bbs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import jp.sljacademy.bbs.bean.AccountBean;

// Accountテーブルを操作するクラス

public class AccountDao {
	
	private DataSource source;
	// 「ACCOUNT テーブルから、指定されたユーザー情報を持つ行のすべての列を選択する」
	private static final String SELECT = "select USER_ID, USER_NAME, EMAIL from ACCOUNT where USER_ID=? and USER_PASS=?";
	
	// コンストラクタ：データベース接続の準備を行う
	public AccountDao() throws NamingException {
		InitialContext context = new InitialContext();
		// データソース（データベース接続情報）を取得
		source = (DataSource)context.lookup("java:comp/env/jdbc/datasource");
	}
	
	// 指定されたIDとパスワードを持つアカウントをデータベースから取得
	public AccountBean getAccount(String id, String password) throws SQLException {
		/// アカウント情報を格納するための変数を初期化
		AccountBean account = null;
		
		// データベースへの接続を確立
		Connection connection = source.getConnection();
		
		try {
			// SQLクエリの準備：指定されたIDとパスワードでアカウントを検索
			PreparedStatement statement = connection.prepareStatement(SELECT);
			
			// プレースホルダー(?)に対応する位置に実際の値（idとpassword）を設定
			statement.setString(1, id);
			statement.setString(2, password);
			
			// SQLクエリを実行し、その結果を result 変数に格納
			ResultSet result = statement.executeQuery();
			
			// ResultSet内の次の行にデータが存在するかどうかを確認し、存在する場合にループを実行
			while (result.next()) {
				// AccountBeanのインスタンスを生成し、データベースの結果から情報を取得して設定
				account = new AccountBean();
				account.setId(result.getString("USER_ID"));
				account.setName(result.getString("USER_NAME")); 
	            account.setEmail(result.getString("EMAIL"));
			}
			statement.close();
			
		} catch (SQLException e) {
			throw e;
			
		} finally {
			// データベース接続を閉じる
			if (connection != null) {
				connection.close();
			}
		}
		// 呼び出し元のクラスなどに返却できるように、取得したアカウント情報を返す
		return account;
	}
}
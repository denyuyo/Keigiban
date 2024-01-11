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
	// 「ACCOUNT テーブルから、指定されたユーザーID (USER_ID) とパスワード (USER_PASS) を持つ行のすべての列を選択する」
	private static final String SELECT = "select * from ACCOUNT where USER_ID=? and USER_PASS=?";
	
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
			
			// クエリのパラメータを設定
			statement.setString(1, id);
			statement.setString(2, password);
			
			// クエリを実行し、結果を取得
			ResultSet result = statement.executeQuery();
			
			// 結果が存在する場合、アカウント情報を取得して変数に格納
			while (result.next()) {
				// アカウントBeanのインスタンスを生成し、データベースの結果から情報を取得して設定
				account = new AccountBean();
				account.setId(result.getString("USER_ID"));
				account.setPassword(result.getString("USER_PASS"));
				account.setName(result.getString("USER_NAME")); 
	            account.setEmail(result.getString("EMAIL"));
			}
			// ステートメントを閉じる
			statement.close();
			
		} catch (SQLException e) {
			// エラーが発生した場合、エラー内容を出力
			e.printStackTrace();
			
		} finally {
			// データベース接続を閉じる
			if (connection != null) {
				connection.close();
			}
		}
		// 最終的に取得したアカウント情報を返す
		return account;
	}
}
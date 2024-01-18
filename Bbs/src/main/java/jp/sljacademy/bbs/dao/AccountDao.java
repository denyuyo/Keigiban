package jp.sljacademy.bbs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.NamingException;

import jp.sljacademy.bbs.bean.AccountBean;
import jp.sljacademy.bbs.util.DbSource;

// Accountテーブルを操作するクラス

public class AccountDao {
	
	// 指定されたIDとパスワードを持つアカウントをデータベースから取得
	public AccountBean getAccount(String id, String password) throws SQLException , NamingException {
		/// アカウント情報を格納するための変数を初期化
		AccountBean account = null;
		
		Connection connection = null;
		
		// ACCOUNT テーブルから、指定されたユーザー情報を持つ行のすべての列を選択する
		String sql = "SELECT USER_ID, USER_NAME, EMAIL FROM ACCOUNT WHERE USER_ID=? AND USER_PASS=?";
		try {
			// データベースへの接続を確立
			connection = DbSource.getDateSource().getConnection();
			
			// SQL文を準備して、指定されたIDとパスワードでアカウントを検索
			PreparedStatement statement = connection.prepareStatement(sql);
			
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
		} catch (Exception e) {
			// 例外がある場合は投げる
			e.printStackTrace();
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
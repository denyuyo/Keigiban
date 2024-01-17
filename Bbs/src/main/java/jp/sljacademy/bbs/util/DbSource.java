package jp.sljacademy.bbs.util;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

// DataSourceを一つだけにしたい

public class DbSource {
	// データベースへの接続情報を格納
	private static DataSource dataSource;
	
	// コンストラクタ
	private DbSource() {
	}
	
	// シングルトンインスタンスを取得するメソッド
	public static synchronized DataSource getDateSource() throws NamingException {
		if (dataSource == null) {
			// コンテキストを取得し、データソースを初期化
			InitialContext context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:comp/env/jdbc/datasource"); 
		}
		return dataSource;
	}
}
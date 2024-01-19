package jp.sljacademy.bbs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.NamingException;

import jp.sljacademy.bbs.bean.ArticleBean;
import jp.sljacademy.bbs.util.DbSource;

// 記事情報をデータベースで扱うためのクラス

public class ArticleDao {
	
	
	// 作成した記事をデータベースに追加しようとしている
	public void createArticle(ArticleBean article) throws SQLException, NamingException {
		Connection connection = null;
		
		// 指定された記事の詳細情報と現在の日時を ARTICLE テーブルに新しい行として挿入する
		String sql = 
			"INSERT INTO ARTICLE ("
			+ 	"CREATE_DATE, "
			+ 	"NAME, "
			+ 	"EMAIL, "
			+ 	"TITLE, "
			+ 	"TEXT, "
			+ 	"COLOR_ID, "
			+ 	"DEL_FLG "
			+ ") VALUES ("
			+ 	"NOW(),  "
			+ 	"?, "
			+ 	"?, "
			+ 	"?, "
			+ 	"?, "
			+ 	"?, "
			+ 	"0"
			+ ");";
		
		try {
			// データベースへの接続を確立
			connection = DbSource.getDateSource().getConnection();
			// SQL文を準備して、値を設定
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, article.getName());
			statement.setString(2, article.getEmail());
			statement.setString(3, article.getTitle());
			statement.setString(4, article.getText());
			statement.setString(5, article.getColorId());
			// SQL文を実行
			statement.executeUpdate();
			statement.close();
		} catch (Exception e) {
			// 例外がある場合は投げる
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}
	
	// データベースからすべての記事を取得
	public ArrayList<ArticleBean> getAllArticles() throws SQLException, NamingException {
		Connection connection = null;
		
		ArrayList<ArticleBean> articles = new ArrayList<ArticleBean>();
		// ARTICLE テーブルと COLOR_MASTER テーブルのCOLOR_IDが一致した場合、結合して COLOR_MASTER テーブル の COLOR_CODEを取得
		String sql = 
			"SELECT "
			+ 	"ARTICLE.ARTICLE_ID,"
			+ 	"ARTICLE.CREATE_DATE, "
			+ 	"ARTICLE.NAME,"
			+ 	"ARTICLE.EMAIL,"
			+ 	"ARTICLE.TITLE,"
			+ 	"ARTICLE.TEXT,"
			+ 	"ARTICLE.COLOR_ID,"
			+ 	"COLOR_MASTER.COLOR_CODE "
			+ "FROM "
			+ 	"ARTICLE "
			+ "JOIN "
			+ 	"COLOR_MASTER "
			+ "ON "
			+ 	"ARTICLE.COLOR_ID = COLOR_MASTER.COLOR_ID "
			+ "ORDER BY "
			// 降順で取得
			+ 	"ARTICLE.CREATE_DATE DESC;";
		
		try {
			// データベース接続を確立
			connection = DbSource.getDateSource().getConnection();
			// SQLクエリをセットして、実行する
			PreparedStatement statement = connection.prepareStatement(sql);
			
			ResultSet resultSet = statement.executeQuery();
			
			//　記事情報に必要な値をセットしています
			while (resultSet.next()) {
				ArticleBean article = new ArticleBean();
				article.setArticleId(resultSet.getInt("ARTICLE_ID"));
				article.setCreateDate(resultSet.getTimestamp("CREATE_DATE"));
				article.setName(resultSet.getString("NAME"));
				article.setEmail(resultSet.getString("EMAIL"));
				article.setTitle(resultSet.getString("TITLE"));
				article.setText(resultSet.getString("TEXT"));
				article.setColorId(resultSet.getString("COLOR_ID"));
				article.setColorCode(resultSet.getString("COLOR_CODE"));
				articles.add(article);
			}
			statement.close();
		} catch (Exception e) {
			// 例外がある場合は投げる
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
		// 最後に記事情報を呼び出し元に返す
		return articles;
	}	
}
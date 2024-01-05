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

import jp.sljacademy.bbs.bean.ArticleBean;

// 記事情報のデータベース

public class ArticleDao {
	private DataSource source;
	
	// ()の中に何もなかったら引数はいらない。NamingExceptionを投げてくる可能性がある
	public ArticleDao() throws NamingException {
		InitialContext context = new InitialContext();
		source = (DataSource)context.lookup("java:comp/env/jdbc/datasource");
	}
	
	public void createArticle(ArticleBean article) throws SQLException {
		Connection connection = source.getConnection();
		String sql = "INSERT INTO articles (name, email, title, text, color) VALUES (?, ?, ?, ?, ?)";
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, article.getName());
			statement.setString(2, article.getEmail());
			statement.setString(3, article.getTitle());
			statement.setString(4, article.getText());
			statement.setString(5, article.getColorId());
			statement.executeUpdate();
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
	}
	
	public List<ArticleBean> getAllArticles() throws SQLException {
		Connection connection = source.getConnection();
		List<ArticleBean> articles = new ArrayList<>();
		String sql = "SELECT name, email, title, text, color FROM articles";
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery(); 
			while (resultSet.next()) {
				ArticleBean article = new ArticleBean();
				article.setName(resultSet.getString("name"));
				article.setEmail(resultSet.getString("email"));
				article.setTitle(resultSet.getString("title"));
				article.setText(resultSet.getString("text"));
				article.setColorId(resultSet.getString("color"));
				articles.add(article);
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
		return articles;
	}
	
}

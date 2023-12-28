package jp.sljacademy.bbs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.sljacademy.bbs.bean.ArticleBean;

// 記事情報のデータベース

public class ArticleDao {
	private Connection connection;
	
	public ArticleDao(Connection connection) {
		this.connection = connection;
	}
	
	public void createArticle(ArticleBean article) throws SQLException {
		String sql = "INSERT INTO articles (name, email, title, text, color) VALUES (?, ?, ?, ?, ?)";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, article.getName());
			statement.setString(2, article.getEmail());
			statement.setString(3, article.getTitle());
			statement.setString(4, article.getText());
			statement.setString(5, article.getColorId());
			statement.executeUpdate();
		}
	}
	
	public List<ArticleBean> getAllArticles() throws SQLException {
		List<ArticleBean> articles = new ArrayList<>();
		String sql = "SELECT name, email, title, text, color FROM articles";
		try (PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				ArticleBean article = new ArticleBean();
				article.setName(resultSet.getString("name"));
				article.setEmail(resultSet.getString("email"));
				article.setTitle(resultSet.getString("title"));
				article.setText(resultSet.getString("text"));
				article.setColorId(resultSet.getString("color"));
				articles.add(article);
			}
		}
		return articles;
	}

	// 他のCRUD操作に関連するメソッド...
}

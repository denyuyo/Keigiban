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

// 記事情報をデータベースで扱うためのクラス

public class ArticleDao {
	private DataSource source;
	
	// ()の中に何もなかったら引数はいらない。NamingExceptionを投げてくる可能性がある
	public ArticleDao() throws NamingException {
		InitialContext context = new InitialContext();
		// データソース（データベース接続情報）を取得
		source = (DataSource)context.lookup("java:comp/env/jdbc/datasource");
	}
	
	// 記事をデータベースに追加するメソッド
	public void createArticle(ArticleBean article) throws SQLException {
		// データベースへの接続を確立
		Connection connection = source.getConnection();
		//「現在の日時と指定された記事の詳細情報（名前、メールアドレス、タイトル、テキスト、色ID）を article テーブルに新しい行として挿入する」
		String sql = "INSERT INTO article (create_date, name, email, title, text, color_id) VALUES (NOW(), ?, ?, ?, ?, ?)";
		try {
			// SQL文を準備し、値を設定
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, article.getName());
			statement.setString(2, article.getEmail());
			statement.setString(3, article.getTitle());
			statement.setString(4, article.getText());
			statement.setString(5, article.getColorId());
			// SQL文を実行
			statement.executeUpdate();
			// ステートメントを閉じる
			statement.close();
		} catch (SQLException e) {
			// SQLの実行中にエラーが発生した場合、エラーを投げる
			throw e;
		} finally {
			// データベース接続を閉じる
			if (connection != null) {
				connection.close();
			}
		}
	}
	
	// データベースからすべての記事を取得するメソッド
	public List<ArticleBean> getAllArticles() throws SQLException {
		Connection connection = source.getConnection();
		/*
		 * 記事情報を格納するためのリストを初期化
		 * List<ArticleBean> ：ArticleBean オブジェクトのリスト
		 * articles：このリストの変数名
		 * new ArrayList<>() ：新しい ArrayList インスタンスを作成する
		 * <>：ダイヤモンド演算子。ArrayList の型パラメータを省略しても、List<ArticleBean>に基づいてコンパイラが型を推論できる
		 */
		List<ArticleBean> articles = new ArrayList<>();
		/*「article テーブルと COLOR_MASTER テーブルを結合し、article テーブルの全ての列と COLOR_MASTER テーブルの COLOR_CODE 列を選択して取得」
		 * article.*：すべての列、*を外すと特定の列（article.title等）*/
		String sql = "SELECT article.*, COLOR_MASTER.COLOR_CODE FROM article JOIN COLOR_MASTER ON article.color_id = COLOR_MASTER.COLOR_ID;";
		try {
			// SQL文を準備し、実行
			PreparedStatement statement = connection.prepareStatement(sql);
			/*
			 * statement：PreparedStatement オブジェクト。これは、実行するSQL文を表しており、データベースに対して実行する前に準備されている
			 * executeQuery() メソッド：SQL文（通常はSELECT文）をデータベースに対して実行し、クエリの結果を取得。データベースからデータを取得するすクエリ等に使用される
			 */
			ResultSet resultSet = statement.executeQuery();
			/* 
			 * 結果セット(resultSet)から記事の情報を取得し、リストに追加
			 * 'ResultSet'
			 * 各行からデータを取得するためには、getString, getInt, getDate などのメソッドを使用する
			 * 基本的に順次アクセス（フォワードオンリー）。つまり、最初の行から最後の行へと順番にデータにアクセスする
			 * データベースのクエリ結果を表す表（テーブル）のようなもので、一度に1行ずつデータにアクセスできる
			 * 内からデータを読むためには、カーソル（現ポインター）を使用。カーソルを次の行に移動するために next() メソッドが一般的に使用される
			 */
			while (resultSet.next()) {
				ArticleBean article = new ArticleBean();
				article.setArticleId(resultSet.getInt("article_id"));
				article.setCreateDate(resultSet.getTimestamp("create_date"));
				article.setName(resultSet.getString("name"));
				article.setEmail(resultSet.getString("email"));
				article.setTitle(resultSet.getString("title"));
				article.setText(resultSet.getString("text"));
				article.setColorId(resultSet.getString("color_id"));
				article.setColorCode(resultSet.getString("color_code"));
				articles.add(article);
			}
			statement.close();
		} catch (SQLException e) {
			// e：発生したエラーをそのまま投げる
			throw e;
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
		// articles オブジェクト（List<ArticleBean> 型：データベースから取得した記事情報のリスト）を、呼び出し元（このメソッドを呼び出したクラスのメソッド）に返却する
		return articles;
	}
	
}

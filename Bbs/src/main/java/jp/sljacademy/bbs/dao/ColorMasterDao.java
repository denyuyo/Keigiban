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

import jp.sljacademy.bbs.bean.ColorMasterBean;

public class ColorMasterDao {
	private DataSource source;
	
	// コンストラクタは、ネコが新しい冒険の準備をするようなもの。
	// ()のなかに何もなかったら、引数を返さなくていい
	public ColorMasterDao() throws NamingException {
		// 冒険の地図を見つける（データベースへの接続情報を探す）。
		InitialContext context = new InitialContext();
		source = (DataSource)
		context.lookup("java:comp/env/jdbc/datasource");
	}
	
	// すべての色を探す冒険を始める。
	public List<ColorMasterBean> getAllColors() throws SQLException {
		// データベースへの道を開く（接続を確立）。
		Connection connection = source.getConnection();
		// 色の宝箱を用意（色情報を保存するリスト）。
		List<ColorMasterBean> colors = new ArrayList<>();
		// 色を探すための指示（SQLクエリ）。
		String sql = "SELECT COLOR_ID, COLOR_CODE, COLOR_NAME FROM COLOR_MASTER";
		try {
			// 指示に従って色を探し始める（クエリを実行）。
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			// 見つけた色を一つずつ宝箱に入れる。
			while (resultSet.next()) {
				ColorMasterBean color = new ColorMasterBean();
				color.setColorId(resultSet.getString("COLOR_ID"));
				color.setColorCode(resultSet.getString("COLOR_CODE"));
				color.setColorName(resultSet.getString("COLOR_NAME"));
				colors.add(color);
			}
			// 色探しを終える（ステートメントを閉じる）。
			statement.close();
		} catch (SQLException e) {
			// 問題が起きたら（例えば道に迷ったら）、それを知らせる。
			// eは発生した例外をそのまま投げる
			throw e;
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
		// 宝箱に入れた色を持ち帰る（色のリストを返す）。
		return colors;
	}
	
	public String getColorCode(String colorId) throws SQLException {
		
		// データベースに接続するのは、ネコがお気に入りのおもちゃを探すようなもの。
		Connection connection = source.getConnection();
		
		// 色を保存する変数は、ネコがおもちゃを置くための小さな箱。
		String color = "";
		
		// SQLクエリは、ネコがおもちゃを探す方法の地図。
		String sql = "SELECT COLOR_CODE FROM COLOR_MASTER WHERE COLOR_ID = ?;";
		try {
			// 地図に従っておもちゃを探し始める。
			PreparedStatement statement = connection.prepareStatement(sql);
			
			// どのおもちゃを探すかを指定（ここでは特定の色のID）。
			statement.setString(1, colorId);
			
			// 探し始めて、おもちゃを見つける。
			ResultSet resultSet = statement.executeQuery();
			// 見つけたおもちゃの色を箱に入れる。(ぐるぐる回ってる)
			while (resultSet.next()) {
				color = resultSet.getString("COLOR_CODE");
			}
			// おもちゃを探すのをやめる。
			statement.close();
		} catch (SQLException e) {
			// もし問題があったら（例えば、間違ったおもちゃを探してたら）、それを伝える。
			throw e;
		} finally {
			// おもちゃ探しを終えたら、ちゃんと部屋から出る（接続を閉じる）。
			if (connection != null) {
				connection.close();
			}
		}
		// おもちゃの色を持って帰る。
		return color;
	}
}
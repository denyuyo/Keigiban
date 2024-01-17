package jp.sljacademy.bbs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import jp.sljacademy.bbs.bean.ColorMasterBean;
import jp.sljacademy.bbs.util.DbSource;

public class ColorMasterDao {
	
	public List<ColorMasterBean> getAllColors() throws SQLException, NamingException {
		Connection connection = null;
		
		// データベースへの接続を確立
		connection = DbSource.getDateSource().getConnection();
		
		// ColorMasterBean型のオブジェクトをたくさん持てる空のcolorsリストを作成
		List<ColorMasterBean> colors = new ArrayList<>();
		// COLOR_MASTERテーブルからCOLOR_ID、COLOR_CODE、COLOR_NAMEという３つの列のデータを取り出す
		String sql = "SELECT COLOR_ID, COLOR_CODE, COLOR_NAME FROM COLOR_MASTER";
		try {
			// sql文を準備
			PreparedStatement statement = connection.prepareStatement(sql);
			// データベースに準備したsql文を送って、executeQuery() で実行、その答えをresultSetの箱に入れている
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				ColorMasterBean color = new ColorMasterBean();
				color.setColorId(resultSet.getString("COLOR_ID"));
				color.setColorCode(resultSet.getString("COLOR_CODE"));
				color.setColorName(resultSet.getString("COLOR_NAME"));
				colors.add(color);
			}
			// 終わったら閉じる
			statement.close();
		} catch (SQLException e) {
			// 例外がある場合は投げる
			e.printStackTrace();
			throw e;
		} finally {
			// データベースとの接続を切る
			if (connection != null) {
				connection.close();
			}
		}
		// 呼び出し元に返す
		return colors;
	}
	
	// 指定された colorId に対応するカラーコード（COLOR_CODE）をデータベースから取得する
	public String getColorCode(String colorId) throws SQLException, NamingException {
		
		Connection connection = null;
		
		// データベースへの接続を確立
		connection = DbSource.getDateSource().getConnection();
		
		// colorを空文字で初期化して、変数が未定義（null）の状態を避ける
		String color = "";
		
		// COLOR_MASTER テーブルから、指定された COLOR_ID に一致する行の COLOR_CODE の値を取得
		String sql = "SELECT COLOR_CODE FROM COLOR_MASTER WHERE COLOR_ID = ?;";
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			// 1番目のプレースホルダ（?）に colorId 変数の値をセット
			statement.setString(1, colorId);
			
			// sql文をデータベースに実行して、その結果をresultSetで持つ
			ResultSet resultSet = statement.executeQuery();
			// colorに取得したカラーコードの情報を入れる
			while (resultSet.next()) {
				color = resultSet.getString("COLOR_CODE");
			}
			// データベースの問い合わせを閉じる
			statement.close();
		} catch (SQLException e) {
			// 例外がある場合は投げる
			e.printStackTrace();
			throw e;
			
		// データベースの接続を切る
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
		// 呼び出し元に返す
		return color;
	}
}
package jp.sljacademy.bbs.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.sljacademy.bbs.bean.AccountBean;

// Accountテーブルを操作する

public class AccountDao {
	// データベース接続に使用する情報
    final String jdbcId = "root";
    final String jdbcPass = "password";
    final String jdbcUrl = "jdbc:mysql://localhost:3306/test?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=JST";

    // ログインアカウントを探す
    public AccountBean findAccount(AccountBean ab) {

        // 戻り値の用意
        AccountBean returnAb = new AccountBean();

        // データベースへ接続
        try (Connection con = DriverManager.getConnection(jdbcUrl, jdbcId, jdbcPass)) {

            String sql = "SELECT id, pass FROM account WHERE id = ? AND pass = ?";
            PreparedStatement ps= con.prepareStatement(sql);

            ps.setString(1, ab.getName());
            ps.setString(2, ab.getPassword());

            ResultSet rs = ps.executeQuery();


            if (rs.next()) {
                // 見つかったアカウント情報を戻り値にセット
                returnAb.setName(rs.getString("name"));
                returnAb.setPassword(rs.getString("password"));
            } else {
                // アカウントがなければnullを返す
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return returnAb;
    }

}

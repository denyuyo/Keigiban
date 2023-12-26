package jp.sljacademy.bbs.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import jp.sljacademy.bbs.bean.AccountBean;

// Accountテーブルを操作する

public class AccountDao {
	
	private DataSource source;
	private static final String SELECT = "select * from ACCOUNT where USER_ID=? and USER_PASS=?";

	
	public AccountDao() throws NamingException {
		InitialContext context = new InitialContext();
		source = (DataSource)
			context.lookup("java:comp/env/jdbc/datasource");
	}
	
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

            ps.setString(1, ab.getId());
            ps.setString(2, ab.getPassword());

            ResultSet rs = ps.executeQuery();


            if (rs.next()) {
                // 見つかったアカウント情報を戻り値にセット
                returnAb.setId(rs.getString("id"));
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

	public AccountBean getAccount(String id, String password) throws SQLException {
		AccountBean account = new AccountBean();
		Connection connection = source.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(SELECT);
			statement.setString(1, id);
			statement.setString(2, password);
			
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				account.setId(result.getString("id"));
				account.setPassword(result.getString("password"));
			}
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
		return account;
	}

}

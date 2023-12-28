package jp.sljacademy.bbs.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;  // 正しいインポート

import jp.sljacademy.bbs.bean.ColorMasterBean;

public class ColorMasterDao {
    
    private DataSource source;

    public ColorMasterDao() throws NamingException {
        InitialContext context = new InitialContext();
        source = (DataSource) context.lookup("java:comp/env/jdbc/datasource");
    }

    public List<ColorMasterBean> getAllColors() throws SQLException {
        List<ColorMasterBean> colors = new ArrayList<>();
        String sql = "SELECT COLOR_ID, COLOR_CODE, COLOR_NAME FROM COLOR_MASTER";

        try (Connection conn = source.getConnection();  // DataSourceからの接続
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                ColorMasterBean color = new ColorMasterBean();
                color.setColorId(rs.getString("COLOR_ID"));
                color.setColorCode(rs.getString("COLOR_CODE"));
                color.setColorName(rs.getString("COLOR_NAME"));
                colors.add(color);
            }
        }
        return colors;
    }
}

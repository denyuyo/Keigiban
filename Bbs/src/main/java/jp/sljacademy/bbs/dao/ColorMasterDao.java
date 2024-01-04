package jp.sljacademy.bbs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.sljacademy.bbs.bean.ColorMasterBean;

public class ColorMasterDao {
    private Connection connection;

    public ColorMasterDao(Connection connection) {
        this.connection = connection;
    }

    public List<ColorMasterBean> getAllColors() throws SQLException {
        List<ColorMasterBean> colors = new ArrayList<>();
        String sql = "SELECT COLOR_ID, COLOR_CODE, COLOR_NAME FROM COLOR_MASTER";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                ColorMasterBean color = new ColorMasterBean();
                color.setColorId(resultSet.getString("COLOR_ID"));
                color.setColorCode(resultSet.getString("COLOR_CODE"));
                color.setColorName(resultSet.getString("COLOR_NAME"));
                colors.add(color);
            }
        }
        return colors;
    }
}


package com.hurenjieee.kylin;

import java.sql.*;

/**
 * @author Jack
 * @date 2020/2/5 18:59
 */
public class KylinTest {
    public static void main(String[] args) {
        try {
            String KYLIN_DRIVER = "org.apache.kylin.jdbc.Driver";
            String KYLIN_URL = "jdbc:kylin://190.1.1.121:7070/joy_ads_biz_date";
            String KYLIN_USER = "ADMIN";
            String KYLIN_PASSWORD = "KYLIN";
            Class.forName(KYLIN_DRIVER);
            Connection connection = DriverManager.getConnection(KYLIN_URL, KYLIN_USER, KYLIN_PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(" SELECT  COUNT(DISTINCT uid) as count_uid  FROM  joy_ads_biz_date.tbl_biz_log_plus WHERE  flag = 1 AND dt = '20200205'");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1) + "         " + resultSet.getInt(2));
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

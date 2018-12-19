package com.diker.basic.jdbc;

import org.apache.tomcat.jdbc.pool.DataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author diker
 * @since 2018/12/9
 */
public class TomcatJdbcTest {

    public static void main(String[] args) throws SQLException {
        DataSource dataSource = new DataSource();
        dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        dataSource.setUrl("jdbc:oracle:thin:@localhost:1521:orcl");
        dataSource.setUsername("eip");
        dataSource.setPassword("eip");
        dataSource.setInitialSize(5);
        dataSource.setMinIdle(10);
        dataSource.setMaxIdle(50);
        dataSource.setMaxActive(100);
        dataSource.setFairQueue(true);
        dataSource.setValidationQuery("select 1 from dual");
        dataSource.setMaxWait(15*1000);
        dataSource.setTimeBetweenEvictionRunsMillis(2*60*1000);
        dataSource.setTestWhileIdle(false);
        dataSource.setJdbcInterceptors("SlowQueryReport(threshold=500)");
//        dataSource.setConnectionProperties("useUnicode=ture;characterEncoding=UTF-8");//mysql的属性
        for (int i = 0; i < 10; i++) {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select 1 from dual");
            rs.next();
            System.out.println("查询结果:"+ rs.getInt(1));
            rs.close();
            statement.close();
            connection.close();
        }


        System.out.println(dataSource.getActive());

    }

}

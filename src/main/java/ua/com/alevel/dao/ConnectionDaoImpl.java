package ua.com.alevel.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;


public class ConnectionDaoImpl implements ConnectionDao {
    private static HikariConfig config;
    private static HikariDataSource dataSource;
    private static Properties properties;
    static {
        properties = new Properties();
        config = new HikariConfig();
        try (InputStream ps = ConnectionDaoImpl.class.getClassLoader().getResourceAsStream("hikari.properties")) {
            properties.load(ps);
            config.setJdbcUrl(properties.getProperty("url"));
            config.setUsername(properties.getProperty("username"));
            config.setPassword(properties.getProperty("password"));
            config.setDataSourceProperties(properties);
        } catch (IOException e) {
            e.printStackTrace();
        }
        dataSource = new HikariDataSource(config);
    }

    public ConnectionDaoImpl() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}

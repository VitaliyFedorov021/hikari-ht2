package ua.com.alevel.dao;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionDao {
    Connection getConnection() throws SQLException;
}

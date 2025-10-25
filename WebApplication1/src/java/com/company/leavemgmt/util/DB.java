package com.company.leavemgmt.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
    static {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (Exception ex) {
            throw new RuntimeException("Load SQLServer JDBC driver failed", ex);
        }
    }
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                Config.getDbUrl(), Config.getDbUser(), Config.getDbPassword());
    }
    public static boolean isDemoIgnorePassword() {
        return Config.isDemoIgnorePassword();
    }
}

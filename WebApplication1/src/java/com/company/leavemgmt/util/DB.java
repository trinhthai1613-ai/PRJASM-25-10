package com.company.leavemgmt.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public final class DB {
    private static String URL  =
        "jdbc:sqlserver://localhost:1433;databaseName=LeaveManagementDB;encrypt=false;trustServerCertificate=true;";
    private static String USER = "sa";
    private static String PASS = "YourStrong!Passw0rd";

    private DB(){}

    static {
        try {
            // Load SQL Server JDBC driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            // Đọc cấu hình (nếu có) từ JNDI env-entry trong web.xml
            try {
                Context ic  = new InitialContext();
                Context env = (Context) ic.lookup("java:comp/env");
                String u   = (String) env.lookup("db.url");
                String usr = (String) env.lookup("db.user");
                String pw  = (String) env.lookup("db.password");
                if (u   != null && !u.isEmpty()) URL = u;
                if (usr != null) USER = usr;
                if (pw  != null) PASS = pw;
            } catch (NamingException ignore) {
                // Không khai báo env-entry thì dùng default ở trên
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Load SQLServer JDBC driver failed", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}

package com.company.leavemgmt.dao;

import com.company.leavemgmt.model.User;
import com.company.leavemgmt.util.DB;
import java.sql.*;
import java.util.*;

public class UserDao {
    public User login(String username, String password) throws SQLException {
        String sql = "SELECT u.UserID, u.Username, u.FullName, u.DivisionID, d.DivisionName, u.PasswordHash " +
                     "FROM dbo.Users u JOIN dbo.Divisions d ON d.DivisionID=u.DivisionID " +
                     "WHERE u.Username=? AND u.IsActive=1";
        try (Connection con = DB.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    if (!DB.isDemoIgnorePassword()) {
                        String hash = rs.getString("PasswordHash");
                        if (hash == null || !hash.equals(password)) return null; // demo: so sánh chuỗi đơn giản
                    }
                    User u = new User();
                    u.setUserId(rs.getInt("UserID"));
                    u.setUsername(rs.getString("Username"));
                    u.setFullName(rs.getString("FullName"));
                    u.setDivisionId(rs.getInt("DivisionID"));
                    u.setDivisionName(rs.getString("DivisionName"));
                    u.setRoles(getRoles(u.getUserId(), con));
                    return u;
                }
                return null;
            }
        }
    }

    private List<String> getRoles(int userId, Connection con) throws SQLException {
        String sql = "SELECT r.RoleCode FROM dbo.UserRoles ur JOIN dbo.Roles r ON r.RoleID = ur.RoleID " +
                     "WHERE ur.UserID = ? AND r.IsActive = 1";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                List<String> roles = new ArrayList<>();
                while (rs.next()) roles.add(rs.getString(1));
                return roles;
            }
        }
    }

    public List<Map<String,Object>> listDivisions() throws SQLException {
        String sql = "SELECT DivisionID, DivisionCode, DivisionName FROM dbo.Divisions WHERE IsActive=1 ORDER BY DivisionName";
        try (Connection con = DB.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            List<Map<String,Object>> list = new ArrayList<>();
            while (rs.next()) {
                Map<String,Object> m = new HashMap<>();
                m.put("DivisionID", rs.getInt(1));
                m.put("DivisionCode", rs.getString(2));
                m.put("DivisionName", rs.getString(3));
                list.add(m);
            }
            return list;
        }
    }
}

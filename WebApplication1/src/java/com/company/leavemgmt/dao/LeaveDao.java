package com.company.leavemgmt.dao;

import com.company.leavemgmt.util.DB;
import java.sql.*;
import java.util.*;

public class LeaveDao {
    public List<Map<String,Object>> listLeaveTypes() throws SQLException {
        String sql = "SELECT LeaveTypeID, TypeCode, TypeName FROM dbo.LeaveTypes WHERE IsActive=1 ORDER BY TypeCode";
        try (Connection con = DB.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            List<Map<String,Object>> list = new ArrayList<>();
            while (rs.next()) {
                Map<String,Object> m = new HashMap<>();
                m.put("LeaveTypeID", rs.getInt(1));
                m.put("TypeCode", rs.getString(2));
                m.put("TypeName", rs.getString(3));
                list.add(m);
            }
            return list;
        }
    }

    public List<Map<String,Object>> listReasonOptions(int leaveTypeId) throws SQLException {
        String sql = "SELECT ReasonOptionID, ReasonCode, ReasonName FROM dbo.LeaveReasonOptions WHERE IsActive=1 AND LeaveTypeID=? ORDER BY ReasonCode";
        try (Connection con = DB.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, leaveTypeId);
            try (ResultSet rs = ps.executeQuery()) {
                List<Map<String,Object>> list = new ArrayList<>();
                while (rs.next()) {
                    Map<String,Object> m = new HashMap<>();
                    m.put("ReasonOptionID", rs.getInt(1));
                    m.put("ReasonCode", rs.getString(2));
                    m.put("ReasonName", rs.getString(3));
                    list.add(m);
                }
                return list;
            }
        }
    }

    public int createRequest(int createdByUserId, int leaveTypeId, Integer reasonOptionId,
                             java.util.Date fromDate, java.util.Date toDate, String reason) throws SQLException {
        try (Connection con = DB.getConnection()) {
            String call = "{call dbo.sp_CreateLeaveRequest(?,?,?,?,?,?)}";
            try (CallableStatement cs = con.prepareCall(call)) {
                cs.setInt(1, createdByUserId);
                cs.setInt(2, leaveTypeId);
                cs.setDate(3, new java.sql.Date(fromDate.getTime()));
                cs.setDate(4, new java.sql.Date(toDate.getTime()));
                cs.setString(5, reason);
                if (reasonOptionId == null) cs.setNull(6, Types.INTEGER); else cs.setInt(6, reasonOptionId);
                boolean hasResult = cs.execute();
                int reqId = -1;
                while (hasResult) {
                    try (ResultSet rs = cs.getResultSet()) {
                        if (rs != null && rs.next()) { reqId = rs.getInt(1); break; }
                    }
                    hasResult = cs.getMoreResults();
                }
                return reqId;
            }
        }
    }

    public List<Map<String,Object>> listMyRequests(int userId) throws SQLException {
        String sql = "SELECT r.RequestID, r.RequestCode, r.FromDate, r.ToDate, s.StatusName, r.DaysBusiness, " +
                     "lt.TypeCode, ISNULL(o.ReasonCode,'') AS ReasonCode " +
                     "FROM dbo.LeaveRequests r " +
                     "JOIN dbo.RequestStatuses s ON s.StatusID=r.CurrentStatusID " +
                     "JOIN dbo.LeaveTypes lt ON lt.LeaveTypeID=r.LeaveTypeID " +
                     "LEFT JOIN dbo.LeaveReasonOptions o ON o.ReasonOptionID=r.ReasonOptionID " +
                     "WHERE r.CreatedByUserID=? ORDER BY r.CreatedAt DESC";
        try (Connection con = DB.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                List<Map<String,Object>> list = new ArrayList<>();
                while (rs.next()) {
                    Map<String,Object> m = new HashMap<>();
                    m.put("RequestID", rs.getInt("RequestID"));
                    m.put("RequestCode", rs.getString("RequestCode"));
                    m.put("FromDate", rs.getDate("FromDate"));
                    m.put("ToDate", rs.getDate("ToDate"));
                    m.put("StatusName", rs.getString("StatusName"));
                    m.put("DaysBusiness", rs.getInt("DaysBusiness"));
                    m.put("TypeCode", rs.getString("TypeCode"));
                    m.put("ReasonCode", rs.getString("ReasonCode"));
                    list.add(m);
                }
                return list;
            }
        }
    }

    public List<Map<String,Object>> listTreeRequests(int approverUserId, java.util.Date from, java.util.Date to) throws SQLException {
        try (Connection con = DB.getConnection()) {
            String call = "{call dbo.sp_GetRequestsForUserAndSubtree(?,?,?)}";
            try (CallableStatement cs = con.prepareCall(call)) {
                cs.setInt(1, approverUserId);
                cs.setDate(2, new java.sql.Date(from.getTime()));
                cs.setDate(3, new java.sql.Date(to.getTime()));
                try (ResultSet rs = cs.executeQuery()) {
                    List<Map<String,Object>> list = new ArrayList<>();
                    while (rs.next()) {
                        Map<String,Object> m = new HashMap<>();
                        m.put("RequestID", rs.getInt("RequestID"));
                        m.put("RequestCode", rs.getString("RequestCode"));
                        m.put("CreatedBy", rs.getString("CreatedBy"));
                        m.put("DivisionName", rs.getString("DivisionName"));
                        m.put("TypeName", rs.getString("TypeName"));
                        m.put("FromDate", rs.getDate("FromDate"));
                        m.put("ToDate", rs.getDate("ToDate"));
                        m.put("CurrentStatus", rs.getString("CurrentStatus"));
                        m.put("DaysBusiness", rs.getInt("DaysBusiness"));
                        list.add(m);
                    }
                    return list;
                }
            }
        }
    }

    public void review(int approverUserId, int requestId, boolean approve, String note) throws SQLException {
        try (Connection con = DB.getConnection()) {
            String call = "{call dbo.sp_ReviewLeaveRequest(?,?,?,?)}";
            try (CallableStatement cs = con.prepareCall(call)) {
                cs.setInt(1, approverUserId);
                cs.setInt(2, requestId);
                cs.setBoolean(3, approve);
                cs.setString(4, note);
                cs.execute();
            }
        }
    }

    public List<Map<String,Object>> agenda(int divisionId, java.util.Date from, java.util.Date to) throws SQLException {
        try (Connection con = DB.getConnection()) {
            String call = "{call dbo.sp_AgendaByDivision(?,?,?)}";
            try (CallableStatement cs = con.prepareCall(call)) {
                cs.setInt(1, divisionId);
                cs.setDate(2, new java.sql.Date(from.getTime()));
                cs.setDate(3, new java.sql.Date(to.getTime()));
                try (ResultSet rs = cs.executeQuery()) {
                    List<Map<String,Object>> list = new ArrayList<>();
                    while (rs.next()) {
                        Map<String,Object> m = new HashMap<>();
                        m.put("FullName", rs.getString("FullName"));
                        m.put("WorkingDate", rs.getDate("WorkingDate"));
                        m.put("Attendance", rs.getString("Attendance"));
                        list.add(m);
                    }
                    return list;
                }
            }
        }
    }

    public List<Map<String,Object>> balances(int userId, int year) throws SQLException {
        try (Connection con = DB.getConnection()) {
            String call = "{call dbo.sp_GetLeaveBalanceForUser(?,?)}";
            try (CallableStatement cs = con.prepareCall(call)) {
                cs.setInt(1, userId);
                cs.setInt(2, year);
                try (ResultSet rs = cs.executeQuery()) {
                    List<Map<String,Object>> list = new ArrayList<>();
                    while (rs.next()) {
                        Map<String,Object> m = new HashMap<>();
                        m.put("TypeCode", rs.getString("TypeCode"));
                        m.put("TypeName", rs.getString("TypeName"));
                        m.put("Year", rs.getInt("Year"));
                        m.put("CarryOver", rs.getBigDecimal("CarryOver"));
                        m.put("UsedDays", rs.getBigDecimal("UsedDays"));
                        m.put("BalanceDays", rs.getBigDecimal("BalanceDays"));
                        m.put("RemainingIncludingCarry", rs.getBigDecimal("RemainingIncludingCarry"));
                        list.add(m);
                    }
                    return list;
                }
            }
        }
    }
}

package dao;

import model.RoleRequest;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoleRequestDAO {
    private final Connection conn;

    public RoleRequestDAO(Connection conn) {
        this.conn = conn;
    }

    // Member gửi yêu cầu
    public void insertRequest(int userId, int requestedRoleId) throws SQLException {
        String sql = "INSERT INTO RoleRequests (UserID, RequestedRoleID) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, requestedRoleId);
            ps.executeUpdate();
        }
    }

    // Admin xem các yêu cầu pending
    public List<RoleRequest> listPending() throws SQLException {
        String sql = """
            SELECT rr.RequestID, rr.UserID, rr.RequestedRoleID, rr.Status, rr.RequestDate,
                   u.FullName AS UserName, r.RoleName AS RequestedRoleName
            FROM RoleRequests rr
            JOIN Users u ON rr.UserID = u.UserID
            JOIN Roles r ON rr.RequestedRoleID = r.RoleID
            WHERE rr.Status = 'Pending'
            ORDER BY rr.RequestDate DESC
        """;
        List<RoleRequest> list = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                RoleRequest x = new RoleRequest();
                x.setRequestID(rs.getInt("RequestID"));
                x.setUserID(rs.getInt("UserID"));
                x.setRequestedRoleID(rs.getInt("RequestedRoleID"));
                x.setStatus(rs.getString("Status"));
                x.setRequestDate(rs.getTimestamp("RequestDate"));
                x.setUserName(rs.getString("UserName"));
                x.setRequestedRoleName(rs.getString("RequestedRoleName"));
                list.add(x);
            }
        }
        return list;
    }

    // Duyệt
    public void approve(int requestId, int adminId) throws SQLException {
        boolean oldAuto = conn.getAutoCommit();
        conn.setAutoCommit(false);
        try {
            int userId = -1, newRoleId = -1;
            try (PreparedStatement ps = conn.prepareStatement(
                    "SELECT UserID, RequestedRoleID FROM RoleRequests WHERE RequestID=?")) {
                ps.setInt(1, requestId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        userId = rs.getInt(1);
                        newRoleId = rs.getInt(2);
                    }
                }
            }
            if (userId == -1) throw new SQLException("Request not found");

            try (PreparedStatement ps = conn.prepareStatement(
                    "UPDATE Users SET RoleID=? WHERE UserID=?")) {
                ps.setInt(1, newRoleId);
                ps.setInt(2, userId);
                ps.executeUpdate();
            }

            try (PreparedStatement ps = conn.prepareStatement(
                    "UPDATE RoleRequests SET Status='Approved', ReviewedBy=?, ReviewedDate=GETDATE() WHERE RequestID=?")) {
                ps.setInt(1, adminId);
                ps.setInt(2, requestId);
                ps.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(oldAuto);
        }
    }

    // Từ chối
    public void reject(int requestId, int adminId) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "UPDATE RoleRequests SET Status='Rejected', ReviewedBy=?, ReviewedDate=GETDATE() WHERE RequestID=?")) {
            ps.setInt(1, adminId);
            ps.setInt(2, requestId);
            ps.executeUpdate();
        }
    }
}

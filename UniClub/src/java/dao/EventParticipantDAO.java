/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author hoang
 */


import java.sql.*;

public class EventParticipantDAO {
    private final Connection conn;

    public EventParticipantDAO(Connection conn) {
        this.conn = conn;
    }

    // Kiểm tra user đã đăng ký event này chưa
    public boolean isRegistered(int eventId, int userId) throws SQLException {
        String sql = "SELECT 1 FROM EventParticipants WHERE EventID=? AND UserID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, eventId);
            ps.setInt(2, userId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    // Đăng ký tham gia
    public void register(int eventId, int userId) throws SQLException {
        String sql = "INSERT INTO EventParticipants (EventID, UserID, Status, RegisterDate) " +
                     "VALUES (?, ?, 'Registered', GETDATE())";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, eventId);
            ps.setInt(2, userId);
            ps.executeUpdate();
        }
    }

    // Đếm số người đăng ký 1 event (dùng để hiển thị)
    public int countRegistered(int eventId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM EventParticipants WHERE EventID=? AND Status='Registered'";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, eventId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return 0;
    }
}


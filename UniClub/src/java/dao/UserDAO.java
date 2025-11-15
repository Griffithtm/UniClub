package dao;

import model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private Connection conn;

    public UserDAO(Connection conn) {
        this.conn = conn;
    }

    /** 
     * 🔐 Kiểm tra đăng nhập
     */
    public User checkLogin(String email, String password) throws SQLException {
        String sql = "SELECT * FROM Users WHERE Email=? AND PasswordHash=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapResultSetToUser(rs);
            }
        }
        return null;
    }

    /** 
     * 📧 Kiểm tra xem email đã tồn tại chưa (phục vụ đăng ký)
     */
    public boolean checkEmailExists(String email) throws SQLException {
        String sql = "SELECT UserID FROM Users WHERE Email=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
    }

    /** 
     * 🆕 Đăng ký tài khoản mới (người dùng tự đăng ký)
     */
    public boolean register(User u) throws SQLException {
        String sql = "INSERT INTO Users (FullName, Email, PasswordHash, RoleID, ClubID) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, u.getFullName());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getPasswordHash());
            ps.setInt(4, u.getRoleID());
            if (u.getClubID() != null) {
                ps.setInt(5, u.getClubID());
            } else {
                ps.setNull(5, java.sql.Types.INTEGER);
            }
            return ps.executeUpdate() > 0;
        }
    }

    /** 
     * 🌐 Thêm user khi đăng nhập bằng Gmail lần đầu
     */
    public void insertUserByGoogle(User user) throws SQLException {
    String sql = "INSERT INTO Users (FullName, Email, RoleID, PasswordHash) VALUES (?, ?, ?, ?)";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, user.getFullName());
        ps.setString(2, user.getEmail());
        ps.setInt(3, user.getRoleID());
        ps.setString(4, user.getPasswordHash()); // <-- đảm bảo dòng này có giá trị
        ps.executeUpdate();
    }
}


    /** 
     * 🔎 Lấy thông tin user theo email (phục vụ đăng nhập Gmail)
     */
    public User getUserByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM Users WHERE Email=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapResultSetToUser(rs);
            }
        }
        return null;
    }

    /** 
     * 🔎 Lấy thông tin user theo ID
     */
    public User getUserById(int id) throws SQLException {
        String sql = "SELECT * FROM Users WHERE UserID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapResultSetToUser(rs);
            }
        }
        return null;
    }

    /** 
     * ✏️ Cập nhật thông tin user
     */
    public boolean updateUser(User u) throws SQLException {
        String sql = "UPDATE Users SET FullName=?, Email=?, PasswordHash=?, RoleID=?, ClubID=? WHERE UserID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, u.getFullName());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getPasswordHash());
            ps.setInt(4, u.getRoleID());
            if (u.getClubID() != null) {
                ps.setInt(5, u.getClubID());
            } else {
                ps.setNull(5, java.sql.Types.INTEGER);
            }
            ps.setInt(6, u.getUserID());
            return ps.executeUpdate() > 0;
        }
    }

    /** 
     * ❌ Xóa user theo ID
     */
    public boolean deleteUser(int userID) throws SQLException {
        String sql = "DELETE FROM Users WHERE UserID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userID);
            return ps.executeUpdate() > 0;
        }
    }

    /** 
     * 📋 Lấy toàn bộ danh sách user (dành cho admin)
     */
    public List<User> getAllUsers() throws SQLException {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM Users";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapResultSetToUser(rs));
            }
        }
        return list;
    }

    /** 
     * 🧠 Ánh xạ ResultSet → User Object
     */
    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User u = new User();
        u.setUserID(rs.getInt("UserID"));
        u.setFullName(rs.getString("FullName"));
        u.setEmail(rs.getString("Email"));
        u.setPasswordHash(rs.getString("PasswordHash"));
        u.setRoleID(rs.getInt("RoleID"));
        Object clubObj = rs.getObject("ClubID");
        u.setClubID(clubObj != null ? rs.getInt("ClubID") : null);
        return u;
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.Club;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClubDAO {
    private final Connection conn;

    public ClubDAO(Connection conn) {
        this.conn = conn;
    }

    public List<Club> getAllClubs() throws SQLException {
        List<Club> list = new ArrayList<>();
        String sql = "SELECT * FROM Clubs";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Club c = new Club();
                c.setClubID(rs.getInt("ClubID"));
                c.setClubName(rs.getString("ClubName"));
                c.setDescription(rs.getString("Description"));
                c.setEstablishedDate(rs.getDate("EstablishedDate"));
                list.add(c);
            }
        }
        return list;
    }

    // Bonus: thêm CLB mới (cho admin dùng sau)
    public void insertClub(Club c) throws SQLException {
        String sql = "INSERT INTO Clubs (ClubName, Description, EstablishedDate) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getClubName());
            ps.setString(2, c.getDescription());
            if (c.getEstablishedDate() != null) {
                ps.setDate(3, new java.sql.Date(c.getEstablishedDate().getTime()));
            } else {
                ps.setNull(3, Types.DATE);
            }
            ps.executeUpdate();
        }
    }
}


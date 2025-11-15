package dao;

import model.Event;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventDAO {
    private final Connection conn;

    public EventDAO(Connection conn) {
        this.conn = conn;
    }

    // Lấy tất cả sự kiện của 1 CLB
    public List<Event> getEventsByClub(int clubId) throws SQLException {
        List<Event> list = new ArrayList<>();
        String sql = "SELECT * FROM Events WHERE ClubID = ? ORDER BY EventDate DESC";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, clubId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Event e = new Event();
                    e.setEventID(rs.getInt("EventID"));
                    e.setEventName(rs.getString("EventName"));
                    e.setDescription(rs.getString("Description"));
                    e.setEventDate(rs.getTimestamp("EventDate")); // có cả giờ
                    e.setLocation(rs.getString("Location"));
                    e.setStatus(rs.getString("Status"));
                    e.setClubID(rs.getInt("ClubID"));
                    e.setCreatedBy(rs.getInt("CreatedBy"));
                    list.add(e);
                }
            }
        }
        return list;
    }

    // Thêm sự kiện mới
    public void insertEvent(Event e) throws SQLException {
        String sql = "INSERT INTO Events " +
                     "(EventName, Description, EventDate, Location, Status, ClubID, CreatedBy) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, e.getEventName());
            ps.setString(2, e.getDescription());
            // chuyển java.util.Date -> Timestamp để lưu cả ngày + giờ
            ps.setTimestamp(3, new java.sql.Timestamp(e.getEventDate().getTime()));
            ps.setString(4, e.getLocation());
            // nếu chưa set status thì mặc định là 'Upcoming'
            String status = (e.getStatus() != null) ? e.getStatus() : "Upcoming";
            ps.setString(5, status);
            ps.setInt(6, e.getClubID());
            ps.setInt(7, e.getCreatedBy());

            ps.executeUpdate();
        }
    }

    // Nếu bạn muốn giữ tên createEvent cho đẹp thì cho nó gọi insertEvent
    public void createEvent(Event e) throws SQLException {
        insertEvent(e);
    }
}

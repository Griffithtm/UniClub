package controller;

import dal.DBConnect;
import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import model.MemberStats;

public class ParticipationReportServlet extends HttpServlet {

    private void handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String clubIdStr = request.getParameter("clubId");
        String semester = request.getParameter("semester");

        List<MemberStats> statsList = new ArrayList<>();

        // Kiểm tra tham số đầu vào
        if (clubIdStr == null || clubIdStr.isEmpty() || semester == null || semester.isEmpty()) {
            request.setAttribute("error", "Thiếu tham số clubId hoặc semester.");
            request.getRequestDispatcher("report.jsp").forward(request, response);
            return;
        }

        try {
            int clubId = Integer.parseInt(clubIdStr);

            DBConnect db = new DBConnect();
            try (Connection conn = db.getConnection()) {
                CallableStatement stmt = conn.prepareCall("{call sp_GetMemberParticipationStats(?, ?)}");
                stmt.setInt(1, clubId);
                stmt.setString(2, semester);

                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    MemberStats ms = new MemberStats();
                    ms.setUserID(rs.getInt("UserID"));
                    ms.setFullName(rs.getString("FullName"));
                    ms.setAttendedEvents(rs.getInt("AttendedEvents"));
                    ms.setTotalEvents(rs.getInt("TotalEvents"));
                    ms.setParticipationRate(rs.getDouble("ParticipationRate"));
                    ms.setClassification(rs.getString("Classification"));
                    statsList.add(ms);
                }

                rs.close();
                stmt.close();
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "clubId không hợp lệ.");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi xảy ra khi lấy dữ liệu.");
        }

        request.setAttribute("statsList", statsList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("report.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        handleRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        handleRequest(request, response);
    }
}

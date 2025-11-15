package controller;

import dao.RoleRequestDAO;
import dal.DBConnect;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class RoleRequestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Nếu muốn, có thể cho GET vào thẳng trang form yêu cầu role
        response.sendRedirect("request_role.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lấy user trong session
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Lấy role mà user chọn trong form
        int requestedRoleId = Integer.parseInt(request.getParameter("requestedRoleId"));

        // Ghi yêu cầu vào bảng RoleRequests
        try (Connection conn = new DBConnect().getConnection()) {
            RoleRequestDAO dao = new RoleRequestDAO(conn);
            dao.insertRequest(user.getUserID(), requestedRoleId);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Lỗi tạo yêu cầu thay đổi vai trò", e);
        }

        // Thông báo lại cho user
        
        request.setAttribute("message", "Yêu cầu của bạn đã được gửi. Vui lòng chờ ban quản trị duyệt.");
        request.getRequestDispatcher("request_role.jsp").forward(request, response);
// hoặc: response.sendRedirect("request_role.jsp");

    }

    @Override
    public String getServletInfo() {
        return "Servlet xử lý gửi yêu cầu nâng vai trò";
    }
}

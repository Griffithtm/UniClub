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

public class ApproveRoleServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User me = (session != null) ? (User) session.getAttribute("user") : null;

        // Chưa đăng nhập -> về login
        if (me == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        int roleId = me.getRoleID();
        System.out.println("ApproveRoleServlet - user = " + me.getEmail()
                + ", roleId = " + roleId);

        // Không phải Admin (1) hoặc Chairman (2) -> không cho duyệt
        if (roleId != 1 && roleId != 2) {
            // Có thể set message vào session nếu muốn
            response.sendRedirect(request.getContextPath() + "/home.jsp");
            return;
        }

        String action = request.getParameter("action"); // approve | reject
        String idRaw = request.getParameter("id");
        if (action == null || idRaw == null) {
            response.sendRedirect(request.getContextPath() + "/role_requests_admin.jsp");
            return;
        }

        int requestId = Integer.parseInt(idRaw);

        try (Connection cn = new DBConnect().getConnection()) {
            RoleRequestDAO dao = new RoleRequestDAO(cn);

            if ("approve".equalsIgnoreCase(action)) {
                dao.approve(requestId, me.getUserID());
            } else if ("reject".equalsIgnoreCase(action)) {
                dao.reject(requestId, me.getUserID());
            }

            response.sendRedirect(request.getContextPath() + "/role_requests_admin.jsp");

        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Lỗi duyệt yêu cầu vai trò", e);
        }
    }
}

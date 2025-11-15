/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dal.DBConnect;
import dao.EventDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Event;
import model.User;
import java.sql.Connection;
import dao.EventDAO;
import dal.DBConnect;
import dao.EventParticipantDAO;
import model.Event;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
/**
 *
 * @author hoang
 */
public class EventListServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet EventListServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EventListServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User me = (session != null) ? (User) session.getAttribute("user") : null;

        if (me == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        if (me.getClubID() == null) {
            response.sendRedirect(request.getContextPath() + "/ClubListServlet");
            return;
        }

        try (Connection conn = new DBConnect().getConnection()) {
            EventDAO eventDao = new EventDAO(conn);
            EventParticipantDAO epDao = new EventParticipantDAO(conn);

            List<Event> events = eventDao.getEventsByClub(me.getClubID());

            // danh sách ID sự kiện mà user đã đăng ký
            java.util.List<Integer> registeredIds = new java.util.ArrayList<>();
            // map eventId -> số người đăng ký
            java.util.Map<Integer, Integer> registeredCounts = new java.util.HashMap<>();

            for (Event e : events) {
                if (epDao.isRegistered(e.getEventID(), me.getUserID())) {
                    registeredIds.add(e.getEventID());
                }
                registeredCounts.put(e.getEventID(), epDao.countRegistered(e.getEventID()));
            }

            request.setAttribute("events", events);
            request.setAttribute("registeredIds", registeredIds);
            request.setAttribute("registeredCounts", registeredCounts);

            // lấy message nếu có
            Object msg = session.getAttribute("eventMsg");
            if (msg != null) {
                request.setAttribute("eventMsg", msg);
                session.removeAttribute("eventMsg");
            }

            request.getRequestDispatcher("events.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Lỗi tải danh sách sự kiện", e);
        }
    }


    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

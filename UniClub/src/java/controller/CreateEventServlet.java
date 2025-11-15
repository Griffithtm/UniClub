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
import model.Event;
import model.User;
import java.sql.Connection;
import dao.EventDAO;
import dal.DBConnect;
import model.Event;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;

/**
 *
 * @author hoang
 */
public class CreateEventServlet extends HttpServlet {
   
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
            out.println("<title>Servlet CreateEventServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CreateEventServlet at " + request.getContextPath () + "</h1>");
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
        processRequest(request, response);
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

        HttpSession session = request.getSession(false);
        User me = (session != null) ? (User) session.getAttribute("user") : null;

        if (me == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        if (me.getRoleID() != 1 && me.getRoleID() != 2) {
            response.sendError(403, "Bạn không có quyền tạo sự kiện");
            return;
        }

        String name = request.getParameter("eventName");
        String date = request.getParameter("eventDate");
        String location = request.getParameter("location");
        String description = request.getParameter("description");

        try (Connection conn = new DBConnect().getConnection()) {

            Event ev = new Event();
            ev.setEventName(name);
            ev.setEventDate(java.sql.Timestamp.valueOf(date.replace("T", " ") + ":00"));
            ev.setLocation(location);
            ev.setDescription(description);
            ev.setClubID(me.getClubID());
            ev.setCreatedBy(me.getUserID());

            EventDAO dao = new EventDAO(conn);
            dao.insertEvent(ev);

            session.setAttribute("eventMsg", "Tạo sự kiện thành công!");

            response.sendRedirect("EventListServlet");

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Lỗi tạo sự kiện", e);
        }
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

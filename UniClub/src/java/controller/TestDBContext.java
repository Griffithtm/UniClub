package controller;

import dal.DBConnect;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

public class TestDBContext extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        try {
            DBConnect db = new DBConnect();
            Connection conn = db.getConnection();
            
            if (conn != null) {
                out.println("<h2 style='color:green;'>✅ Kết nối cơ sở dữ liệu thành công!</h2>");
            } else {
                out.println("<h2 style='color:red;'>❌ Không thể kết nối CSDL.</h2>");
            }
        } catch (Exception e) {
            out.println("<h2 style='color:red;'>❌ Lỗi: " + e.getMessage() + "</h2>");
            e.printStackTrace(out); // In stack trace ra trang web
        }
    }
}

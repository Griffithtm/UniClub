<%-- 
    Document   : approve_role_request
    Created on : Oct 23, 2025, 9:05:20 PM
    Author     : hoang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%@ page import="dao.RoleRequestDAO, java.util.*, model.RoleRequest" %>
    <%
        RoleRequestDAO dao = new RoleRequestDAO();
        List<RoleRequest> list = dao.getAllPending();
    %>

    <h3>Danh sách yêu cầu thay đổi vai trò</h3>
    <table border="1">
    <tr><th>User</th><th>Role yêu cầu</th><th>Ngày gửi</th><th>Hành động</th></tr>
    <% for (RoleRequest r : list) { %>
    <tr>
        <td><%= r.getUserName() %></td>
        <td><%= r.getRequestedRoleName() %></td>
        <td><%= r.getRequestDate() %></td>
        <td>
            <a href="ApproveRoleServlet?action=approve&id=<%=r.getRequestID()%>">Duyệt</a> |
            <a href="ApproveRoleServlet?action=reject&id=<%=r.getRequestID()%>">Từ chối</a>
        </td>
    </tr>
    <% } %>
    </table>

    </body>
</html>

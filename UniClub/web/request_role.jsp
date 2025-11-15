<%-- 
    Document   : request_role
    Created on : Oct 23, 2025, 9:00:28 PM
    Author     : hoang
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.User" %>

<%
    User me = (User) session.getAttribute("user");
    if (me == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<%@ include file="includes/header.jsp" %>

<div class="container" style="margin-top: 120px;">
    <h2>Yêu cầu nâng vai trò</h2>

    <% if (request.getAttribute("message") != null) { %>
        <div class="alert alert-success">
            <%= request.getAttribute("message") %>
        </div>
    <% } %>

    <form action="RoleRequestServlet" method="post">
        <div class="form-group">
            <label>Chọn vai trò muốn đăng ký:</label>
            <select name="requestedRoleId" class="form-control" required>
                <option value="2">Chairman</option>
                <option value="3">ViceChairman</option>
                <option value="4">TeamLeader</option>
            </select>
        </div>
        <button type="submit" class="btn btn-primary mt-3">Gửi yêu cầu</button>
    </form>
</div>

<%@ include file="includes/footer.jsp" %>


<%-- 
    Document   : create_event
    Created on : Nov 13, 2025, 9:56:32 PM
    Author     : hoang
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="model.User" %>

<%
    User me = (User) session.getAttribute("user");
    if (me == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    if (me.getRoleID() != 1 && me.getRoleID() != 2) {
        response.sendError(403, "Bạn không có quyền tạo sự kiện.");
        return;
    }
%>

<%
    request.setAttribute("pageTitle", "Tạo sự kiện");
%>

<%@ include file="includes/header.jsp" %>

<div class="container" style="margin-top:120px;">
    <h2>Tạo sự kiện mới</h2>

    <form action="CreateEventServlet" method="post" class="mt-3">

        <div class="mb-3">
            <label>Tên sự kiện</label>
            <input type="text" name="eventName" class="form-control" required>
        </div>

        <div class="mb-3">
            <label>Ngày giờ</label>
            <input type="datetime-local" name="eventDate" class="form-control" required>
        </div>

        <div class="mb-3">
            <label>Địa điểm</label>
            <input type="text" name="location" class="form-control" required>
        </div>

        <div class="mb-3">
            <label>Mô tả</label>
            <textarea name="description" class="form-control" rows="4"></textarea>
        </div>

        <button type="submit" class="btn btn-primary">Tạo sự kiện</button>
    </form>
</div>

<%@ include file="includes/footer.jsp" %>


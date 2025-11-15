<%-- 
    Document   : report
    Created on : Oct 2, 2025, 8:05:01 PM
    Author     : hoang
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.MemberStats" %>
<% String error = (String) request.getAttribute("error"); %>
<% if (error != null) { %>
    <div class="alert alert-danger text-center"><%= error %></div>
<% } %>

<%
    List<MemberStats> statsList = (List<MemberStats>) request.getAttribute("statsList");
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Báo cáo tham gia CLB</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container mt-5">
    <h2 class="text-center mb-4">📊 Báo cáo tham gia thành viên</h2>

    <% if (statsList == null || statsList.isEmpty()) { %>
        <div class="alert alert-warning text-center">
            Không có dữ liệu tham gia cho học kỳ này.
        </div>
    <% } else { %>
        <table class="table table-bordered table-striped table-hover align-middle shadow-sm">
            <thead class="table-dark text-center">
                <tr>
                    <th>ID</th>
                    <th>Họ và Tên</th>
                    <th>Số sự kiện đã tham gia</th>
                    <th>Tổng sự kiện</th>
                    <th>Tỉ lệ tham gia (%)</th>
                    <th>Xếp loại</th>
                </tr>
            </thead>
            <tbody>
            <% for (MemberStats ms : statsList) { %>
                <tr>
                    <td class="text-center"><%= ms.getUserID() %></td>
                    <td><%= ms.getFullName() %></td>
                    <td class="text-center"><%= ms.getAttendedEvents() %></td>
                    <td class="text-center"><%= ms.getTotalEvents() %></td>
                    <td class="text-center"><%= ms.getParticipationRate() %></td>
                    <td class="text-center">
                        <% if ("Tích cực".equals(ms.getClassification())) { %>
                            <span class="badge bg-success">Tích cực</span>
                        <% } else if ("Bình thường".equals(ms.getClassification())) { %>
                            <span class="badge bg-warning text-dark">Bình thường</span>
                        <% } else { %>
                            <span class="badge bg-danger">Không tích cực</span>
                        <% } %>
                    </td>
                </tr>
            <% } %>
            </tbody>
        </table>
    <% } %>

    <div class="text-center mt-4">
        <a href="index.jsp" class="btn btn-primary">⬅ Quay lại</a>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>


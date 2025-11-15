<%-- 
    Document   : clubs
    Created on : Nov 13, 2025, 8:20:50 PM
    Author     : hoang
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, model.Club, model.User" %>

<%
    User me = (User) session.getAttribute("user");
    if (me == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<%@ include file="includes/header.jsp" %>

<div class="container" style="margin-top: 120px;">
    <h2>Danh sách Câu lạc bộ</h2>

    <table class="table table-bordered table-striped mt-3">
        <thead>
        <tr>
            <th>#</th>
            <th>Tên CLB</th>
            <th>Mô tả</th>
            <th>Ngày thành lập</th>
            <th>Thao tác</th>
        </tr>
        </thead>
        <tbody>
        <%
            List<Club> clubs = (List<Club>) request.getAttribute("clubs");
            if (clubs != null) {
                for (Club c : clubs) {
        %>
        <tr>
            <td><%= c.getClubID() %></td>
            <td><%= c.getClubName() %></td>
            <td><%= c.getDescription() %></td>
            <td><%= c.getEstablishedDate() %></td>
            <td>
                <% if (me.getClubID() != null && me.getClubID() == c.getClubID()) { %>
                    <span class="badge bg-success">Đã tham gia</span>
                <% } else { %>
                    <form action="JoinClubServlet" method="post" style="display:inline;">
                        <input type="hidden" name="clubId" value="<%= c.getClubID() %>">
                        <button type="submit" class="btn btn-primary btn-sm">Tham gia</button>
                    </form>
                <% } %>
            </td>
        </tr>
        <%
                }
            }
        %>
        </tbody>
    </table>
</div>

<%@ include file="includes/footer.jsp" %>


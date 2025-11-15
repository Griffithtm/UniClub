<%-- 
    Document   : role_requests_admin
    Created on : Nov 13, 2025, 5:06:15 PM
    Author     : hoang
--%>

<%@ page import="java.util.*,java.sql.*,dao.RoleRequestDAO,dal.DBConnect,model.RoleRequest,model.User" %>
<%
  User me = (User) session.getAttribute("user");
  if (me == null || !(me.getRoleID()==1 || me.getRoleID()==2)) {
      response.sendError(403); return;
  }

  List<RoleRequest> list;
  try (Connection cn = new DBConnect().getConnection()) {
      list = new RoleRequestDAO(cn).listPending();
  } catch (SQLException e) {
      throw new RuntimeException(e);
  }
%>

<h2>Danh sách yêu c?u thay ??i vai trò (Pending)</h2>

<table border="1" cellpadding="6" cellspacing="0">
  <tr>
    <th>ID</th>
    <th>Ng??i dùng</th>
    <th>Vai trò yêu c?u</th>
    <th>Ngày g?i</th>
    <th>Hành ??ng</th>
  </tr>
  <%
    for (RoleRequest r : list) {
  %>
  <tr>
    <td><%= r.getRequestID() %></td>
    <td><%= r.getUserName() %></td>
    <td><%= r.getRequestedRoleName() %></td>
    <td><%= r.getRequestDate() %></td>
    <td>
      <a href="ApproveRoleServlet?action=approve&id=<%= r.getRequestID() %>">Duy?t</a> |
      <a href="ApproveRoleServlet?action=reject&id=<%= r.getRequestID() %>">T? ch?i</a>
    </td>
  </tr>
  <% } %>
</table>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="model.User" %>

<%
    User me = (User) session.getAttribute("user");
    if (me == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<%
    request.setAttribute("pageTitle", "Sự kiện câu lạc bộ");
%>

<%@ include file="includes/header.jsp" %>

<div class="container" style="margin-top: 120px;">
    <h2>Sự kiện của câu lạc bộ</h2>

    <c:if test="${not empty eventMsg}">
        <div class="alert alert-info mt-3">${eventMsg}</div>
    </c:if>

    <c:if test="${empty events}">
        <p>Hiện chưa có sự kiện nào.</p>
    </c:if>

    <c:if test="${not empty events}">
        <table class="table table-bordered table-striped mt-3">
            <thead>
            <tr>
                <th>Tên sự kiện</th>
                <th>Thời gian</th>
                <th>Địa điểm</th>
                <th>Mô tả</th>
                <th>Người đăng ký</th>
                <th>Thao tác</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="e" items="${events}">
                <tr>
                    <td>${e.eventName}</td>
                    <td><fmt:formatDate value="${e.eventDate}" pattern="dd/MM/yyyy HH:mm"/></td>
                    <td>${e.location}</td>
                    <td>${e.description}</td>
                    <td>
                        <c:out value="${registeredCounts[e.eventID]}"/>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${fn:contains(registeredIds, e.eventID)}">
                                <span class="badge bg-success">Đã đăng ký</span>
                            </c:when>
                            <c:otherwise>
                                <form action="RegisterEventServlet" method="post" style="display:inline;">
                                    <input type="hidden" name="eventId" value="${e.eventID}">
                                    <button type="submit" class="btn btn-primary btn-sm">Đăng ký</button>
                                </form>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
</div>

<%@ include file="includes/footer.jsp" %>

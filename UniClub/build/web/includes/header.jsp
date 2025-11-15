<%-- 
    Document   : header
    Created on : Oct 20, 2025, 4:59:41 PM
    Author     : hoang
--%>

<%-- 
    Header chung cho UniClub
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="TemplateMo">
    <link href="https://fonts.googleapis.com/css?family=Poppins:100,200,300,400,500,600,700,800,900" rel="stylesheet">

    <title>
        <c:choose>
            <c:when test="${not empty pageTitle}">${pageTitle}</c:when>
            <c:otherwise>UniClub</c:otherwise>
        </c:choose>
    </title>

    <!-- Bootstrap core CSS -->
    <link href="${pageContext.request.contextPath}/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Template CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/fontawesome.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/templatemo-edu-meeting.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/owl.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/lightbox.css">
</head>
<body>

<!-- Lấy user từ session bằng JSTL -->
<c:set var="me" value="${sessionScope.user}" />

<!-- Sub Header -->
<div class="sub-header">
    <div class="container">
        <div class="row">
            <div class="col-lg-8 col-sm-8">
                <div class="left-content">
                    <p>Hệ thống quản lý <em>UniClub</em> - Kết nối sinh viên và CLB.</p>
                </div>
            </div>
            <div class="col-lg-4 col-sm-4">
                <div class="right-icons">
                    <ul>
                        <li><a href="#"><i class="fa fa-facebook"></i></a></li>
                        <li><a href="#"><i class="fa fa-twitter"></i></a></li>
                        <li><a href="#"><i class="fa fa-instagram"></i></a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Header Navigation -->
<header class="header-area header-sticky">
    <div class="container">
        <div class="row">
            <div class="col-12">
                <nav class="main-nav">
                    <a href="${pageContext.request.contextPath}/home.jsp" class="logo">UniClub</a>
                    <ul class="nav">
                        <li><a href="${pageContext.request.contextPath}/home.jsp">Trang chủ</a></li>
                        <li><a href="${pageContext.request.contextPath}/EventListServlet">Sự kiện</a></li>
                        <li><a href="${pageContext.request.contextPath}/ClubListServlet">Câu lạc bộ</a></li>
                        <li><a href="#">Báo cáo</a></li>
                        <li><a href="#">Liên hệ</a></li>

                        <!-- Member (RoleID = 5) -> Yêu cầu nâng vai trò -->
                        <c:if test="${not empty me and me.roleID == 5}">
                            <li><a href="${pageContext.request.contextPath}/request_role.jsp">
                                Yêu cầu nâng vai trò</a></li>
                        </c:if>

                        <!-- Admin (1) hoặc Chairman (2) -> Duyệt yêu cầu & Tạo sự kiện -->
                        <c:if test="${not empty me and (me.roleID == 1 or me.roleID == 2)}">
                            <li><a href="${pageContext.request.contextPath}/role_requests_admin.jsp">
                                Duyệt yêu cầu vai trò</a></li>
                            <li><a href="${pageContext.request.contextPath}/create_event.jsp">
                                Tạo sự kiện</a></li>
                        </c:if>

                        <!-- Đăng xuất nếu đã đăng nhập -->
                        <c:if test="${not empty me}">
                            <li><a href="${pageContext.request.contextPath}/logout">Đăng xuất</a></li>
                        </c:if>
                    </ul>
                    <a class="menu-trigger"><span>Menu</span></a>
                </nav>
            </div>
        </div>
    </div>
</header>


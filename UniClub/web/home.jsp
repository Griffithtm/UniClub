<%
    // Nếu chưa đăng nhập thì quay lại trang login
    if (session.getAttribute("user") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<%@ include file="includes/header.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%
    request.setAttribute("pageTitle", "Trang chủ UniClub");
%>

<section class="section main-banner" id="top" data-section="section1">
    <video autoplay muted loop id="bg-video">
        <source src="${pageContext.request.contextPath}/assets/images/course-video.mp4" type="video/mp4" />
    </video>
    <div class="video-overlay header-text">
        <div class="container">
            <div class="caption">
                <h6>Xin chào sinh viên!</h6>
                <h2>Chào mừng đến với <em>UniClub</em></h2>
                <p>Nơi quản lý và kết nối các Câu lạc bộ, Sự kiện và Thành viên trong trường.</p>
                <div class="main-button-red">
                    <a href="${pageContext.request.contextPath}/meetings.jsp">Khám phá ngay</a>
                </div>
            </div>
        </div>
    </div>
</section>

<section class="services">
    <div class="container">
        <div class="row">
            <div class="col-lg-4">
                <div class="item">
                    <div class="icon"><img src="${pageContext.request.contextPath}/assets/images/service-icon-01.png" alt=""></div>
                    <div class="down-content">
                        <h4>Quản lý sự kiện</h4>
                        <p>Tạo, chỉnh sửa và theo dõi các sự kiện CLB nhanh chóng.</p>
                    </div>
                </div>
            </div>
            <div class="col-lg-4">
                <div class="item">
                    <div class="icon"><img src="${pageContext.request.contextPath}/assets/images/service-icon-02.png" alt=""></div>
                    <div class="down-content">
                        <h4>Thành viên CLB</h4>
                        <p>Theo dõi danh sách, điểm rèn luyện và đóng góp của thành viên.</p>
                    </div>
                </div>
            </div>
            <div class="col-lg-4">
                <div class="item">
                    <div class="icon"><img src="${pageContext.request.contextPath}/assets/images/service-icon-03.png" alt=""></div>
                    <div class="down-content">
                        <h4>Báo cáo & Thống kê</h4>
                        <p>Xem dữ liệu, hoạt động và hiệu quả từng CLB.</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<%@ include file="includes/footer.jsp" %>


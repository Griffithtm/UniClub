<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="includes/header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    request.setAttribute("pageTitle", "Đăng nhập - UniClub");

    // Nếu đã đăng nhập, chuyển về trang chủ
    if (session.getAttribute("user") != null) {
        response.sendRedirect("index.jsp");
        return;
    }
%>

<section class="section main-banner" id="top" data-section="section1">
    <video autoplay muted loop id="bg-video">
        <source src="${pageContext.request.contextPath}/assets/images/course-video.mp4" type="video/mp4" />
    </video>
    <div class="video-overlay header-text">
        <div class="container">
            <div class="caption text-center">
                <h6>Chào mừng trở lại!</h6>
                <h2>Đăng nhập vào <em>UniClub</em></h2>
                <p>Truy cập để quản lý Câu lạc bộ, Sự kiện và Thành viên.</p>

                <!-- Form đăng nhập -->
                <div class="d-flex justify-content-center mt-4">
                    <div class="card p-4" style="max-width:400px; background: rgba(255,255,255,0.9); border-radius: 12px;">
                        <form action="login" method="post">
                            <div class="mb-3 text-start">
                                <label for="email" class="form-label">📧 Email</label>
                                <input type="email" id="email" name="email" class="form-control" placeholder="Nhập email" required>
                            </div>
                            <div class="mb-3 text-start">
                                <label for="password" class="form-label">🔒 Mật khẩu</label>
                                <input type="password" id="password" name="password" class="form-control" placeholder="Nhập mật khẩu" required>
                            </div>
                            <button type="submit" class="btn btn-danger w-100 mb-2">Đăng nhập</button>

                            <a href="GoogleLoginServlet" class="btn btn-outline-secondary w-100 mb-2">Đăng nhập bằng Gmail</a>

                            <div class="text-center mt-2">
                                <a href="register.jsp" class="text-dark me-3">Đăng ký</a>
                                <a href="forgot.jsp" class="text-dark">Quên mật khẩu?</a>
                            </div>
                        </form>
                    </div>
                </div>

            </div>
        </div>
    </div>
</section>

<%@ include file="includes/footer.jsp" %>

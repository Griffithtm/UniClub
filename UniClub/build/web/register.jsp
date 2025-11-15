<%-- 
    Document   : register
    Created on : Oct 23, 2025, 4:20:27 PM
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
        <form action="register" method="post">
            <label>Họ tên:</label>
            <input type="text" name="fullname" required>
            <label>Email:</label>
            <input type="email" name="email" required>
            <label>Mật khẩu:</label>
            <input type="password" name="password" required>
            <button type="submit">Đăng ký</button>
        </form>
    </body>
</html>

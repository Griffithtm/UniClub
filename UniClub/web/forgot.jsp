<%-- 
    Document   : forgot
    Created on : Oct 23, 2025, 4:24:46 PM
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
        <form action="forgot" method="post">
            <label>Nhập email của bạn:</label>
            <input type="email" name="email" required>
            <button type="submit">Gửi link khôi phục</button>
        </form>

    </body>
</html>

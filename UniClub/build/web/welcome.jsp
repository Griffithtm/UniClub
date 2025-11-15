<%-- 
    Document   : welcome
    Created on : Oct 23, 2025, 3:03:09 PM
    Author     : hoang
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.User" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Chào mừng bạn!</title>
    <style>
        body {
            margin: 0;
            height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            background: linear-gradient(135deg, #6a11cb, #2575fc);
            overflow: hidden;
            color: white;
            font-family: 'Segoe UI', sans-serif;
        }

        .welcome {
            text-align: center;
            animation: fadeIn 1.5s ease-in-out;
        }

        .welcome h1 {
            font-size: 2.8em;
            margin-bottom: 10px;
            animation: slideUp 1.5s ease-in-out;
        }

        .welcome p {
            font-size: 1.3em;
            opacity: 0;
            animation: fadeInText 2s ease-in-out 1.5s forwards;
        }

        @keyframes fadeIn {
            from { opacity: 0; transform: scale(0.9); }
            to { opacity: 1; transform: scale(1); }
        }

        @keyframes slideUp {
            from { transform: translateY(50px); opacity: 0; }
            to { transform: translateY(0); opacity: 1; }
        }

        @keyframes fadeInText {
            from { opacity: 0; }
            to { opacity: 1; }
        }
        
        @keyframes float {
            0%, 100% { transform: translateY(0); }
            50% { transform: translateY(-10px); }
        }
        .welcome h1 {
            animation: float 2s ease-in-out infinite;
        }

    </style>

    <script>
        // ⏳ Sau 3 giây, chuyển đến home.jsp
        setTimeout(() => {
            window.location.href = "home.jsp";
        }, 3000);
    </script>
</head>
<body>
    <div class="welcome">
        <h1>✨ Chào mừng bạn, <%= user.getFullName() %>!</h1>
        <p>Đang chuyển đến trang chủ UniClub...</p>
    </div>
</body>
</html>

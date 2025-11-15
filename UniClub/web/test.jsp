<%-- 
    Document   : index
    Created on : Oct 2, 2025, 8:13:35 PM
    Author     : hoang
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Chọn CLB và Học kỳ</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container mt-5">
    <h2 class="text-center mb-4">📌 Xem báo cáo tham gia CLB</h2>

    <form action="participationreport" method="get" class="card p-4 shadow-sm">
        <div class="mb-3">
            <label for="clubId" class="form-label">ID Câu lạc bộ</label>
            <input type="number" class="form-control" id="clubId" name="clubId" placeholder="Nhập Club ID" required>
        </div>

        <div class="mb-3">
            <label for="semester" class="form-label">Học kỳ</label>
            <select class="form-select" id="semester" name="semester" required>
                <option value="">-- Chọn học kỳ --</option>
                <option value="2025-HK1">2025A</option>
                <option value="2025B">2025B</option>
                <option value="2024A">2024A</option>
                <option value="2024B">2024B</option>
            </select>
        </div>

        <div class="text-center">
            <button type="submit" class="btn btn-primary">📊 Xem báo cáo</button>
        </div>
    </form>
</div>

</body>
</html>


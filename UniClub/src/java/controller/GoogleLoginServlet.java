package controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dal.DBConnect;
import dao.UserDAO;
import java.sql.SQLException;
import java.sql.Connection;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import model.User;

import java.io.*;
import java.util.*;

public class GoogleLoginServlet extends HttpServlet {

    private static final String CLIENT_ID = "70967209815-9opcu15jdkfh5b3f7cf4ub6kpldt9g2g.apps.googleusercontent.com";
    private static final String CLIENT_SECRET = "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";
    private static final String REDIRECT_URI = "http://localhost:9999/UniClub/GoogleLoginServlet";

    private static final Collection<String> SCOPES = Arrays.asList("email", "profile");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        String code = req.getParameter("code");

        if (code == null || code.isEmpty()) {
            // 🔹 Bước 1: Chuyển hướng đến trang đăng nhập Google
            GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                    new NetHttpTransport(),
                    JacksonFactory.getDefaultInstance(),
                    CLIENT_ID,
                    CLIENT_SECRET,
                    SCOPES
            ).build();

            String authUrl = flow.newAuthorizationUrl()
                    .setRedirectUri(REDIRECT_URI)
                    .build();

            resp.sendRedirect(authUrl);
            return;
        }

        try {
            // 🔹 Bước 2: Lấy token từ Google
            GoogleTokenResponse tokenResponse = new GoogleAuthorizationCodeFlow.Builder(
                    new NetHttpTransport(),
                    JacksonFactory.getDefaultInstance(),
                    CLIENT_ID,
                    CLIENT_SECRET,
                    SCOPES
            ).build()
                    .newTokenRequest(code)
                    .setRedirectUri(REDIRECT_URI)
                    .execute();

            String accessToken = tokenResponse.getAccessToken();

            // 🔹 Bước 3: Lấy thông tin user từ Google API
            String userInfoUrl = "https://www.googleapis.com/oauth2/v2/userinfo?access_token=" + accessToken;
            JsonObject profile;
            try (InputStreamReader reader = new InputStreamReader(new java.net.URL(userInfoUrl).openStream())) {
                profile = JsonParser.parseReader(reader).getAsJsonObject();
            }

            String email = profile.get("email").getAsString();
            String name = profile.get("name").getAsString();

            // 🔹 Bước 4: Kiểm tra và lưu vào DB
            try (Connection conn = new DBConnect().getConnection()) {
                UserDAO dao = new UserDAO(conn);
                User user = dao.getUserByEmail(email);

                if (user == null) {
                    // Nếu user chưa có, tạo mới
                    user = new User();
                    user.setFullName(name);
                    user.setEmail(email);
                    user.setRoleID(5); // 5 = Member
                    // 🔹 Thêm giá trị mặc định cho PasswordHash để tránh NULL
                    user.setPasswordHash("GOOGLE_USER"); // hoặc UUID.randomUUID().toString()

                    dao.insertUserByGoogle(user);
                    user = dao.getUserByEmail(email); // lấy lại user sau khi insert
                }

                // 🔹 Lưu vào session
                req.getSession().setAttribute("user", user);
            }

            // 🔹 Chuyển hướng về trang chủ
            resp.sendRedirect(req.getContextPath() + "/welcome.jsp");

        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Database error during Google login", e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Google login failed", e);
        }
    }
}

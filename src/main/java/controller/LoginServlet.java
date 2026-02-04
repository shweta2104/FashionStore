package controller;

import dao.UserDAO;
import model.User;
import util.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Get Login Form Data
        String loginId = request.getParameter("loginId");
        String password = request.getParameter("password");

        // 2. DAO Login Check
        UserDAO dao = new UserDAO();
        User user = dao.login(loginId, password);

        // 3. If Login Success
        if (user != null) {

            // Create Session
            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            // ✅ Update Visit Count in Database
            try {
                Connection con = DBConnection.getConnection();

                PreparedStatement ps = con.prepareStatement(
                    "UPDATE user_master SET visit_count = visit_count + 1 WHERE user_id = ?"
                );

                ps.setInt(1, user.getUserId());
                ps.executeUpdate();

                ps.close();
                con.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

            // Redirect to Shop Page
            response.sendRedirect("shop.jsp");

        } 
        // 4. If Login Failed
        else {
            response.sendRedirect("login.jsp?error=invalid");
        }
    }
}

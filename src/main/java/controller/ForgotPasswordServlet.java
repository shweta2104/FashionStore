/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import dao.UserDAO;
import util.MailUtil;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
/**
 *
 * @author root
 */
@WebServlet("/ForgotPasswordServlet")
public class ForgotPasswordServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String loginId = request.getParameter("loginId");
        String email = request.getParameter("email");

        UserDAO dao = new UserDAO();
        String password = dao.getPassword(loginId, email);

        if (password != null) {
            // Send email with password
            MailUtil.sendMail(email, "Fashion Store Password Recovery",
                    "Your password is: " + password);

            response.sendRedirect("forgotPassword.jsp?msg=sent");
        } else {
            response.sendRedirect("forgotPassword.jsp?error=notfound");
        }
    }
}


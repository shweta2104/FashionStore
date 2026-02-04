/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java
 */
package controller;

import dao.UserDAO;
import model.User;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author root
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        // ✅ CAPTCHA Validation
        int captchaAnswer = Integer.parseInt(request.getParameter("captcha"));
        int captchaSession = (int) session.getAttribute("captcha");

        if (captchaAnswer != captchaSession) {
            response.sendRedirect("register.jsp?error=captcha");
            return;
        }

        // ✅ Collect User Form Data
        User user = new User();

        user.setUsername(request.getParameter("username"));
        user.setLoginId(request.getParameter("loginId"));
        user.setPassword(request.getParameter("password"));

        user.setEmail(request.getParameter("email"));
        user.setPhone(request.getParameter("phone"));

        // ✅ New Fields Added
        user.setAddress(request.getParameter("address"));
        user.setCity(request.getParameter("city"));
        user.setState(request.getParameter("state"));
        user.setCountry(request.getParameter("country"));
        user.setPin(request.getParameter("pin"));
        user.setDob(request.getParameter("dob")); // Date as String

        // ✅ Register User using DAO
        UserDAO dao = new UserDAO();
        boolean status = dao.register(user);

        if (status) {
            response.sendRedirect("login.jsp?msg=registered");
        } else {
            response.sendRedirect("register.jsp?error=failed");
        }
    }
}

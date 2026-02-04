/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import util.BirthdayEmailUtil;
import util.DBConnection;

/**
 *
 * @author root
 */
@WebServlet("/BirthdayMailServlet")
public class BirthdayMailServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                "SELECT name, email FROM user_master " +
                "WHERE DAY(dob)=DAY(CURDATE()) AND MONTH(dob)=MONTH(CURDATE())"
            );

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");

                BirthdayEmailUtil.sendBirthdayMail(email, name);
            }

            response.getWriter().println("Birthday Emails Sent Successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


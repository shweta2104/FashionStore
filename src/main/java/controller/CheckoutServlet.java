package controller;

import util.DBConnection;
import model.CartItem;
import model.Product;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/CheckoutServlet")
public class CheckoutServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        ArrayList<CartItem> cart =
                (ArrayList<CartItem>) session.getAttribute("cart");

        if (cart == null || cart.isEmpty()) {
            response.sendRedirect("cart.jsp");
            return;
        }

        String paymentMode = request.getParameter("paymentMode");

        try {
            Connection con = DBConnection.getConnection();

            double totalAmount = 0;

            // Calculate Total
            for (CartItem item : cart) {
                Product p = item.getProduct();

                double price = p.getPrice();
                double discount = p.getDiscount();

                double subtotal =
                        (price - (price * discount / 100))
                                * item.getQuantity();

                totalAmount += subtotal;
            }

            double tax = totalAmount * 0.05;
            double finalTotal = totalAmount + tax;

            // ✅ Insert into order_master
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO order_master(order_datetime, session_id, payment_mode, tax, total_amount, order_status) " +
                            "VALUES (NOW(), ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );

            ps.setString(1, session.getId());
            ps.setString(2, paymentMode);
            ps.setDouble(3, tax);
            ps.setDouble(4, finalTotal);
            ps.setString(5, "Pending");

            ps.executeUpdate();

            // ✅ Get Generated Order ID
            ResultSet rs = ps.getGeneratedKeys();
            int orderId = 0;

            if (rs.next()) {
                orderId = rs.getInt(1);
            }

            // ✅ Insert into order_details
            PreparedStatement ps2 = con.prepareStatement(
                    "INSERT INTO order_details(order_id, product_id, product_price, discount) " +
                            "VALUES (?, ?, ?, ?)"
            );

            for (CartItem item : cart) {

                Product p = item.getProduct();

                ps2.setInt(1, orderId);
                ps2.setInt(2, p.getProductId());
                ps2.setDouble(3, p.getPrice());
                ps2.setDouble(4, p.getDiscount());

                ps2.executeUpdate();
            }

            // ✅ Clear Cart
            session.removeAttribute("cart");

            // ✅ Redirect to Bill Page
            response.sendRedirect("bill.jsp?orderId=" + orderId);

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error in Checkout: " + e.getMessage());
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import dao.OrderDAO;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import model.CartItem;
import model.Order;
import model.Product;
/**
 *
 * @author root
 */
@WebServlet("/OrderServlet")
public class OrderServlet extends HttpServlet {

    private OrderDAO orderDAO = new OrderDAO();

   @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    HttpSession session = request.getSession();
    List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

    if (cart == null || cart.isEmpty()) {
        response.sendRedirect("shop.jsp");
        return;
    }

    try {
        String paymentMode = request.getParameter("paymentMode");
        double taxRate = 0.05; // 5% tax
        double totalAmount = 0;

        // Calculate total amount
        for (CartItem item : cart) {
            totalAmount += item.getSubtotal();
        }

        double tax = totalAmount * taxRate;
        double grandTotal = totalAmount + tax;

        // Create Order object
        Order order = new Order();
        order.setOrderDatetime(java.util.Date.from(java.time.Instant.now()));
        order.setSessionId(session.getId());
        order.setPaymentMode(paymentMode);
        order.setTax(tax);
        order.setTotalAmount(grandTotal);
        order.setOrderStatus("Pending"); // initial status

        // 🔹 Convert List<CartItem> to List<Product>
        List<Product> products = new ArrayList<>();
        for (CartItem item : cart) {
            products.add(item.getProduct()); // extract Product from CartItem
        }

        // Save order in DB
        int orderId = orderDAO.placeOrder(order, products);

        // Clear cart after order
        session.removeAttribute("cart");

        // Redirect to order confirmation page
        response.sendRedirect("orderConfirmation.jsp?orderId=" + orderId);

    } catch (Exception e) {
        e.printStackTrace();
        response.sendRedirect("shop.jsp");
    }
}

    }

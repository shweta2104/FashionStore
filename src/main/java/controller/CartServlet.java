/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Product;
import model.CartItem;
import dao.ProductDAO;
/**
 *
 * @author root
 */
@WebServlet("/CartServlet")
public class CartServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }

        String remove = request.getParameter("remove");
        String update = request.getParameter("update");
        String productIdParam = request.getParameter("productId");

        ProductDAO dao = new ProductDAO();

        if (remove != null) {
            int removeId = Integer.parseInt(remove);
            cart.removeIf(item -> item.getProduct().getProductId() == removeId);
        } else if (update != null) {
            for (CartItem item : cart) {
                String qtyParam = request.getParameter("quantity_" + item.getProduct().getProductId());
                if (qtyParam != null) {
                    int qty = Integer.parseInt(qtyParam);
                    item.setQuantity(qty);
                }
            }
        } else if (productIdParam != null) { // Add to Cart
            int productId = Integer.parseInt(productIdParam);
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            Product product = dao.getProductById(productId);

            if (product != null) {
                boolean found = false;
                for (CartItem item : cart) {
                    if (item.getProduct().getProductId() == productId) {
                        item.setQuantity(item.getQuantity() + quantity);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    cart.add(new CartItem(product, quantity));
                }
            }
        }

        session.setAttribute("cart", cart);
        response.sendRedirect("cart.jsp"); // redirect to cart page
    }
}

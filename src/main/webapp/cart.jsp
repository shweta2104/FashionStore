<%-- 
    Document   : cart
    Created on : 27-Jan-2026, 11:21:50 am
    Author     : root
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.CartItem" %>
<%@ page import="model.Product" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Shopping Cart</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<header>
    <h1>Your Shopping Cart</h1>
    <nav>
        <a href="shop.jsp">Continue Shopping</a>
    </nav>
</header>

<%
    List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
    if (cart == null || cart.isEmpty()) {
%>
    <p class="message">Your cart is empty. <a href="shop.jsp">Go shopping!</a></p>
<%
    } else {
        double totalAmount = 0;
%>

<form action="CartServlet" method="post">
    <table>
        <tr>
            <th>Product</th>
            <th>Price</th>
            <th>Discount (%)</th>
            <th>Quantity</th>
            <th>Subtotal</th>
            <th>Action</th>
        </tr>
    <%
        for (CartItem item : cart) {
            Product p = item.getProduct();
            double subtotal = (p.getPrice() - p.getPrice() * p.getDiscount() / 100) * item.getQuantity();
            totalAmount += subtotal;
    %>
        <tr>
            <td><%= p.getProductName() %></td>
            <td>$<%= p.getPrice() %></td>
            <td><%= p.getDiscount() %>%</td>
            <td>
                <input type="number" name="quantity_<%= p.getProductId() %>" value="<%= item.getQuantity() %>" min="1">
            </td>
            <td>$<%= String.format("%.2f", subtotal) %></td>
            <td>
                <button type="submit" name="remove" value="<%= p.getProductId() %>" class="remove-btn">Remove</button>
            </td>
        </tr>
    <%
        }
    %>
        <tr>
            <td colspan="4" style="text-align:right;"><strong>Total:</strong></td>
            <td colspan="2">$<%= String.format("%.2f", totalAmount) %></td>
        </tr>
    </table>

    <div style="text-align:center; margin-top:20px;">
        <button type="submit" name="update" class="update-btn">Update Cart</button>
        <a href="OrderServlet" class="checkout-btn" style="text-decoration:none; padding:10px 20px; color:white; border-radius:3px;">Checkout</a>
    </div>
</form>

<%
    }
%>

</body>
</html>


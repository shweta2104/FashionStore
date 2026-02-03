<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.CartItem"%>
<%@page import="model.Product"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Checkout Page</title>

    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/checkout.css">
</head>

<body>

<h2 align="center">Checkout Page</h2>

<%
    ArrayList<CartItem> cart =
            (ArrayList<CartItem>) session.getAttribute("cart");

    if (cart == null || cart.isEmpty()) {
%>

    <h3 align="center" style="color:red;">
        Your Cart is Empty!
    </h3>

    <center>
        <a href="shop.jsp">Go Back to Shop</a>
    </center>

<%
        return;
    }

    double totalAmount = 0;
%>

<!-- Cart Items Table -->
<table border="1" cellpadding="10" cellspacing="0"
       align="center" width="80%">

    <tr>
        <th>Product</th>
        <th>Price</th>
        <th>Discount (%)</th>
        <th>Quantity</th>
        <th>Subtotal</th>
    </tr>

<%
    for (CartItem item : cart) {

        Product p = item.getProduct();

        double price = p.getPrice();
        double discount = p.getDiscount();

        double discountedPrice =
                price - (price * discount / 100);

        double subtotal =
                discountedPrice * item.getQuantity();

        totalAmount += subtotal;
%>

    <tr>
        <td><%= p.getProductName() %></td>
        <td>₹<%= price %></td>
        <td><%= discount %>%</td>
        <td><%= item.getQuantity() %></td>
        <td>₹<%= String.format("%.2f", subtotal) %></td>
    </tr>

<%
    }
%>

</table>

<%
    double tax = totalAmount * 0.05;   // 5% Tax
    double finalTotal = totalAmount + tax;
%>

<br>

<!-- Total Summary -->
<h3 align="center">Bill Summary</h3>

<table border="1" cellpadding="8" align="center">
    <tr>
        <td>Total Amount</td>
        <td>₹<%= String.format("%.2f", totalAmount) %></td>
    </tr>
    <tr>
        <td>Tax (5%)</td>
        <td>₹<%= String.format("%.2f", tax) %></td>
    </tr>
    <tr>
        <td><b>Final Total</b></td>
        <td><b>₹<%= String.format("%.2f", finalTotal) %></b></td>
    </tr>
</table>

<br><br>

<!-- Checkout Form -->
<form action="<%= request.getContextPath() %>/CheckoutServlet" method="post" align="center">


    <h3>Select Payment Mode:</h3>

    <select name="paymentMode" required>
        <option value="COD">Cash On Delivery</option>
        <option value="CARD">Card Payment</option>
    </select>

    <!-- Hidden Total -->
    <input type="hidden" name="totalAmount"
           value="<%= finalTotal %>">

    <br><br>

    <input type="submit" value="Place Order">

</form>

</body>
</html>

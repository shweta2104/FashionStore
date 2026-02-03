<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>
<%@page import="util.DBConnection"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Order Details</title>

    <link rel="stylesheet" href="css/style.css">
</head>

<body>

<h2 align="center">🛒 Order Details</h2>

<%
    String orderId = request.getParameter("orderId");

    if(orderId == null){
%>
    <h3 align="center" style="color:red;">No Order Found!</h3>
<%
        return;
    }

    Connection con = DBConnection.getConnection();

    // Fetch Order Master Info
    PreparedStatement ps =
        con.prepareStatement("SELECT * FROM order_master WHERE order_id=?");

    ps.setInt(1, Integer.parseInt(orderId));

    ResultSet rs = ps.executeQuery();

    if(rs.next()){

        String paymentMode = rs.getString("payment_mode");
        double totalAmount = rs.getDouble("total_amount");
        double tax = rs.getDouble("tax");
        String status = rs.getString("order_status");
%>

<!-- ============================
     Order Summary Section
============================ -->

<table border="1" cellpadding="10" align="center" width="60%">
    <tr>
        <th>Order ID</th>
        <td><%= orderId %></td>
    </tr>

    <tr>
        <th>Payment Mode</th>
        <td><%= paymentMode %></td>
    </tr>

    <tr>
        <th>Status</th>
        <td><%= status %></td>
    </tr>
</table>

<br><br>

<h3 align="center">📦 Ordered Products</h3>

<!-- ============================
     Product Details Section
============================ -->

<table border="1" cellpadding="10" align="center" width="85%">

    <tr>
        <th>Product Name</th>
        <th>Price</th>
        <th>Discount (%)</th>
        <th>Final Price</th>
    </tr>

<%
    PreparedStatement ps2 =
        con.prepareStatement(
            "SELECT p.product_name, od.product_price, od.discount " +
            "FROM order_details od " +
            "JOIN product_master p ON od.product_id=p.product_id " +
            "WHERE od.order_id=?"
        );

    ps2.setInt(1, Integer.parseInt(orderId));

    ResultSet rs2 = ps2.executeQuery();

    double grandTotal = 0;

    while(rs2.next()){

        String productName = rs2.getString("product_name");
        double price = rs2.getDouble("product_price");
        double discount = rs2.getDouble("discount");

        // Final Price after Discount
        double finalPrice = price - (price * discount / 100);

        grandTotal += finalPrice;
%>

<tr>
    <td><%= productName %></td>
    <td>₹<%= price %></td>
    <td><%= discount %>%</td>
    <td>₹<%= String.format("%.2f", finalPrice) %></td>
</tr>

<%
    }
%>

</table>

<br><br>

<!-- ============================
     Total Summary Section
============================ -->

<h3 align="center">🧾 Payment Summary</h3>

<table border="1" cellpadding="10" align="center" width="50%">

    <tr>
        <th>Grand Total</th>
        <td>₹<%= String.format("%.2f", grandTotal) %></td>
    </tr>

    <tr>
        <th>Tax</th>
        <td>₹<%= String.format("%.2f", tax) %></td>
    </tr>

    <tr>
        <th><b>Final Amount Paid</b></th>
        <td><b>₹<%= String.format("%.2f", totalAmount) %></b></td>
    </tr>

</table>

<%
    }
%>

<br><br>

<center>
    <a href="MyOrdersServlet">⬅ Back to My Orders</a>
    &nbsp;&nbsp; | &nbsp;&nbsp;
    <a href="shop.jsp">Continue Shopping</a>
</center>

</body>
</html>

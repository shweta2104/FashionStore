<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>
<%@page import="util.DBConnection"%>

<!DOCTYPE html>
<html>
<head>
    <title>Bill Invoice</title>
    <link rel="stylesheet" href="css/bill.css">
</head>

<body>

<h2 align="center">Fashion Store - Bill Invoice</h2>

<%
    int orderId = Integer.parseInt(request.getParameter("orderId"));

    Connection con = DBConnection.getConnection();

    /* ===========================
       Fetch Order Master Details
       =========================== */
    PreparedStatement psOrder = con.prepareStatement(
        "SELECT * FROM order_master WHERE order_id=?"
    );
    psOrder.setInt(1, orderId);

    ResultSet rsOrder = psOrder.executeQuery();

    if(rsOrder.next()){
%>

<div class="bill-box">

    <h3>Order ID: <%= orderId %></h3>

    <p><b>Payment Mode:</b> <%= rsOrder.getString("payment_mode") %></p>

    <p><b>Order Date:</b> <%= rsOrder.getString("order_datetime") %></p>

    <p><b>Status:</b> <%= rsOrder.getString("order_status") %></p>

</div>

<br>

<!-- ===========================
     Product Details Table
     =========================== -->

<h3 align="center">Ordered Products</h3>

<table border="1" align="center" cellpadding="10" width="80%">

    <tr>
        <th>Product Name</th>
        <th>Price</th>
        <th>Discount (%)</th>
        <th>Final Price</th>
    </tr>

<%
    /* ===========================
       Fetch Order Details + Products
       =========================== */
    PreparedStatement psDetails = con.prepareStatement(
        "SELECT od.product_price, od.discount, pm.product_name " +
        "FROM order_details od " +
        "JOIN product_master pm ON od.product_id = pm.product_id " +
        "WHERE od.order_id=?"
    );

    psDetails.setInt(1, orderId);

    ResultSet rsDetails = psDetails.executeQuery();

    double grandTotal = 0;

    while(rsDetails.next()) {

        String productName = rsDetails.getString("product_name");
        double price = rsDetails.getDouble("product_price");
        double discount = rsDetails.getDouble("discount");

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

<br>

<!-- ===========================
     Total Summary
     =========================== -->

<div class="bill-box">

    <p><b>Grand Total:</b> ₹<%= String.format("%.2f", grandTotal) %></p>

    <p><b>Tax (5%):</b>
        ₹<%= String.format("%.2f", rsOrder.getDouble("tax")) %>
    </p>

    <p><b>Final Total Amount:</b>
        ₹<%= String.format("%.2f", rsOrder.getDouble("total_amount")) %>
    </p>

</div>

<br><br>

<center>

    <!-- Print Bill -->
    <button onclick="window.print()">Print Bill</button>

    <!-- Download PDF -->
    <a href="DownloadBillServlet?orderId=<%= orderId %>">
        <button>Download PDF</button>
    </a>

</center>

<%
    }
%>

<br><br>

<center>
    <a href="orderDetails.jsp">View My Orders History</a>
</center>

</body>
</html>

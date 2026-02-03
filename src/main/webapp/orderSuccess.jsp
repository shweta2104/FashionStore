<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Order Success</title>

    <link rel="stylesheet" href="css/style.css">
</head>

<body>

    <h2 align="center" style="color:green;">
        🎉 Order Placed Successfully!
    </h2>

    <hr>

    <center>
        <h3>Thank you for shopping with Fashion Store!</h3>

        <%
            String orderId = request.getParameter("orderId");
            if(orderId != null){
        %>

            <p><b>Your Order ID:</b> <%= orderId %></p>

        <%
            }
        %>

        <p>Your payment status is currently: <b>Pending</b></p>

        <br><br>

        <!-- Buttons -->
        <a href="shop.jsp"
           style="padding:10px 20px; background:blue; color:white; text-decoration:none; border-radius:5px;">
            Continue Shopping
        </a>

        <br><br>

        <a href="cart.jsp"
           style="padding:10px 20px; background:gray; color:white; text-decoration:none; border-radius:5px;">
            Back to Cart
        </a>

    </center>

</body>
</html>

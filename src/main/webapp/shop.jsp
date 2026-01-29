<%-- 
    Document   : shop
    Created on : 27-Jan-2026
    Author     : root
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="dao.ProductDAO" %>
<%@ page import="model.Product" %>

<!DOCTYPE html>
<html>
<head>
    <title>Shop</title>
   
        <link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/shop.css">


    </style
</head>
<body>

<div class="top-bar">
    <h1>Shop</h1>
    <a href="cart.jsp">🛒 View Cart</a>
</div>
<hr>

<hr>

<%
    ProductDAO dao = new ProductDAO();
    List<Product> products = dao.getAllProducts();

    if (products == null || products.isEmpty()) {
%>
        <p>No products available.</p>
<%
    } else {
        for (Product p : products) {
%>
        <div class="product-card">

            <!-- PRODUCT IMAGE -->
            <img src="<%= request.getContextPath() %>/images/product/<%= p.getImage() %>"
                 alt="<%= p.getProductName() %>">

            <!-- PRODUCT DETAILS -->
            <div>
                <h3><%= p.getProductName() %></h3>
                <p>Price: ₹<%= p.getPrice() %></p>
                <p>Discount: <%= p.getDiscount() %>%</p>

                <form action="CartServlet" method="post">
                    <input type="hidden" name="productId" value="<%= p.getProductId() %>">
                    <input type="hidden" name="action" value="add">
                    Quantity:
                    <input type="number" name="quantity" value="1" min="1">
                    <button type="submit">Add to Cart</button>
                </form>
            </div>

        </div>
<%
        }
    }
%>

</body>
</html>

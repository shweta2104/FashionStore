<%-- 
    Document   : forgotPassword
    Created on : 27-Jan-2026, 11:21:29 am
    Author     : root
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Forgot Password | Fashion Store</title>
        <link rel="stylesheet" type="text/css" href="css/style.css">
    </head>
    <body>
        <div class="login-box">
            <h2>Forgot Password</h2>

            <%-- Error/Info Messages --%>
            <%
                String error = request.getParameter("error");
                String msg = request.getParameter("msg");

                if ("notfound".equals(error)) {
            %>
                <div class="error">No user found with these details!</div>
            <%
                } else if ("sent".equals(msg)) {
            %>
                <div class="success">Password has been sent to your email.</div>
            <%
                }
            %>

            <form action="ForgotPasswordServlet" method="post">
                <label>Login ID</label>
                <input type="text" name="loginId" required>

                <label>Email</label>
                <input type="email" name="email" required>

                <input type="submit" value="Recover Password">
            </form>

            <div class="links">
                <a href="login.jsp">Back to Login</a>
            </div>
        </div>
    </body>
</html>

<%-- 
    Document   : login
    Created on : 27-Jan-2026, 11:21:19 am
    Author     : root
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
    <meta charset="UTF-8">
    <title>Login | Fashion Store</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <link rel="stylesheet" type="text/css" href="css/login.css">
</head>


    <body>

        <div class="login-box">
            <h2>User Login</h2>

            <%-- Error / Success Messages --%>
            <%
                String error = request.getParameter("error");
                String msg = request.getParameter("msg");

                if ("invalid".equals(error)) {
            %>
                <div class="error">Invalid Login ID or Password</div>
            <%
                }
                if ("registered".equals(msg)) {
            %>
                <div class="success">Registration successful. Please login.</div>
            <%
                }
            %>

            <%-- Login Form --%>
            <form action="LoginServlet" method="post">
                <label>Login ID</label>
                <input type="text" name="loginId" required>

                <label>Password</label>
                <input type="password" name="password" required>

                <input type="submit" value="Login">
            </form>

            <div class="links">
                <a href="register.jsp">New User? Register</a><br>
                <a href="forgotPassword.jsp">Forgot Password?</a>
            </div>
        </div>

    </body>
</html>

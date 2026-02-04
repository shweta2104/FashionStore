<%-- 
    Document   : register
    Created on : 27-Jan-2026, 11:20:49 am
    Author     : root
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="util.CaptchaUtil"%>

<%
    // Generate CAPTCHA number
    int captcha = CaptchaUtil.generateCaptcha();
    session.setAttribute("captcha", captcha);
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Register | Fashion Store</title>

    <link rel="stylesheet" type="text/css" href="css/style.css">
    <link rel="stylesheet" type="text/css" href="css/register.css">
</head>

<body>

<div class="login-box">
    <h2>User Registration</h2>

    <%-- Show messages --%>
    <%
        String error = request.getParameter("error");
        if ("captcha".equals(error)) {
    %>
        <div class="error">CAPTCHA incorrect. Try again.</div>
    <%
        } else if ("failed".equals(error)) {
    %>
        <div class="error">Registration failed. Try again.</div>
    <%
        }
    %>

    <form action="RegisterServlet" method="post">

    <div class="form-row">

        <!-- LEFT SECTION -->
        <div class="form-col">

            <label>Name</label>
            <input type="text" name="username" required>

            <label>Login ID</label>
            <input type="text" name="loginId" required>

            <label>Password</label>
            <input type="password" name="password" required>

            <label>Email</label>
            <input type="email" name="email" required>

            <label>Phone</label>
            <input type="text" name="phone" required>

        </div>


        <!-- RIGHT SECTION -->
        <div class="form-col">

            <label>Address</label>
            <textarea name="address" rows="3" required></textarea>

            <label>City</label>
            <input type="text" name="city" required>

            <label>State</label>
            <input type="text" name="state" required>

            <label>Country</label>
            <input type="text" name="country" required>

            <label>Pin Code</label>
            <input type="text" name="pin" required>

            <label>Date of Birth</label>
            <input type="date" name="dob" required>

        </div>

    </div>

    <!-- CAPTCHA -->
    <label>CAPTCHA: What is <%= captcha %> ?</label>
    <input type="text" name="captcha" required>

    <input type="submit" value="Register">

</form>


    <div class="links">
        <a href="login.jsp">Already have an account? Login</a>
    </div>
</div>

</body>
</html>

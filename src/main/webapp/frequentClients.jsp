<%-- 
    Document   : frequentClients
    Created on : 04-Feb-2026, 8:26:10 am
    Author     : root
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>
<%@page import="util.DBConnection"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Frequent Clients</title>

    <link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/frequentClients.css">
   
</head>

<body>

<h2>⭐ Frequent Clients (Visited More Than 5 Times)</h2>

<table>
    <tr>
        <th>User ID</th>
        <th>Name</th>
        <th>Email</th>
        <th>Visit Count</th>
        <th>Date of Birth</th>
    </tr>

<%
    try {

        Connection con = DBConnection.getConnection();

        PreparedStatement ps = con.prepareStatement(
            "SELECT * FROM user_master WHERE visit_count > 5"
        );

        ResultSet rs = ps.executeQuery();

        boolean found = false;

        while (rs.next()) {
            found = true;
%>

    <tr>
        <td><%= rs.getInt("user_id") %></td>
        <td><%= rs.getString("user_name") %></td>
        <td><%= rs.getString("email") %></td>
        <td><%= rs.getInt("visit_count") %></td>
        <td><%= rs.getString("dob") %></td>
    </tr>

<%
        }

        if (!found) {
%>

    <tr>
        <td colspan="5" style="color:red;">
            No Frequent Clients Found!
        </td>
    </tr>

<%
        }

        rs.close();
        ps.close();
        con.close();

    } catch (Exception e) {
        out.println("<h3 style='color:red;text-align:center;'>Error: "
                + e.getMessage() + "</h3>");
    }
%>

</table>

<br><br>

<center>
    <a href="adminDashboard.jsp">⬅ Back to Dashboard</a>
</center>

</body>
</html>


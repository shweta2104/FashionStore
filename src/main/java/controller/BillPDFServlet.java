package controller;

import util.DBConnection;
import java.io.IOException;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

@WebServlet("/BillPDFServlet")
public class BillPDFServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get Order ID from URL
        String orderId = request.getParameter("orderId");

        // Set PDF Response
        response.setContentType("application/pdf");

        // Force Download
        response.setHeader("Content-Disposition",
                "attachment; filename=Bill_Order_" + orderId + ".pdf");

        try {
            Connection con = DBConnection.getConnection();

            // Fetch Order Data
            PreparedStatement ps =
                    con.prepareStatement("SELECT * FROM order_master WHERE order_id=?");

            ps.setInt(1, Integer.parseInt(orderId));
            ResultSet rs = ps.executeQuery();

            // Create PDF Document
            Document doc = new Document();
            PdfWriter.getInstance(doc, response.getOutputStream());

            doc.open();

            // Title
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            doc.add(new Paragraph("Fashion Store - Bill Invoice", titleFont));

            doc.add(new Paragraph("------------------------------------------------"));
            doc.add(new Paragraph("Order ID: " + orderId));

            if (rs.next()) {
                doc.add(new Paragraph("Payment Mode: " + rs.getString("payment_mode")));
                doc.add(new Paragraph("Tax: ₹" + rs.getDouble("tax")));
                doc.add(new Paragraph("Total Amount: ₹" + rs.getDouble("total_amount")));
                doc.add(new Paragraph("Status: " + rs.getString("order_status")));
            }

            doc.add(new Paragraph("\nProducts Purchased:\n"));

            // Table for Products
            PdfPTable table = new PdfPTable(3);
            table.addCell("Product Name");
            table.addCell("Price");
            table.addCell("Discount");

            // Fetch Product Details
            PreparedStatement ps2 =
                    con.prepareStatement(
                            "SELECT p.product_name, od.product_price, od.discount " +
                            "FROM order_details od " +
                            "JOIN product_master p ON od.product_id=p.product_id " +
                            "WHERE od.order_id=?"
                    );

            ps2.setInt(1, Integer.parseInt(orderId));
            ResultSet rs2 = ps2.executeQuery();

            while (rs2.next()) {
                table.addCell(rs2.getString("product_name"));
                table.addCell("₹" + rs2.getDouble("product_price"));
                table.addCell(rs2.getDouble("discount") + "%");
            }

            doc.add(table);

            doc.add(new Paragraph("\nThank you for shopping with Fashion Store!"));

            doc.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.List;
import model.Order;
import model.Product;
import util.DBConnection;

/**
 *
 * @author root
 */
public class OrderDAO {

    // 🛒 Place Order
    public int placeOrder(Order order, List<Product> cart) {
        int orderId = 0;
        try {
            Connection con = DBConnection.getConnection();

            // Insert into order_master with all fields
            String sql = "INSERT INTO order_master(order_datetime, session_id, payment_mode, tax, total_amount, order_status) VALUES (NOW(), ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, order.getSessionId());
            ps.setString(2, order.getPaymentMode());
            ps.setDouble(3, order.getTax());
            ps.setDouble(4, order.getTotalAmount());
            ps.setString(5, order.getOrderStatus());

            ps.executeUpdate();

            // Get generated order ID
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                orderId = rs.getInt(1);
            }

            // Insert order details
            String detailSql = "INSERT INTO order_details(order_id, product_id, product_price, discount) VALUES (?, ?, ?, ?)";
            PreparedStatement ps2 = con.prepareStatement(detailSql);

            for (Product p : cart) {
                ps2.setInt(1, orderId);
                ps2.setInt(2, p.getProductId());
                ps2.setDouble(3, p.getPrice());
                ps2.setDouble(4, p.getDiscount());
                ps2.executeUpdate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderId;
    }
}

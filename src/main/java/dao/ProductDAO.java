/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.Product;
import util.DBConnection;
/**
 *
 * @author root
 */
public class ProductDAO {
      // ➕ Add Product
    public boolean addProduct(Product p) {
        boolean status = false;
        try {
            Connection con = DBConnection.getConnection();
            String sql = "INSERT INTO product_master(product_name,price,discount,stock,image,category_id) VALUES (?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, p.getProductName());
            ps.setDouble(2, p.getPrice());
            ps.setDouble(3, p.getDiscount());
            ps.setInt(4, p.getStock());
            ps.setString(5, p.getImage());
            ps.setInt(6, p.getCategoryId());

            status = ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    // 🛍️ View All Products
    public ArrayList<Product> getAllProducts() {
        ArrayList<Product> list = new ArrayList<>();
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM product_master";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Product p = new Product();
                p.setProductId(rs.getInt("product_id"));
                p.setProductName(rs.getString("product_name"));
                p.setPrice(rs.getDouble("price"));
                p.setDiscount(rs.getDouble("discount"));
                p.setStock(rs.getInt("stock"));
                p.setImage(rs.getString("image"));
                p.setCategoryId(rs.getInt("category_id"));
                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // 🔹 Get Product By ID
public Product getProductById(int productId) {
    Product product = null;
    try {
        Connection con = DBConnection.getConnection();
        String sql = "SELECT * FROM product_master WHERE product_id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, productId);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            product = new Product();
            product.setProductId(rs.getInt("product_id"));
            product.setProductName(rs.getString("product_name"));
            product.setPrice(rs.getDouble("price"));
            product.setDiscount(rs.getDouble("discount"));
            product.setStock(rs.getInt("stock"));
            product.setImage(rs.getString("image"));
            product.setCategoryId(rs.getInt("category_id"));
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return product;
}

// ❌ Delete Product by ID
public boolean deleteProduct(int productId) {
    boolean status = false;
    try {
        Connection con = DBConnection.getConnection();
        String sql = "DELETE FROM product_master WHERE product_id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, productId);

        status = ps.executeUpdate() > 0;
    } catch (Exception e) {
        e.printStackTrace();
    }
    return status;
}

// 🔄 Update Product
public boolean updateProduct(Product p) {
    boolean status = false;
    try {
        Connection con = DBConnection.getConnection();
        String sql = "UPDATE product_master SET product_name=?, price=?, discount=?, stock=?, image=?, category_id=? WHERE product_id=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, p.getProductName());
        ps.setDouble(2, p.getPrice());
        ps.setDouble(3, p.getDiscount());
        ps.setInt(4, p.getStock());
        ps.setString(5, p.getImage());
        ps.setInt(6, p.getCategoryId());
        ps.setInt(7, p.getProductId());

        status = ps.executeUpdate() > 0;
    } catch (Exception e) {
        e.printStackTrace();
    }
    return status;
}


}

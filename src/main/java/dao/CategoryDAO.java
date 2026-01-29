/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.Category;
import util.DBConnection;
/**
 *
 * @author root
 */
public class CategoryDAO {
      // ➕ Add Category
    public boolean addCategory(Category c) {
        boolean status = false;
        try {
            Connection con = DBConnection.getConnection();
            String sql = "INSERT INTO category_master(category_name) VALUES(?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, c.getCategoryName());
            status = ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    // 📄 View All Categories
    public ArrayList<Category> getAllCategories() {
        ArrayList<Category> list = new ArrayList<>();
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM category_master";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Category c = new Category();
                c.setCategoryId(rs.getInt("category_id"));
                c.setCategoryName(rs.getString("category_name"));
                list.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ❌ Delete Category
    public boolean deleteCategory(int id) {
        boolean status = false;
        try {
            Connection con = DBConnection.getConnection();
            String sql = "DELETE FROM category_master WHERE category_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            status = ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }
    
    // 🔹 Get Category By ID
public Category getCategoryById(int categoryId) {
    Category category = null;
    try {
        Connection con = DBConnection.getConnection();
        String sql = "SELECT * FROM category_master WHERE category_id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, categoryId);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            category = new Category();
            category.setCategoryId(rs.getInt("category_id"));
            category.setCategoryName(rs.getString("category_name"));
            // If parent_category_id exists in table
            try {
                category.setParentCategoryId(rs.getInt("parent_category_id"));
            } catch (Exception ex) {
                category.setParentCategoryId(0); // default if column not found
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return category;
}
// 🔄 Update Category
public boolean updateCategory(Category c) {
    boolean status = false;
    try {
        Connection con = DBConnection.getConnection();
        String sql = "UPDATE category_master SET category_name = ?, parent_category_id = ? WHERE category_id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, c.getCategoryName());
        ps.setInt(2, c.getParentCategoryId());
        ps.setInt(3, c.getCategoryId());

        status = ps.executeUpdate() > 0;
    } catch (Exception e) {
        e.printStackTrace();
    }
    return status;
}

}

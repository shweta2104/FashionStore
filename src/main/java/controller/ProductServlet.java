/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.ProductDAO;
import model.Product;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
/**
 *
 * @author root
 */
@WebServlet("/ProductServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
                 maxFileSize = 1024 * 1024 * 10,      // 10MB
                 maxRequestSize = 1024 * 1024 * 50)   // 50MB
public class ProductServlet extends HttpServlet {

    private ProductDAO dao = new ProductDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("add".equals(action)) {
            addProduct(request, response);
        } else if ("update".equals(action)) {
            updateProduct(request, response);
        } else if ("delete".equals(action)) {
            deleteProduct(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("edit".equals(action)) {
            editProduct(request, response);
        } else {
            listProducts(request, response);
        }
    }

    // ➕ Add Product
    private void addProduct(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String name = request.getParameter("productName");
        double price = Double.parseDouble(request.getParameter("price"));
        double discount = Double.parseDouble(request.getParameter("discount"));
        int stock = Integer.parseInt(request.getParameter("stock"));
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));

        // Handle image upload
        Part filePart = request.getPart("image");
        String fileName = filePart.getSubmittedFileName();
        String uploadPath = getServletContext().getRealPath("") + "images/products/" + fileName;
        filePart.write(uploadPath);

        Product p = new Product();
        p.setProductName(name);
        p.setPrice(price);
        p.setDiscount(discount);
        p.setStock(stock);
        p.setCategoryId(categoryId);
        p.setImage(fileName);

        dao.addProduct(p);
        response.sendRedirect("admin/product.jsp");
    }

    // ✏️ Edit Product (pre-fill form)
    private void editProduct(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        Product p = dao.getProductById(id);
        request.setAttribute("product", p);
        request.getRequestDispatcher("admin/product.jsp").forward(request, response);
    }

    // 🔄 Update Product
    private void updateProduct(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("productId"));
        String name = request.getParameter("productName");
        double price = Double.parseDouble(request.getParameter("price"));
        double discount = Double.parseDouble(request.getParameter("discount"));
        int stock = Integer.parseInt(request.getParameter("stock"));
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));

        // Image update (optional)
        Part filePart = request.getPart("image");
        String fileName = filePart.getSubmittedFileName();
        if (!fileName.isEmpty()) {
            String uploadPath = getServletContext().getRealPath("") + "images/products/" + fileName;
            filePart.write(uploadPath);
        }

        Product p = new Product();
        p.setProductId(id);
        p.setProductName(name);
        p.setPrice(price);
        p.setDiscount(discount);
        p.setStock(stock);
        p.setCategoryId(categoryId);
        if (!fileName.isEmpty()) p.setImage(fileName);

        dao.updateProduct(p);
        response.sendRedirect("admin/product.jsp");
    }

    // ❌ Delete Product
    private void deleteProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        dao.deleteProduct(id);
        response.sendRedirect("admin/product.jsp");
    }

    // 📃 List All Products
    private void listProducts(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setAttribute("products", dao.getAllProducts());
        request.getRequestDispatcher("admin/product.jsp").forward(request, response);
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import dao.CategoryDAO;
import model.Category;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
/**
 *
 * @author root
 */
@WebServlet("/CategoryServlet")
public class CategoryServlet extends HttpServlet {

    private CategoryDAO dao = new CategoryDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("add".equals(action)) {
            addCategory(request, response);
        } else if ("update".equals(action)) {
            updateCategory(request, response);
        } else if ("delete".equals(action)) {
            deleteCategory(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("edit".equals(action)) {
            editCategory(request, response);
        } else {
            listCategories(request, response);
        }
    }

    // ➕ Add Category
    private void addCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("categoryName");
        int parentId = 0;
        try {
            parentId = Integer.parseInt(request.getParameter("parentCategoryId"));
        } catch (Exception e) {
            // parent category is optional
        }

        Category c = new Category();
        c.setCategoryName(name);
        c.setParentCategoryId(parentId);

        dao.addCategory(c);
        response.sendRedirect("admin/category.jsp");
    }

    // ✏️ Edit Category (pre-fill form)
    private void editCategory(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        Category c = dao.getCategoryById(id);
        request.setAttribute("category", c);
        request.getRequestDispatcher("admin/category.jsp").forward(request, response);
    }

    // 🔄 Update Category
    private void updateCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("categoryId"));
        String name = request.getParameter("categoryName");
        int parentId = 0;
        try {
            parentId = Integer.parseInt(request.getParameter("parentCategoryId"));
        } catch (Exception e) {}

        Category c = new Category();
        c.setCategoryId(id);
        c.setCategoryName(name);
        c.setParentCategoryId(parentId);

        dao.updateCategory(c);
        response.sendRedirect("admin/category.jsp");
    }

    // ❌ Delete Category
    private void deleteCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        dao.deleteCategory(id);
        response.sendRedirect("admin/category.jsp");
    }

    // 📃 List All Categories
    private void listCategories(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setAttribute("categories", dao.getAllCategories());
        request.getRequestDispatcher("admin/category.jsp").forward(request, response);
    }
}

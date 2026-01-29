/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package listener;
import dao.CategoryDAO;
import model.Category;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import java.util.List;

/**
 *
 * @author root
 */
@WebListener
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Application Starting...");

        // Example: Load all categories into application context
        CategoryDAO categoryDAO = new CategoryDAO();
        List<Category> categories = categoryDAO.getAllCategories();

        // Store categories in application scope
        sce.getServletContext().setAttribute("categories", categories);

        System.out.println("Loaded " + categories.size() + " categories into application context.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Application Shutting Down...");
        // You can close DB connections or cleanup resources here if needed
    }
}


package model;

/**
 *
 * @author root
 */
public class Category {
    private int categoryId;
    private String categoryName;
    private int parentCategoryId; // ➕ Add this field

    // Getter and Setter for categoryId
    public int getCategoryId() {
        return categoryId;
    }
    
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    // Getter and Setter for categoryName
    public String getCategoryName() {
        return categoryName;
    }
    
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    // ➕ Getter and Setter for parentCategoryId
    public int getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(int parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }
}

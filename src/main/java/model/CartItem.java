package model;

public class CartItem {

    private Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    // Existing Getters/Setters
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // ✅ Subtotal Calculation
    public double getSubtotal() {
        double price = product.getPrice();
        double discount = product.getDiscount();
        return quantity * price * (1 - discount / 100.0);
    }

    // ====================================================
    // ✅ REQUIRED METHODS FOR CHECKOUT (DAO SUPPORT)
    // ====================================================

    // Product ID
    public int getProductId() {
        return product.getProductId();
    }

    // Product Price
    public double getPrice() {
        return product.getPrice();
    }

    // Product Discount
    public double getDiscount() {
        return product.getDiscount();
    }

    // Product Name (Optional, useful for bill display)
    public String getProductName() {
        return product.getProductName();
    }
}

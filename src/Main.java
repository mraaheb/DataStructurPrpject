import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.Date; 

/**
 * This is the main runnable class for the E-Commerce System.
 * (FINAL-FIXED Version with all input protections)
 */
public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static ECommerceSystem system = new ECommerceSystem();
    /**
     * A single, reusable date formatter for all output.
     * This makes all dates look clean and consistent (e.g., "2025-10-30").
     */
    private static SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

    public static void main(String[] args) {
        
        System.out.println("Initializing E-Commerce System...");
        try {
            system.readDataFromCSV("prodcuts.csv", "customers.csv", "orders.csv", "reviews.csv");
            System.out.println("--- Data Loaded Successfully ---");
        } catch (Exception e) {
            System.err.println("FATAL ERROR: Could not load data files. Exiting.");
            e.printStackTrace();
            return; 
        }

        boolean running = true;
        while (running) {
            printMenu();
            
            int choice = -1;
            try {
                choice = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
            }
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    handleAddProduct();
                    break;
                case 2:
                    handleRegisterCustomer();
                    break;
                case 3:
                    handlePlaceOrder();
                    break;
                case 4:
                    handleAddReview(); // This method is now fixed
                    break;
                case 5:
                    handleFindProduct();
                    break;
                case 6:
                    handleListOutOfStock();
                    break;
                case 7:
                    handleListTop3();
                    break;
                case 8:
                    handleFindReviewsByCustomer();
                    break;
                case 9:
                    handleFindCommonProducts();
                    break;
                case 10:
                    handleViewAllCustomers(); // The 'proof' query
                    break;
                    case 11:
                    handleOrdersBetweenDates(); 
                    break;
                    case 12:
                    handleViewCustomerOrders(); 
                    break;
                    case 13:
                    handleViewAllProducts(); // نستدعي الميثود الجديدة
                    break;
                case 14:
                    handleViewAllOrders(); // نستدعي الميثود الجديدة
                    break;
                    case 15:
                    handleRemoveProduct();
                    break;
                case 16:
                    handleSearchByName();
                    break;
                case 17:
                    handleCancelOrder();
                    break;
                case 18:
                    handleSearchOrderById();
                    break;
                case 19:
                    handleEditReview();
                    break;
                case 0:
                    running = false;
                    System.out.println("Thank you for using the system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            
            if (running) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }
        
        scanner.close(); 
    }

    private static void printMenu() {
        System.out.println("\n--- E-Commerce System Menu ---");
        System.out.println("1. Add a new Product");
        System.out.println("2. Register a new Customer");
        System.out.println("3. Place a new Order");
        System.out.println("4. Add a Product Review");
        System.out.println("---");
        System.out.println("5. Find Product by ID");
        System.out.println("6. List Out-of-Stock Products");
        System.out.println("7. List Top 3 Products (by rating)");
        System.out.println("8. Find all Reviews by a Customer");
        System.out.println("9. Find Common Reviewed Products (by 2 customers)");
        System.out.println("10. View All Customers"); // Added for proof
        System.out.println("11. Find Orders Between Two Dates");
        System.out.println("12. View Order History for a Customer");
        System.out.println("13. View All Products"); 
        System.out.println("14. View All Orders");
        
        System.out.println("--- (Admin & Edit Actions) ---");
        System.out.println("15. Remove a Product");
        System.out.println("16. Search Product by Name");
        System.out.println("17. Cancel an Order");
        System.out.println("18. Search Order by ID");
        System.out.println("19. Edit a Review");
 
        System.out.println("---");
        System.out.println("0. Exit System");
        System.out.print("Enter your choice: ");
    }


    // ==========================================================
    // --- (FIXED METHOD: handleAddProduct) ---
    // ==========================================================
    /**
     * Handles adding a new product.
     * IMPROVED: Now auto-generates Product ID and uses try-catch for numbers.
     */
    private static void handleAddProduct() {
        System.out.println("--- Add New Product ---");
        
        String id = system.getNewProductId();
        System.out.println("Your new (auto-generated) Product ID is: " + id);

        System.out.print("Enter Product Name: ");
        String name = scanner.nextLine();
        
        double price = 0;
        boolean validPrice = false;
        while (!validPrice) {
            System.out.print("Enter Price (e.g., 49.99): ");
            try {
                price = scanner.nextDouble();
                if (price > 0) {
                    validPrice = true;
                } else {
                    System.out.println("Error: Price must be greater than 0.");
                }
            } catch (Exception e) {
                System.out.println("Error: Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear the bad input
            }
        }
        scanner.nextLine(); // Consume newline after valid double
        
        int stock = 0;
        boolean validStock = false;
        while (!validStock) {
            System.out.print("Enter Stock Quantity (e.g., 10): ");
            try {
                stock = scanner.nextInt();
                if (stock >= 0) {
                    validStock = true;
                } else {
                    System.out.println("Error: Stock cannot be negative.");
                }
            } catch (Exception e) {
                System.out.println("Error: Invalid input. Please enter a whole number.");
                scanner.nextLine(); // Clear the bad input
            }
        }
        scanner.nextLine(); // Consume newline after valid int

        Product p = new Product(id, name, price, stock);
        system.addProduct(p);
        System.out.println("SUCCESS: Product '" + name + "' added.");
    }

    // ==========================================================
    // --- (FIXED METHOD: handleRegisterCustomer) ---
    // ==========================================================
    /**
     * Handles registering a new customer.
     * IMPROVED: Now auto-generates Customer ID.
     */
    private static void handleRegisterCustomer() {
        System.out.println("--- Register New Customer ---");
        
        String id = system.getNewCustomerId();
        System.out.println("Your new (auto-generated) Customer ID is: " + id);

        System.out.print("Enter Customer Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Customer Email: ");
        String email = scanner.nextLine();

        Customer c = new Customer(id, name, email);
        system.registerNewCustomer(c);
        System.out.println("SUCCESS: Customer '" + name + "' registered.");
    }


    // ==========================================================
    // --- (FIXED METHOD: handlePlaceOrder) ---
    // ==========================================================
    /**
     * Handles the "Place Order" workflow.
     * IMPROVED: Shows a NUMBERED list, auto-generates Order ID, and uses try-catch.
     */
    private static void handlePlaceOrder() {
        System.out.println("--- Place New Order ---");
        System.out.print("Enter your Customer ID: ");
        String customerId = scanner.nextLine();
        
        Customer customer = system.findCustomerById(customerId);
        if (customer == null) {
            System.out.println("ERROR: Customer ID not found.");
            return;
        }

        System.out.println("\n--- Available Products (Please choose a number) ---");
        MyLinkedList<Product> allProducts = system.getAllProducts();
        if (allProducts.isEmpty()) {
            System.out.println("Sorry, there are no products available to order.");
            return;
        }
        
        for (int i = 0; i < allProducts.size(); i++) {
            Product p = allProducts.get(i);
            System.out.println(
                (i + 1) + ". " + 
                "Name: " + p.getName() + 
                " | Price: " + p.getPrice() + 
                " | Stock: " + p.getStock()
            );
        }
        System.out.println("-------------------------------------");

        String orderId = system.getNewOrderId(); 
        Order order = new Order(orderId, customerId, new Date());
        System.out.println("Your new Order ID is: " + orderId);

        // --- Shopping Cart Loop ---
        while (true) {
            System.out.print("Enter Product *Number* to add to cart (or 0 to finish): ");
            
            int productNumber = -1; // Default
            try {
                productNumber = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
            }
            scanner.nextLine(); // Consume newline

            if (productNumber == 0) {
                break; // Exit the shopping loop
            }
            
            if (productNumber < 1 || productNumber > allProducts.size()) {
                System.out.println("ERROR: Invalid number. Please choose from the list.");
                continue; 
            }
            
            int productIndex = productNumber - 1; 
            Product product = allProducts.get(productIndex); 
            
            if (product.getStock() == 0) {
                System.out.println("ERROR: Sorry, '" + product.getName() + "' is out of stock.");
            } else {
                order.addProductToOrder(product);
                product.setStock(product.getStock() - 1); 
                System.out.println("Added '" + product.getName() + "' to cart. Current Total: " + order.getTotalPrice());
            }
        }
        
        // --- Finalize Order ---
        if (order.getProducts().isEmpty()) {
            System.out.println("Order canceled (no products were added).");
        } else {
            // --- NEW: Added the Order Summary (Receipt) ---
            System.out.println("\n--- Your Order Summary (ID: " + order.getOrderId() + ") ---");
            MyLinkedList<Product> items = order.getProducts();
            for (int i = 0; i < items.size(); i++) {
                Product p = items.get(i);
                System.out.println("- " + p.getName() + " (" + p.getPrice() + ")");
            }
            System.out.println("-------------------------------------");
            
            system.placeNewOrder(customerId, order);
            System.out.println("SUCCESS: Order placed! Your final total is: " + order.getTotalPrice());
        }
    }
    
    // ==========================================================
    // --- (FIXED METHOD: handleAddReview) ---
    // ==========================================================
    /**
     * Handles adding a new product review.
     * IMPROVED: Now uses try-catch and validation for the rating.
     */
    private static void handleAddReview() {
        System.out.println("--- Add Product Review ---");
        System.out.print("Enter Product ID to review: ");
        String productId = scanner.nextLine();
        
        Product product = system.findProductById(productId);
        if (product == null) {
            System.out.println("ERROR: Product ID not found.");
            return;
        }

        System.out.print("Enter your Customer ID: ");
        String customerId = scanner.nextLine();
        if (system.findCustomerById(customerId) == null) {
            System.out.println("ERROR: Customer ID not found.");
            return;
        }

        // --- This whole block is new to handle the error ---
        int rating = 0;
        boolean validRating = false;
        while (!validRating) {
            System.out.print("Enter Rating (a whole number 1-5): ");
            try {
                rating = scanner.nextInt();
                if (rating >= 1 && rating <= 5) {
                    validRating = true; // Good, exit the loop
                } else {
                    System.out.println("Error: Rating must be between 1 and 5.");
                }
            } catch (Exception e) {
                // This will catch "4.5" or "five"
                System.out.println("Error: Invalid input. Please enter a whole number (e.g., 4).");
                scanner.nextLine(); // Clear the bad input from the scanner
            }
        }
        scanner.nextLine(); // Consume the newline after the valid int
        // --- End of new block ---

        System.out.print("Enter Comment: ");
        String comment = scanner.nextLine();

        Review review = new Review(customerId, rating, comment);
        product.addReview(review);
        
        System.out.println("SUCCESS: Your review for '" + product.getName() + "' has been added.");
    }

    // ==========================================================
    // --- (Rest of the methods are unchanged) ---
    // ==========================================================

    private static void handleFindProduct() {
        System.out.println("--- Find Product by ID ---");
        System.out.print("Enter Product ID: ");
        String productId = scanner.nextLine();
        
        Product p = system.findProductById(productId);
        if (p != null) {
            System.out.println("Found Product:");
            System.out.println("  Name: " + p.getName());
            System.out.println("  Price: " + p.getPrice());
            System.out.println("  Stock: " + p.getStock());
            System.out.println("  Avg Rating: " + p.getAverageRating());
        } else {
            System.out.println("ERROR: Product ID not found.");
        }
    }

    private static void handleListOutOfStock() {
        System.out.println("--- Out-of-Stock Products ---");
        MyLinkedList<Product> outOfStock = system.getOutOfStockProducts();
        
        if (outOfStock.isEmpty()) {
            System.out.println("No products are out of stock.");
            return;
        }
        
        for (int i = 0; i < outOfStock.size(); i++) {
            Product p = outOfStock.get(i);
            System.out.println("- " + p.getName() + " (ID: " + p.getProductId() + ")");
        }
    }

    private static void handleListTop3() {
        System.out.println("--- Top 3 Rated Products ---");
        MyLinkedList<Product> topProducts = system.getTop3Products();
        
        if (topProducts.isEmpty()) {
            System.out.println("No products have been rated yet.");
            return;
        }
        
        for (int i = 0; i < topProducts.size(); i++) {
            Product p = topProducts.get(i);
            System.out.println((i+1) + ". " + p.getName() + " (Avg Rating: " + p.getAverageRating() + ")");
        }
    }

    private static void handleFindReviewsByCustomer() {
        System.out.println("--- Find Reviews by Customer ---");
        System.out.print("Enter Customer ID: ");
        String customerId = scanner.nextLine();
        
        MyLinkedList<Review> reviews = system.extractCustomerReviews(customerId);
        
        if (reviews.isEmpty()) {
            System.out.println("No reviews found for this customer.");
            return;
        }
        
        System.out.println("Reviews by Customer " + customerId + ":");
        for (int i = 0; i < reviews.size(); i++) {
            Review r = reviews.get(i);
            System.out.println("- Rating: " + r.getRatingScore() + " | Comment: " + r.getTextComment());
        }
    }

    private static void handleFindCommonProducts() {
        System.out.println("--- Find Common Reviewed Products (> 4 stars) ---");
        System.out.print("Enter Customer ID 1: ");
        String customerId1 = scanner.nextLine();
        System.out.print("Enter Customer ID 2: ");
        String customerId2 = scanner.nextLine();
        
        MyLinkedList<Product> common = system.getCommonReviewedProducts(customerId1, customerId2);
        
        if (common.isEmpty()) {
            System.out.println("No products with >4 stars were reviewed by both customers.");
            return;
        }
        
        System.out.println("Common Products (>4 stars) reviewed by both:");
        for (int i = 0; i < common.size(); i++) {
            System.out.println("- " + common.get(i).getName());
        }
    }

    /**
     * Handles viewing all registered customers.
     * This is used to "prove" that the Add Customer (Option 2) works.
     */
    private static void handleViewAllCustomers() {
        System.out.println("--- All Registered Customers ---");
        MyLinkedList<Customer> allCustomers = system.getAllCustomers();
        
        if (allCustomers.isEmpty()) {
            System.out.println("There are no customers registered yet.");
            return;
        }
        
        // Loop and print them
        for (int i = 0; i < allCustomers.size(); i++) {
            Customer c = allCustomers.get(i);
            System.out.println(
                "- ID: " + c.getCustomerId() + 
                " | Name: " + c.getName() + 
                " | Email: " + c.getEmail()
            );
        }
        System.out.println("Total Customers: " + allCustomers.size());
    }
    // ==========================================================
    // --- (NEW METHOD 1: Helper for parsing dates) ---
    // ==========================================================
    /**
     * A helper method to robustly ask the user for a date.
     * It will keep asking until the user enters a valid "yyyy-MM-dd" format.
     * @param prompt The message to show the user (e.g., "Enter start date:")
     * @return A valid Date object.
     */
    private static Date parseDateFromUser(String prompt) {
        // We define the *only* format we accept
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        boolean validDate = false;
        
        while (!validDate) {
            System.out.print(prompt + " (yyyy-MM-dd): ");
            String input = scanner.nextLine();
            
            try {
                // Try to parse the user's input
                date = dateFormat.parse(input);
                validDate = true; // If it worked, exit the loop
            } catch (ParseException e) {
                // If parsing failed (wrong format)
                System.out.println("Error: Invalid date format. Please use yyyy-MM-dd (e.g., 2025-01-20).");
            }
        }
        return date;
    }

    // ==========================================================
    // --- (NEW METHOD 2: The actual feature) ---
    // ==========================================================
    /**
     * Handles finding and listing all orders between two given dates.
     * This fulfills the requirement from the PDF.
     */
    private static void handleOrdersBetweenDates() {
        System.out.println("--- Find Orders Between Two Dates ---");
        
        // 1. Get the start date (uses our robust helper method)
        Date startDate = parseDateFromUser("Enter Start Date");
        
        // 2. Get the end date
        Date endDate = parseDateFromUser("Enter End Date");

        // 3. Call the ECommerceSystem to get the results
        MyLinkedList<Order> orders = system.getOrdersBetweenDates(startDate, endDate);
        
        // 4. Print the results
        if (orders.isEmpty()) {
            System.out.println("No orders found between " + startDate + " and " + endDate);
        } else {
            System.out.println("--- Orders Found ---");
            for (int i = 0; i < orders.size(); i++) {
                Order o = orders.get(i);
            System.out.println(
                "- Order ID: " + o.getOrderId() +
                " | Date: " + dateFormatter.format(o.getOrderDate()) +
                " | Status: " + o.getStatus() +
                " | Total: " + o.getTotalPrice()
            );
    // ...
            }
            System.out.println("Found " + orders.size() + " orders.");
        }
    }
    // ==========================================================
    // --- (NEW METHOD: To view a customer's order history) ---
    // ==========================================================
    /**
     * Handles finding and listing all orders for a specific customer.
     * This proves that the "Place Order" (Option 3) works.
     */
    private static void handleViewCustomerOrders() {
        System.out.println("--- View Customer Order History ---");
        System.out.print("Enter Customer ID: ");
        String customerId = scanner.nextLine();
        
        // 1. Find the customer
        Customer c = system.findCustomerById(customerId);
        if (c == null) {
            System.out.println("ERROR: Customer ID not found.");
            return;
        }
        
        // 2. Get their history (this comes from the Customer class)
        MyLinkedList<Order> orders = c.getOrderHistory();
        
        // 3. Check if they have any orders
        if (orders.isEmpty()) {
            System.out.println("Customer '" + c.getName() + "' has no orders on record.");
            return;
        }
        
        // 4. Print all their orders
        System.out.println("--- Orders for " + c.getName() + " ---");
        for (int i = 0; i < orders.size(); i++) {
            Order o = orders.get(i);
            System.out.println(
                "- Order ID: " + o.getOrderId() +
                " | Date: " + dateFormatter.format(o.getOrderDate()) +
                " | Status: " + o.getStatus() +
                " | Total: " + o.getTotalPrice()
            );
    // ...
            // (Optional: We can also loop through o.getProducts() to show items)
        }
        System.out.println("Found " + orders.size() + " total orders.");
    }
    // ==========================================================
    // --- (NEW METHOD: To view ALL products) ---
    // ==========================================================
    /**
     * Handles finding and listing all products in the system.
     */
    private static void handleViewAllProducts() {
        System.out.println("--- All Products in System ---");
        MyLinkedList<Product> allProducts = system.getAllProducts();
        
        if (allProducts.isEmpty()) {
            System.out.println("There are no products in the system.");
            return;
        }
        
        // نلف على القائمة ونطبعهم
        for (int i = 0; i < allProducts.size(); i++) {
            Product p = allProducts.get(i);
            System.out.println(
                "- ID: " + p.getProductId() + 
                " | Name: " + p.getName() + 
                " | Price: " + p.getPrice() +
                " | Stock: " + p.getStock() +
                " | Avg Rating: " + p.getAverageRating()
            );
        }
        System.out.println("Total Products: " + allProducts.size());
    }

    // ==========================================================
    // --- (NEW METHOD: To view ALL orders) ---
    // ==========================================================
    /**
     * Handles finding and listing all orders in the system.
     */
    private static void handleViewAllOrders() {
        System.out.println("--- All Orders in System ---");
        MyLinkedList<Order> allOrders = system.getAllOrders();
        
        if (allOrders.isEmpty()) {
            System.out.println("There are no orders in the system.");
            return;
        }
        
        for (int i = 0; i < allOrders.size(); i++) {
            Order o = allOrders.get(i);
            System.out.println(
                "- Order ID: " + o.getOrderId() +
                " | Customer ID: " + o.getCustomerId() +
                " | Date: " + dateFormatter.format(o.getOrderDate()) +
                " | Status: " + o.getStatus() +
                " | Total: " + o.getTotalPrice()
            );
    // ...
        }
        System.out.println("Total Orders: " + allOrders.size());
    }
    
    // ==========================================================
    // --- (NEW METHODS FOR OPTIONS 15-19) ---
    // ==========================================================

    /**
     * Handles Removing a product from the system.
     */
    private static void handleRemoveProduct() {
        System.out.println("--- Remove a Product ---");
        System.out.print("Enter the Product ID to remove: ");
        String productId = scanner.nextLine();
        
        // We must check if the product exists first
        Product p = system.findProductById(productId);
        if (p == null) {
            System.out.println("ERROR: Product ID not found. No product was removed.");
            return;
        }
        
        // If it exists, remove it
        system.removeProduct(productId);
        System.out.println("SUCCESS: Product '" + p.getName() + "' (ID: " + productId + ") has been removed.");
        System.out.println("Note: This change is temporary and will be saved only on exit (Option 0).");
    }

    /**
     * Handles Searching for a product by its name.
     */
    private static void handleSearchByName() {
        System.out.println("--- Search Product by Name ---");
        System.out.print("Enter Product Name to search for: ");
        String name = scanner.nextLine();
        
        Product p = system.findProductByName(name);
        
        if (p != null) {
            System.out.println("--- Product Found ---");
            System.out.println("  ID: " + p.getProductId());
            System.out.println("  Name: " + p.getName());
            System.out.println("  Price: " + p.getPrice());
            System.out.println("  Stock: " + p.getStock());
            System.out.println("  Avg Rating: " + p.getAverageRating());
        } else {
            System.out.println("ERROR: No product found with that name.");
        }
    }

    /**
     * Handles Canceling an order.
     */
    private static void handleCancelOrder() {
        System.out.println("--- Cancel an Order ---");
        System.out.print("Enter the Order ID to cancel: ");
        String orderId = scanner.nextLine();
        
        boolean success = system.cancelOrder(orderId);
        
        if (success) {
            System.out.println("SUCCESS: Order ID " + orderId + " has been marked as 'canceled'.");
            System.out.println("Note: This change is temporary and will be saved only on exit (Option 0).");
        } else {
            System.out.println("ERROR: Order ID not found.");
        }
    }

    /**
     * Handles Searching for an order by its ID.
     */
    private static void handleSearchOrderById() {
        System.out.println("--- Search Order by ID ---");
        System.out.print("Enter Order ID to search for: ");
        String orderId = scanner.nextLine();
        
        Order o = system.findOrderById(orderId);
        
        if (o != null) {
            System.out.println("--- Order Found ---");
            System.out.println("  Order ID: " + o.getOrderId());
            System.out.println("  Customer ID: " + o.getCustomerId());
            System.out.println("  Date: " + dateFormatter.format(o.getOrderDate())); // Using our formatter
            System.out.println("  Status: " + o.getStatus());
            System.out.println("  Total Price: " + o.getTotalPrice());
            
            // Also print the products in that order
            System.out.println("  Products in this order:");
            MyLinkedList<Product> products = o.getProducts();
            for(int i = 0; i < products.size(); i++) {
                System.out.println("    - " + products.get(i).getName());
            }
        } else {
            System.out.println("ERROR: No order found with that ID.");
        }
    }

    /**
     * Handles Editing an existing review.
     */
    private static void handleEditReview() {
        System.out.println("--- Edit a Review ---");
        
        // 1. Find the product
        System.out.print("Enter the Product ID for the review you want to edit: ");
        String productId = scanner.nextLine();
        Product p = system.findProductById(productId);
        if (p == null) {
            System.out.println("ERROR: Product ID not found.");
            return;
        }

        // 2. Find the customer (to identify the review)
        System.out.print("Enter *your* Customer ID (the review's author): ");
        String customerId = scanner.nextLine();
        if (system.findCustomerById(customerId) == null) {
            System.out.println("ERROR: Customer ID not found.");
            return;
        }
        
        // 3. Get the new rating
        int rating = 0;
        boolean validRating = false;
        while (!validRating) {
            System.out.print("Enter New Rating (a whole number 1-5): ");
            try {
                rating = scanner.nextInt();
                if (rating >= 1 && rating <= 5) {
                    validRating = true;
                } else {
                    System.out.println("Error: Rating must be between 1 and 5.");
                }
            } catch (Exception e) {
                System.out.println("Error: Invalid input. Please enter a whole number.");
                scanner.nextLine();
            }
        }
        scanner.nextLine(); // Consume newline

        // 4. Get the new comment
        System.out.print("Enter New Comment: ");
        String comment = scanner.nextLine();
        
        // 5. Call the edit method
        boolean success = p.editReview(customerId, comment, rating);
        
        if (success) {
            System.out.println("SUCCESS: Your review for '" + p.getName() + "' has been updated.");
            System.out.println("Note: This change is temporary and will be saved only on exit (Option 0).");
        } else {
            System.out.println("ERROR: No review from customer " + customerId + " was found for this product.");
        }
    }
    
}
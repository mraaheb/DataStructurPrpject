import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.Date; 

/**
 * This is the main runnable class for the E-Commerce System.
 * (REFACTORED Version with Nested Menus and NO SAVE on exit)
 */
public class Main {

    // --- (Global Tools: We keep these as they are) ---
    private static Scanner scanner = new Scanner(System.in);
    private static ECommerceSystem system = new ECommerceSystem();
    private static SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

    
    // ==========================================================
    // --- (MAIN METHOD: This is the new, smaller main method) ---
    // ==========================================================
    public static void main(String[] args) {
        
        // --- Step 1: Load all data from CSV files (Same as before) ---
        System.out.println("Initializing E-Commerce System...");
        try {
            system.readDataFromCSV("prodcuts.csv", "customers.csv", "orders.csv", "reviews.csv");
            System.out.println("--- Data Loaded Successfully ---");
        } catch (Exception e) {
            System.err.println("FATAL ERROR: Could not load data files. Exiting.");
            e.printStackTrace();
            return; 
        }

        // --- Step 2: Start the NEW MAIN menu loop ---
        boolean running = true;
        while (running) {
            printMainMenu(); // Print the *new* main menu
            
            int choice = getUserIntInput(); // Get user choice safely

            switch (choice) {
                case 1:
                    handleProductManagement(); // Go to Product Sub-Menu
                    break;
                case 2:
                    handleCustomerOrderManagement(); // Go to Customer/Order Sub-Menu
                    break;
                case 3:
                    handleReportManagement(); // Go to Reports Sub-Menu
                    break;
                case 0:
                    // --- THIS IS THE CHANGE: NO SAVE ---
                    // We just set running to false and exit.
                    running = false;
                    System.out.println("Thank you for using the system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        
        scanner.close(); 
    }

    // ==========================================================
    // --- ( Main Menu & Sub-Menu Handlers) ---
    // ==========================================================

    /**
     * Prints the Main Menu (Level 1)
     */
    private static void printMainMenu() {
        System.out.println("\n===== MAIN MENU =====");
        System.out.println("1. Product Management");
        System.out.println("2. Customer & Order Management");
        System.out.println("3. Reports & Queries");
        System.out.println("---------------------");
        System.out.println("0. Exit (Without Saving)");
        System.out.print("Enter your choice: ");
    }

    /**
     * Handles the Product Management Sub-Menu (Level 2)
     */
    private static void handleProductManagement() {
        boolean inProductMenu = true;
        while (inProductMenu) {
            System.out.println("\n--- [Product Management Menu] ---");
            System.out.println("1. Add a new Product ");
            System.out.println("2. Remove a Product ");
            System.out.println("3. Find Product by ID ");
            System.out.println("4. Search Product by Name ");
            System.out.println("6. Update a Product's Details");
            System.out.println("5. View All Products ");
            System.out.println("---------------------");
            System.out.println("0. Return to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = getUserIntInput();
            switch (choice) {
                case 1: handleAddProduct(); break;
                case 2: handleRemoveProduct(); break;
                case 3: handleFindProduct(); break;
                case 4: handleSearchByName(); break;
                case 5: handleViewAllProducts(); break;
                case 6: handleUpdateProduct(); 
                    break;
                case 0: inProductMenu = false; break; // Exit this loop
                default: System.out.println("Invalid choice.");
            }
            if (inProductMenu) pauseForUser();
        }
    }

    /**
     * Handles the Customer & Order Management Sub-Menu (Level 2)
     */
    private static void handleCustomerOrderManagement() {
        boolean inCustomerMenu = true;
        while (inCustomerMenu) {
            System.out.println("\n--- [Customer & Order Menu] ---");
            System.out.println("1. Register a new Customer ");
            System.out.println("2. View All Customers ");
            System.out.println("---");
            System.out.println("3. Place a new Order ");
            System.out.println("4. Cancel an Order ");
            System.out.println("5. Search Order by ID ");
            System.out.println("6. View All Orders ");
            System.out.println("7. View Order History for a Customer");
            System.out.println("8. Update an Order's Status");
            System.out.println("---------------------");
            System.out.println("0. Return to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = getUserIntInput();
            switch (choice) {
                case 1: handleRegisterCustomer(); break;
                case 2: handleViewAllCustomers(); break;
                case 3: handlePlaceOrder(); break;
                case 4: handleCancelOrder(); break;
                case 5: handleSearchOrderById(); break;
                case 6: handleViewAllOrders(); break;
                case 7: handleViewCustomerOrders(); break;
                case 8:handleUpdateOrderStatus(); 
                    break;
                case 0: inCustomerMenu = false; break; // Exit this loop
                default: System.out.println("Invalid choice.");
            }
            if (inCustomerMenu) pauseForUser();
        }
    }

    /**
     * Handles the Reports & Queries Sub-Menu (Level 2)
     */
    private static void handleReportManagement() {
        boolean inReportMenu = true;
        while (inReportMenu) {
            System.out.println("\n--- [Reports & Queries Menu] ---");
            System.out.println("1. Add a Product Review ");
            System.out.println("2. Edit a Product Review ");
            System.out.println("3. List Top 3 Products (by rating) )");
            System.out.println("4. List Out-of-Stock Products ");
            System.out.println("5. Find all Reviews by a Customer ");
            System.out.println("6. Find Common Reviewed Products (by 2 customers) ");
            System.out.println("7. Find Orders Between Two Dates ");
            System.out.println("---------------------");
            System.out.println("0. Return to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = getUserIntInput();
            switch (choice) {
                case 1: handleAddReview(); break;
                case 2: handleEditReview(); break;
                case 3: handleListTop3(); break;
                case 4: handleListOutOfStock(); break;
                case 5: handleFindReviewsByCustomer(); break;
                case 6: handleFindCommonProducts(); break;
                case 7: handleOrdersBetweenDates(); break;
                case 0: inReportMenu = false; break; // Exit this loop
                default: System.out.println("Invalid choice.");
            }
            if (inReportMenu) pauseForUser();
        }
    }

    // ==========================================================
    // 
    // (These are small helper methods to keep the code clean)
    // ==========================================================

    /**
     * A safe way to get an integer from the user.
     */
    private static int getUserIntInput() {
        int choice = -1;
        try {
            choice = scanner.nextInt();
        } catch (Exception e) {
            System.out.println("Invalid input. Please enter a number.");
        }
        scanner.nextLine(); // Always consume the newline
        return choice;
    }

    /**
     * Pauses the screen until the user presses Enter.
     */
    private static void pauseForUser() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
    
    /**
     * A helper method to robustly ask the user for a date.
     *
     */
    private static Date parseDateFromUser(String prompt) {
        SimpleDateFormat userDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        boolean validDate = false;
        
        while (!validDate) {
            System.out.print(prompt + " (yyyy-MM-dd): ");
            String input = scanner.nextLine();
            try {
                date = userDateFormat.parse(input);
                validDate = true; 
            } catch (ParseException e) {
                System.out.println("Error: Invalid date format. Please use yyyy-MM-dd (e.g., 2025-01-20).");
            }
        }
        return date;
    }


    // ==========================================================
    // --- (ACTION METHODS) ---
    // 
    // ==========================================================

    // (handleAddProduct)
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
                scanner.nextLine(); 
            }
        }
        scanner.nextLine(); 
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
                scanner.nextLine(); 
            }
        }
        scanner.nextLine(); 
        Product p = new Product(id, name, price, stock);
        system.addProduct(p);
        System.out.println("SUCCESS: Product '" + name + "' added.");
    }

    // (handleRegisterCustomer)
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

    // (handlePlaceOrder)
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
        while (true) {
            System.out.print("Enter Product *Number* to add to cart (or 0 to finish): ");
            int productNumber = getUserIntInput(); 
            
            if (productNumber == 0) {
                break; 
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
        if (order.getProducts().isEmpty()) {
            System.out.println("Order canceled (no products were added).");
        } else {
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
    
    // (handleAddReview)
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
        int rating = 0;
        boolean validRating = false;
        while (!validRating) {
            System.out.print("Enter Rating (a whole number 1-5): ");
            try {
                rating = scanner.nextInt();
                if (rating >= 1 && rating <= 5) {
                    validRating = true; 
                } else {
                    System.out.println("Error: Rating must be between 1 and 5.");
                }
            } catch (Exception e) {
                System.out.println("Error: Invalid input. Please enter a whole number (e.g., 4).");
                scanner.nextLine(); 
            }
        }
        scanner.nextLine(); 
        System.out.print("Enter Comment: ");
        String comment = scanner.nextLine();
        Review review = new Review(customerId, rating, comment);
        product.addReview(review);
        System.out.println("SUCCESS: Your review for '" + product.getName() + "' has been added.");
    }

    // (handleFindProduct)
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

    // (handleListOutOfStock)
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

    // (handleListTop3)
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

    // (handleFindReviewsByCustomer)
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

    // (handleFindCommonProducts)
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

    // (handleViewAllCustomers)
    private static void handleViewAllCustomers() {
        System.out.println("--- All Registered Customers ---");
        MyLinkedList<Customer> allCustomers = system.getAllCustomers();
        if (allCustomers.isEmpty()) {
            System.out.println("There are no customers registered yet.");
            return;
        }
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

    // (handleOrdersBetweenDates)
    private static void handleOrdersBetweenDates() {
        System.out.println("--- Find Orders Between Two Dates ---");
        Date startDate = parseDateFromUser("Enter Start Date");
        Date endDate = parseDateFromUser("Enter End Date");
        MyLinkedList<Order> orders = system.getOrdersBetweenDates(startDate, endDate);
        if (orders.isEmpty()) {
            System.out.println("No orders found between " + dateFormatter.format(startDate) + " and " + dateFormatter.format(endDate));
        } else {
            System.out.println("--- Orders Found ---");
            for (int i = 0; i < orders.size(); i++) {
                Order o = orders.get(i);
                System.out.println(
                    "- Order ID: " + o.getOrderId() +
                    " | Customer ID: " + o.getCustomerId() +
                    " | Date: " + dateFormatter.format(o.getOrderDate()) +
                    " | Total: " + o.getTotalPrice()
                );
            }
            System.out.println("Found " + orders.size() + " orders.");
        }
    }

    // (handleViewCustomerOrders)
    private static void handleViewCustomerOrders() {
        System.out.println("--- View Customer Order History ---");
        System.out.print("Enter Customer ID: ");
        String customerId = scanner.nextLine();
        Customer c = system.findCustomerById(customerId);
        if (c == null) {
            System.out.println("ERROR: Customer ID not found.");
            return;
        }
        MyLinkedList<Order> orders = c.getOrderHistory();
        if (orders.isEmpty()) {
            System.out.println("Customer '" + c.getName() + "' has no orders on record.");
            return;
        }
        System.out.println("--- Orders for " + c.getName() + " ---");
        for (int i = 0; i < orders.size(); i++) {
            Order o = orders.get(i);
            System.out.println(
                "- Order ID: " + o.getOrderId() +
                " | Date: " + dateFormatter.format(o.getOrderDate()) +
                " | Status: " + o.getStatus() +
                " | Total: " + o.getTotalPrice()
            );
            System.out.println("  Items Purchased:");
            MyLinkedList<Product> productsInOrder = o.getProducts();
            if (productsInOrder.isEmpty()) {
                System.out.println("    (No items listed for this order - possible data error)");
            } else {
                for (int j = 0; j < productsInOrder.size(); j++) {
                    Product p = productsInOrder.get(j);
                    System.out.println("    - " + p.getName() + " (" + p.getPrice() + ")");
                }
            }
        }
        System.out.println("Found " + orders.size() + " total orders.");
    }
    
    // (handleViewAllProducts)
    private static void handleViewAllProducts() {
        System.out.println("--- All Products in System ---");
        MyLinkedList<Product> allProducts = system.getAllProducts();
        if (allProducts.isEmpty()) {
            System.out.println("There are no products in the system.");
            return;
        }
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

    // (handleViewAllOrders)
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
        }
        System.out.println("Total Orders: " + allOrders.size());
    }
    
    // (handleRemoveProduct)
    private static void handleRemoveProduct() {
        System.out.println("--- Remove a Product ---");
        System.out.print("Enter the Product ID to remove: ");
        String productId = scanner.nextLine();
        Product p = system.findProductById(productId);
        if (p == null) {
            System.out.println("ERROR: Product ID not found. No product was removed.");
            return;
        }
        system.removeProduct(productId);
        System.out.println("SUCCESS: Product '" + p.getName() + "' (ID: " + productId + ") has been removed.");
        System.out.println("(Note: This change is temporary and will be lost on exit.)"); // No-Save message
    }

    // (handleSearchByName)
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

    // (handleCancelOrder)
    private static void handleCancelOrder() {
        System.out.println("--- Cancel an Order ---");
        System.out.print("Enter the Order ID to cancel: ");
        String orderId = scanner.nextLine();
        boolean success = system.cancelOrder(orderId);
        if (success) {
            System.out.println("SUCCESS: Order ID " + orderId + " has been marked as 'canceled'.");
            System.out.println("(Note: This change is temporary and will be lost on exit.)"); // No-Save message
        } else {
            System.out.println("ERROR: Order ID not found.");
        }
    }

    // (handleSearchOrderById)
    private static void handleSearchOrderById() {
        System.out.println("--- Search Order by ID ---");
        System.out.print("Enter Order ID to search for: ");
        String orderId = scanner.nextLine();
        Order o = system.findOrderById(orderId);
        if (o != null) {
            System.out.println("--- Order Found ---");
            System.out.println("  Order ID: " + o.getOrderId());
            System.out.println("  Customer ID: " + o.getCustomerId());
            System.out.println("  Date: " + dateFormatter.format(o.getOrderDate())); 
            System.out.println("  Status: " + o.getStatus());
            System.out.println("  Total Price: " + o.getTotalPrice());
            System.out.println("  Products in this order:");
            MyLinkedList<Product> products = o.getProducts();
            for(int i = 0; i < products.size(); i++) {
                System.out.println("    - " + products.get(i).getName());
            }
        } else {
            System.out.println("ERROR: No order found with that ID.");
        }
    }

    // (handleEditReview)
    private static void handleEditReview() {
        System.out.println("--- Edit a Review ---");
        System.out.print("Enter the Product ID for the review: ");
        String productId = scanner.nextLine();
        Product p = system.findProductById(productId);
        if (p == null) {
            System.out.println("ERROR: Product ID not found.");
            return;
        }
        System.out.print("Enter *your* Customer ID (the author): ");
        String customerId = scanner.nextLine();
        
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
                System.out.println("Error: Invalid input.");
                scanner.nextLine();
            }
        }
        scanner.nextLine(); // Consume newline
        
        System.out.print("Enter New Comment: ");
        String comment = scanner.nextLine();
        
        boolean success = p.editReview(customerId, comment, rating);
        if (success) {
            System.out.println("SUCCESS: Your review for '" + p.getName() + "' has been updated.");
            System.out.println("(Note: This change is temporary and will be lost on exit.)"); // No-Save message
        } else {
            System.out.println("ERROR: No review from customer " + customerId + " was found.");
        }
    }
    // ==========================================================
    // --- (METHOD: To update any product detail) ---
    // ==========================================================
    /**
     * Handles updating the details of an existing product (Name, Price, or Stock).
     */
    private static void handleUpdateProduct() {
        System.out.println("--- Update Product Details ---");
        
        // 1. Find the product
        System.out.print("Enter the Product ID to update: ");
        String productId = scanner.nextLine();
        Product p = system.findProductById(productId);
        
        if (p == null) {
            System.out.println("ERROR: Product ID not found.");
            return;
        }

        // 2. Show the "Update" sub-menu
        System.out.println("What do you want to update for '" + p.getName() + "'?");
        System.out.println("1. Update Name");
        System.out.println("2. Update Price");
        System.out.println("3. Update Stock");
        System.out.println("0. Cancel");
        System.out.print("Enter your choice: ");
        
        int choice = getUserIntInput();

        // 3. Execute the chosen update
        switch (choice) {
            case 1: // Update Name
                System.out.print("Enter new Name: ");
                String newName = scanner.nextLine();
                p.setName(newName); //  setter
                System.out.println("SUCCESS: Name updated.");
                break;
                
            case 2: // Update Price
                System.out.print("Enter new Price: ");
                double newPrice = 0;
                try {
                    newPrice = scanner.nextDouble();
                    p.setPrice(newPrice); //  setter)
                    System.out.println("SUCCESS: Price updated.");
                } catch (Exception e) {
                    System.out.println("Error: Invalid input.");
                }
                scanner.nextLine(); // Consume newline
                break;
                
            case 3: // Update Stock
                System.out.print("Enter new Stock quantity: ");
                int newStock = 0;
                try {
                    newStock = scanner.nextInt();
                    p.setStock(newStock); //  setter)
                    System.out.println("SUCCESS: Stock updated.");
                } catch (Exception e) {
                    System.out.println("Error: Invalid input.");
                }
                scanner.nextLine(); // Consume newline
                break;
                
            case 0: // Cancel
                System.out.println("Update canceled.");
                break;
                
            default:
                System.out.println("Invalid choice.");
        }
        
        System.out.println("(Note: This change is temporary and will be lost on exit.)");
    }
    // ==========================================================
    // --- (METHOD: To update an order's status) ---
    // ==========================================================
    /**
     * Handles manually updating the status of an existing order.
     */
    private static void handleUpdateOrderStatus() {
        System.out.println("--- Update Order Status ---");
        
        // 1. Find the order
        System.out.print("Enter the Order ID to update: ");
        String orderId = scanner.nextLine();
        Order o = system.findOrderById(orderId);
        
        if (o == null) {
            System.out.println("ERROR: Order ID not found.");
            return;
        }

        // 2. Show current status and ask for new one
        System.out.println("Current status for Order " + o.getOrderId() + " is: " + o.getStatus());
        System.out.print("Enter new status (e.g., pending, shipped, delivered): ");
        String newStatus = scanner.nextLine();

        // 3. Call the method in the Order class
        o.updateStatus(newStatus);
        
        System.out.println("SUCCESS: Order status has been updated to '" + newStatus + "'.");
        System.out.println("(Note: This change is temporary and will be lost on exit.)");
    }
}
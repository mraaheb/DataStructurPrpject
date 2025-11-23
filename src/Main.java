import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.Date; 

/**
 * CSC 212 Project - Phase 2
 * E-Commerce Inventory & Order Management System
 * * This class serves as the User Interface (UI) / Control Panel.
 * It utilizes the 'ECommerceSystem' (Logic Layer) to perform operations.
 * * Key Features:
 * - Nested Menus for better organization.
 * - Input Validation (try-catch) to prevent crashes.
 * - Distinct separation between Phase 1 and Phase 2 requirements.
 */
public class Main {

    // --- Global Helper Objects ---
    private static Scanner scanner = new Scanner(System.in);
    private static ECommerceSystem system = new ECommerceSystem();
    private static SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

    
    // =========================================================================
    // --- MAIN METHOD: System Entry Point ---
    // =========================================================================
    public static void main(String[] args) {
        
        System.out.println(">>> Initializing E-Commerce System (Phase 2)...");
        
        // 1. Load Data from CSV Files
        // This populates our BSTs (Products/Customers) and LinkedList (Orders)
        try {
            system.readDataFromCSV("prodcuts.csv", "customers.csv", "orders.csv", "reviews.csv");
            System.out.println(">>> Data Loaded Successfully into BSTs and Lists.");
        } catch (Exception e) {
            System.err.println("FATAL ERROR: Could not load data files. Exiting.");
            e.printStackTrace();
            return; 
        }

        // 2. Start the Interactive Menu Loop
        boolean running = true;
        while (running) {
            printMainMenu(); // Display Level 1 Menu
            
            int choice = getUserIntInput(); 

            switch (choice) {
                case 1:
                    handleProductManagement(); // Sub-menu for Product Operations
                    break;
                case 2:
                    handleCustomerOrderManagement(); // Sub-menu for Customer/Order Operations
                    break;
                case 3:
                    handleReportManagement(); // Sub-menu for Queries & Analytics
                    break;
                case 0:
                    // Exit Strategy: In-Memory Only (No write-back to CSV)
                    running = false;
                    System.out.println("Thank you for using the system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        
        scanner.close(); 
    }

    // =========================================================================
    // --- MENUS & NAVIGATION (The "View" Layer) ---
    // =========================================================================

    private static void printMainMenu() {
        System.out.println("\n================ MAIN MENU ================");
        System.out.println("1. Product Management (Add, Update, Search)");
        System.out.println("2. Customer & Order Management");
        System.out.println("3. Reports & Advanced Queries (Phase 2)");
        System.out.println("-------------------------------------------");
        System.out.println("0. Exit System");
        System.out.print("Enter your choice: ");
    }

    /**
     * Sub-Menu 1: Manages all Product-related operations.
     * Covers requirements: Add/Remove/Update/Search Products.
     */
    private static void handleProductManagement() {
        boolean inProductMenu = true;
        while (inProductMenu) {
            System.out.println("\n--- [Product Management Menu] ---");
            System.out.println("1. Add a new Product");
            System.out.println("2. Remove a Product");
            System.out.println("3. Update a Product's Details");
            System.out.println("4. Find Product by ID (Logarithmic Search)");
            System.out.println("5. Search Product by Name");
            System.out.println("6. View All Products (In-Order Traversal)");
            System.out.println("---------------------------------");
            System.out.println("0. Return to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = getUserIntInput();
            switch (choice) {
                case 1: handleAddProduct(); break;
                case 2: handleRemoveProduct(); break;
                case 3: handleUpdateProduct(); break;
                case 4: handleFindProduct(); break;
                case 5: handleSearchByName(); break;
                case 6: handleViewAllProducts(); break;
                case 0: inProductMenu = false; break;
                default: System.out.println("Invalid choice.");
            }
            if (inProductMenu) pauseForUser();
        }
    }

    /**
     * Sub-Menu 2: Manages Customers and Orders.
     * Covers requirements: Register, Place/Cancel Order, View History.
     */
    private static void handleCustomerOrderManagement() {
        boolean inCustomerMenu = true;
        while (inCustomerMenu) {
            System.out.println("\n--- [Customer & Order Management Menu] ---");
            System.out.println("1. Register a new Customer (BST Insert)");
            System.out.println("2. View All Customers (Sorted by ID)");
            System.out.println("---");
            System.out.println("3. Place a new Order");
            System.out.println("4. Cancel an Order");
            System.out.println("5. Update an Order's Status");
            System.out.println("6. Search Order by ID");
            System.out.println("7. View All Orders");
            System.out.println("8. View Order History for a Customer");
            System.out.println("9. Search Customer by ID");
            System.out.println("------------------------------------------");
            System.out.println("0. Return to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = getUserIntInput();
            switch (choice) {
                case 1: handleRegisterCustomer(); break;
                case 2: handleListSortedCustomers(); break; // Phase 2 Feature
                case 3: handlePlaceOrder(); break;
                case 4: handleCancelOrder(); break;
                case 5: handleUpdateOrderStatus(); break;
                case 6: handleSearchOrderById(); break;
                case 7: handleViewAllOrders(); break;
                case 8: handleViewCustomerOrders(); break;
                case 9: handleSearchCustomerById(); break;
                case 0: inCustomerMenu = false; break;
                default: System.out.println("Invalid choice.");
            }
            if (inCustomerMenu) pauseForUser();
        }
    }

    /**
     * Sub-Menu 3: Analytics and Advanced Search.
     * Covers Phase 2 Advanced Queries (Range, Top 3, Common Products).
     */
    private static void handleReportManagement() {
        boolean inReportMenu = true;
        while (inReportMenu) {
            System.out.println("\n--- [Reports & Advanced Queries Menu] ---");
            System.out.println("1. Add a Product Review");
            System.out.println("2. Edit a Product Review");
            System.out.println("3. Show Customers Who Reviewed a Product (Phase 2)");
            System.out.println("4. Find all Reviews by a Customer");
            System.out.println("---");
            System.out.println("5. Search Products by Price Range (Phase 2)");
            System.out.println("6. List Top 3 Products (by Avg Rating)");
            System.out.println("7. List Out-of-Stock Products");
            System.out.println("8. Find Common Reviewed Products (Complex Query)");
            System.out.println("9. Find Orders Between Two Dates");
            System.out.println("-----------------------------------------");
            System.out.println("0. Return to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = getUserIntInput();
            switch (choice) {
                case 1: handleAddReview(); break;
                case 2: handleEditReview(); break;
                case 3: handleListProductReviews(); break; // Phase 2
                case 4: handleFindReviewsByCustomer(); break;
                case 5: handlePriceRangeQuery(); break;    // Phase 2
                case 6: handleListTop3(); break;
                case 7: handleListOutOfStock(); break;
                case 8: handleFindCommonProducts(); break;
                case 9: handleOrdersBetweenDates(); break;
                case 0: inReportMenu = false; break;
                default: System.out.println("Invalid choice.");
            }
            if (inReportMenu) pauseForUser();
        }
    }

    // =========================================================================
    // --- HELPER UTILITIES (Input Handling) ---
    // =========================================================================

    private static int getUserIntInput() {
        int choice = -1;
        try {
            choice = scanner.nextInt();
        } catch (Exception e) {
            System.out.println("Invalid input. Please enter a number.");
        }
        scanner.nextLine(); // Consume newline
        return choice;
    }

    private static void pauseForUser() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
    
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
                System.out.println("Error: Invalid date format. Please use yyyy-MM-dd.");
            }
        }
        return date;
    }


    // =========================================================================
    // --- ACTION METHODS (Implementation of Requirements) ---
    // =========================================================================

    // --- 1. PRODUCT OPERATIONS ---

    /** [PHASE 1] Requirement: Add Product */
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
                if (price > 0) validPrice = true;
                else System.out.println("Error: Price must be greater than 0.");
            } catch (Exception e) { System.out.println("Error: Invalid input."); scanner.nextLine(); }
        }
        scanner.nextLine(); 
        
        int stock = 0;
        boolean validStock = false;
        while (!validStock) {
            System.out.print("Enter Stock Quantity: ");
            try {
                stock = scanner.nextInt();
                if (stock >= 0) validStock = true;
                else System.out.println("Error: Stock cannot be negative.");
            } catch (Exception e) { System.out.println("Error: Invalid input."); scanner.nextLine(); }
        }
        scanner.nextLine(); 
        
        Product p = new Product(id, name, price, stock);
        system.addProduct(p); // Uses BST Insert
        System.out.println("SUCCESS: Product '" + name + "' added.");
    }

    /** [PHASE 1] Requirement: Remove Product */
    private static void handleRemoveProduct() {
        System.out.println("--- Remove a Product ---");
        System.out.print("Enter the Product ID to remove: ");
        String productId = scanner.nextLine();
        Product p = system.findProductById(productId);
        if (p == null) {
            System.out.println("ERROR: Product ID not found.");
            return;
        }
        system.removeProduct(productId); // Uses BST Delete
        System.out.println("SUCCESS: Product '" + p.getName() + "' has been removed.");
        System.out.println("(Note: Change is in-memory only).");
    }

    /** [PHASE 1] Requirement: Update Product */
    private static void handleUpdateProduct() {
        System.out.println("--- Update Product Details ---");
        System.out.print("Enter the Product ID to update: ");
        String productId = scanner.nextLine();
        Product p = system.findProductById(productId);
        if (p == null) {
            System.out.println("ERROR: Product ID not found.");
            return;
        }
        System.out.println("Update '" + p.getName() + "': 1.Name, 2.Price, 3.Stock, 0.Cancel");
        System.out.print("Choice: ");
        int choice = getUserIntInput();
        switch (choice) {
            case 1:
                System.out.print("New Name: ");
                p.setName(scanner.nextLine());
                System.out.println("SUCCESS: Name updated.");
                break;
            case 2:
                System.out.print("New Price: ");
                try { p.setPrice(scanner.nextDouble()); System.out.println("SUCCESS: Price updated."); } 
                catch (Exception e) { System.out.println("Error: Invalid input."); }
                scanner.nextLine();
                break;
            case 3:
                System.out.print("New Stock: ");
                try { p.setStock(scanner.nextInt()); System.out.println("SUCCESS: Stock updated."); } 
                catch (Exception e) { System.out.println("Error: Invalid input."); }
                scanner.nextLine();
                break;
            case 0: System.out.println("Canceled."); break;
            default: System.out.println("Invalid choice.");
        }
    }

    /** [PHASE 2] Requirement: Search Product by ID (Logarithmic) */
    private static void handleFindProduct() {
        System.out.println("--- Find Product by ID ---");
        System.out.print("Enter Product ID: ");
        String productId = scanner.nextLine();
        // Uses BST Search O(log n)
        Product p = system.findProductById(productId);
        if (p != null) {
            System.out.println("Found: " + p.getName() + " | Price: " + p.getPrice() + " | Stock: " + p.getStock() + " | Avg Rating: " + p.getAverageRating());
        } else {
            System.out.println("ERROR: Product ID not found.");
        }
    }

    /** [PHASE 1] Requirement: Search Product by Name */
    private static void handleSearchByName() {
        System.out.println("--- Search Product by Name ---");
        System.out.print("Enter Product Name: ");
        String name = scanner.nextLine();
        Product p = system.findProductByName(name);
        if (p != null) {
            System.out.println("Found: " + p.getName() + " (ID: " + p.getProductId() + ")");
        } else {
            System.out.println("ERROR: Not found.");
        }
    }

    /** [PHASE 1] Requirement: List Out-of-Stock Products */
    private static void handleListOutOfStock() {
        System.out.println("--- Out-of-Stock Products ---");
        MyLinkedList<Product> list = system.getOutOfStockProducts();
        if (list.isEmpty()) { System.out.println("No products are out of stock."); return; }
        for (int i = 0; i < list.size(); i++) {
            Product p = list.get(i);
            System.out.println("- " + p.getName() + " (ID: " + p.getProductId() + ")");
        }
    }

    // --- 2. CUSTOMER & ORDER OPERATIONS ---

    /** [PHASE 2] Requirement: Register Customer (BST Insert) */
    private static void handleRegisterCustomer() {
        System.out.println("--- Register New Customer ---");
        String id = system.getNewCustomerId();
        System.out.println("Generated ID: " + id);
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        Customer c = new Customer(id, name, email);
        system.registerNewCustomer(c); // BST Insert
        System.out.println("SUCCESS: Customer registered.");
    }

    /** [PHASE 2] Requirement: List All Customers Sorted */
    private static void handleListSortedCustomers() {
        System.out.println("--- All Customers (Sorted by ID) ---");
        // Uses In-Order Traversal of BST
        MyLinkedList<Customer> list = system.getCustomersSorted();
        for (int i = 0; i < list.size(); i++) {
            Customer c = list.get(i);
            System.out.println("- ID: " + c.getCustomerId() + " | Name: " + c.getName());
        }
        System.out.println("Total: " + list.size());
    }

    /** [PHASE 1] Requirement: Place Order */
    private static void handlePlaceOrder() {
        System.out.println("--- Place New Order ---");
        System.out.print("Enter Customer ID: ");
        String customerId = scanner.nextLine();
        Customer customer = system.findCustomerById(customerId);
        if (customer == null) { System.out.println("ERROR: Customer ID not found."); return; }
        
        // Display Menu
        System.out.println("--- Products Menu ---");
        MyLinkedList<Product> all = system.getAllProducts();
        for(int i=0; i<all.size(); i++) System.out.println((i+1) + ". " + all.get(i).getName() + " (" + all.get(i).getPrice() + ")");
        
        String orderId = system.getNewOrderId();
        Order order = new Order(orderId, customerId, new Date());
        
        while (true) {
            System.out.print("Enter Product # to add (0 to finish): ");
            int num = getUserIntInput();
            if (num == 0) break;
            if (num > 0 && num <= all.size()) {
                Product p = all.get(num-1);
                if (p.getStock() > 0) {
                    order.addProductToOrder(p);
                    p.setStock(p.getStock()-1); // Auto-update stock
                    System.out.println("Added " + p.getName());
                } else System.out.println("Out of stock!");
            }
        }
        if (!order.getProducts().isEmpty()) {
            system.placeNewOrder(customerId, order);
            System.out.println("Order Placed! Total: " + order.getTotalPrice());
        } else System.out.println("Order canceled.");
    }

    /** [PHASE 1] Requirement: Cancel Order */
    private static void handleCancelOrder() {
        System.out.print("Enter Order ID to Cancel: ");
        if (system.cancelOrder(scanner.nextLine())) System.out.println("Order Canceled.");
        else System.out.println("Order not found.");
    }

    /** [PHASE 1] Requirement: Update Order Status */
    private static void handleUpdateOrderStatus() {
        System.out.print("Enter Order ID: ");
        Order o = system.findOrderById(scanner.nextLine());
        if (o != null) {
            System.out.print("Enter New Status: ");
            o.updateStatus(scanner.nextLine());
            System.out.println("Status Updated.");
        } else System.out.println("Order not found.");
    }

    /** [PHASE 1] Requirement: Search Order by ID */
    private static void handleSearchOrderById() {
        System.out.print("Enter Order ID: ");
        Order o = system.findOrderById(scanner.nextLine());
        if (o != null) System.out.println("Order Found: " + o.getOrderId() + " | Status: " + o.getStatus());
        else System.out.println("Not found.");
    }

    private static void handleViewAllOrders() {
        System.out.println("--- All Orders ---");
        MyLinkedList<Order> list = system.getAllOrders();
        for(int i=0; i<list.size(); i++) {
            Order o = list.get(i);
            System.out.println("- Order " + o.getOrderId() + " | Date: " + dateFormatter.format(o.getOrderDate()) + " | Total: " + o.getTotalPrice());
        }
    }

    /** [PHASE 1] Requirement: View Order History */
    private static void handleViewCustomerOrders() {
        System.out.print("Enter Customer ID: ");
        Customer c = system.findCustomerById(scanner.nextLine());
        if (c != null) {
            MyLinkedList<Order> orders = c.getOrderHistory();
            System.out.println("Orders for " + c.getName() + ":");
            for(int i=0; i<orders.size(); i++) {
                Order o = orders.get(i);
                System.out.println("-------------------------------------" +
                                   "\n- Order ID: " + o.getOrderId() + 
                                   "\n  Date: " + dateFormatter.format(o.getOrderDate()) +
                                   "\n  Status: " + o.getStatus() + 
                                   "\n  Total: " + o.getTotalPrice());
                System.out.println("  Items:");
                MyLinkedList<Product> items = o.getProducts();
                for(int j=0; j<items.size(); j++) System.out.println("    - " + items.get(j).getName());
            }
        } else System.out.println("Customer not found.");
    }

    // --- 3. REPORTS & QUERIES ---

    /** [PHASE 1] Requirement: Add Review */
    private static void handleAddReview() {
        System.out.print("Enter Product ID: ");
        Product p = system.findProductById(scanner.nextLine());
        if (p == null) { System.out.println("Product not found."); return; }
        System.out.print("Enter Customer ID: ");
        if (system.findCustomerById(scanner.nextLine()) == null) { System.out.println("Customer not found."); return; }
        
        System.out.print("Rating (1-5): ");
        int rating = getUserIntInput();
        System.out.print("Comment: ");
        String comment = scanner.nextLine();
        
        p.addReview(new Review("temp", rating, comment)); 
        System.out.println("Review Added.");
    }

    /** [PHASE 1] Requirement: Edit Review */
    private static void handleEditReview() {
        System.out.println("--- Edit Review ---");
        System.out.print("Product ID: ");
        Product p = system.findProductById(scanner.nextLine());
        if(p == null) { System.out.println("Not found."); return; }
        System.out.print("Your Customer ID: ");
        String cid = scanner.nextLine();
        System.out.print("New Rating: ");
        int rating = getUserIntInput();
        System.out.print("New Comment: ");
        String comment = scanner.nextLine();
        if (p.editReview(cid, comment, rating)) System.out.println("Updated.");
        else System.out.println("Review not found.");
    }

    /** [PHASE 2] Requirement: Display Customers Who Reviewed a Product */
    private static void handleListProductReviews() {
        System.out.print("Enter Product ID: ");
        Product p = system.findProductById(scanner.nextLine());
        if (p != null) {
            MyLinkedList<Review> reviews = p.getReviews();
            System.out.println("Customers who reviewed '" + p.getName() + "':");
            for(int i=0; i<reviews.size(); i++) {
                System.out.println("- Rating: " + reviews.get(i).getRatingScore() + " | " + reviews.get(i).getTextComment());
            }
        } else System.out.println("Product not found.");
    }

    /** [PHASE 1] Requirement: Find Reviews by Customer */
    private static void handleFindReviewsByCustomer() {
        System.out.print("Enter Customer ID: ");
        MyLinkedList<Review> reviews = system.extractCustomerReviews(scanner.nextLine());
        for(int i=0; i<reviews.size(); i++) System.out.println("- " + reviews.get(i).getTextComment());
    }

    /** [PHASE 2] Requirement: Range Query by Price */
    private static void handlePriceRangeQuery() {
        System.out.println("--- Search Products by Price Range ---");
        System.out.print("Enter Min Price: ");
        double min = 0; try { min = scanner.nextDouble(); } catch(Exception e){} scanner.nextLine();
        System.out.print("Enter Max Price: ");
        double max = 0; try { max = scanner.nextDouble(); } catch(Exception e){} scanner.nextLine();

        MyLinkedList<Product> results = system.getProductsByPriceRange(min, max);
        if (results.isEmpty()) System.out.println("No products found.");
        else {
            for (int i = 0; i < results.size(); i++) {
                System.out.println("- " + results.get(i).getName() + " (" + results.get(i).getPrice() + ")");
            }
        }
    }

    /** [PHASE 1] Requirement: Top 3 Products */
    private static void handleListTop3() {
        System.out.println("--- Top 3 Products ---");
        MyLinkedList<Product> top = system.getTop3Products();
        for(int i=0; i<top.size(); i++) System.out.println((i+1) + ". " + top.get(i).getName() + " (" + top.get(i).getAverageRating() + ")");
    }

    /** [PHASE 1] Requirement: Common Products */
    private static void handleFindCommonProducts() {
        System.out.print("Cust 1 ID: ");
        String c1 = scanner.nextLine();
        System.out.print("Cust 2 ID: ");
        String c2 = scanner.nextLine();
        MyLinkedList<Product> common = system.getCommonReviewedProducts(c1, c2);
        for(int i=0; i<common.size(); i++) System.out.println("- " + common.get(i).getName());
    }

    /** [PHASE 1] Requirement: Orders Between Dates */
    private static void handleOrdersBetweenDates() {
        Date start = parseDateFromUser("Start Date");
        Date end = parseDateFromUser("End Date");
        MyLinkedList<Order> list = system.getOrdersBetweenDates(start, end);
        for(int i=0; i<list.size(); i++) System.out.println("- Order " + list.get(i).getOrderId() + " | Date: " + dateFormatter.format(list.get(i).getOrderDate()));
    }
    
    private static void handleViewAllProducts() {
        System.out.println("--- All Products ---");
        MyLinkedList<Product> all = system.getAllProducts();
        for(int i=0; i<all.size(); i++) {
            Product p = all.get(i);
            System.out.println("- " + p.getName() + " | ID: " + p.getProductId() + " | Price: " + p.getPrice());
        }
    }
    /**
     * Requirement: "Search Customer: via BST/AVL"
     */
    private static void handleSearchCustomerById() {
        System.out.println("--- Search Customer by ID ---");
        System.out.print("Enter Customer ID: ");
        String id = scanner.nextLine();
        
        // Calls BST Search (O(log n))
        Customer c = system.findCustomerById(id);
        
        if (c != null) {
            System.out.println("--- Customer Found ---");
            System.out.println("  ID: " + c.getCustomerId());
            System.out.println("  Name: " + c.getName());
            System.out.println("  Email: " + c.getEmail());
        } else {
            System.out.println("ERROR: Customer ID not found.");
        }
    }
}
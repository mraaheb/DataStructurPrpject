import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;

/**
 * CSC 212 Project - Phase 2
 * Class: ECommerceSystem (The Logic Layer)
 * * This class manages all data (Products, Customers, Orders) using BSTs.
 * It handles File I/O, searching, insertion, and complex queries.
 */
public class ECommerceSystem {

    // ==========================================================
    // --- ATTRIBUTES (Data Storage) ---
    // ==========================================================
    
    // [PHASE 2 Requirement]: Use BST instead of Linear List
    private BST<Product> allProducts;   // Stores Products sorted by ID
    private BST<Customer> allCustomers; // Stores Customers sorted by ID
    private BST<Order> allOrders;       // Stores Orders sorted by ID

    // --- ID Counters (Auto-Increment) ---
    private int orderCounter = 501;
    private int productCounter = 151;
    private int customerCounter = 231;
    private int reviewCounter = 501;

    /**
     * Constructor: Initializes the BSTs.
     * Time Complexity: O(1)
     */
    public ECommerceSystem() {
        this.allProducts = new BST<>();
        this.allCustomers = new BST<>();
        this.allOrders = new BST<>();
    }

    // ==========================================================
    // --- HELPER METHODS (ID Generation & List Conversion) ---
    // ==========================================================

    public String getNewOrderId() { return String.valueOf(orderCounter++); }
    public String getNewProductId() { return String.valueOf(productCounter++); }
    public String getNewCustomerId() { return String.valueOf(customerCounter++); }
    public String getNewReviewId() { return String.valueOf(reviewCounter++); }

    /**
     * Converts BST to List (In-Order Traversal).
     * Time Complexity: O(N) - Must visit every node.
     */
    public MyLinkedList<Product> getAllProducts() {
        return allProducts.getAllElements(); 
    }
    
    public MyLinkedList<Customer> getAllCustomers() {
        return allCustomers.getAllElements(); 
    }
    
    public MyLinkedList<Order> getAllOrders() {
        return allOrders.getAllElements(); 
    }

    // ==========================================================
    // --- SECTION 1: FILE I/O (Reading Data) ---
    // ==========================================================
    
    /**
     * Reads data from CSV files and populates the BSTs.
     * Time Complexity: O(N log N) for N records (N inserts * log N per insert).
     */
    public void readDataFromCSV(String productsFile, String customersFile, String ordersFile, String reviewsFile) {
        
        // 1. Read Customers -> Insert into BST
        try (Scanner scanner = new Scanner(new File(customersFile))) {
            scanner.nextLine(); 
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(",");
                Customer c = new Customer(data[0].trim(), data[1].trim(), data[2].trim());
                allCustomers.insert(c); // O(log N)
            }
            System.out.println("Loaded Customers into BST.");
        } catch (FileNotFoundException e) {
            System.err.println("Error reading customers.csv: " + e.getMessage());
        }

        // 2. Read Products -> Insert into BST
        try (Scanner scanner = new Scanner(new File(productsFile))) {
            scanner.nextLine(); 
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(",");
                Product p = new Product(
                    data[0].trim(),
                    data[1].trim(),
                    Double.parseDouble(data[2].trim()),
                    Integer.parseInt(data[3].trim())
                );
                allProducts.insert(p); // O(log N)
            }
            System.out.println("Loaded Products into BST.");
        } catch (FileNotFoundException e) {
            System.err.println("Error reading prodcuts.csv: " + e.getMessage());
        }

        // 3. Read Reviews -> Link to Product (Search BST)
        try (Scanner scanner = new Scanner(new File(reviewsFile))) {
            scanner.nextLine(); 
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(",", 5);
                
                String productId = data[1].trim();
                String customerId = data[2].trim();
                int rating = Integer.parseInt(data[3].trim());
                String comment = data[4].trim();
                if (comment.startsWith("\"") && comment.endsWith("\"")) {
                    comment = comment.substring(1, comment.length() - 1);
                }

                Review r = new Review(customerId, rating, comment);
                Product p = this.findProductById(productId); // O(log N)
                if (p != null) {
                    p.addReview(r); // O(1)
                }
            }
            System.out.println("Loaded reviews.");
        } catch (FileNotFoundException e) {
            System.err.println("Error reading reviews.csv: " + e.getMessage());
        }

        // 4. Read Orders -> Insert into BST
        try (Scanner scanner = new Scanner(new File(ordersFile))) {
            scanner.nextLine(); 
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(",");
                
                String orderId = data[0].trim();
                String customerId = data[1].trim();
                String productIdsString = data[2].trim();
                Date orderDate = dateFormat.parse(data[4].trim());
                String status = data[5].trim();
                
                Order order = new Order(orderId, customerId, orderDate);
                order.updateStatus(status);
                
                String[] productIds = productIdsString.split(";");
                for (String pid : productIds) {
                    Product p = this.findProductById(pid.trim()); // O(log N)
                    if (p != null) {
                        order.addProductToOrder(p);
                    }
                }
                this.placeNewOrder(customerId, order); // O(log N)
            }
            System.out.println("Loaded orders.");
        } catch (Exception e) {
            System.err.println("Error reading orders.csv: " + e.getMessage());
        }
    }

    // ==========================================================
    // --- SECTION 2: CORE OPERATIONS (BST Logic) ---
    // ==========================================================

    // --- Product Operations ---
    
    /**
     * Adds a product to the BST.
     * Time Complexity: O(log N) - Standard BST Insertion.
     */
    public void addProduct(Product product) {
        allProducts.insert(product);
    }

    /**
     * Removes a product from the BST.
     * Time Complexity: O(log N) - Standard BST Deletion.
     */
    public void removeProduct(String productId) {
        Product dummy = new Product(productId, "", 0, 0);
        allProducts.delete(dummy);
    }

    /**
     * Searches for a product by ID using BST logic.
     * Time Complexity: O(log N) - Standard BST Search.
     */
    public Product findProductById(String productId) {
        Product dummy = new Product(productId, "", 0, 0);
        return allProducts.search(dummy);
    }
    
    /**
     * Searches for a product by Name.
     * Time Complexity: O(N) - Must traverse all nodes (tree is not sorted by name).
     */
    public Product findProductByName(String name) {
        MyLinkedList<Product> list = getAllProducts();
        for (int i = 0; i < list.size(); i++) {
            Product p = list.get(i);
            if (p.getName().equalsIgnoreCase(name)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Finds all products with 0 stock.
     * Time Complexity: O(N) - Must check every product.
     */
    public MyLinkedList<Product> getOutOfStockProducts() {
        MyLinkedList<Product> outOfStock = new MyLinkedList<>();
        MyLinkedList<Product> list = getAllProducts(); 
        for (int i = 0; i < list.size(); i++) {
            Product p = list.get(i);
            if (p.getStock() == 0) {
                outOfStock.add(p);
            }
        }
        return outOfStock;
    }

    // --- Customer Operations ---

    /**
     * Registers a new customer into the BST.
     * Time Complexity: O(log N)
     */
    public void registerNewCustomer(Customer customer) {
        allCustomers.insert(customer);
    }

    /**
     * Finds a customer by ID using BST logic.
     * Time Complexity: O(log N)
     */
    public Customer findCustomerById(String customerId) {
        Customer dummy = new Customer(customerId, "", "");
        return allCustomers.search(dummy);
    }

    // --- Order Operations ---

    /**
     * Places a new order and stores it in the BST.
     * Time Complexity: O(log N) (to find customer + insert order)
     */
    public boolean placeNewOrder(String customerId, Order order) {
        Customer c = findCustomerById(customerId); // O(log N)
        if (c != null) {
            c.addOrderToHistory(order); // O(1)
            allOrders.insert(order);    // O(log N)
            return true;
        }
        return false;
    }

    /**
     * Finds an order by ID using BST logic.
     * Time Complexity: O(log N)
     */
    public Order findOrderById(String orderId) {
        Order dummy = new Order(orderId, "", new Date());
        return allOrders.search(dummy);
    }
    
    /**
     * Cancels an order by ID.
     * Time Complexity: O(log N) (Search) + O(1) (Update)
     */
    public boolean cancelOrder(String orderId) {
        Order o = findOrderById(orderId); // O(log N)
        if (o != null) {
            o.updateStatus("canceled");
            return true;
        }
        return false;
    }

    // ==========================================================
    // --- SECTION 3: COMPLEX QUERIES & REPORTING ---
    // ==========================================================

    /**
     * Extracts reviews for a specific customer.
     * Time Complexity: O(N * R) - N products * R reviews per product.
     */
    public MyLinkedList<Review> extractCustomerReviews(String customerId) {
        MyLinkedList<Review> customerReviews = new MyLinkedList<>();
        MyLinkedList<Product> list = getAllProducts(); 
        
        for (int i = 0; i < list.size(); i++) {
            Product p = list.get(i);
            MyLinkedList<Review> productReviews = p.getReviews();
            for (int j = 0; j < productReviews.size(); j++) {
                Review r = productReviews.get(j);
                if (r.getCustomerId().equals(customerId)) {
                    customerReviews.add(r);
                }
            }
        }
        return customerReviews;
    }

    /**
     * Finds Top 3 Products by average rating.
     * Time Complexity: O(N * R) - Scan all products and calculate avg rating.
     */
    public MyLinkedList<Product> getTop3Products() {
        MyLinkedList<Product> list = getAllProducts();
        Product top1 = null, top2 = null, top3 = null;
        double avg1 = -1, avg2 = -1, avg3 = -1;

        for (int i = 0; i < list.size(); i++) {
            Product p = list.get(i);
            double currentAvg = p.getAverageRating(); 

            if (currentAvg > avg1) {
                top3 = top2; avg3 = avg2;
                top2 = top1; avg2 = avg1;
                top1 = p; avg1 = currentAvg;
            } else if (currentAvg > avg2) {
                top3 = top2; avg3 = avg2;
                top2 = p; avg2 = currentAvg;
            } else if (currentAvg > avg3) {
                top3 = p; avg3 = currentAvg;
            }
        }
        MyLinkedList<Product> top3List = new MyLinkedList<>();
        if (top1 != null) top3List.add(top1);
        if (top2 != null) top3List.add(top2);
        if (top3 != null) top3List.add(top3);
        return top3List;
    }

    /**
     * Finds orders within a date range.
     * Time Complexity: O(N) - Must scan all orders (BST not sorted by date).
     */
    public MyLinkedList<Order> getOrdersBetweenDates(Date startDate, Date endDate) {
        MyLinkedList<Order> result = new MyLinkedList<>();
        MyLinkedList<Order> list = getAllOrders(); 
        
        for (int i = 0; i < list.size(); i++) {
            Order order = list.get(i);
            Date orderDate = order.getOrderDate();
            if (orderDate.after(startDate) && orderDate.before(endDate)) {
                result.add(order);
            }
        }
        return result;
    }

    /**
     * Finds common products reviewed by two customers > 4.0 rating.
     * Time Complexity: O(N * R)
     */
    public MyLinkedList<Product> getCommonReviewedProducts(String customerId1, String customerId2) {
        MyLinkedList<Product> finalResult = new MyLinkedList<>();
        MyLinkedList<Product> list = getAllProducts(); 
        
        for (int i = 0; i < list.size(); i++) {
            Product p = list.get(i);
            if (p.getAverageRating() > 4.0) {
                boolean customer1Reviewed = false;
                boolean customer2Reviewed = false;
                MyLinkedList<Review> reviews = p.getReviews();
                for (int j = 0; j < reviews.size(); j++) {
                    String reviewerId = reviews.get(j).getCustomerId();
                    if (reviewerId.equals(customerId1)) customer1Reviewed = true;
                    if (reviewerId.equals(customerId2)) customer2Reviewed = true;
                    if (customer1Reviewed && customer2Reviewed) break;
                }
                if (customer1Reviewed && customer2Reviewed) {
                    finalResult.add(p);
                }
            }
        }
        return finalResult;
    }
    
    // ==========================================================
    // --- SECTION 4: PHASE 2 NEW ADVANCED QUERIES ---
    // ==========================================================
    
    /**
     * Requirement: "Range Query by Price"
     * Time Complexity: O(N) - In-Order Traversal then linear scan.
     */
    public MyLinkedList<Product> getProductsByPriceRange(double minPrice, double maxPrice) {
        MyLinkedList<Product> result = new MyLinkedList<>();
        MyLinkedList<Product> all = getAllProducts(); 
        
        for (int i = 0; i < all.size(); i++) {
            Product p = all.get(i);
            if (p.getPrice() >= minPrice && p.getPrice() <= maxPrice) {
                result.add(p);
            }
        }
        return result;
    }
    
    /**
     * Requirement: "List All Customers Sorted"
     * Time Complexity: O(N) - In-Order Traversal of BST returns sorted data.
     */
    public MyLinkedList<Customer> getCustomersSorted() {
        return getAllCustomers(); // BST inherently sorts by ID
    }
    
    // --- Additional Helper Queries ---
    
    public Product getHighestPriceProduct() {
        MyLinkedList<Product> list = getAllProducts();
        Product maxP = null;
        double maxPrice = -1;
        for (int i = 0; i < list.size(); i++) {
            Product p = list.get(i);
            if (p.getPrice() > maxPrice) {
                maxPrice = p.getPrice();
                maxP = p;
            }
        }
        return maxP;
    }
    /**
     * [Phase 2 Requirement]: "List All Customers Sorted Alphabetically"
     * Manually sorts customers by Name using Bubble Sort.
     * Time Complexity: O(N^2)
     */
    public MyLinkedList<Customer> getCustomersSortedByName() {
        // 1. Get all customers (currently sorted by ID from BST)
        MyLinkedList<Customer> list = getAllCustomers();
        int n = list.size();
        
        // 2. Convert to array for easier sorting
        Customer[] arr = new Customer[n];
        for(int i=0; i<n; i++) arr[i] = list.get(i);
        
        // 3. Bubble Sort by Name
        for (int i = 0; i < n-1; i++) {
            for (int j = 0; j < n-i-1; j++) {
                if (arr[j].getName().compareToIgnoreCase(arr[j+1].getName()) > 0) {
                    // Swap
                    Customer temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
        }
        
        // 4. Convert back to LinkedList
        MyLinkedList<Customer> sortedList = new MyLinkedList<>();
        for(Customer c : arr) sortedList.add(c);
        
        return sortedList;
    }
}